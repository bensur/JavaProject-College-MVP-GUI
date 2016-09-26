/**
 * 
 */
package model;

import java.util.HashMap;

import algorithms.search.Solution;
import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;

/**
 * @author bensu
 *
 */
public interface Model {

	//public void doSomething(Object[] args);
	
	/**
	 * 
	 * @param string
	 */
	public void display(String string);
	
	public void generateMaze(String name, int floors, int rows, int cols);

	public void addMaze(String mazeName, Maze3d maze);

	public void addMazeSolution(String mazeName, Solution<Position> solution);

	void saveMaze(String mazeName, String fileName);

	void loadMaze(String mazeName, String fileName);

	HashMap<String, Maze3d> getMazes();

	HashMap<String, Solution<Position>> getSolutions();

	void exit();

	void solveMaze(String mazeName, String method);

	public void openXML(String file);
}
