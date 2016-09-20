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
			notifyObservers("No argument given");
			return;
		} else if (!(args[0] instanceof String)) {
			notifyObservers("Invalid arguments");
			return;
		}
		switch ((String)args[0]) {
		case "generate_maze":
			if (args.length != 6) {
				notifyObservers("generate_maze command need 5 arguments:\ngenerate_maze <MAZE_NAME> <FLOORS> <ROWS> <COLUMNS> <ALGORITHM>");
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
				notifyObservers("solve command need 2 arguments:\nsolve <MAZE_NAME> <ALGORITHM>");
			} else {
				String[] stringArgs = (String[])args;
				String mazeName = stringArgs[1];
				String alg = stringArgs[2];
				if (!mazes.containsKey(mazeName)) {
					notifyObservers("No such maze " + args[1]);
				} else {
					executor.execute(new SolveMaze(this, mazes.get(mazeName), mazeName, alg));
				}
			}
			break;
		case "dir":
			if (args.length != 2) {
				notifyObservers("dir command need 1 argument:\ndir <PATH>");
			} else {
				String[] stringArgs = (String[])args;
				String path = stringArgs[1];
				//TODO
				executor.execute(new Dir(this, path));
			}
			break;
		case "display":
			if (args.length != 2) {
				notifyObservers("display command need 1 argument:\ndisplay <MAZE_NAME>");
			} else {
				String[] stringArgs = (String[])args;
				String mazeName = stringArgs[1];
				//TODO
				executor.execute(new Display(this, mazeName));
			}
			break;
		case "display_cross_section":
			if (args.length != 2) {
				notifyObservers("display_cross_section command need 3 arguments:\ndisplay_cross_section <MAZE_NAME> <AXIS> <INDEX>");
			} else {
				String[] stringArgs = (String[])args;
				String mazeName = stringArgs[1];
				char axis = (stringArgs[2]).toCharArray()[0];
				int index = Integer.parseInt(stringArgs[3]);
				//TODO
				executor.execute(new DisplayCrossSection(this, mazeName, axis, index, mazes));
			}
			break;
		case "save_maze":
			if (args.length != 3) {
				notifyObservers("save_maze command need 2 arguments:\nsave_maze <MAZE_NAME> <FILE_NAME>");
			} else {
				String[] stringArgs = (String[])args;
				String mazeName = stringArgs[1];
				String fileName = stringArgs[2];
				executor.execute(new SaveMaze(mazeName, fileName, mazes, this));
			}
			break;
		case "load_maze":
			if (args.length != 3) {
				notifyObservers("load_maze command need 2 arguments:\nsave_maze <MAZE_NAME> <FILE_NAME>");
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
		default:
			notifyObservers((String)args[0] + ": command not found");
			break;
		}
	}

	/**
	 * 
	 */
	@Override
	public void notifyObservers(String string) {
		notifyObservers(string);
	}
}