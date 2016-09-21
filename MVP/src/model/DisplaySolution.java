/**
 * 
 */
package model;

import java.util.HashMap;

import algorithms.search.Solution;
import mazeGenerators.algorithms.Position;

/**
 * @author yschori
 *
 */
public class DisplaySolution implements Runnable {
	private Model model;
	private String mazeName;
	private HashMap<String, Solution<Position>> solutions;

	/**
	 * 
	 * @param model
	 * @param mazeName
	 * @param solutions
	 */
	public DisplaySolution(Model model, String mazeName, HashMap<String, Solution<Position>> solutions) {
		this.model = model;
		this.mazeName = mazeName;
		this.solutions = solutions;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Solution<Position> sol = solutions.get(mazeName);
		if (solutions.containsKey(mazeName)) {
			model.display(sol.toString());
		}
		else {
			model.display("No solution found for maze " + mazeName);
		}

	}

}
