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

public class Godclass {
//	static int m;
//	static int n;
 public void godclass(TypeDeclaration types,AST ast) {
//	 System.out.println(AnnotationRefactoring.godMap);
//	 System.out.println(AnnotationRefactoring.deList);
//	 System.out.println(AnnotationRefactoring.deList.size());
//	 System.out.println(AnnotationRefactoring.exList);
//	 System.out.println(AnnotationRefactoring.exList.size());
//	 System.out.println(AnnotationRefactoring.typeList);
//	 System.out.println(AnnotationRefactoring.typeList.size());
//	 System.out.println(AnnotationRefactoring.maplist);
	 Iterator<Entry<String, ArrayList<String>>> it=AnnotationRefactoring.godMap.entrySet().iterator();
	 while(it.hasNext()){
		 Map.Entry<String,ArrayList<String>> entry=it.next();
		 if(entry.getValue().size()<2) {
			 it.remove();
		 }
		
	 }
	 System.out.println(AnnotationRefactoring.godMap);
//	 Iterator<Map.Entry<String, ArrayList<String>>> it = AnnotationRefactoring.godMap.entrySet().iterator();
//	 while(it.hasNext()){
//		 Map.Entry<String, ArrayList<String>> entry = it.next();
//		 if(entry.getValue().isEmpty()) {
//			 it.remove();
//		 }
//	 }
//	 System.out.println(AnnotationRefactoring.maplist);
//	 for(int i=0;i<AnnotationRefactoring.maplist.size();i++) {
//		 
//	 }
	  
//	 List<List<Integer>> locaList=new ArrayList<List<Integer>>();
//	 getype(root, typeDeclarations);
//	 for(int i=0;i<AnnotationRefactoring.exList.size();i++) {
//		 for(int j=i+1;j<AnnotationRefactoring.exList.size();j++) {
//			 if(AnnotationRefactoring.exList.get(i).equals(AnnotationRefactoring.exList.get(j))) {
//				 List<Integer> numberIntegers=new ArrayList<Integer>();
//				 numberIntegers.add(i);
//				 numberIntegers.add(j);
//				 locaList.add(numberIntegers);
//			 }			
//		 }
//	 }
//	 for(int i=0;i<locaList.size();i++) {
//		 List<Integer> locList=locaList.get(i);
//		 int a=locList.get(0);
//		 int b=locList.get(1);
//		 for(int j=0;j<AnnotationRefactoring.typeList.size();j++) {
//			  List<MethodDeclaration> mList=new ArrayList<MethodDeclaration>();
//			  List<String> mStrings=new ArrayList<String>();
//			  List<MethodDeclaration> nList=new ArrayList<MethodDeclaration>();
//			  List<String> nStrings=new ArrayList<String>();
//			 if(AnnotationRefactoring.typeList.get(j).getName().toString().equals(AnnotationRefactoring.deList.get(a).toString())) {
//				 getMethod(AnnotationRefactoring.typeList.get(j), mList);
//				 for(MethodDeclaration mtemp:mList) {
//					 if(!mtemp.getName().toString().equals(AnnotationRefactoring.typeList.get(j).getName().toString())) {
//						 mStrings.add(mtemp.getName().toString());
//					 }
//				 }
//			 }
//			 if(AnnotationRefactoring.typeList.get(j).getName().toString().equals(AnnotationRefactoring.deList.get(b).toString())) {
//				 getMethod(AnnotationRefactoring.typeList.get(j), nList);
//				 for(MethodDeclaration mtemp:mList) {
//					if(!mtemp.getName().toString().equals(AnnotationRefactoring.typeList.get(j).getName().toString())) {
//						nStrings.add(mtemp.getName().toString());
//					} 
//				 }
//			 }
//             if(mStrings!=null) {
//            	 System.out.println(mStrings);
//             }
//             if(nStrings!=null) {
//            	 System.out.println(nStrings);
//             }
//             System.out.println("---");
//		 }
//	 }
//	 System.out.println(locaList);
//	 System.out.println(AnnotationRefactoring.exList);
//	 System.out.println(AnnotationRefactoring.deList);
//	 System.out.println(locaList);
//	 System.out.println(AnnotationRefactoring.deList);
//	 for(TypeDeclaration temp:typeDeclarations) {
//		 System.out.println(temp.getName());
//		 for(int i=0;i<locaList.size();i++) {
//			 
//		 }
//	 }
	 
//	 for(int i=0;i<locaList.size();i++) {
//		 List<Integer> locList=locaList.get(i);
//		 int a=locList.get(0);
//		int a=locList.get(0);
//		 for(int j=0;j<typeDeclarations.size();j++) {
////			 System.out.println(typeDeclarations.get(j).getName());
//		 }
//		 for(TypeDeclaration temp:typeDeclarations) {
////			 System.out.println(temp.getName());
////			 System.out.println("-");
//			 List<String> mList=new ArrayList<String>();
//			 List<String> nList=new ArrayList<String>();
//			 if(temp.getName().toString().equals(AnnotationRefactoring.deList.get(a).toString())) {
//				List<MethodDeclaration> methodDeclarations=new ArrayList<MethodDeclaration>();
//				getMethod(temp, methodDeclarations);
//				for(MethodDeclaration mtemp:methodDeclarations) {
//				  if(!mtemp.getName().toString().equals(temp.getName().toString())) {
//					 mList.add(mtemp.getName().toString());
//				  }
//				}
////				System.out.println(mList);
//			 }
//			 if(temp.getName().toString().equals(AnnotationRefactoring.deList.get(b).toString())) {
////				 System.out.println("yes");
//				List<MethodDeclaration> methodDeclarations=new ArrayList<MethodDeclaration>();
//				getMethod(temp, methodDeclarations);
//				for(MethodDeclaration mtemp:methodDeclarations) {
//				  if(!mtemp.getName().toString().equals(temp.getName().toString())) {
//					 nList.add(mtemp.getName().toString());
//				  }
//				}
////				 System.out.println(nList);
//			 }
////			 System.out.println(mList);
////			 System.out.println(nList);
//		 }
//	
////		 
//	 }
 }
	private void getype(ASTNode cuu, List<TypeDeclaration> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(TypeDeclaration node) {
				types.add(node);
				return false;
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
//	static List<String> deList=new ArrayList<String>();
//	static List<List<String>> exList=new ArrayList<List<String>>();
//	 for(TypeDeclaration temp:typeDeclarations) {
//	 MethodDeclaration[] methods = temp.getMethods();
//	 if(methods.length>0) {
//	 List<String> extendStrings =new ArrayList<String>();
//	 if(temp.getSuperclassType()!=null) { 
//		 extendStrings.add(temp.getSuperclassType().toString());
//	 }
//	 deList.add(temp.getName().toString());
//	 exList.add(extendStrings);
//	 }
//}
//for(int i=0;i<exList.size();i++) {
//	 if(exList.get(i).isEmpty()) {
//		 exList.remove(i);
//		 deList.remove(i);
//		 i--;
//	 }
//}

//for(int s=0;s<deList.size();s++) {
//	 for(int x=s+1;x<deList.size();x++) {
//		 if(deList.get(s).equals(deList.get(x))) {
//			 deList.remove(x);
//			 exList.remove(x);
//		 }
//	 }
//}
//System.out.println(deList);
//System.out.println(deList.size());
//System.out.println(exList);
//System.out.println(exList.size());
}
