/**
 * 
 */
package view;

import java.util.Observable;

import mazeGenerators.algorithms.Maze3d;

/**
 * @author bensu
 *
 */
public interface View {
	public void start();
	public void display(String s);
	
	public void generateMaze(String name, int rows, int cols, int flos, String alg);
	public void displayMaze(Maze3d maze3d);
	
}
