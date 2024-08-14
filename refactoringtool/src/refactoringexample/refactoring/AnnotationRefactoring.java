package refactoringexample.refactoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jface.text.Document;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.TextEdit;



import org.eclipse.core.runtime.IPath;


public class AnnotationRefactoring extends Refactoring {
	public static Map<String, ArrayList<String>> maplist = new HashMap<String, ArrayList<String>>();
	public static Map<String, ArrayList<String>> godMap = new HashMap<String, ArrayList<String>>();
	public static List<String> deList=new ArrayList<String>();
	public static List<List<String>> exList=new ArrayList<List<String>>();
	public static List<List<Integer>> locaList=new ArrayList<List<Integer>>();
	public static List<TypeDeclaration> typeList=new ArrayList<TypeDeclaration>();
	private static final Object PrimitiveType = null;
	ASTRewrite rewrite;
	private IJavaElement element;
	// 所要重构程序的路径
	static IPath filename;
	// List<Change> changeManager = new ArrayList<Change>();
	List<Change> changeManager = new ArrayList<Change>();
	// private TextChangeManager changeManager;
	private List<ICompilationUnit> compilationUnits;

	public AnnotationRefactoring(IJavaElement select) {
		element = select;
		// changeManager = new TextChangeManager();
		compilationUnits = findAllCompilationUnits(element);
		filename =element.getJavaProject().getProject().getLocation();
		System.out.println("项目：" + filename.toString());
//		cg=new MakeCallGraph(filename.toString());
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor arg0)
			throws CoreException, OperationCanceledException {
		collectChanges();

		if (changeManager.size() == 0)
			return RefactoringStatus.createFatalErrorStatus("No  found!");
		else
			return RefactoringStatus.createInfoStatus("Final condition has been checked");

	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor arg0)
			throws CoreException, OperationCanceledException {
		return RefactoringStatus.createInfoStatus("Initial Condition is OK!");
	}

	@Override
	public Change createChange(IProgressMonitor arg0) throws CoreException, OperationCanceledException {
		Change[] changes = new Change[changeManager.size()];
		// TextChange[] changes = changeManager.getAllChanges();
		System.arraycopy(changeManager.toArray(), 0, changes, 0, changeManager.size());
		CompositeChange change = new CompositeChange(element.getJavaProject().getElementName(), changes);
		return change;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Add @Test annotation";
	}

