package refactoringexample.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class LongParameterList2 {
	public void LongParameterList(TypeDeclaration types,AST ast,CompilationUnit root) {
		List<TypeDeclaration> IntoTypeList=new ArrayList<TypeDeclaration>();//访问者遍历类
		List<String> typeDeclarationNameList=new ArrayList<String>();//类名列表
		List<MethodDeclaration> methodDeclarationList=new ArrayList<MethodDeclaration>();//访问者遍历方法
		getIntoTypes(root, IntoTypeList);
		for(TypeDeclaration temp:IntoTypeList) {
			typeDeclarationNameList.add(temp.getName().toString());
			
		}
		getMethod(types, methodDeclarationList);
		for(MethodDeclaration mtemp: methodDeclarationList) {
			for(int i=0;i<mtemp.parameters().size();i++) {
				String pNameString=mtemp.parameters().get(i).toString();
				if(pNameString.indexOf(" ")!=-1) {
					String newpara=pNameString.substring(0, pNameString.indexOf(" "));
					String newparabefore=pNameString.substring(pNameString.lastIndexOf(" ")+1);
					for(int j=0;j<typeDeclarationNameList.size();j++) {
						if(newpara.equals(typeDeclarationNameList.get(j).toString())) {
						   for(TypeDeclaration intype:IntoTypeList) {
							   if(intype.getName().toString().equals(newpara)&&!types.getName().toString().equals(newpara)) {
								  for(int b=0;b<intype.bodyDeclarations().size();b++) {
									  if(intype.bodyDeclarations().get(b) instanceof FieldDeclaration) {
										  FieldDeclaration fieldDeclaration=(FieldDeclaration)intype.bodyDeclarations().get(b);
										  if(fieldDeclaration.getType()!=null&&fieldDeclaration.fragments().size()==1) {
											  System.out.println(fieldDeclaration);
											  System.out.println(fieldDeclaration.getType());
											  System.out.println(fieldDeclaration.fragments());
											  CastExpression dsad=(CastExpression)fieldDeclaration.fragments().get(0);
											  if(fieldDeclaration.toString().contains("")) {
												  
											  }
											  
										  }else {
											  break;
										  }
									  }else {
										  break;
									  }
									  
								  }
								  
							   }else if(intype.getName().toString().equals(newpara)&&types.getName().toString().equals(newpara)) {
								   
							   }
						   }
							
							
							if(mtemp.getBody() instanceof Block) {
								Block block=(Block)mtemp.getBody();
								List<QualifiedName> qualifiedNamesList=new ArrayList<QualifiedName>();//访问者遍历QualifiedName
								List<String> qStringList=new ArrayList<String>();
								getQualifiedName(block, qualifiedNamesList);
								for(QualifiedName qtemp:qualifiedNamesList) {
									if(qtemp.getQualifier().toString().equals(newparabefore)) {
										
									}
								}
								
							}
						}
					}
				}
			}
		}

	}
	private void getQualifiedName(ASTNode cuu,List<QualifiedName> Qname) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(QualifiedName node) {
				Qname.add(node);
				return false;
			}
		});
	}
	private void getArrayAccess(ASTNode cuu,List<ArrayAccess> array) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(ArrayAccess node) {
				array.add(node);
				return false;
			}
		});
	}

	private void getMethod(ASTNode cuu,List<MethodDeclaration> method) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(MethodDeclaration node) {
				method.add(node);
				return true;
			}
		});
	}
	private void getIntoTypes(ASTNode cuu,List<TypeDeclaration> intypes) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(TypeDeclaration node) {
				intypes.add(node);
				return true;
			}
		});
	}
}
