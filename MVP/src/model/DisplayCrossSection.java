/**
 * 
 */
package model;

import java.util.HashMap;

import mazeGenerators.algorithms.Maze3d;

/**
 * @author yschori
 *
 */
public class DisplayCrossSection implements Runnable {
	private Model model;
	private String mazeName;
	private HashMap<String, Maze3d> mazes;
	private char axis;
	private int index;
	
	/**
	 * 
	 * @param model
	 * @param mazeName
	 * @param mazes
	 * @param axis
	 * @param index
	 */
	public DisplayCrossSection(Model model, String mazeName, HashMap<String, Maze3d> mazes, char axis, int index) {
		this.model = model;
		this.mazeName = mazeName;
		this.mazes = mazes;
		this.axis = axis;
		this.index = index;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		StringBuilder sb = new StringBuilder();
		if (!mazes.containsKey(mazeName))
			sb.append("No such maze " + mazeName);
		else {
			Maze3d maze = mazes.get(mazeName);
			int[][] maze2d;
			if (axis == 'X')
				maze2d = maze.getCrossSectionByX(index);
			else if (axis == 'Y')
				maze2d = maze.getCrossSectionByY(index);
			else if (axis == 'Z')
				maze2d = maze.getCrossSectionByZ(index);
			else
				throw new IllegalArgumentException("No such axis " + axis);
			sb.append(maze2dToString(maze2d));
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