	private void collectChanges() throws JavaModelException {
		int sumInstance = 0;
		int sumnegateInstance =0;
		for (IJavaElement element : compilationUnits) {
			ICompilationUnit cu = (ICompilationUnit) element;
			String source = cu.getSource();
			Document document = new Document(source);
			// 创建AST
			ASTParser parser = ASTParser.newParser(AST.JLS14);
			parser.setSource(cu);
			CompilationUnit astRoot = (CompilationUnit) parser.createAST(null);
			
			List<TypeDeclaration> typeDeclarations = new ArrayList<TypeDeclaration>();
			getype(astRoot.getRoot(), typeDeclarations);
			
			//继承关系
			for (TypeDeclaration temp : typeDeclarations) {
//				List<?> daList = temp.superInterfaceTypes();
				ArrayList<String> adastring = new ArrayList<String>();
				if (temp.getSuperclassType() != null) {
					adastring.add(temp.getSuperclassType().toString());
//					maplist.put(temp.getName().toString(), adastring);
				}
//				if (!daList.isEmpty()) {
//					for(int i=0;i<temp.superInterfaceTypes().size();i++) {
//						adastring.add(temp.superInterfaceTypes().get(i).toString());					
//					}
////					maplist.put(temp.getName().toString(), adastring);
//				} 
				maplist.put(temp.getName().toString(), adastring);
//				
			}
			for(TypeDeclaration temp : typeDeclarations) {
				ArrayList<String> adastring = new ArrayList<String>();
				if (temp.getSuperclassType() != null) {
//					System.out.println(temp.getName().toString());
					adastring.add(temp.getName().toString());
					if(godMap.isEmpty()) {
						godMap.put(temp.getSuperclassType().toString(), adastring);
					}else if(godMap.containsKey(temp.getSuperclassType().toString())) {
						Iterator<Entry<String, ArrayList<String>>> it=AnnotationRefactoring.godMap.entrySet().iterator();
						while(it.hasNext()){
							Map.Entry entry = it.next();
							if(entry.getKey().toString().equals(temp.getSuperclassType().toString())) {
								List<String> newsStrings=(List<String>) entry.getValue();
								newsStrings.add(temp.getName().toString());
								godMap.put((String) entry.getKey(), (ArrayList<String>) newsStrings);
							}
						}
					}else {
						godMap.put(temp.getSuperclassType().toString(), adastring);
					}
//						
				
				}
				
			}
			 //上帝类
//			 for(TypeDeclaration temp:typeDeclarations) {
//				 MethodDeclaration[] methods = temp.getMethods();
//				 if(methods.length>0) {
//				 List<String> extendStrings =new ArrayList<String>();
//				 if(temp.getSuperclassType()!=null) { 
//					 extendStrings.add(temp.getSuperclassType().toString());
//				 }
//				 deList.add(temp.getName().toString());
//				 exList.add(extendStrings);
//				 typeList.add(temp);
//				 
//				 }
//			 }
//			 for(int i=0;i<exList.size();i++) {
//				 if(exList.get(i).isEmpty()) {
//					 exList.remove(i);
//					 deList.remove(i);
//					 typeList.remove(i);
//					 i--;
//				 }
//			 }
//			 for(int s=0;s<deList.size();s++) {
//				 for(int x=s+1;x<deList.size();x++) {
//					 if(deList.get(s).equals(deList.get(x))) {
//						 deList.remove(x);
//						 exList.remove(x);
//						 typeList.remove(x);
//					 }
//				 }
//			 }
//			 for(int i=0;i<AnnotationRefactoring.exList.size();i++) {
//				 for(int j=i+1;j<AnnotationRefactoring.exList.size();j++) {
//					 if(AnnotationRefactoring.exList.get(i).equals(AnnotationRefactoring.exList.get(j))) {
//						 List<Integer> numberIntegers=new ArrayList<Integer>();
//						 numberIntegers.add(i);
//						 numberIntegers.add(j);
//						 locaList.add(numberIntegers);
//					 }
//					
//				 }
//			 }
//			 for(int s=0;s<locaList.size();s++) {
//				 for(int x=s+1;x<locaList.size();x++) {
//					 if(locaList.get(s).equals(locaList.get(x))) {
//						 locaList.remove(x);
//					 }
//				 }
//			 }
			
		}
		
//		System.out.println(maplist);
		for (IJavaElement element : compilationUnits) {
//			System.out.println(element);
			// 创建一个document(jface)
			ICompilationUnit cu = (ICompilationUnit) element;
			String source = cu.getSource();
			Document document = new Document(source);
			// 创建AST
			ASTParser parser = ASTParser.newParser(AST.JLS14);
			parser.setSource(cu);
			CompilationUnit astRoot = (CompilationUnit) parser.createAST(null);
			rewrite = ASTRewrite.create(astRoot.getAST());
			// 记录更改
			astRoot.recordModifications();
			List<TypeDeclaration> types = new ArrayList<TypeDeclaration>();
			getTypes(astRoot.getRoot(), types);
			
			List<IfStatement> list = new ArrayList<IfStatement>();
			getIf(astRoot.getRoot(), list);
			for(IfStatement ifTemp : list) {
				if(ifTemp.getExpression() instanceof InstanceofExpression) {
					sumInstance ++;
				}
			}
			
			
			
			for (TypeDeclaration ty : types) {
				collectChanges(astRoot, ty);
			}

			TextEdit edits = astRoot.rewrite(document, cu.getJavaProject().getOptions(true));
			TextFileChange change = new TextFileChange("", (IFile) cu.getResource());
			change.setEdit(edits);
			changeManager.add(change);

		}
//		System.out.println("Instance数量：" +sumInstance);
//		System.out.println("!Instanceof数量"+sumnegateInstance);
	}

	private void collectChanges(ICompilationUnit cu) throws JavaModelException {
		// create a document
		String source = "";
		try {
			source = cu.getSource();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		Document document = new Document(source);

		// creation of DOM/AST from a ICompilationUnit
		ASTParser parser = ASTParser.newParser(AST.JLS12);
		parser.setSource(cu);
		parser.setResolveBindings(true);
		CompilationUnit astRoot = (CompilationUnit) parser.createAST(null);

		// creation of ASTRewrite
		final ASTRewrite rewrite = ASTRewrite.create(astRoot.getAST());
		astRoot.recordModifications();
		List<TypeDeclaration> types = new ArrayList<TypeDeclaration>();
		getTypes(astRoot.getRoot(), types);
		for (TypeDeclaration ty : types) {
			collectChanges(astRoot, ty);
		}

//		TextEdit edits = astRoot.rewrite(document, cu.getJavaProject().getOptions(true));
//		TextFileChange change = new TextFileChange("", (IFile) cu.getResource());
//		change.setEdit(edits);
//		changeManager.add(change);

		TextEdit edits = rewrite.rewriteAST(document, cu.getJavaProject().getOptions(true));
		TextFileChange change = new TextFileChange("", (IFile) cu.getResource());
		change.setEdit(edits);
		// changeManager.manage(cu, change);
	}

	private void getTypes(ASTNode cuu, final List types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(TypeDeclaration node) {
				types.add(node);
				return false;
			}
		});
	}
	
	private void getIf(ASTNode cuu,List<IfStatement> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(IfStatement node) {
				types.add(node);
				return true;
			}
		});
	}
	private void getype(ASTNode cuu,List<TypeDeclaration> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(TypeDeclaration node) {
				types.add(node);
				return false;
			}
		});
	}
	private void getMethod(ASTNode cuu,List<MethodDeclaration> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(MethodDeclaration node) {
				types.add(node);
				return true;
			}
		});
	}
	private void getif(ASTNode cuu,List<IfStatement> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(IfStatement node) {
				types.add(node);
				return false;
			}
		});
	}
	private void getfor(ASTNode cuu,List<ForStatement> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(ForStatement node) {
				types.add(node);
				return true;
			}
		});
	}
	private void getEquals(ASTNode cuu, final List types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(MethodDeclaration node) {
				types.add(node);
				return true;
			}
		});
	}
