package refactoringexample.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;

public class SingleClassTest {
public void singclass(TypeDeclaration types,AST ast) {
	List<MethodDeclaration> methodList=new ArrayList<MethodDeclaration>();
	List<String> mnameString=new ArrayList<String>();
	List<MethodInvocation> compareMethodInvocation=new ArrayList<MethodInvocation>();
	List<IfStatement> ifList=new ArrayList<IfStatement>();
	List<TryStatement> tryList=new ArrayList<TryStatement>();
	List<WhileStatement> whileList=new ArrayList<WhileStatement>();
	getMethod(types, methodList);
	for(MethodDeclaration mtemp:methodList) {
		if(mtemp.getName().toString().equals(types.getName().toString())) {
			
		}else {
			mnameString.add(mtemp.getName().toString());
		}
	}
	/*存储types中全部方法名*/
	
	for(MethodDeclaration mtemp:methodList) {
		List<MethodInvocation> methodInvocationList=new ArrayList<MethodInvocation>();
		getMethodInvocation(mtemp, methodInvocationList);
		for(MethodInvocation mItemp:methodInvocationList) {
			for(int i=0;i<mnameString.size();i++) {
				if(mItemp.getName().toString().equals(mnameString.get(i).toString())
						&&mItemp.arguments().isEmpty()
						&&!mItemp.toString().contains(".")
						&&!mItemp.getName().toString().equals(mtemp.getName().toString())) {
					 if(mItemp.getParent() instanceof ExpressionStatement) {
							 compareMethodInvocation.add(mItemp);
					 }
				}
			}
		}
	}
	
	/*存储method中全部和方法名相同的方法调用*/

	
	
	if(!compareMethodInvocation.isEmpty()) {
//		System.out.println(compareMethodInvocation);
		for(int q=0;q<compareMethodInvocation.size();q++) {
			for(int w=compareMethodInvocation.size()-1;w>q;w--) {
				if(compareMethodInvocation.get(w).toString().equals(compareMethodInvocation.get(q).toString())) {
					compareMethodInvocation.remove(w);
				}
			}
		}
		//消除compareMethodInvocation中的重复项
		System.out.println(compareMethodInvocation);
//		System.out.println(types.getName().toString());
		for(MethodDeclaration mtemp:methodList) {
			if(mtemp.getBody() instanceof Block) {
				Block block=(Block)mtemp.getBody();
				for(int k=0;k<block.statements().size();k++) {
					for(int i=0;i<compareMethodInvocation.size();i++) {
						if(block.statements().get(k) instanceof ExpressionStatement
								&&block.statements().get(k).toString().contains(compareMethodInvocation.get(i).toString())
								&&!block.statements().get(k).toString().contains(".")) {
							  ExpressionStatement expressionStatement=(ExpressionStatement)block.statements().get(k);
							  if(expressionStatement.getExpression() instanceof MethodInvocation) {
								  MethodInvocation methodInvocation=(MethodInvocation)expressionStatement.getExpression();
								  if(methodInvocation.arguments().isEmpty()
										  &&methodInvocation.toString().contains(compareMethodInvocation.get(i).toString())
										  &&!methodInvocation.getName().toString().equals(mtemp.getName().toString())) {
//									   System.out.println(methodInvocation);
//									   System.out.println(compareMethodInvocation);
									  for(MethodDeclaration intomtemp:methodList) {
										  if(intomtemp.getName().toString().equals(compareMethodInvocation.get(i).getName().toString())
												  &&intomtemp.parameters().isEmpty()) {
											  List<Statement> newStatementsList=new ArrayList<Statement>();
											  if(intomtemp.getBody() instanceof Block) {
												  Block newblock=(Block)intomtemp.getBody();
												  int size=newblock.statements().size();
												  if(size>2) {
												  for(int b=0;b<newblock.statements().size();b++) {
													  newStatementsList.add((Statement) newblock.statements().get(b));
												  }
												  block.statements().remove(k);
												  for(int s=0,loc=k;s<newStatementsList.size();s++,loc++) {
				        							  Statement copyStatement=(Statement)ASTNode.copySubtree(ast, newStatementsList.get(s));
				        							  block.statements().add(loc,copyStatement);
				        						  }
											  }
											  }
											  
											 
										  }
									  }
								  }
							  }
							   
						}
						
						
						
						
//						else if(block.statements().get(k) instanceof IfStatement
//								&&block.statements().get(k).toString().contains(compareMethodInvocation.get(i).toString())) {
//							   IfStatement ifStatement=(IfStatement)block.statements().get(k);
//							   if(ifStatement.getThenStatement() instanceof Block) {
//								   Block ifBlock=(Block)ifStatement.getThenStatement();
//								   for(int b=0;b<ifBlock.statements().size();b++) {
//									   
//								   }
//							   }
//							   
//						}
						
//						getIf(block, ifList);
//						for(IfStatement iftemp:ifList) {
//							 if(iftemp.getThenStatement() instanceof Block) {
//								 Block ifBlock=(Block)iftemp.getThenStatement();
//								 for(int f=0;f<ifBlock.statements().size();f++) {
//									 if(ifBlock.statements().get(f) instanceof ExpressionStatement
//											 &&ifBlock.statements().get(f).toString().contains(compareMethodInvocation.get(i).toString())
//											 &&!ifBlock.statements().get(f).toString().contains(".")) {
//										 ExpressionStatement expressionStatement=(ExpressionStatement)ifBlock.statements().get(f);
//										  if(expressionStatement.getExpression() instanceof MethodInvocation) {
//											  MethodInvocation methodInvocation=(MethodInvocation)expressionStatement.getExpression();
//											  if(methodInvocation.arguments().isEmpty()
//													  &&methodInvocation.toString().contains(compareMethodInvocation.get(i).toString())
//													  &&!methodInvocation.getName().toString().equals(mtemp.getName().toString())) {
////												   System.out.println(methodInvocation);
////												   System.out.println(compareMethodInvocation);
//												  
//												  
//												  for(MethodDeclaration intomtemp:methodList) {
//													  if(intomtemp.getName().toString().equals(compareMethodInvocation.get(i).getName().toString())
//															  &&intomtemp.parameters().isEmpty()) {
//														  List<Statement> newStatementsList=new ArrayList<Statement>();
//														  if(intomtemp.getBody() instanceof Block) {
//															  Block newblock=(Block)intomtemp.getBody();
//															  int size=newblock.statements().size();
//															  if(size>2) {
//															  for(int b=0;b<newblock.statements().size();b++) {
//																  newStatementsList.add((Statement) newblock.statements().get(b));
//															  }
//															  ifBlock.statements().remove(f);
//															  for(int s=0,loc=f;s<newStatementsList.size();s++,loc++) {
//							        							  Statement copyStatement=(Statement)ASTNode.copySubtree(ast, newStatementsList.get(s));
//							        							  ifBlock.statements().add(loc,copyStatement);
//							        						  }
//														  }
//														  }
//														  
//														 
//													  }
//												  }
//											  }
//										  }
//									 }
//								 }
//							 }
//						}
//						
//						gettry(block, tryList);
//						for(TryStatement trytemp:tryList) {
//							 if(trytemp.getBody() instanceof Block) {
//								 Block tryBlock=(Block)trytemp.getBody();
//								 for(int t=0;t<tryBlock.statements().size();t++) {
//									 if(tryBlock.statements().get(t)instanceof ExpressionStatement
//											 &&tryBlock.statements().get(t).toString().contains(compareMethodInvocation.get(i).toString())
//											 &&!tryBlock.statements().get(t).toString().contains(".")) {
//										 
//									 }
//								 }
//							 }
//						}
//						getwhile(block, whileList);
						
						
						
					}
				}
			}
		}
//		System.out.println(types);
		
	}
}
private void getMethod(ASTNode cuu,List<MethodDeclaration> method) {
	cuu.accept(new ASTVisitor() {
		@SuppressWarnings("unchecked")
		public boolean visit(MethodDeclaration node) {
			method.add(node);
			return false;
		}
	});
}
private void getIf(ASTNode cuu,List<IfStatement> types) {
	cuu.accept(new ASTVisitor() {
		@SuppressWarnings("unchecked")
		public boolean visit(IfStatement node) {
			types.add(node);
			return false;
		}
	});
}
private void gettry(ASTNode cuu,List<TryStatement> trynode) {
	cuu.accept(new ASTVisitor() {
		@SuppressWarnings("unchecked")
		public boolean visit(TryStatement node) {
			trynode.add(node);
			return false;
		}
	});
}
private void getwhile(ASTNode cuu,List<WhileStatement> whilenode) {
	cuu.accept(new ASTVisitor() {
		@SuppressWarnings("unchecked")
		public boolean visit(WhileStatement node) {
			whilenode.add(node);
			return false;
		}
	});
}
private void getMethodInvocation(ASTNode cuu,List<MethodInvocation> methodInvocaton) {
	cuu.accept(new ASTVisitor() {
		@SuppressWarnings("unchecked")
		public boolean visit(MethodInvocation node) {
			methodInvocaton.add(node);
			return false;
		}
	});
}
}
