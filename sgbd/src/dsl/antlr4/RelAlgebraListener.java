package dsl.antlr4;
// Generated from grammar/RelAlgebra.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RelAlgebraParser}.
 */
public interface RelAlgebraListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(RelAlgebraParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(RelAlgebraParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(RelAlgebraParser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(RelAlgebraParser.ImportStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#nameDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterNameDeclaration(RelAlgebraParser.NameDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#nameDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitNameDeclaration(RelAlgebraParser.NameDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#pathStatement}.
	 * @param ctx the parse tree
	 */
	void enterPathStatement(RelAlgebraParser.PathStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#pathStatement}.
	 * @param ctx the parse tree
	 */
	void exitPathStatement(RelAlgebraParser.PathStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(RelAlgebraParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(RelAlgebraParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#createTable}.
	 * @param ctx the parse tree
	 */
	void enterCreateTable(RelAlgebraParser.CreateTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#createTable}.
	 * @param ctx the parse tree
	 */
	void exitCreateTable(RelAlgebraParser.CreateTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(RelAlgebraParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(RelAlgebraParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#position}.
	 * @param ctx the parse tree
	 */
	void enterPosition(RelAlgebraParser.PositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#position}.
	 * @param ctx the parse tree
	 */
	void exitPosition(RelAlgebraParser.PositionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(RelAlgebraParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(RelAlgebraParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#selection}.
	 * @param ctx the parse tree
	 */
	void enterSelection(RelAlgebraParser.SelectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#selection}.
	 * @param ctx the parse tree
	 */
	void exitSelection(RelAlgebraParser.SelectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#projection}.
	 * @param ctx the parse tree
	 */
	void enterProjection(RelAlgebraParser.ProjectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#projection}.
	 * @param ctx the parse tree
	 */
	void exitProjection(RelAlgebraParser.ProjectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#join}.
	 * @param ctx the parse tree
	 */
	void enterJoin(RelAlgebraParser.JoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#join}.
	 * @param ctx the parse tree
	 */
	void exitJoin(RelAlgebraParser.JoinContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#leftJoin}.
	 * @param ctx the parse tree
	 */
	void enterLeftJoin(RelAlgebraParser.LeftJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#leftJoin}.
	 * @param ctx the parse tree
	 */
	void exitLeftJoin(RelAlgebraParser.LeftJoinContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#rightJoin}.
	 * @param ctx the parse tree
	 */
	void enterRightJoin(RelAlgebraParser.RightJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#rightJoin}.
	 * @param ctx the parse tree
	 */
	void exitRightJoin(RelAlgebraParser.RightJoinContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#cartesianProduct}.
	 * @param ctx the parse tree
	 */
	void enterCartesianProduct(RelAlgebraParser.CartesianProductContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#cartesianProduct}.
	 * @param ctx the parse tree
	 */
	void exitCartesianProduct(RelAlgebraParser.CartesianProductContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelAlgebraParser#union}.
	 * @param ctx the parse tree
	 */
	void enterUnion(RelAlgebraParser.UnionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelAlgebraParser#union}.
	 * @param ctx the parse tree
	 */
	void exitUnion(RelAlgebraParser.UnionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simple}
	 * labeled alternative in {@link RelAlgebraParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterSimple(RelAlgebraParser.SimpleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simple}
	 * labeled alternative in {@link RelAlgebraParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitSimple(RelAlgebraParser.SimpleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nested}
	 * labeled alternative in {@link RelAlgebraParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterNested(RelAlgebraParser.NestedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nested}
	 * labeled alternative in {@link RelAlgebraParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitNested(RelAlgebraParser.NestedContext ctx);
}