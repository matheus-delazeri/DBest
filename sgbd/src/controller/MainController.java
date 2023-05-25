package controller;

import java.awt.Container;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

import dsl.entities.BinaryExpression;
import dsl.entities.OperationExpression;
import dsl.entities.Relation;
import entities.Action.CreateOperationAction;
import entities.Action.CreateTableAction;
import entities.Action.CurrentAction;
import entities.Action.CurrentAction.ActionType;
import entities.Edge;
import entities.Tree;
import entities.buttons.Button;
import entities.buttons.OperationButton;
import entities.cells.Cell;
import entities.cells.OperationCell;
import entities.cells.TableCell;
import entities.utils.CellUtils;
import entities.utils.TreeUtils;
import enums.OperationArity;
import enums.OperationType;
import gui.frames.CellInformationFrame;
import gui.frames.dsl.Console;
import gui.frames.dsl.TextEditor;
import gui.frames.forms.create.FormFrameCreateTable;
import gui.frames.forms.importexport.FormFrameExportTable;
import gui.frames.forms.importexport.FormFrameImportAs;
import gui.frames.main.MainFrame;
import util.Export;
import util.ImportFile;

@SuppressWarnings("serial")
public class MainController extends MainFrame {

	private static Map<Integer, Tree> trees = new HashMap<>();
	private static File lastDirectory = new File("");

	private static final List<String> unaryOp = new ArrayList<>(new ArrayList<>(Arrays.asList(OperationType.values()))
			.stream().filter(operation -> operation.getArity() == OperationArity.UNARY)
			.map(OperationType::getOperationName).toList());
	private static final List<String> binaryOp = new ArrayList<>(new ArrayList<>(Arrays.asList(OperationType.values()))
			.stream().filter(operation -> operation.getArity() == OperationArity.BINARY)
			.map(OperationType::getOperationName).toList());

	private Container textEditor = new TextEditor(this).getContentPane();

	private mxCell jCell;
	private mxCell ghostJCell = null;

	private AtomicReference<CurrentAction> currentActionRef = new AtomicReference<>();
	private AtomicReference<Edge> edgeRef = new AtomicReference<>(new Edge());

	private static Set<Button> buttons = new HashSet<>();

	public static final CreateOperationAction projectionOperation = new CreateOperationAction(
			CurrentAction.ActionType.CREATE_OPERATOR_CELL, OperationType.PROJECTION.getDisplayNameAndSymbol(),
			OperationType.PROJECTION.getDisplayName(), OperationType.PROJECTION);
	public static final CreateOperationAction selectionOperation = new CreateOperationAction(
			CurrentAction.ActionType.CREATE_OPERATOR_CELL, OperationType.SELECTION.getDisplayNameAndSymbol(),
			OperationType.SELECTION.getDisplayName(), OperationType.SELECTION);
	public static final CreateOperationAction joinOperation = new CreateOperationAction(
			CurrentAction.ActionType.CREATE_OPERATOR_CELL, OperationType.JOIN.getDisplayNameAndSymbol(),
			OperationType.JOIN.getDisplayName(), OperationType.JOIN);
	public static final CreateOperationAction leftJoinOperation = new CreateOperationAction(
			CurrentAction.ActionType.CREATE_OPERATOR_CELL, OperationType.LEFT_JOIN.getDisplayNameAndSymbol(),
			OperationType.LEFT_JOIN.getDisplayName(), OperationType.LEFT_JOIN);
	public static final CreateOperationAction rightJoinOperation = new CreateOperationAction(
			CurrentAction.ActionType.CREATE_OPERATOR_CELL, OperationType.RIGHT_JOIN.getDisplayNameAndSymbol(),
			OperationType.RIGHT_JOIN.getDisplayName(), OperationType.RIGHT_JOIN);
	public static final CreateOperationAction cartesianProductOperation = new CreateOperationAction(
			CurrentAction.ActionType.CREATE_OPERATOR_CELL, OperationType.CARTESIAN_PRODUCT.getDisplayNameAndSymbol(),
			OperationType.CARTESIAN_PRODUCT.getDisplayName(), OperationType.CARTESIAN_PRODUCT);
	public static final CreateOperationAction unionOperation = new CreateOperationAction(
			CurrentAction.ActionType.CREATE_OPERATOR_CELL, OperationType.UNION.getDisplayNameAndSymbol(),
			OperationType.UNION.getDisplayName(), OperationType.UNION);

