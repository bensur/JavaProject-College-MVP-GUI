/**
 * 
 */
package view;

import algorithms.search.Solution;
import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;

/**
 * View interface for view
 * @author Ben Surkiss and Yovel Shchori
 */
public interface View {
	/**
	 * Start view
	 */
	public void start();
	/**
	 * Display given string
	 * @param s string to display
	 */
	public void display(String s);
	/**
	 * Generate maze by given arguments
	 * @param name of maze
	 * @param rows to set
	 * @param cols to set
	 * @param flos to set
	 */
	public void generateMaze(String name, int rows, int cols, int flos);
	/**
	 * Display given maze
	 * @param maze3d to display
	 */
	public void displayMaze(Maze3d maze3d);
	/**
	 * Solve maze for given method
	 * @param method to forward
	 */
	public void solveMaze(String method);
	/**
	 * Display hint for given solution
	 * @param solution to get hint from
	 */
	public void displayHint(Solution<Position> solution);
	/**
	 * Display given solution
	 * @param solution to display
	 */
	public void displaySolution(Solution<Position> solution);
}
