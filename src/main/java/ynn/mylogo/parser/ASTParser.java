package ynn.mylogo.parser;

import static ynn.mylogo.parser.ParserUtil.keywordEquals;

import java.util.ArrayList;
import java.util.List;

import ynn.mylogo.parser.ast.AbstractStatement;
import ynn.mylogo.parser.ast.AbstractValueExpression;
import ynn.mylogo.parser.ast.ActionCallStatement;
import ynn.mylogo.parser.ast.ActionDefinitionStatement;
import ynn.mylogo.parser.ast.ErrorStatement;
import ynn.mylogo.parser.ast.IntegerValueExpression;
import ynn.mylogo.parser.ast.ParameterDefinitionExpression;
import ynn.mylogo.parser.ast.ParameterValueExpression;
import ynn.mylogo.parser.ast.Script;
import ynn.mylogo.parser.ast.StatementListValueExpression;

public class ASTParser {
	
	private final static String KEYWORD_ACTION_DEF_START = "ED";
	private final static String KEYWORD_ACTION_DEF_END = "END";
	
	public Script parseScript(String text) {
		Lexer lexer = new Lexer(text);
		List<AbstractStatement> statements = new ArrayList<AbstractStatement>();
		Token currentToken  = nextNonWhitespaceToken(lexer);
		while (currentToken != null) {
			if (TokenType.WHITESPACE.equals(currentToken.getType())) {
				currentToken = lexer.next();
				continue;
			}
			AbstractStatement statement = parseStatement(lexer);
			statements.add(statement);
			currentToken = lexer.current();
		}
		return new Script(statements);
	}

	private AbstractStatement parseStatement(Lexer lexer) {
		Token currentToken = lexer.current();
		switch (currentToken.getType()) {
			case NAME:
				return parseStatementByName(lexer);
			case PARAMETER:
			case VALUE_INTEGER:
			case SQUARE_BRACKET_OPEN:
			case SQUARE_BRACKET_CLOSE:
			case WHITESPACE:
			default:
				nextNonWhitespaceToken(lexer);
				return new ErrorStatement(currentToken, "A statement is expected");
		}
	}

	private AbstractStatement parseStatementByName(Lexer lexer) {
		Token currentToken = lexer.current();
		if (!TokenType.NAME.equals(currentToken.getType())) {
			throw new IllegalArgumentException("Illegal token type, only name tokens are legal: " + currentToken);
		}
		String name = currentToken.getValue();
		if (ParserUtil.keywordEquals(KEYWORD_ACTION_DEF_START, name)) {
			return parseActionDefinition(lexer);
		} else { // Assume action call 
			return parseActionCall(lexer);
		}
	}

	private ActionDefinitionStatement parseActionDefinition(Lexer lexer) {
		Token currentToken = lexer.current();
		if (!keywordEquals(KEYWORD_ACTION_DEF_START, currentToken.getValue())) {
			throw new IllegalArgumentException("Illegal token value, only 'ED' is accepted: " + currentToken);
		}
		Token actionDefStartToken = currentToken;
		Token actionDefEndToken = null;
		Token actionNameToken = null;
		currentToken = nextNonWhitespaceToken(lexer);
		if (currentToken == null) {
			return new ActionDefinitionStatement(actionDefStartToken, actionNameToken, actionDefEndToken, null, null);
		}
		// Read defined action's name
		if (TokenType.NAME.equals(currentToken.getType())) {
			actionNameToken = currentToken;
			currentToken = nextNonWhitespaceToken(lexer);
		}
		if (currentToken == null) {
			return new ActionDefinitionStatement(actionDefStartToken, actionNameToken, actionDefEndToken, null, null);
		}
		// Read defined action's parameters [0..*]
		List<ParameterDefinitionExpression> parameters = new ArrayList<ParameterDefinitionExpression>();
		while (currentToken != null && TokenType.PARAMETER.equals(currentToken.getType())) {
			ParameterDefinitionExpression paramExp = new ParameterDefinitionExpression(currentToken);
			parameters.add(paramExp);
			currentToken = nextNonWhitespaceToken(lexer);
		}
		if (currentToken == null) {
			return new ActionDefinitionStatement(actionDefStartToken, actionNameToken, actionDefEndToken, parameters, null);
		}
		// Read defined action's body
		List<AbstractStatement> statements = new ArrayList<AbstractStatement>();
		while (currentToken != null) {
			if (TokenType.NAME.equals(currentToken.getType()) && keywordEquals(KEYWORD_ACTION_DEF_END, currentToken.getValue())) {
				actionDefEndToken = currentToken;
				lexer.next();
				break;
			}
			AbstractStatement statement = parseStatement(lexer);
			statements.add(statement);
			currentToken = lexer.current();
		}
		// Return result
		return new ActionDefinitionStatement(actionDefStartToken, actionNameToken, actionDefEndToken, parameters, statements);
	}

