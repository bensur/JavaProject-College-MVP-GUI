/**
 * 
 */
package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
//		// Creates new thread using anonymous
//				Thread t = new Thread()
//			    {
//			        public void run() {
//			        	try {
//			    			String cliInput;
//			    			// Run as long as user did not entered 'exit'
//			    			do {
//			    				display("Please enter command");
//			    				cliInput = in.readLine();
//			    				// Split user input by ' '
//			    				String[] arr = cliInput.split(" ");
//			    				// Set commands as first argument
//			    				setChanged();
//			    				notifyObservers(arr);
//			    			} while (!cliInput.equals("exit"));
//			    		} catch (IOException e) {
//			    			e.printStackTrace();
//			    		}
//			        }
//			    }; 
//			    // Start the thread
//			    t.start();
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
