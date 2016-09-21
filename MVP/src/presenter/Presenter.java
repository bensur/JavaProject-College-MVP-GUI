/**
 * 
 */
package presenter;

import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

/**
 * @author bensu
 *
 */
public class Presenter implements Observer {
	private View view;
	private Model model;
	
	public Presenter(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		String[] stringArg = (String[])arg;
		if (o == view) {
			model.doSomething(stringArg);
		}
		else if (o == model) {
			switch (stringArg[0]) {
			case "print":
				view.display(stringArg[1]);
				break;
			default:
				view.display("No such command: " + stringArg[0]);
				break;
			}
		}
		else {
			throw new IllegalArgumentException();
		}
	}

}
