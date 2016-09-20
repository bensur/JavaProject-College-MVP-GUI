/**
 * 
 */
package model;

import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.SearchableMaze3d;
import algorithms.search.Searcher;
import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;

/**
 * @author Ben Surkiss & Yovel Shchori
 *
 */
public class SolveMaze implements Runnable {
	private Model model;
	private Maze3d maze;
	private String mazeName;
	private String alg;
	
	/**
	 * c'tor
	 * @param model model to set
	 * @param maze maze to set
	 * @param mazeName mazeName to set
	 * @param alg alg to set
	 */
	public SolveMaze(Model model, Maze3d maze, String mazeName, String alg) {
		this.model = model;
		this.maze = maze;
		this.mazeName = mazeName;
		this.alg = alg;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Searcher<Position> search;
		switch (alg) {
		case "BFS":
			search = new BFS<Position>();
			break;
		case "DFS":
			search = new DFS<Position>();
			break;
		default:
			throw new IllegalArgumentException("No such algorithm '" + alg + "'");
		}
		model.doSomething(new Object[]{"add_maze_solution", mazeName, search.search(new SearchableMaze3d(maze))}); //TODO

	}

}
