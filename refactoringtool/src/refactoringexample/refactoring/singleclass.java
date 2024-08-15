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
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;
public class singleclass {
	public void singclass(TypeDeclaration types,AST ast) {
		List<MethodDeclaration> methodList=new ArrayList<MethodDeclaration>();
		List<String> mnameString=new ArrayList<String>();
		List<MethodInvocation> compareMethodInvocation=new ArrayList<MethodInvocation>();
		List<ExpressionStatement> statementList=new ArrayList<ExpressionStatement>();
		getMethod(types, methodList);
		for(MethodDeclaration mtemp:methodList) {
			if(mtemp.getName().toString().equals(types.getName().toString())) {
				
			}else {
				mnameString.add(mtemp.getName().toString());
			}
		}
		/*存储types中全部方法名*/
		if(!methodList.isEmpty()) {
			for(int q=0;q<methodList.size();q++) {
				for(int w=methodList.size()-1;w>q;w--) {
					if(methodList.get(w).getName().toString().equals(methodList.get(q).getName().toString())) {
						methodList.remove(w);
					}
				}
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
		
		/*存储method中全部和方法名相同的方法调用*/

		
		
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
		
		List<String> deletemtemp=new ArrayList<String>();
		if(!compareMethodInvocation.isEmpty()) {
		getMethod(types, methodList);
//		   System.out.println(compareMethodInvocation);
			for(MethodDeclaration mtemp:methodList) {
				if(mtemp.getBody() instanceof Block) {
					Block block=(Block)mtemp.getBody();
					getStatement(block, statementList);
					for(ExpressionStatement etemp:statementList) {				
						for(int i=0;i<compareMethodInvocation.size();i++) {
							if(etemp.toString().contains(compareMethodInvocation.get(i).toString())
									&&!etemp.toString().contains(".")
									&&etemp.getParent() instanceof Block) {
					            if(etemp.getExpression() instanceof MethodInvocation) {
					            	MethodInvocation methodInvocation=(MethodInvocation)etemp.getExpression();
					            	if(methodInvocation.arguments().isEmpty()
					            			&&methodInvocation.toString().contains(compareMethodInvocation.get(i).toString())
					            			&&!methodInvocation.getName().toString().equals(mtemp.getName().toString())) {
//					            	    if(etemp.toString().contains("readNestedParenthesisedTokens")) {
//					            	    	System.out.println(etemp.getParent());
//					            	    }
//					            		System.out.println(etemp);
					            		Block eBlock=(Block)etemp.getParent();
					            		for(int e=0;e<eBlock.statements().size();e++) {
					            			if(eBlock.statements().get(e).toString().equals(etemp.toString())) {
					            				  for(MethodDeclaration intomtemp:methodList) {
					            					  if(intomtemp.getName().toString().equals(methodInvocation.getName().toString())
					            							  &&intomtemp.parameters().isEmpty()
					            							  &&!intomtemp.getName().toString().equals(mtemp.getName().toString())) {
					            						  List<Statement> newStatementsList=new ArrayList<Statement>();
					            						  if(intomtemp.getBody() instanceof Block) {
					            							  Block newblock=(Block)intomtemp.getBody();
					            							  if(newblock.statements().size()>2) {
					            								  for(int b=0;b<newblock.statements().size();b++) {
					            									  if(newblock.statements().get(b).toString().contains(etemp.toString())) {
					            										  newStatementsList.clear();
					            										  break;
					            									  }
					            									  else {
																	  newStatementsList.add((Statement) newblock.statements().get(b));
					            									  }
																  }
					            								  if(intomtemp.modifiers().toString().contains("private")) {
					            									  deletemtemp.add(intomtemp.getName().toString());
					            								  }
//					            								  System.out.println(newStatementsList);
					            								  if(!newStatementsList.isEmpty()) {
																  eBlock.statements().remove(e);
																  if(newStatementsList.get(newStatementsList.size()-1).toString().contains("return")) {
																	  newStatementsList.remove(newStatementsList.size()-1);
																  }
																  for(int s=0,loc=e;s<newStatementsList.size();s++,loc++) {
								        							  Statement copyStatement=(Statement)ASTNode.copySubtree(ast, newStatementsList.get(s));
								        							  eBlock.statements().add(loc,copyStatement);
								        						  }
//																  MethodInvocation newio=ast.newMethodInvocation();
//																  newio.setName(ast.newSimpleName("hongshuai"));
//																  ExpressionStatement newex=ast.newExpressionStatement(newio);
//																  eBlock.statements().add(e,newex);
																  break;
					            								  }
					            							  }else {
					            								  break;
					            							  }

					            						  }
					            						
					            					  }
					            				  }
					            			
					            		}else {
					            			break;
					            		}
					            	}
					            		
					            		
					            	}
					            }
							}
						}
						
					}
				}
				
			}


//}
//			System.out.println(types);
			
		}
		if(!deletemtemp.isEmpty()) {
			for(int q=0;q<deletemtemp.size();q++) {
				for(int w=deletemtemp.size()-1;w>q;w--) {
					if(deletemtemp.get(w).toString().equals(deletemtemp.get(q).toString())) {
						deletemtemp.remove(w);
					}
				}
			}
		}
		getMethod(types, methodList);
		for(MethodDeclaration mtemp:methodList) {
			for(int i=0;i<deletemtemp.size();i++) {
				if(deletemtemp.get(i).toString().equals(mtemp.getName().toString())) {
					mtemp.delete();
				}
			}
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
	private void getStatement(ASTNode cuu,List<ExpressionStatement> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(ExpressionStatement node) {
				types.add(node);
				return true;
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
