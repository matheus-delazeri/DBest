package gui.frames.forms.operations.binary;

import java.util.List;

import com.mxgraph.model.mxCell;

import controller.MainController;
import entities.cells.Cell;
import entities.cells.OperationCell;
import exceptions.TreeException;
import gui.frames.forms.operations.IOperator;
import sgbd.query.Operator;
import sgbd.query.binaryop.joins.BlockNestedLoopJoin;

public class CartesianProduct implements IOperator {

	public CartesianProduct() {
		
	}
	
	public CartesianProduct(mxCell jCell) {

		executeOperation(jCell, null);

	}

	public void executeOperation(mxCell jCell, List<String> data) {
		
		OperationCell cell = (OperationCell) Cell.getCells().get(jCell);
		
		try {
		
			if (!cell.hasParents() || cell.getParents().size() != 2 || cell.hasParentErrors()) {
				
				throw new TreeException();
				
			}
			
			Cell parentCell1 = cell.getParents().get(0);
			Cell parentCell2 = cell.getParents().get(1);
			
			Operator table1 = parentCell1.getOperator();
			Operator table2 = parentCell2.getOperator();
	
			Operator operator = new BlockNestedLoopJoin(table1, table2, (t1, t2) -> {
				return true;
			});
	
			cell.setColumns(List.of(parentCell1.getColumns(), parentCell2.getColumns()),
					operator.getContentInfo().values());
			cell.setOperator(operator);
			cell.setName(parentCell1.getName() + " X " + parentCell2.getName());
			
			MainController.getGraph().getModel().setValue(jCell, "X");
			
			cell.removeError();
		
		}catch(TreeException e) {
			
			cell.setError();
			
		}

	}
}
