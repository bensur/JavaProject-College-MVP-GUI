package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
/**
 * CLI object in MVP architecture
 * @author Ben Surkiss and Yovel Shchori
 */
public class CLI extends Observable {
	private BufferedReader in;
	private PrintWriter out;	
	/**
	 * C'tor
	 * @param in input stream to use
	 * @param out output stream to use
	 */
	public CLI(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;		
	}
	/**
	 * Print menue for the user
	 */
	private void printMenu() {
		out.print("Choose command: ");
		out.flush();
	}
	/**
	 * Start the CLI
	 */
	public void start() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					printMenu();
					try {
						String commandLine = in.readLine();
						setChanged();
						notifyObservers(commandLine);
						
						if (commandLine.equals("exit"))
							break;
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}			
		});
		thread.start();		
	}
}