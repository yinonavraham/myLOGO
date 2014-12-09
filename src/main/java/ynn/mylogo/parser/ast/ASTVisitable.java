package ynn.mylogo.parser.ast;

public interface ASTVisitable {
	
	void accept(ASTVisitor visitor);

}
