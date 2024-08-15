package refactoringexample.refactoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class Getif {
public void getif(CompilationUnit root, TypeDeclaration type,Map<String, ArrayList<String>> maplist) {
//	while(!getextends.a) {
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	System.out.println(maplist);
	List<TypeDeclaration> typeDeclarations = new ArrayList<TypeDeclaration>();
	getype(root, typeDeclarations);
	for (TypeDeclaration temp : typeDeclarations) {
	List<MethodDeclaration> methodDeclarations = new ArrayList<MethodDeclaration>();
	getMethod(temp, methodDeclarations);
	for (MethodDeclaration mtemp : methodDeclarations) {
		List<IfStatement> ifStatements = new ArrayList<IfStatement>();
		getif(mtemp, ifStatements);
		for (IfStatement iftemp : ifStatements) {
			if (iftemp.getExpression() instanceof InstanceofExpression) {
//				System.out.println(mtemp.getName().toString());
				InstanceofExpression instanceofExpression = (InstanceofExpression) iftemp.getExpression();
				String lString = instanceofExpression.getLeftOperand().toString();
				String rString = instanceofExpression.getRightOperand().toString();
//				System.out.println("if" + " " + lString + "---" + "instanceof" + "---" + rString);
				if (iftemp.getElseStatement() instanceof IfStatement) {
					IfStatement eIfStatement = (IfStatement) iftemp.getElseStatement();
					while (eIfStatement.getElseStatement() instanceof IfStatement) {
						if (eIfStatement.getExpression() instanceof InstanceofExpression) {
							InstanceofExpression eInstanceofExpression = (InstanceofExpression) eIfStatement
									.getExpression();
							String elString = eInstanceofExpression.getLeftOperand().toString();
							String erString = eInstanceofExpression.getRightOperand().toString();
//							System.out.println("else if" + " " + elString + "---" + "instanceof" + "---" + erString);
						}
						if (eIfStatement.getElseStatement() instanceof IfStatement) {
							eIfStatement = (IfStatement) eIfStatement.getElseStatement();
						} else {
							break;
						}
					}
				}

			}

		}
	}

}
}
private void getype(ASTNode cuu,List<TypeDeclaration> types) {
	cuu.accept(new ASTVisitor() {
		@SuppressWarnings("unchecked")
		public boolean visit(TypeDeclaration node) {
			types.add(node);
			return true;
		}
	});
}
private void getMethod(ASTNode cuu,List<MethodDeclaration> types) {
	cuu.accept(new ASTVisitor() {
		@SuppressWarnings("unchecked")
		public boolean visit(MethodDeclaration node) {
			types.add(node);
			return true;
		}
	});
}
private void getif(ASTNode cuu,List<IfStatement> types) {
	cuu.accept(new ASTVisitor() {
		@SuppressWarnings("unchecked")
		public boolean visit(IfStatement node) {
			types.add(node);
			return false;
		}
	});
}
}
