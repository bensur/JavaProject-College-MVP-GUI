/**
 * 
 */
package view;

import java.util.Observable;

/**
 * @author bensu
 *
 */
public interface View {
	public void start();
	public void display(String s);
	void update(Observable o, Object arg);
	
}