	private AbstractStatement parseActionCall(Lexer lexer) {
		Token currentToken = lexer.current();
		if (!TokenType.NAME.equals(currentToken.getType())) {
			throw new IllegalArgumentException("Illegal token type, only name tokens are legal: " + currentToken);
		}
		Token actionToken = currentToken;
		currentToken = nextNonWhitespaceToken(lexer);
		List<AbstractValueExpression> argumentValues = new ArrayList<AbstractValueExpression>();
		boolean stop = false;
		while (!stop && currentToken != null) {
			switch (currentToken.getType()) {
				case NAME:
					stop = true;
					break;
				case PARAMETER:
					ParameterValueExpression pvExp = new ParameterValueExpression(currentToken);
					argumentValues.add(pvExp);
					currentToken = nextNonWhitespaceToken(lexer);
					break;
				case SQUARE_BRACKET_OPEN:
					StatementListValueExpression slvExp = parseStatementListValueExpression(lexer);
					argumentValues.add(slvExp);
					currentToken = lexer.current();
					break;
				case SQUARE_BRACKET_CLOSE:
					stop = true;
					break;
				case VALUE_INTEGER:
					IntegerValueExpression ivExp = new IntegerValueExpression(currentToken);
					argumentValues.add(ivExp);
					currentToken = nextNonWhitespaceToken(lexer);
					break;
				case WHITESPACE:
					// Ignore
					currentToken = nextNonWhitespaceToken(lexer);
					continue;
				case UNKNOWN:
					stop = true;
					break;
			}
		}
		return new ActionCallStatement(actionToken, argumentValues);
	}

	private StatementListValueExpression parseStatementListValueExpression(Lexer lexer) {
		Token currentToken = lexer.current();
		if (!TokenType.SQUARE_BRACKET_OPEN.equals(currentToken.getType())) {
			throw new IllegalArgumentException("Illegal token type, only open square bracket tokens are legal: " + currentToken);
		}
		Token openBracketToken = currentToken;
		Token closeBracketToken = null;
		List<AbstractStatement> statements = new ArrayList<AbstractStatement>();
		boolean stop = false;
		currentToken = lexer.next();
		while (!stop && currentToken != null) {
			switch (currentToken.getType()) {
				case NAME:
					AbstractStatement statement = parseStatement(lexer);
					statements.add(statement);
					currentToken = lexer.current();
					break;
				case SQUARE_BRACKET_CLOSE:
					closeBracketToken = currentToken;
					currentToken = nextNonWhitespaceToken(lexer);
					stop = true;
					break;
				case PARAMETER:
				case SQUARE_BRACKET_OPEN:
				case VALUE_INTEGER:
				case UNKNOWN:
					ErrorStatement errStatement = new ErrorStatement(currentToken, "A statement is expected");
					statements.add(errStatement);
					currentToken = nextNonWhitespaceToken(lexer);
					break;
				case WHITESPACE:
					// Ignore
					currentToken = nextNonWhitespaceToken(lexer);
					continue;
			}
		}
		return new StatementListValueExpression(openBracketToken, closeBracketToken, statements);
	}

	private Token nextNonWhitespaceToken(Lexer lexer) {
		Token currentToken;
		do { // Read tokens until the first non-whitespace token
			currentToken = lexer.next();
		} while (currentToken != null && TokenType.WHITESPACE.equals(currentToken.getType()));
		return currentToken;
	}
	
}
