package refactoringexample.refactoring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.Type;

public class timetest {
	public void timetest(TypeDeclaration types,AST ast) {
		List<MethodDeclaration> methodList=new ArrayList<MethodDeclaration>();
		String tnameString=types.getName().toString();
		getMethod(types, methodList);
		for(MethodDeclaration mtemp:methodList) {
			if(mtemp.getName().toString().equals("Game")) {
				if (mtemp.getBody() instanceof Block) {
					Block Block=(Block)mtemp.getBody();
					for(int i=0;i<Block.statements().size();i++) {
//						System.out.println(Block.statements().get(i));
						if(Block.statements().get(i).toString().contains("Long s")) {
//							System.out.println(Block.statements().get(i));
//							VariableDeclarationStatement variableDeclarationStatement=(VariableDeclarationStatement)Block.statements().get(i);
//							VariableDeclaration vDeclarationExpression = (VariableDeclaration) (variableDeclarationStatement.fragments().get(0));
//							System.out.println(vDeclarationExpression);
//							System.out.println(vDeclarationExpression.getInitializer());
////							CastExpression xCastExpression=(CastExpression)vDeclarationExpression.getInitializer();
//							MethodInvocation nInvocation=(MethodInvocation)vDeclarationExpression.getInitializer();
//							System.out.println(nInvocation.getExpression());
//							System.out.println(nInvocation.getName());
						}
						
					}
					}
				
			}
				
				
					
			if (mtemp.getBody() instanceof Block) {
				Block Block=(Block)mtemp.getBody();
				String string=mtemp.getName().toString();
//				System.out.println(Block);
				if (!(Block.statements().size()==0)&&!string.equals(tnameString)) {
					 
//					 VariableDeclarationFragment variableDeclarationFragment=ast.newVariableDeclarationFragment();
//					 variableDeclarationFragment.setName(ast.newSimpleName("longs"));
//					 VariableDeclarationExpression variableDeclarationExpression=ast.newVariableDeclarationExpression(variableDeclarationFragment);
//					 Type type=ast.newSimpleType(ast.newSimpleName("Long"));
//					 variableDeclarationExpression.setType(type);
					 MethodInvocation methodInvocation1=ast.newMethodInvocation();
					 methodInvocation1.setExpression(ast.newSimpleName("System"));
					 methodInvocation1.setName(ast.newSimpleName("currentTimeMillis"));
					 MethodInvocation methodInvocation2=ast.newMethodInvocation();
					 methodInvocation2.setExpression(ast.newSimpleName("System"));
					 methodInvocation2.setName(ast.newSimpleName("currentTimeMillis"));
//					 VariableDeclaration variableDeclaration=ast.newSingleVariableDeclaration();
//					 variableDeclaration.setInitializer(methodInvocation);
					 PrimitiveType type=ast.newPrimitiveType(PrimitiveType.LONG);
					 PrimitiveType type1=ast.newPrimitiveType(PrimitiveType.LONG);
					 SimpleName name1=ast.newSimpleName("longs");
					 SimpleName name2=ast.newSimpleName("longe");
					 
					 VariableDeclarationFragment fragment1=ast.newVariableDeclarationFragment();
					 fragment1.setName(name1);
					 fragment1.setInitializer(methodInvocation1);			 
					 VariableDeclarationStatement statement1=ast.newVariableDeclarationStatement(fragment1);
					 statement1.setType(type);			 
					 
					 Block.statements().add(0,statement1);
					 
					 int x=Block.statements().size();
//					 System.out.println(Block.statements().get(x-1));
					 if(!(Block.statements().get(x-1) instanceof ReturnStatement)) {
					 VariableDeclarationFragment fragment2=ast.newVariableDeclarationFragment();
					 fragment2.setName(name2);
					 fragment2.setInitializer(methodInvocation2);			 
					 VariableDeclarationStatement statement2=ast.newVariableDeclarationStatement(fragment2);
					 statement2.setType(type1);
					 
					
					 Block.statements().add(x,statement2);
					 
					 MethodInvocation methodInvocation=ast.newMethodInvocation();
					 SimpleName nameSys=ast.newSimpleName("System");
					 SimpleName nameOut=ast.newSimpleName("out");
					 SimpleName namePrint=ast.newSimpleName("println");
					 
					 QualifiedName namesysQualifiedName=ast.newQualifiedName(nameSys, nameOut);
					 methodInvocation.setExpression(namesysQualifiedName);
					 methodInvocation.setName(namePrint);
					 
					 StringLiteral printlongLiteral=ast.newStringLiteral();
					 printlongLiteral.setEscapedValue("\"Time:\"+longe-longs");
					 
					 methodInvocation.arguments().add(printlongLiteral);
					 
					 ExpressionStatement eStatement=ast.newExpressionStatement(methodInvocation);
					 
					 Block.statements().add(eStatement);
					 
					 MethodInvocation methodInvocation0=ast.newMethodInvocation();
					 SimpleName nameSys1=ast.newSimpleName("System");
					 SimpleName nameOut1=ast.newSimpleName("out");
					 SimpleName namePrint1=ast.newSimpleName("println");
					 
					 QualifiedName namesysQualifiedName1=ast.newQualifiedName(nameSys1, nameOut1);
					 methodInvocation0.setExpression(namesysQualifiedName1);
					 methodInvocation0.setName(namePrint1);
					 
					 StringLiteral printlongLiteral1=ast.newStringLiteral();
					 printlongLiteral1.setEscapedValue("\"Name:\""+"+"+"\""+string+"\"");
					 
					 methodInvocation0.arguments().add(printlongLiteral1);
					 
                     ExpressionStatement eStatement0=ast.newExpressionStatement(methodInvocation0);
					 
					 Block.statements().add(eStatement0);
				}else {
					 VariableDeclarationFragment fragment2=ast.newVariableDeclarationFragment();
					 fragment2.setName(name2);
					 fragment2.setInitializer(methodInvocation2);			 
					 VariableDeclarationStatement statement2=ast.newVariableDeclarationStatement(fragment2);
					 statement2.setType(type1);
					 
					
					 Block.statements().add(x-1,statement2);
					 
					 MethodInvocation methodInvocation=ast.newMethodInvocation();
					 SimpleName nameSys=ast.newSimpleName("System");
					 SimpleName nameOut=ast.newSimpleName("out");
					 SimpleName namePrint=ast.newSimpleName("println");
					 
					 QualifiedName namesysQualifiedName=ast.newQualifiedName(nameSys, nameOut);
					 methodInvocation.setExpression(namesysQualifiedName);
					 methodInvocation.setName(namePrint);
					 
					 StringLiteral printlongLiteral=ast.newStringLiteral();
					 printlongLiteral.setEscapedValue("\"Time:\"+longe-longs");
					 
					 methodInvocation.arguments().add(printlongLiteral);
					 
					 ExpressionStatement eStatement=ast.newExpressionStatement(methodInvocation);
					 int i=Block.statements().size();
					 
					 
					 Block.statements().add(i-1,eStatement);
					 
					 MethodInvocation methodInvocation0=ast.newMethodInvocation();
					 SimpleName nameSys1=ast.newSimpleName("System");
					 SimpleName nameOut1=ast.newSimpleName("out");
					 SimpleName namePrint1=ast.newSimpleName("println");
					 
					 QualifiedName namesysQualifiedName1=ast.newQualifiedName(nameSys1, nameOut1);
					 methodInvocation0.setExpression(namesysQualifiedName1);
					 methodInvocation0.setName(namePrint1);
					 
					 StringLiteral printlongLiteral1=ast.newStringLiteral();
					 printlongLiteral1.setEscapedValue("\"Name:\""+"+"+"\""+string+"\"");
					 
					 
					 methodInvocation0.arguments().add(printlongLiteral1);
					 
                    ExpressionStatement eStatement0=ast.newExpressionStatement(methodInvocation0);
                     int j=Block.statements().size();
					 Block.statements().add(j-1,eStatement0);
				
				}
				
			}}
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
}
