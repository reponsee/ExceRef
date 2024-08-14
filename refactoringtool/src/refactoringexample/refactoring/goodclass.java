package refactoringexample.refactoring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class goodclass {
 static	List<TypeDeclaration> typeList=new ArrayList<TypeDeclaration>();
 public void godclass(TypeDeclaration types,AST ast) {
	 Iterator<Entry<String, ArrayList<String>>> it=AnnotationRefactoring.godMap.entrySet().iterator();
	 while(it.hasNext()){
		 Map.Entry<String,ArrayList<String>> entry=it.next();
		 if(entry.getValue().size()<2) {
			 it.remove();
		 }
		
	 }
	 System.out.println(AnnotationRefactoring.godMap);
	 System.out.println(types.getName());
	 Iterator<Entry<String, ArrayList<String>>> its=AnnotationRefactoring.godMap.entrySet().iterator();
	 while(its.hasNext()) {
		 Map.Entry<String,ArrayList<String>> entry=its.next();
		 List<String> tList=entry.getValue();
//		 System.out.println(tList);
//		 List<TypeDeclaration> typeList=new ArrayList<TypeDeclaration>();
		 for(int i=0;i<tList.size();i++) {
			 if(types.getName().toString().equals(tList.get(0).toString())&&typeList.isEmpty()) {
				 typeList.add(types);
			 }else if(types.getName().toString().equals(tList.get(i).toString())&&i!=0) {
				 List<MethodDeclaration> mList=new ArrayList<MethodDeclaration>();
				 List<MethodDeclaration> inmList=new ArrayList<MethodDeclaration>();
				 getMethod(typeList.get(0), mList);
				 getMethod(types, inmList);
				 List<String> mStrings=new ArrayList<String>();
				 List<String> inmStrings=new ArrayList<String>();
				 for(int s=0;s<mList.size();s++) {
					 mStrings.add(mList.get(s).getName().toString());
				 }
				 for(int s=0;s<inmList.size();s++) {                   
					 inmStrings.add(inmList.get(s).getName().toString());
				 }
//				 System.out.println(mStrings);
//				 System.out.println(inmStrings);
				 for(int s=0;s<mStrings.size();s++) {
					 if(!inmStrings.contains(mStrings.get(s))&&!mStrings.get(s).equals("main")) {
						 List<MethodDeclaration> moveMethodDeclarations=new ArrayList<MethodDeclaration>();
						for(MethodDeclaration mtemp:mList) {
							if(mtemp.getName().toString().equals(mStrings.get(s))) {
								moveMethodDeclarations.add(mtemp);
							}
						}
						if(!moveMethodDeclarations.isEmpty()) {
                           for(int x=0;x<moveMethodDeclarations.size();x++) {
                            	MethodDeclaration newDeclaration=(MethodDeclaration) ASTNode.copySubtree(ast, moveMethodDeclarations.get(x));
                            	types.bodyDeclarations().add(newDeclaration);
                             }					     
						}
					 }
				 }
				
			 }
		 }
	 }
}
	private void getMethod(ASTNode cuu, List<MethodDeclaration> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(MethodDeclaration node) {
				types.add(node);
				return false;
			}
		});
	}
}
