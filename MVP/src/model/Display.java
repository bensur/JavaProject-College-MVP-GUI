/**
 * 
 */
package model;

import java.util.HashMap;

import algorithms.search.SearchableMaze3d;
import mazeGenerators.algorithms.Maze3d;

/**
 * @author yschori
 *
 */
public class Display implements Runnable {
	private Model model;
	private String mazeName;
	private HashMap <String, Maze3d> mazes;
	
	/**
	 * 
	 * @param model
	 * @param mazeName
	 */
	public Display(Model model, String mazeName, HashMap <String, Maze3d> mazes) {
		this.model = model;
		this.mazeName = mazeName;
		this.mazes = mazes;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// Add error msg if no such mazeName
		StringBuilder sb = new StringBuilder();
		if (!mazes.containsKey(mazeName)) {
			sb.append("No such maze " + mazeName);
		} else { // Convert maze to string
			Maze3d maze = mazes.get(mazeName);
			for (int z = 0 ; z < maze.getFlos() -1 ; z++) {
				sb.append(maze2dToString(maze.getCrossSectionByZ(z)) + "\n");
			}
			sb.append(maze2dToString(maze.getCrossSectionByZ(maze.getFlos() -1)));
		}
		model.display(sb.toString());
	}
	
	private String maze2dToString(int[][] maze) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for (int i = 0 ; i < (maze.length - 1) ; i++) {
			sb.append("\t{");
			for (int j = 0 ; j < (maze[i].length - 1) ; j++)
				sb.append(maze[i][j] + ", ");
			sb.append(maze[i][maze[i].length - 1] + "},\n");
		}
		sb.append("\t{");
		for (int i = 0 ; i < (maze[maze.length - 1].length - 1) ; i++)
			sb.append(maze[maze.length - 1][i] + ", ");
		sb.append(maze[maze.length - 1][maze[maze.length - 1].length - 1] + "}\n");
		sb.append("}");
		return sb.toString();
	}

}
