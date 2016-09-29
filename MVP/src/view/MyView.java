/**
 * 
 */
package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

import algorithms.search.Solution;
import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;

/**
 * View implementation for MVP architecture
 * @author Ben Surkiss & Yovel Shchori
 */
public class MyView extends Observable implements View, Observer {
	@SuppressWarnings("unused") //Used in CLI
	private BufferedReader in;
	private PrintWriter out;
	private CLI cli;
	/**
	 * C'tor
	 * @param in input stream to use
	 * @param out output stream to use
	 */
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

	@Override
	public void generateMaze(String name, int rows, int cols, int flos) {
		setChanged();
		notifyObservers("generate_maze " + name + " " + rows + " " + cols + " " + flos);
	}

	@Override
	public void displayMaze(Maze3d maze3d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayHint(Solution<Position> solution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolution(Solution<Position> solution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void solveMaze(String method) {
		// TODO Auto-generated method stub
		
	}
}
