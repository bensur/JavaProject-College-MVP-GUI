/**
 * 
 */
package model;

import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import algorithms.search.Solution;
import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;

/**
 * @author Ben Surkiss & Yovel Shchori
 *
 */
public class MyModel extends Observable implements Model {
	private HashMap<String, Maze3d> mazes = new HashMap<String, Maze3d>();
	private HashMap<String, Solution<Position>> solutions = new HashMap<String, Solution<Position>>();
	private ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * 
	 */
	@Override
	public void doSomething(Object[] args) {
		if ((args == null) || (args[0] == null)) {
			display("No argument given");
			return;
		} else if (!(args[0] instanceof String)) {
			display("Invalid arguments");
			return;
		}
		switch ((String)args[0]) {
		case "generate_maze":
			if (args.length != 6) {
				display("generate_maze command need 5 arguments:\ngenerate_maze <MAZE_NAME> <FLOORS> <ROWS> <COLUMNS> <ALGORITHM>");
			} else {
				String[] stringArgs = (String[])args;
				String mazeName = stringArgs[1];
				int floors = Integer.parseInt(stringArgs[2]);
				int rows = Integer.parseInt(stringArgs[3]);
				int columns = Integer.parseInt(stringArgs[4]);
				String alg = stringArgs[5];
				executor.execute(new GenerateMaze(mazeName, floors, rows, columns, alg, this));
			}
			break;
		case "solve":
			if (args.length != 3) {
				display("solve command need 2 arguments:\nsolve <MAZE_NAME> <ALGORITHM>");
			} else {
				String[] stringArgs = (String[])args;
				String mazeName = stringArgs[1];
				String alg = stringArgs[2];
				if (!mazes.containsKey(mazeName)) {
					display("No such maze " + args[1]);
				} else {
					executor.execute(new SolveMaze(this, mazes.get(mazeName), mazeName, alg));
				}
			}
			break;
		case "dir":
			if (args.length != 2) {
				display("dir command need 1 argument:\ndir <PATH>");
			} else {
				String[] stringArgs = (String[])args;
				String path = stringArgs[1];
				executor.execute(new Dir(this, path));
			}
			break;
		case "display":
			if (args.length != 2) {
				display("display command need 1 argument:\ndisplay <MAZE_NAME>");
			} else {
				String[] stringArgs = (String[])args;
				String mazeName = stringArgs[1];
				executor.execute(new Display(this, mazeName, mazes));
			}
			break;
		case "display_cross_section":
			if (args.length != 2) {
				display("display_cross_section command need 3 arguments:\ndisplay_cross_section <MAZE_NAME> <AXIS> <INDEX>");
			} else {
				String[] stringArgs = (String[])args;
				String mazeName = stringArgs[1];
				char axis = (stringArgs[2]).toCharArray()[0];
				int index = Integer.parseInt(stringArgs[3]);
				executor.execute(new DisplayCrossSection(this, mazeName, mazes, axis, index));
			}
			break;
		case "save_maze":
			if (args.length != 3) {
				display("save_maze command need 2 arguments:\nsave_maze <MAZE_NAME> <FILE_NAME>");
			} else {
				String[] stringArgs = (String[])args;
				String mazeName = stringArgs[1];
				String fileName = stringArgs[2];
				executor.execute(new SaveMaze(mazeName, fileName, mazes, this));
			}
			break;
		case "load_maze":
			if (args.length != 3) {
				display("load_maze command need 2 arguments:\nsave_maze <MAZE_NAME> <FILE_NAME>");
			} else {
				String[] stringArgs = (String[])args;
				String mazeName = stringArgs[1];
				String fileName = stringArgs[2];
				executor.execute(new LoadMaze(mazeName, fileName, mazes, this));
			}
			break;
		case "exit":
			try {
				executor.awaitTermination(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		case "add_maze":
			String mazeName = (String)args[1];
			Maze3d maze = (Maze3d)args[2];
			if (mazes.containsKey(mazeName))
				display("Maze " + mazeName + " already exist!");
			else
				mazes.put(mazeName, maze);
			break;
		case "add_solution":
			String solvedMazeName = (String)args[1];
			Solution<Position> solution = (Solution<Position>)args[2];
			if (solutions.containsKey(solvedMazeName))
				display("Solution of maze " + solvedMazeName + " already exist!");
			else
				solutions.put(solvedMazeName, solution);
			break;
		default:
			display((String)args[0] + ": command not found");
			break;
		}
	}

	/**
	 * 
	 */
	@Override
	public void display(String string) {
		setChanged();
		notifyObservers(string);
	}
}