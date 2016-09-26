/**
 * 
 */
package view;

import algorithms.search.Solution;
import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;

/**
 * @author bensu
 *
 */
public interface View {
	public void start();
	public void display(String s);
	
	public void generateMaze(String name, int rows, int cols, int flos);
	public void displayMaze(Maze3d maze3d);
	public void solveMaze(String method);
	public void displayHint(Solution<Position> solution);
	public void displaySolution(Solution<Position> solution);
	
}
