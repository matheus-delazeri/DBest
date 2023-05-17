package entities;

import javax.swing.JOptionPane;

import com.mxgraph.model.mxCell;

import controller.MainController;
import entities.cells.Cell;
import entities.cells.OperationCell;
import entities.cells.TableCell;
import enums.OperationArity;

public class Edge {

	private mxCell parent;
	private mxCell child;

	public Edge() {
		parent = null;
		child = null;
	}

	public void addParent(mxCell parent) {

		Cell cell = MainController.getCells().get(parent);

		boolean cellHasTree; 
		boolean cellHasError = cell.hasError();
		boolean cellIsParent = cell.hasChild();
		
		if(cell instanceof OperationCell) {

			cellHasTree = ((OperationCell) cell).hasTree();
			
		}else {
			
			cellHasTree = true;
			
		}
		
		if(cellIsParent)
			JOptionPane.showMessageDialog(null, "Essa célula já possui filho", "Erro", JOptionPane.ERROR_MESSAGE);
		
		else if(!cellHasTree)
			JOptionPane.showMessageDialog(null, "Uma operação vazia não pode se associar a ninguém", "Erro", JOptionPane.ERROR_MESSAGE);
	
		else if(cellHasError)
			JOptionPane.showMessageDialog(null, "Não é possível associar uma operação com erros a outra", "Erro", JOptionPane.ERROR_MESSAGE);
	
		else
			this.parent = parent;
		
	}

	public void addChild(mxCell child) {

		Cell cell = MainController.getCells().get(child);

		boolean isOperatorCell = !(cell instanceof TableCell);
		boolean hasEnoughParents;
		
		if(isOperatorCell) {
			
			if(((OperationCell)cell).getArity() == OperationArity.UNARY) {
					
				hasEnoughParents =((OperationCell) cell).getParents().size() >= 1;
					
			}else{
				
				hasEnoughParents =((OperationCell) cell).getParents().size() >= 2;
				
			}
			
		}else {
			
			hasEnoughParents = false;
			
		}

		if (hasParent() && isOperatorCell && !hasEnoughParents)
			this.child = child;

		if (!isOperatorCell)
			JOptionPane.showMessageDialog(null, "Uma tabela não pode ser associada a outra", "Erro", JOptionPane.ERROR_MESSAGE);

		else if (hasEnoughParents && ((OperationCell)cell).getArity() == OperationArity.UNARY)
			JOptionPane.showMessageDialog(null, "Não é possível associar duas tabelas a uma operação unária", "Erro",
					JOptionPane.ERROR_MESSAGE);

		else if (hasEnoughParents && ((OperationCell)cell).getArity() == OperationArity.BINARY)
			JOptionPane.showMessageDialog(null, "Não é possível associar três tabelas a uma operação binária", "Erro",
					JOptionPane.ERROR_MESSAGE);
	
	}

	public Boolean hasParent() {

		return parent != null;

	}

	public boolean isDifferent(mxCell jCell) {

		return hasParent() && jCell != parent;

	}

	public void reset() {
		parent = null;
		child = null;
	}

	public Boolean isReady() {

		return child != null && parent != null;

	}

	public mxCell[] getEdge() {

		return new mxCell[] { parent, child };

	}

	public mxCell getParent() {

		return parent;

	}
	
	@Override
	public String toString() {
		return parent + " " + child;
	}

}