/*	
	private void getIfStatement(ASTNode cuu, final List IfStatement) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(IfStatement node) {
				IfStatement.add(node);
				return true;
			}
		});
	}
	
*/
	
	
	private boolean collectChanges(CompilationUnit root, TypeDeclaration types)throws IllegalArgumentException {
		AST ast = types.getAST();
		// 获取类中所有方法
//		LongParameterList longParameterList=new LongParameterList();
//		longParameterList.LongParameterList(types, ast,root);
//		 MethodToLong methodToLong=new MethodToLong();
//		 methodToLong.methodtolong(types,ast);
//		LongParametertest longParametertest=new LongParametertest();
//		longParametertest.LongParametertest(types, ast, root);
//		LongParameterList2 longParameterList2=new LongParameterList2();
//		longParameterList2.LongParameterList(types, ast, root);

		
		
		//大脑方法
//		singleclass singleclass=new singleclass();
//      singleclass.singclass(types, ast);
		timetest timetest=new timetest();
		timetest.timetest(types, ast);
		
		//继承关系
//		  getextends getextend=new getextends();
//		  getextend.getextends(root,types);
//		  Getif getif=new Getif();
//		  getif.getif(root, types, getextend.maplist);
		//上帝类
//		Godclass godclass=new Godclass();
//		godclass.godclass(types,ast);
		
		
		//上帝类2
//		goodclass goodclass=new goodclass();
//		goodclass.godclass(types, ast);
//		List<List<Integer>> locaList=new ArrayList<List<Integer>>();
//		 getype(root, typeDeclarations);
//		 for(int i=0;i<AnnotationRefactoring.exList.size();i++) {
//			 for(int j=i+1;j<AnnotationRefactoring.exList.size();j++) {
//				 if(AnnotationRefactoring.exList.get(i).equals(AnnotationRefactoring.exList.get(j))) {				 
//					 List<Integer> numberIntegers=new ArrayList<Integer>();
//					 numberIntegers.add(i);
//					 numberIntegers.add(j);
//					 locaList.add(numberIntegers);
//				 }
//				
//			 }
//		 }
	
	return true;
}

private List<ICompilationUnit> findAllCompilationUnits(IJavaElement project) {

	List<ICompilationUnit> cus = new ArrayList<ICompilationUnit>();

	try {
		if(project instanceof IJavaProject) {
			IJavaProject iJ=(IJavaProject)project;
		for (IJavaElement element : iJ.getChildren()) { // IPackageFragmentRoot
				IPackageFragmentRoot root = (IPackageFragmentRoot) element;
				for (IJavaElement ele : root.getChildren()) {
					if (ele instanceof IPackageFragment) {
						IPackageFragment fragment = (IPackageFragment) ele;
						for (ICompilationUnit unit : fragment.getCompilationUnits()) {
							cus.add(unit);
						}
				 }
			  }
			}
		}else if(project instanceof IPackageFragmentRoot) {
			IPackageFragmentRoot root=(IPackageFragmentRoot)project;
			for (IJavaElement ele : root.getChildren()) {
				if (ele instanceof IPackageFragment) {
					IPackageFragment fragment = (IPackageFragment) ele;
					for (ICompilationUnit unit : fragment.getCompilationUnits()) {
						cus.add(unit);
					}
			 }
		  }	
			
		}else if(project instanceof IPackageFragment) {
			IPackageFragment fragment = (IPackageFragment)project;
			for (ICompilationUnit unit : fragment.getCompilationUnits()) {
				cus.add(unit);
			}
		}else if(project instanceof ICompilationUnit) {
			ICompilationUnit unit=(ICompilationUnit)project;
			cus.add(unit);
		}
	} catch (JavaModelException e) {
		e.printStackTrace();
	}
	return cus;
}
}

