package refactoringexample.actions;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import refactoringexample.refactoring.AnnotationRefactoring;
import refactoringexample.ui.AnnotationRefactoringWizard;

public class RefactoringMyActionGroup implements IObjectActionDelegate {
	IJavaElement select;

	@Override
	public void run(IAction action) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		Shell shell = new Shell();
		shell.setSize(500, 150);
		AnnotationRefactoring refactor = new AnnotationRefactoring(select);
		AnnotationRefactoringWizard wizard = new AnnotationRefactoringWizard(refactor);
		RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(wizard);
		try {
			op.run(shell, "Inserting @Test Annotation");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		if(selection.isEmpty()) 
			select=null;
		else if(selection instanceof IStructuredSelection) {
			IStructuredSelection strut=((IStructuredSelection)selection);
			if(strut.size()!=1)
				select=null;
			if(strut.getFirstElement() instanceof IJavaElement) 
				select=(IJavaElement)strut.getFirstElement();
		}else {
			select=null;
		}
		action.setEnabled(true);
        action.setEnabled(select!=null);
	}

	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		// TODO Auto-generated method stub

	}

}
