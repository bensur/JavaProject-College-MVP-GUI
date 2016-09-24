/**
 * 
 */
package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

/**
 * @author yschori
 *
 */
public class GenerateMazeWindow extends DialogWindow {
	View view;
	
	public GenerateMazeWindow(View view) {
		this.view = view;
	}
	
	/* (non-Javadoc)
	 * @see view.DialogWindow#initWidgets()
	 */
	@Override
	protected void initWidgets() {
		shell.setText("Generate maze window");
		shell.setSize(300, 200);		
				
		shell.setLayout(new GridLayout(2, false));	
				
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		
		Text txtRows = new Text(shell, SWT.BORDER);
		txtRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Columns: ");
		
		Text txtCols = new Text(shell, SWT.BORDER);
		txtCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				
		Label lblFlos = new Label(shell, SWT.NONE);
		lblFlos.setText("Floors: ");
		
		Text txtFlos = new Text(shell, SWT.BORDER);
		txtFlos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblAlg = new Label(shell, SWT.NONE);
		lblAlg.setText("Algorithm: ");
		
		Text txtAlg = new Text(shell, SWT.BORDER);
		txtAlg.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name: ");
		
		Text txtName = new Text(shell, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Button btnGenerateMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(btnGenerateMaze);
		btnGenerateMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnGenerateMaze.setText("Generate maze");
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("Title");
				//msg.setMessage("Button was clicked");
				int rows = Integer.parseInt(txtRows.getText());
				int cols = Integer.parseInt(txtCols.getText());
				int flos = Integer.parseInt(txtFlos.getText());
				String name = txtName.getText();
				String alg = txtAlg.getText();

				view.generateMaze(name, rows, cols, flos, alg);
				
				msg.setMessage("Generating maze: " + name + " with rows: " + rows + " cols: " + cols + " floors: " + flos + " using algorithm: " + alg);				
				msg.open();
				
				
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {			
				
			}
		});	

	}

}
