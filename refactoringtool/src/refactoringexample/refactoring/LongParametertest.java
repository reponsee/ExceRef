package refactoringexample.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class LongParametertest {
public void LongParametertest(TypeDeclaration types,AST ast,CompilationUnit root) {
	List<TypeDeclaration> IntoTypeList=new ArrayList<TypeDeclaration>();//访问者遍历类
	List<String> typeDeclarationNameList=new ArrayList<String>();//类名列表
	List<MethodDeclaration> methodDeclarationList=new ArrayList<MethodDeclaration>();//访问者遍历方法
	getIntoTypes(root, IntoTypeList);
	for(TypeDeclaration temp:IntoTypeList) {
		typeDeclarationNameList.add(temp.getName().toString());
		
	}

	getMethod(types, methodDeclarationList);
	for(MethodDeclaration mtemp: methodDeclarationList) {
//		List<String> paNameList=new ArrayList<String>();//可去掉
		for(int i=0;i<mtemp.parameters().size();i++) {
			String pNameString=mtemp.parameters().get(i).toString();
			if(pNameString.indexOf(" ")!=-1) {
				String newpara=pNameString.substring(0, pNameString.indexOf(" "));
				String newparabefore=pNameString.substring(pNameString.lastIndexOf(" ")+1);
				for(int j=0;j<typeDeclarationNameList.size();j++) {
    				if(newpara.equals(typeDeclarationNameList.get(j).toString())) {
//    					paNameList.add(newpara);//可去掉
    					List<SingleVariableDeclaration> fieldSvdList=new ArrayList<SingleVariableDeclaration>();//储存参数列表
    					for(TypeDeclaration intype:IntoTypeList) {
     						if(intype.getName().toString().equals(newpara)&&!types.getName().toString().equals(newpara)) { 							
    							for(int b=0;b<intype.bodyDeclarations().size();b++) {
    								if(intype.bodyDeclarations().get(b)instanceof FieldDeclaration) {
    									    FieldDeclaration fieldDeclaration=(FieldDeclaration)intype.bodyDeclarations().get(b);    									    
    									    if(fieldDeclaration.getType()!=null
    									    		&&fieldDeclaration.fragments().size()==1) {
    									    	System.out.println(fieldDeclaration.fragments());
    									    	//需要限定
    									    	if(fieldDeclaration.getType().toString().equals("String")
    									    			||fieldDeclaration.getType().toString().equals("int")
    									    			||fieldDeclaration.getType().toString().equals("double")
    									    			||fieldDeclaration.getType().toString().equals("long")
    									    			||fieldDeclaration.getType().toString().equals("float")
    									    			||fieldDeclaration.getType().toString().equals("char")
    									    			||fieldDeclaration.getType().toString().equals("byte")) {
    									    	SingleVariableDeclaration newsvd=ast.newSingleVariableDeclaration();
    									    	Type type=(Type)ASTNode.copySubtree(ast, fieldDeclaration.getType());
    									    	newsvd.setType(type);    	
    									    	SimpleName name=ast.newSimpleName(fieldDeclaration.fragments().get(0).toString());
    									    	newsvd.setName(name);
    									    	fieldSvdList.add(newsvd);
    									    	}else {
    									    		break;
    									    	}
    									    }else {
    									    	break;
    									    }
    								}else {
    									break;
    								}
    							}
    						}
    					}
//    					System.out.println(fieldSvdList);
    					if(!fieldSvdList.isEmpty()) {
    						mtemp.parameters().remove(i);
    						for(int into=0 ,loc=i;into<fieldSvdList.size();into++,loc++) {
    							mtemp.parameters().add(loc,fieldSvdList.get(into));
    						}
    						//修改函数内部参数
    						if(mtemp.getBody() instanceof Block) {
    							List<QualifiedName> Qname=new ArrayList<QualifiedName>();
    							Block block=(Block)mtemp.getBody();
    							for(int s=0;s<block.statements().size();s++) {
    								getQualifiedName(block, Qname);
    							    for(QualifiedName qtemp:Qname) {
    							    	if(qtemp.getQualifier().toString().equals(newparabefore)) {
    							    		System.out.println("yes");
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
			//此处重构
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
