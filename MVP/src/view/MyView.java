/**
 * 
 */
package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Ben Surkiss & Yovel Shchori
 *
 */
public class MyView extends Observable implements View, Observer {
	private BufferedReader in;
	private PrintWriter out;
	private CLI cli;
	
	public MyView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		
		cli = new CLI(in, out);
		cli.addObserver(this);
	}
	
	/**
	 * start method from the view, calls the start method of the cli
	 */
	public void start() {
		cli.start();
	}
	
	/**
	 * print data from the view
	 * @param s String to print
	 */
	public void display(String s) {
		out.println(s);
		out.flush();
	}
	

	@Override
	public void update(Observable o, Object arg) {
		if (o == cli) {
			setChanged();
			notifyObservers(arg);
		}
	}
}
