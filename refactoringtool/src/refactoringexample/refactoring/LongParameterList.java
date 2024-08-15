package refactoringexample.refactoring;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.TextEdit;
public class LongParameterList {
public void LongParameterList(TypeDeclaration types,AST ast,CompilationUnit root) {
	 List<MethodDeclaration> mlist=new ArrayList<MethodDeclaration>();
	 List<String> ClassNameList=new ArrayList<String>();
	 List<TypeDeclaration> IntoTypeList=new ArrayList<TypeDeclaration>();
	 List<String> CompareList=new ArrayList<String>();
	 List<SingleVariableDeclaration> singvariableList=new ArrayList<SingleVariableDeclaration>();
	 List<String> paraStringList=new ArrayList<String>();
	 List<String> mnameList=new ArrayList<String>();
	 getIntoTypes(root, IntoTypeList);
	 for(TypeDeclaration typetemp:IntoTypeList) {
		ClassNameList.add(typetemp.getName().toString());
	 }
	 getMethod(types, mlist);
	 for(MethodDeclaration mtemp: mlist) {
	    	for(int i=0;i<mtemp.parameters().size();i++) {
	    		String parameterString=mtemp.parameters().get(i).toString();
//	    		SimpleName name=ast.newSimpleName("dadad");
//	    		mtemp.parameters().add(name);
	    		
	    		if(parameterString.indexOf(" ")!=-1) {
	    			String newpara=parameterString.substring(0, parameterString.indexOf(" "));
	    			for(int j=0;j<ClassNameList.size();j++) {
	    				if(ClassNameList.get(j).toString().equals(newpara)) {
	    					if(paraStringList.isEmpty()) {
	    						paraStringList.add(parameterString);
	    					}else {
	    						
	    					}
	    				}
	    			}
	    			
	    			
	    			
	    			for(int j=0;j<ClassNameList.size();j++) {
	    				if(ClassNameList.get(j).toString().equals(newpara)) {
	    					CompareList.add(ClassNameList.get(j));
//	    					if(paraStringList.isEmpty()) {
//	    					paraStringList.add(parameterString);
//	    					}else {
//	    						for(int size=0;size<paraStringList.size();size++) {
//	    							if(parameterString.equals(paraStringList.get(size).toString())) {
//	    								
//	    							}else {
//	    								paraStringList.add(parameterString);
//	    							}
//	    						}
//	    					}
	    				}
	    			}
	    
	    		}
	    		
	    	}
	    	
	    }
	 for(TypeDeclaration typetemp:IntoTypeList) {
		 for(int i=0;i<CompareList.size();i++) {
			 if(CompareList.get(i).toString().equals(typetemp.getName().toString())) {
				 for(int j=0;j<typetemp.bodyDeclarations().size();j++) {
					 if(typetemp.bodyDeclarations().get(j) instanceof FieldDeclaration) {
						 FieldDeclaration fieldDeclaration=(FieldDeclaration)typetemp.bodyDeclarations().get(j);
						if(fieldDeclaration.getType()!=null&&fieldDeclaration.fragments()!=null&&fieldDeclaration.fragments().size()==1) {
							SingleVariableDeclaration newsvd=ast.newSingleVariableDeclaration();
							Type newType=(Type)ASTNode.copySubtree(ast, fieldDeclaration.getType());
							newsvd.setType(newType);
							SimpleName name=ast.newSimpleName(fieldDeclaration.fragments().get(0).toString());
							newsvd.setName(name);
							singvariableList.add(newsvd);
							
						}
						 
					 }
				 }
			 }
		 }
	 }
	 System.out.println(paraStringList);
	  for(MethodDeclaration mtemp: mlist) {
		 for(int i=0;i<mtemp.parameters().size();i++) {
			 for(int j=0;j<paraStringList.size();j++) {
				 if(mtemp.parameters().get(i).toString().equals(paraStringList.get(j).toString())) {
					 mtemp.parameters().remove(i);
					 if(mnameList.isEmpty()) {
						 mnameList.add(mtemp.getName().toString());
					 }else {
					 for(int size=0;size<mnameList.size();size++) {
						 if(mtemp.getName().toString().equals(mnameList.get(size))) {
							 
						 }else {
							 mnameList.add(mtemp.getName().toString());
						 }
					 }
					 }
//					 for(int x=0;x<singvariableList.size();x++) {
//						 mtemp.parameters().add(singvariableList.get(x));
////						 System.out.println(mtemp.parameters());
//					 }
				 }
				 
			 }
		 }
	  }
	  
	  for(MethodDeclaration mtemp: mlist) {
		  for(int k=0;k<mnameList.size();k++) {
			  if(mtemp.getName().toString().equals(mnameList.get(k).toString())) {
				  for(int x=0;x<singvariableList.size();x++) {
						 mtemp.parameters().add(singvariableList.get(x));
//						 System.out.println(mtemp.parameters());
					 }
			  }
		  }
	  }

	  for(TypeDeclaration typetemp:IntoTypeList) {

	  }
//	  for(MethodDeclaration mtemp: mlist) {
//		  for(int i=0;i<mtemp.parameters().size();i++) {
//				 for(int j=0;j<paraStringList.size();j++) {
//					 if(mtemp.parameters().get(i).toString().equals(paraStringList.get(j).toString())) {
//						 for(int x=0;x<singvariableList.size();x++) {
//						 mtemp.parameters().add(singvariableList.get(x));
////						 System.out.println(mtemp.parameters());
//					 }
//					 }
//				}
//			}
//	  }


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
