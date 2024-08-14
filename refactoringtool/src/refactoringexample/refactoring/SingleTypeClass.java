package refactoringexample.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;

public class SingleTypeClass {
public void singleTypeClass(TypeDeclaration types,AST ast,CompilationUnit root) {
	List<TypeDeclaration> typesList=new ArrayList<TypeDeclaration>();
	getType(root, typesList);
	for(TypeDeclaration temp:typesList) {
		List<MethodDeclaration> methodList=new ArrayList<MethodDeclaration>();
		List<String> mnameString=new ArrayList<String>();
		List<MethodInvocation> compareMethodInvocation=new ArrayList<MethodInvocation>();
		getMethod(temp, methodList);
		for(MethodDeclaration mtemp:methodList) {
			if(mtemp.getName().toString().equals(types.getName().toString())) {	
			}else {
				mnameString.add(mtemp.getName().toString());
			}
		}
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
		if(!compareMethodInvocation.isEmpty()) {
//			System.out.println(compareMethodInvocation);
			for(int q=0;q<compareMethodInvocation.size();q++) {
				for(int w=compareMethodInvocation.size()-1;w>q;w--) {
					if(compareMethodInvocation.get(w).toString().equals(compareMethodInvocation.get(q).toString())) {
						compareMethodInvocation.remove(w);
					}
				}
			}
		}
		if(!compareMethodInvocation.isEmpty()) {
			System.out.println(mnameString);
			System.out.println(temp.getName().toString());
			System.out.println(compareMethodInvocation);
			for(MethodDeclaration mtemp:methodList) {
				if(mtemp.getBody() instanceof Block) {
					Block block=(Block)mtemp.getBody();
					List<ExpressionStatement> statementList=new ArrayList<ExpressionStatement>();
					getExpressionStatement(block, statementList);
					for(ExpressionStatement etemp:statementList) {
						for(int i=0;i<compareMethodInvocation.size();i++) {
							if(etemp.getExpression() instanceof MethodInvocation
									&&etemp.toString().contains(compareMethodInvocation.get(i).toString())
									&&!etemp.toString().contains(".")
									&&etemp.getParent() instanceof Block) {
								System.out.println(etemp);
								MethodInvocation methodInvocation=(MethodInvocation)etemp.getExpression();
								if(methodInvocation.arguments().isEmpty()
				            			&&methodInvocation.toString().contains(compareMethodInvocation.get(i).toString())
				            			&&!methodInvocation.getName().toString().equals(mtemp.getName().toString())) {
									Block eBlock=(Block)etemp.getParent();
									for(int e=0;e<eBlock.statements().size();e++) {
										for(MethodDeclaration intomtemp:methodList) {
											List<String> testList=new ArrayList<String>();
											if(intomtemp.getName().toString().equals(methodInvocation.getName().toString())
			            							  &&intomtemp.parameters().isEmpty()
			            							  &&!intomtemp.getName().toString().equals(mtemp.getName().toString())) {
												 List<Statement> newStatementsList=new ArrayList<Statement>();
												 if(intomtemp.getBody() instanceof Block) {
													  Block newblock=(Block)intomtemp.getBody();
			            							  int size=newblock.statements().size();
			            							  if(size>2) {
			            								  for(int b=0;b<newblock.statements().size();b++) {
			            									  if(newblock.statements().get(b).toString().contains(etemp.toString())) {
			            										  newStatementsList.clear();
//			            										  for(int L=0;L<newStatementsList.size();L++) {
//			            											  newStatementsList.remove(L);
//			            										  }
			            										  break;
			            									  }
			            									  else {
															  newStatementsList.add((Statement) newblock.statements().get(b));
			            									  }
														  }
			            								  if(!newStatementsList.isEmpty()) {
															  eBlock.statements().remove(e);
															  for(int s=0,loc=e;s<newStatementsList.size();s++,loc++) {
							        							  Statement copyStatement=(Statement)ASTNode.copySubtree(ast, newStatementsList.get(s));
							        							  eBlock.statements().add(loc,copyStatement);
							        						  }
															  MethodInvocation newio=ast.newMethodInvocation();
															  newio.setName(ast.newSimpleName("hongshuai"));
															  ExpressionStatement newex=ast.newExpressionStatement(newio);
															  eBlock.statements().add(e,newex);
				            								  }
			            							  }
												 }
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
		
}
private void getType(ASTNode cuu,List<TypeDeclaration> types) {
	cuu.accept(new ASTVisitor() {
		@SuppressWarnings("unchecked")
		public boolean visit(TypeDeclaration node) {
			types.add(node);
			return true;
		}
	});
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
private void getMethodInvocation(ASTNode cuu,List<MethodInvocation> methodInvocaton) {
	cuu.accept(new ASTVisitor() {
		@SuppressWarnings("unchecked")
		public boolean visit(MethodInvocation node) {
			methodInvocaton.add(node);
			return false;
		}
	});
}
private void getExpressionStatement(ASTNode cuu,List<ExpressionStatement> types) {
	cuu.accept(new ASTVisitor() {
		@SuppressWarnings("unchecked")
		public boolean visit(ExpressionStatement node) {
			types.add(node);
			return false;
		}
	});
}
}
