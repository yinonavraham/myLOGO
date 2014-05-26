package ynn.mylogo.parser;

import static ynn.mylogo.parser.ParserUtil.argumentNameEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import ynn.mylogo.model.ActionsRegistry;
import ynn.mylogo.model.designtime.ActionDefinition;
import ynn.mylogo.model.designtime.ArgumentDefinition;
import ynn.mylogo.model.designtime.CompositeActionDefinition;
import ynn.mylogo.model.designtime.impl.ArgumentDefinitionImpl;
import ynn.mylogo.model.designtime.impl.CompositeActionDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.ActionList;
import ynn.mylogo.model.runtime.NumericValue;
import ynn.mylogo.model.runtime.impl.ActionListImpl;
import ynn.mylogo.model.runtime.impl.NumericValueImpl;
import ynn.mylogo.model.runtime.impl.VariableArgumentValueImpl;
import ynn.mylogo.parser.ast.ASTVisitor;
import ynn.mylogo.parser.ast.AbstractNode;
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

public class LogoParser {
	
	private final ActionsRegistry registry;
	
	public LogoParser(ActionsRegistry registry) {
		this.registry = registry;
	}
	
	public ParserResult parse(String text) {
		ASTParser parser = new ASTParser();
		Script script = parser.parseScript(text);
		LogoParserVisitor visitor = new LogoParserVisitor();
		script.accept(visitor);
		List<Action> actions = visitor.getActions();
		List<ErrorEntry> errors = visitor.getErrors();
		ParserResult result = new ParserResult(actions, errors, script);
		return result;
	}
	
	private class LogoParserVisitor implements ASTVisitor {
		
		private final List<Action> actions;
		private final List<ErrorEntry> errors;
		private final Deque<ActionDefinition> actionDefinitionStack;
		private final Deque<Action> actionStack;
		private final Deque<ArgumentDefinition> argumentDefinitionStack;
		private final Deque<List<Action>> actionListStack;

		public LogoParserVisitor() {
			this.actions = new ArrayList<Action>();
			this.errors = new ArrayList<ErrorEntry>();
			this.actionDefinitionStack = new ArrayDeque<ActionDefinition>();
			this.actionStack = new ArrayDeque<Action>();
			this.argumentDefinitionStack = new ArrayDeque<ArgumentDefinition>();
			this.actionListStack = new ArrayDeque<List<Action>>();
		}
		
		public List<Action> getActions() {
			return Collections.unmodifiableList(this.actions);
		}
		
		public List<ErrorEntry> getErrors() {
			return Collections.unmodifiableList(this.errors);
		}

		@Override
		public void visit(Script script) {
			for (AbstractStatement statement : script.getStatements()) {
				statement.accept(this);
			}
		}

		@Override
		public void visit(ActionDefinitionStatement statement) { 
			boolean hasErrors = false;
			Token actionNameToken = statement.getActionNameToken();
			if (actionNameToken == null) {
				errors.add(new ErrorEntry(statement, "Action name is missing"));
				hasErrors = true;
			}
			Token actionDefEndToken = statement.getActionDefEndToken();
			if (actionDefEndToken == null) {
				errors.add(new ErrorEntry(statement, "Action definition END is missing"));
				hasErrors = true;
			}
			if (hasErrors) return;
			int errorsCountBefore = errors.size();
			// Create action definition
			String actionName = statement.getName();
			CompositeActionDefinition actionDef = new CompositeActionDefinitionImpl(actionName);
			actionDefinitionStack.push(actionDef);
			// Add argument definitions
			for (ParameterDefinitionExpression param : statement.getParameters()) {
				param.accept(this);
			}
			// Add sub actions
			actionListStack.add(actionDef.getActions());
			for (AbstractStatement stmnt : statement.getStatements()) {
				stmnt.accept(this);
			}
			actionListStack.pop();
			// Register the new action only if it does not have errors
			int errorsCountAfter = errors.size();
			if (errorsCountAfter == errorsCountBefore) registry.registerAction(actionDef);
			actionDefinitionStack.pop();
		}

		@Override
		public void visit(ParameterDefinitionExpression expression) {
			ArgumentDefinition arg = new ArgumentDefinitionImpl(expression.getName(), NumericValue.class);
			if (!actionDefinitionStack.isEmpty()) actionDefinitionStack.peek().getArguments().add(arg);
			else errors.add(new ErrorEntry(expression, "Parameter definition is not associated to any action"));
		}

		@Override
		public void visit(ErrorStatement statement) {
			errors.add(new ErrorEntry(statement, statement.getMessage()));			
		}

		@Override
		public void visit(ActionCallStatement statement) {
			String actionName = statement.getName();
			ActionDefinition actionDefinition = registry.getActionDefinition(actionName);
			if (actionDefinition == null) {
				errors.add(new ErrorEntry(statement, "Unknown action: " + actionName));
				return;
			}
			Action action = actionDefinition.createRuntimeAction();
			actionStack.push(action);
			List<ArgumentDefinition> arguments = actionDefinition.getArguments();
			List<? extends AbstractValueExpression> argumentValues = statement.getArgumentValues();
			if (arguments.size() != argumentValues.size()) {
				errors.add(new ErrorEntry(statement, "Number of values given to the action is not as expected: " + actionDefinition.getSignature()));
				if (argumentValues.size() > arguments.size()) {
					for (int i = arguments.size(); i < argumentValues.size(); i++) {
						AbstractValueExpression argValue = argumentValues.get(i);
						errors.add(new ErrorEntry(argValue, "Unexpected value for action: " + argValue));
					}
				}
			} else {
				int errorsCountBefore = errors.size();
				int argIndex = 0;
				for (AbstractValueExpression argumentValue : argumentValues) {
					argumentDefinitionStack.push(actionDefinition.getArguments().get(argIndex));
					argumentValue.accept(this);
					argumentDefinitionStack.pop();
					argIndex++;
				}
				int errorsCountAfter = errors.size();
				// Add the action to the actions list only if it does not have errors 
				if (errorsCountAfter == errorsCountBefore) {
					if (actionListStack.isEmpty()) actions.add(action);
					else actionListStack.peek().add(action);
				}
			}
			actionStack.pop();
		}

