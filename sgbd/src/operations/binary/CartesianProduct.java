package operations.binary;

import com.mxgraph.model.mxCell;
import entities.cells.Cell;
import entities.cells.OperationCell;
import exceptions.tree.TreeException;
import operations.IOperator;
import operations.Operation;
import operations.OperationErrorVerifier;
import operations.OperationErrorVerifier.ErrorMessage;
import sgbd.query.Operator;
import sgbd.query.binaryop.joins.BlockNestedLoopJoin;

import java.util.List;

public class CartesianProduct implements IOperator {

	public CartesianProduct() {

	}

	public CartesianProduct(mxCell jCell) {

		executeOperation(jCell, null);

	}

	public void executeOperation(mxCell jCell, List<String> arguments) {

		OperationCell cell = (OperationCell) Cell.getCells().get(jCell);

		ErrorMessage error = null;

		try {

			error = ErrorMessage.NO_PARENT;
			OperationErrorVerifier.hasParent(cell);
			
			error = ErrorMessage.NO_TWO_PARENTS;
			OperationErrorVerifier.twoParents(cell);
			
			error = ErrorMessage.PARENT_ERROR;
			OperationErrorVerifier.noParentError(cell);
			
			error = null;
			
		} catch (TreeException e) {

			cell.setError(error);

		}
		
		if(error != null) return;
		
		Cell parentCell1 = cell.getParents().get(0);
		Cell parentCell2 = cell.getParents().get(1);

		Operator table1 = parentCell1.getOperator();
		Operator table2 = parentCell2.getOperator();

		Operator operator = new BlockNestedLoopJoin(table1, table2, (t1, t2) -> {
			return true;
		});

		Operation.operationSetter(cell, "  X  ", List.of(), operator);

	}
}