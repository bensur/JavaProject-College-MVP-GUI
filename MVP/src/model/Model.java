/**
 * 
 */
package model;

import java.util.HashMap;

import algorithms.search.Solution;
import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;

/**
 * Model interface
 * @author Ben Surkiss & Yovel Shchori
 */
public interface Model {

	/**
	 * Display given string in view
	 * @param string to display
	 */
	public void display(String string);
	/**
	 * Generate new maze by given arguments
	 * @param name of new maze
	 * @param floors to create
	 * @param rows to create
	 * @param cols to create
	 */
	public void generateMaze(String name, int floors, int rows, int cols);
	/**
	 * Add given maze to mazes map
	 * @param mazeName to map
	 * @param maze to add
	 */
	public void addMaze(String mazeName, Maze3d maze);
	/**
	 * Add given solution to solutions map
	 * @param mazeName to map solution to
	 * @param solution to add
	 */
	public void addMazeSolution(String mazeName, Solution<Position> solution);
	/**
	 * Save maze from mazes HashMap to given file
	 * @param mazeName to save
	 * @param fileName to save to
	 */
	void saveMaze(String mazeName, String fileName);
	/**
	 * Load maze from given file to mazes HashMap
	 * @param mazeName to load
	 * @param fileName to load from
	 */
	void loadMaze(String mazeName, String fileName);
	/**
	 * Mazes getter
	 * @return Mazes HashMap
	 */
	HashMap<String, Maze3d> getMazes();
	/**
	 * Clean exit
	 */
	void exit();
	/**
	 * Solve given maze (by maze name) and notify observers when ready
	 * @param mazeName to solve
	 * @param method to pass to observers
	 */
	void solveMaze(String mazeName, String method);
	/**
	 * Load properties.xml from given file
	 * @param file to load from
	 */
	public void openXML(String file);
	/**
	 * Get solution for given maze
	 * @param maze to get solution to
	 * @return Solution for given maze
	 */
	public Solution<Position> getSolution(Maze3d maze);
	/**
	 * Save solutions map of model to given file
	 * @param solutionsFile to save map to
	 */
	void saveSolutionsMap(String solutionsFile);
	/**
	 * Load solutions map to model from given file
	 * @param solutionsFile to load from
	 */
	void loadSolutionsMap(String solutionsFile);
}