	public MainController() {

		super(buttons);

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {

				File directory = new File(".");
				File[] filesList = directory.listFiles();
				for (File file : filesList) {
					if (file.isFile() && (file.getName().endsWith(".dat") || file.getName().endsWith(".head"))) {
						file.delete();
					}
				}

				System.exit(0);

			}

		});

	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void actionPerformed(ActionEvent e) {

		graph.removeCells(new Object[] { ghostJCell }, true);
		ghostJCell = null;

		Button btnClicked = buttons.stream().filter(x -> x.getButton() == e.getSource()).findAny().orElse(null);
		String style = null;

		if (btnClicked != null) {

			edgeRef.get().reset();
			btnClicked.setCurrentAction(currentActionRef);

			switch (currentActionRef.get().getType()) {

			case DELETE_CELL -> CellUtils.deleteCell(jCell);
			case DELETE_ALL -> CellUtils.deleteAllGraph();
			case SAVE_CELL -> exportTable();
			case SHOW_CELL -> CellUtils.showTable(jCell);
			case IMPORT_FILE -> newTable(CurrentAction.ActionType.IMPORT_FILE);
			case CREATE_TABLE -> newTable(CurrentAction.ActionType.CREATE_TABLE);
			case OPEN_CONSOLE -> new Console();
			case OPEN_TEXT_EDITOR -> changeScreen();

			}

			if (btnClicked instanceof OperationButton btnOpClicked) {

				style = btnOpClicked.getStyle();
				((CreateOperationAction) currentActionRef.get()).setParent(null);

			}

		}

		CreateOperationAction opAction = null;

		if (e.getSource() == menuItemShow) {

			CellUtils.showTable(jCell);

		} else if (e.getSource() == menuItemInformations) {

			new CellInformationFrame(jCell);

		} else if (e.getSource() == menuItemExport) {

			new Export(Cell.getCells().get(jCell).getTree());

		} else if (e.getSource() == menuItemEdit) {

			((OperationCell) Cell.getCells().get(jCell)).editOperation(jCell);
			TreeUtils.recalculateContent(Cell.getCells().get(jCell));

		} else if (e.getSource() == menuItemRemove) {

			CellUtils.deleteCell(jCell);

		} else if (e.getSource() == menuItemSelection) {

			opAction = selectionOperation;
			style = OperationType.SELECTION.getDisplayName();

		} else if (e.getSource() == menuItemProjection) {

			opAction = projectionOperation;
			style = OperationType.PROJECTION.getDisplayName();

		} else if (e.getSource() == menuItemJoin) {

			opAction = joinOperation;
			style = OperationType.JOIN.getDisplayName();

		} else if (e.getSource() == menuItemLeftJoin) {

			opAction = leftJoinOperation;
			style = OperationType.LEFT_JOIN.getDisplayName();

		} else if (e.getSource() == menuItemRightJoin) {

			opAction = rightJoinOperation;
			style = OperationType.RIGHT_JOIN.getDisplayName();

		} else if (e.getSource() == menuItemCartesianProduct) {

			opAction = cartesianProductOperation;
			style = OperationType.CARTESIAN_PRODUCT.getDisplayName();

		} else if (e.getSource() == menuItemUnion) {

			opAction = unionOperation;
			style = OperationType.UNION.getDisplayName();

		}

		if (opAction != null) {

			opAction.setParent(jCell);
			currentActionRef.set(opAction);

		}

		if (currentActionRef.get() != null && (opAction != null
				|| (btnClicked != null && currentActionRef.get().getType() == ActionType.CREATE_OPERATOR_CELL))) {

			ghostJCell = (mxCell) graph.insertVertex((mxCell) graph.getDefaultParent(), "ghost", style,
					MouseInfo.getPointerInfo().getLocation().getX() - MainFrame.getGraphComponent().getWidth(),
					MouseInfo.getPointerInfo().getLocation().getY() - MainFrame.getGraphComponent().getHeight(), 80, 30,
					style);
		}
		edgeRef.get().reset();

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		jCell = (mxCell) MainFrame.getGraphComponent().getCellAt(e.getX(), e.getY());

		if (e.getButton() == MouseEvent.BUTTON3 && Cell.getCells().get(jCell) != null) {

			Cell cell = Cell.getCells().get(jCell);

			popupMenuJCell.add(menuItemShow);
			popupMenuJCell.add(menuItemInformations);
			popupMenuJCell.add(menuItemExport);
			popupMenuJCell.add(menuItemEdit);
			popupMenuJCell.add(menuItemOperations);
			popupMenuJCell.add(menuItemRemove);

			if (cell instanceof OperationCell opCell && !opCell.hasForm()) {

				popupMenuJCell.remove(menuItemShow);
				popupMenuJCell.remove(menuItemOperations);
				popupMenuJCell.remove(menuItemEdit);

			}
			if (cell instanceof TableCell || ((OperationCell) cell).getType() == OperationType.CARTESIAN_PRODUCT) {

				popupMenuJCell.remove(menuItemEdit);

			}
			if (cell.hasChild()) {

				popupMenuJCell.remove(menuItemOperations);

			}
			if (cell.hasError()) {

				popupMenuJCell.remove(menuItemShow);
				popupMenuJCell.remove(menuItemOperations);

				if (!cell.hasParents())
					popupMenuJCell.remove(menuItemEdit);

			}
			popupMenuJCell.show(MainFrame.getGraphComponent().getGraphControl(), e.getX(), e.getY());

		}

		ClickController.clicked(currentActionRef, jCell, edgeRef, e, ghostJCell);

		if (Cell.getCells().get(jCell) != null && e.getClickCount() == 2) {

			CellUtils.showTable(jCell);

		}

	}

	private void changeScreen() {

		setContentPane(textEditor);
		revalidate();

	}

	private void newTable(CurrentAction.ActionType action) {

		AtomicReference<Boolean> cancelServiceReference = new AtomicReference<>(false);

		TableCell tableCell = action == CurrentAction.ActionType.CREATE_TABLE
				? new FormFrameCreateTable(cancelServiceReference).getResult()
				: new FormFrameImportAs(cancelServiceReference).getResult();

		if (!cancelServiceReference.get()) {

			currentActionRef.set(new CreateTableAction(action, tableCell.getName(), tableCell.getStyle(), tableCell));

		} else {

			if (tableCell != null)
				TreeUtils.deleteTree(tableCell.getTree());
			currentActionRef.set(null);

		}

	}

	private void exportTable() {

		AtomicReference<Boolean> exitRef = new AtomicReference<>(false);

		if (!Cell.getCells().isEmpty())
			new FormFrameExportTable(exitRef);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_S) {

			if (jCell != null)
				CellUtils.showTable(jCell);

		} else if (e.getKeyCode() == KeyEvent.VK_DELETE) {

			if (jCell != null) {

				CellUtils.deleteCell(jCell);
				currentActionRef.set(null);

			}

		} else if (e.getKeyCode() == KeyEvent.VK_E) {

			currentActionRef.set(new CurrentAction(CurrentAction.ActionType.EDGE));

		} else if (e.getKeyCode() == KeyEvent.VK_I) {

			newTable(CurrentAction.ActionType.IMPORT_FILE);

		} else if (e.getKeyCode() == KeyEvent.VK_X) {

			if (Cell.getCells().size() > 0)
				exportTable();

		} else if (e.getKeyCode() == KeyEvent.VK_C) {

			newTable(CurrentAction.ActionType.CREATE_TABLE);

		} else if (e.getKeyCode() == KeyEvent.VK_L) {

			System.out.println("--------------------------");
			System.out.println("Árvores: ");
			for (Integer i : trees.keySet()) {

				System.out.println(trees.get(i));

			}
			System.out.println();
			System.out.println();

		} else if (e.getKeyCode() == KeyEvent.VK_A) {

			if (jCell != null && Cell.getCells().get(jCell) != null) {

				System.out.println(((OperationCell) Cell.getCells().get(jCell)).getData());

			}

		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if(currentActionRef != null && currentActionRef.get() != null)
			if (currentActionRef.get().getType() == CurrentAction.ActionType.CREATE_OPERATOR_CELL
					&& ghostJCell != null) {
	
				mxGeometry geo = ghostJCell.getGeometry();
				double dx = e.getX() - geo.getCenterX();
				double dy = e.getY() - geo.getCenterY();
				MainFrame.getGraph().moveCells(new Object[] { ghostJCell }, dx, dy);
	
			}else if(currentActionRef.get() instanceof CreateTableAction createTable) {
				
				mxGeometry geo = createTable.getTableCell().getJGraphCell().getGeometry();
				double dx = e.getX() - geo.getCenterX();
				double dy = e.getY() - geo.getCenterY();
				MainFrame.getGraph().moveCells(new Object[] { createTable.getTableCell().getJGraphCell() }, dx, dy);
				
			}

	}

	public static Map<Integer, Tree> getTrees() {
		return trees;
	}

	public static List<String> unaryOperationsName() {
		return Collections.unmodifiableList(unaryOp);
	}

	public static List<String> binaryOperationsName() {
		return Collections.unmodifiableList(binaryOp);
	}

	public static File getLastDirectory() {
		return lastDirectory;
	}

	public static void setLastDirectory(File newLastDirectory) {
		lastDirectory = newLastDirectory;
	}

	public static void putTableCell(Relation relation) {

		int x, y;
		if (relation.getCoordinates().isPresent()) {

			x = relation.getCoordinates().get().x();
			y = relation.getCoordinates().get().y();

		} else {

			x = (int) (Math.random() * 600);
			y = (int) (Math.random() * 600);

		}
		
		mxCell jTableCell = (mxCell) MainFrame.getGraph().insertVertex((mxCell) graph.getDefaultParent(), null,
				relation.getName(), x, y, 80, 30, "tabela");
		
		relation.setCell(new ImportFile(relation.getName() + ".head", jTableCell).getResult());
		
	}

	public static void putOperationCell(OperationExpression operationExpression) {

		int x, y;
		if (operationExpression.getCoordinates().isPresent()) {

			x = operationExpression.getCoordinates().get().x();
			y = operationExpression.getCoordinates().get().y();

		}else {
			
			x = (int) (Math.random() * 600);
			y = (int) (Math.random() * 600);
			
		}
		
		OperationType type = operationExpression.getType();
		
		mxCell jCell = (mxCell) MainFrame.getGraph().insertVertex((mxCell) graph.getDefaultParent(), null,
					type.getDisplayNameAndSymbol(), x, y, 80, 30, type.getDisplayName());

		List<Cell> parents = new ArrayList<>();
		parents.add(operationExpression.getSource().getCell());
		
		if(operationExpression instanceof BinaryExpression binaryExpression) parents.add(binaryExpression.getSource2().getCell());
		
		operationExpression.setCell(new OperationCell(jCell, type, parents, operationExpression.getArguments()));
		
		OperationCell cell = operationExpression.getCell();
		
		cell.setAllNewTrees();

	}

}
