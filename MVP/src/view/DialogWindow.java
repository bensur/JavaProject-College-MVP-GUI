/**
 * 
 */
package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Abstract class for all dialog windows to use 
 * @author Ben Surkiss & Yovel Shchori
 */
public abstract class DialogWindow {
	protected Shell shell;	
	/**
	 * Initialize all desired GUI components in the dialog window
	 */
	protected abstract void initWidgets();
	/**
	 * Start dialog window
	 * @param display to use
	 */
	public void start(Display display) {		
		shell = new Shell(display);
		initWidgets();
		shell.open();		
	}
}