		@Override
		public void visit(IntegerValueExpression expression) {
			if (actionStack.isEmpty() || argumentDefinitionStack.isEmpty()) {
				errors.add(new ErrorEntry(expression, "Value is not assigned to any action"));
				return;
			}
			if (argumentDefinitionStack.peek().isValueTypeSupported(NumericValue.class)) {
				int value = expression.getValue();
				NumericValue numericValue = new NumericValueImpl(value);
				actionStack.peek().getValues().add(numericValue);
			}
		}

		@Override
		public void visit(StatementListValueExpression expression) {
			boolean hasErrors = false;
			if (actionStack.isEmpty() || argumentDefinitionStack.isEmpty()) {
				errors.add(new ErrorEntry(expression, "Value is not assigned to any action"));
				hasErrors = true;
			}
			if (expression.getCloseBracketToken() == null) {
				errors.add(new ErrorEntry(expression, "A closing bracket is missing"));
				hasErrors = true;
			}
			if (hasErrors) return;
			if (argumentDefinitionStack.peek().isValueTypeSupported(ActionList.class)) {
				List<Action> actionList = new ArrayList<Action>();
				actionListStack.push(actionList);
				for (AbstractStatement statement : expression.getStatements()) {
					statement.accept(this);
				}
				actionListStack.pop();
				ActionList actionListValue = new ActionListImpl(actionList);
				actionStack.peek().getValues().add(actionListValue);
			}
		}

		@Override
		public void visit(ParameterValueExpression expression) {
			boolean hasErrors = false;
			if (actionStack.isEmpty() || argumentDefinitionStack.isEmpty()) {
				errors.add(new ErrorEntry(expression, "Value is not assigned to any action"));
				hasErrors = true;
			}
			// Check the param is being used inside an action definition
			if (actionDefinitionStack.isEmpty()) {
				errors.add(new ErrorEntry(expression, "Parameter is used outside an action definition"));
				hasErrors = true;
			}
			// Exit if there are critical errors
			if (hasErrors) return; 
			// Check the param name used was declared in the action definition 
			String paramName = expression.getName();
			ActionDefinition actionDefinition = actionDefinitionStack.peek();
			boolean argDefFound = false;
			for (ArgumentDefinition arg : actionDefinition.getArguments()) {
				if (argumentNameEquals(arg.getName(), paramName)) {
					argDefFound = true;
					break;
				}
			}
			if (!argDefFound) {
				errors.add(new ErrorEntry(expression, "Parameter is not declared in the scope of the current action definition: " + paramName));
				hasErrors = true;
			}
			// Exit if there are errors
			if (hasErrors) return; 
			Action action = actionStack.peek();
			VariableArgumentValueImpl paramValue = new VariableArgumentValueImpl(paramName);
			action.getValues().add(paramValue);
		}
		
	}
	
	public static class ErrorEntry {
		
		private final AbstractNode astNode;
		private final Token token;
		private final String message;
		
		public ErrorEntry(AbstractNode astNode, Token token, String message) {
			this.astNode = astNode;
			this.token = token;
			this.message = message;
		}
		
		public ErrorEntry(AbstractNode astNode, String message) {
			this(astNode, astNode.getToken(), message);
		}
		
		public AbstractNode getAstNode() {
			return astNode;
		}
		
		public Token getToken() {
			return token;
		}
		
		public String getMessage() {
			return message;
		}
		
		@Override
		public String toString() {
			return String.format("Node=%s, Token=%s, Message=%s", astNode, token, message);
		}
		
	}
	
	public static class ParserResult {
		
		private final List<Action> actions;
		private final List<ErrorEntry> errors;
		private final Script script;
		
		public ParserResult(List<Action> actions, List<ErrorEntry> errors, Script script) {
			this.actions = new ArrayList<Action>(actions);
			this.errors = new ArrayList<LogoParser.ErrorEntry>(errors);
			this.script = script;
		}
		
		public List<Action> getActions() {
			return Collections.unmodifiableList(actions);
		}
		
		public List<ErrorEntry> getErrors() {
			return Collections.unmodifiableList(errors);
		}
		
		public Script getScript() {
			return script;
		}
		
	}
	
	public static void main(String[] args) {
		ActionsRegistry registry = new ActionsRegistry();
		LogoParser parser = new LogoParser(registry);
		ParserResult result = parser.parse("FD 100 ED HELLO :A FD :A & ^^fd END HELLO 90 RT 90 LT 90 [] REPEAT 4 [FD 70 RT 100 REPEAT 10 [BK 100 RT 90 HELLO 10]]");
		System.out.println("Actions:");
		for (Action action : result.getActions()) {
			System.out.println("   " + action);
		}
		System.out.println("Errors:");
		for (ErrorEntry error : result.getErrors()) {
			System.out.println("   " + error);
		}
	}

}
