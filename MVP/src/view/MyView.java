/**
 * 
 */
package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Observable;

/**
 * @author Ben Surkiss & Yovel Shchori
 *
 */
public class MyView extends Observable implements View {
	private BufferedReader in;
	private PrintWriter out;
	
	public MyView() {
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = new PrintWriter(System.out);
	}
	
	/**
	 * start method from the view, calls the start method of the cli
	 */
	public void start() {
		// Creates new thread using anonymous
				Thread t = new Thread()
			    {
			        public void run() {
			        	try {
			    			String cliInput;
			    			// Run as long as user did not entered 'exit'
			    			do {
			    				display("Please enter command");
			    				cliInput = in.readLine();
			    				// Split user input by ' '
			    				String[] arr = cliInput.split(" ");
			    				// Set commands as first argument
			    				notifyObservers(arr);
			    			} while (!cliInput.equals("exit"));
			    		} catch (IOException e) {
			    			e.printStackTrace();
			    		}
			        }
			    }; 
			    // Start the thread
			    t.start();
	}
	
	/**
	 * print data from the view
	 * @param s String to print
	 */
	public void display(String s) {
		out.print(s);
		out.flush();
	}
}
