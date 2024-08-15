package refactoringexample.refactoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.Soundbank;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.w3c.dom.ls.LSOutput;

public class getextends {
//	static Map<String, ArrayList<String>> maplist = new HashMap<String, ArrayList<String>>();
	static Boolean a = false;

	public void getextends(CompilationUnit root, TypeDeclaration type) {
		List<TypeDeclaration> typeDeclarations = new ArrayList<TypeDeclaration>();
		getype(root, typeDeclarations);

//		for (TypeDeclaration temp : typeDeclarations) {
//			List<?> daList = temp.superInterfaceTypes();
//			ArrayList<String> adastring = new ArrayList<String>();
//			if (temp.getSuperclassType() != null) {
////		String tempString=temp.getName().toString();
//				adastring.add(temp.getSuperclassType().toString());
////				System.out.println("classname" + ":" + " " + temp.getName().toString() + " " + "---" + " " + "extends"+ "--->" + temp.getSuperclassType().toString());
//				maplist.put(temp.getName().toString(), adastring);
//			} else if (!daList.isEmpty()) {
////				String tempString = temp.getName().toString();
//				for(int i=0;i<temp.superInterfaceTypes().size();i++) {
//					adastring.add(temp.superInterfaceTypes().get(i).toString());					
//				}
//				maplist.put(temp.getName().toString(), adastring);
////				System.out.println("classname" + ":" + " " + temp.getName().toString() + " " + "---" + " "+ "implements" + "--->" + temp.superInterfaceTypes().toString());
//			} else {
//				
////				System.out.println("classname" + " " + temp.getName() + " " + "no extends");
//			}
////			maplist.put(temp.getName().toString(), adastring);
//		
//		}
//		a=true;
//      System.out.println(maplist);
		for (TypeDeclaration temp : typeDeclarations) {
			List<MethodDeclaration> methodDeclarations = new ArrayList<MethodDeclaration>();
			getMethod(temp, methodDeclarations);
			for (MethodDeclaration mtemp : methodDeclarations) {
				List<IfStatement> ifStatements = new ArrayList<IfStatement>();
				getif(mtemp, ifStatements);
				for (IfStatement iftemp : ifStatements) {
					ArrayList<String> typeList = new ArrayList<String>();
					if (iftemp.getExpression() instanceof InstanceofExpression) {
						InstanceofExpression instanceofExpression = (InstanceofExpression) iftemp.getExpression();
						String lString = instanceofExpression.getLeftOperand().toString();
						String rString = instanceofExpression.getRightOperand().toString();
						typeList.add(rString);
						if (iftemp.getElseStatement() instanceof IfStatement) {
							IfStatement eIfStatement = (IfStatement) iftemp.getElseStatement();
							if (eIfStatement.getExpression() instanceof InstanceofExpression
									&& !(eIfStatement.getElseStatement() instanceof IfStatement)) {
								if (eIfStatement.getExpression() instanceof InstanceofExpression) {
									InstanceofExpression eInstanceofExpression = (InstanceofExpression) eIfStatement
											.getExpression();
									String elString = eInstanceofExpression.getLeftOperand().toString();
									String erString = eInstanceofExpression.getRightOperand().toString();
									typeList.add(erString);
								}
							}
							while (eIfStatement.getElseStatement() instanceof IfStatement) {
								if (eIfStatement.getExpression() instanceof InstanceofExpression) {
									InstanceofExpression eInstanceofExpression = (InstanceofExpression) eIfStatement
											.getExpression();
									String elString = eInstanceofExpression.getLeftOperand().toString();
									String erString = eInstanceofExpression.getRightOperand().toString();
									typeList.add(erString);
								}
								if (eIfStatement.getElseStatement() instanceof IfStatement) {
									eIfStatement = (IfStatement) eIfStatement.getElseStatement();
								} else {
									break;
								}
							}
						}

					}
//					System.out.println(typeList);
					for (int s = 0; s < typeList.size(); s++) {
						if (AnnotationRefactoring.maplist.containsKey(typeList.get(s))) {
							ArrayList<String> sss = AnnotationRefactoring.maplist.get(typeList.get(s));
							if (s > 0) {
								for (int b = 0; b < s; b++) {
									for (int a = 0; a < sss.size(); a++) {
										if (typeList.get(b).equals(sss.get(a))) {
											System.out.println(sss.get(a));
											System.out.println("######");
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

	private void getype(ASTNode cuu, List<TypeDeclaration> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(TypeDeclaration node) {
				types.add(node);
				return true;
			}
		});
	}

	private void getMethod(ASTNode cuu, List<MethodDeclaration> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(MethodDeclaration node) {
				types.add(node);
				return true;
			}
		});
	}

	private void getif(ASTNode cuu, List<IfStatement> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(IfStatement node) {
				types.add(node);
				return false;
			}
		});
	}
}
