/**
 * 
 */
package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.SearchableMaze3d;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import mazeGenerators.algorithms.GrowingTreeGenerator;
import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Maze3dGenerator;
import mazeGenerators.algorithms.Position;
import mazeGenerators.algorithms.SimpleMaze3dGenerator;
import mazeGenerators.algorithms.lastCellChooser;
import mazeGenerators.algorithms.randomCellChooser;

/**
 * @author Ben Surkiss & Yovel Shchori
 *
 */
public class MyModel extends Observable implements Model {
	private HashMap<String, Maze3d> mazes = new HashMap<String, Maze3d>();
	private HashMap<String, Solution<Position>> solutions = new HashMap<String, Solution<Position>>();
	private ExecutorService executor = Executors.newCachedThreadPool();
	
//	public MyModel() {
//		properties = PropertiesLoader.getInstance().getProperties();
//		executor = Executors.newFixedThreadPool(properties.getNumOfThreads());
//		loadSolutions();
//	}
	
	@Override
	public HashMap<String, Maze3d> getMazes() {
		return this.mazes;
	}
	
	@Override
	public HashMap<String, Solution<Position>> getSolutions() {
		return this.solutions;
	}
	
	@Override
	public void generateMaze(String name, int floors, int rows, int cols, String alg) {
		executor.submit(new Callable<Maze3d>() { //TODO return Future<V>

			@Override
			public Maze3d call() throws Exception {
				Maze3dGenerator gen;
				Maze3d maze;
				// Choose algorithm based on given alg
				switch (alg) {
				case "GrowingTreeRand":
					gen = new GrowingTreeGenerator(new randomCellChooser());
					break;
				case "GrowingTreeLast":
					gen = new GrowingTreeGenerator(new lastCellChooser());
					break;
				case "Simple":
					gen = new SimpleMaze3dGenerator();
					break;
				default:
					throw new IllegalArgumentException("No such algorithm '" + alg + "'");
				}
				// Generate maze and add to model
				maze = gen.generate(rows, cols, floors);
				
				setChanged();
				notifyObservers("maze_ready " + name);	
				
				mazes.put(name, maze);
				return maze;
			}
			
		});
			
	}
	
	@Override
	public void loadMaze(String mazeName, String fileName) {
		Future<Maze3d> fMaze = executor.submit(new Callable<Maze3d>() { //TODO return Future<V>

			@Override
			public Maze3d call() throws Exception {
				if (mazes.containsKey(mazeName))
					display("Maze '" + mazeName + "' already exist!");
				else {
					InputStream in;
					try {
						in = new MyDecompressorInputStream(new FileInputStream(fileName));
						byte[] sizeArr = new byte[4];
						for (int i = 0; i < sizeArr.length; i++)
							sizeArr[i] = (byte) in.read();
						int size = ((sizeArr[0] * sizeArr[1]) + (sizeArr[2] * sizeArr[3]));
						byte b[] = new byte[size];
						in.read(b);
						in.close();
						addMaze(mazeName, new Maze3d(b));
					} catch (FileNotFoundException e) {
						display("Cannot access '" + fileName + "': No such file");
						e.printStackTrace();
					} catch (IOException e) {
						display("IOException occured while loading from '" + fileName + "'");
						e.printStackTrace();
					}
				}
				setChanged();
				notifyObservers("maze_loaded " + mazeName);
				return mazes.get(mazeName);	
			}
		});
	
	}
	
	@Override
	public void saveMaze(String mazeName, String fileName) {
		executor.submit(new Callable<Maze3d>() { //TODO return Future<V>

			@Override
			public Maze3d call() throws Exception {
				if (!mazes.containsKey(mazeName))
					display("No such maze " + mazeName);
				else {
					OutputStream out;
					try {
						out = new MyCompressorOutputStream(new FileOutputStream(fileName));
						out.write(mazes.get(mazeName).toByteArray());
						out.flush();
						out.close();
					} catch (FileNotFoundException e) {
						display("Cannot access '" + fileName + "': No such file");
						e.printStackTrace();
					} catch (IOException e) {
						display("IOException occured while saving to '" + fileName + "'");
						e.printStackTrace();
					}
				}
				return mazes.get(mazeName);	
			}
		});
	}
	
	@Override
	public void solveMaze(String mazeName, String alg) {
		Future<Solution<Position>> fSolution = executor.submit(new Callable<Solution<Position>>() { //TODO return Future<V>

			@Override
			public Solution<Position> call() throws Exception {
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
				Solution<Position> sol = search.search(new SearchableMaze3d(mazes.get(mazeName)));
				
				setChanged();
				notifyObservers("solution_ready_for " + mazeName);	
				
				solutions.put(mazeName, sol);
				return sol;				
			}	
		});
	}

	/**
	 * 
	 */
	@Override
	public void display(String string) {
		setChanged();
		notifyObservers("print " + string);
	}
	
	@Override
	public void addMaze(String mazeName, Maze3d maze) {
		if (mazes.containsKey(mazeName))
			display("Maze " + mazeName + " already exist!");
		else
			mazes.put(mazeName, maze);
	}
	
	@Override
	public void addMazeSolution(String mazeName, Solution<Position> solution) {
		if (solutions.containsKey(mazeName))
			display("Solution for maze " + mazeName + " already exist!");
		else
			solutions.put(mazeName, solution);
	}
	
	@Override
	public void exit() {
		try {
			executor.awaitTermination(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//	String mazeName = (String)args[1];
	//	Maze3d maze = (Maze3d)args[2];
	//	if (mazes.containsKey(mazeName))
	//		display("Maze " + mazeName + " already exist!");
	//	else
	//		mazes.put(mazeName, maze);
	//	break;
	//case "add_solution":
	//	String solvedMazeName = (String)args[1];
	//	Solution<Position> solution = (Solution<Position>)args[2];
	//	if (solutions.containsKey(solvedMazeName))
	//		display("Solution of maze " + solvedMazeName + " already exist!");
	//	else
	//		solutions.put(solvedMazeName, solution);
	//	break;
	//default:
	//	display((String)args[0] + ": command not found");
	//	break;
	
//	@SuppressWarnings("unchecked")
//	private void loadSolutions() {
//		File file = new File("solutions.dat");
//		if (!file.exists())
//			return;
//		
//		ObjectInputStream ois = null;
//		
//		try {
//			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solutions.dat")));
//			mazes = (Map<String, Maze2d>)ois.readObject();
//			solutions = (Map<String, Solution<Position>>)ois.readObject();		
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally{
//			try {
//				ois.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}		
//	}
	
}
	

//	/**
//	 * 
//	 */
//	@Override
//	public void doSomething(Object[] args) {
//		if ((args == null) || (args[0] == null)) {
//			display("No argument given");
//			return;
//		} else if (!(args[0] instanceof String)) {
//			display("Invalid arguments");
//			return;
//		}
//		switch ((String)args[0]) {
//		case "generate_maze":
//			if (args.length != 6) {
//				display("generate_maze command need 5 arguments:\ngenerate_maze <MAZE_NAME> <FLOORS> <ROWS> <COLUMNS> <ALGORITHM>");
//			} else {
//				String[] stringArgs = (String[])args;
//				String mazeName = stringArgs[1];
//				int floors = Integer.parseInt(stringArgs[2]);
//				int rows = Integer.parseInt(stringArgs[3]);
//				int columns = Integer.parseInt(stringArgs[4]);
//				String alg = stringArgs[5];
//				executor.execute(new GenerateMaze(mazeName, floors, rows, columns, alg, this));
//			}
//			break;
//		case "solve":
//			if (args.length != 3) {
//				display("solve command need 2 arguments:\nsolve <MAZE_NAME> <ALGORITHM>");
//			} else {
//				String[] stringArgs = (String[])args;
//				String mazeName = stringArgs[1];
//				String alg = stringArgs[2];
//				if (!mazes.containsKey(mazeName)) {
//					display("No such maze " + args[1]);
//				} else {
//					executor.execute(new SolveMaze(this, mazes.get(mazeName), mazeName, alg));
//				}
//			}
//			break;
//		case "dir":
//			if (args.length != 2) {
//				display("dir command need 1 argument:\ndir <PATH>");
//			} else {
//				String[] stringArgs = (String[])args;
//				String path = stringArgs[1];
//				executor.execute(new Dir(this, path));
//			}
//			break;
//		case "display":
//			if (args.length != 2) {
//				display("display command need 1 argument:\ndisplay <MAZE_NAME>");
//			} else {
//				String[] stringArgs = (String[])args;
//				String mazeName = stringArgs[1];
//				executor.execute(new Display(this, mazeName, mazes));
//			}
//			break;
//		case "display_cross_section":
//			if (args.length != 2) {
//				display("display_cross_section command need 3 arguments:\ndisplay_cross_section <MAZE_NAME> <AXIS> <INDEX>");
//			} else {
//				String[] stringArgs = (String[])args;
//				String mazeName = stringArgs[1];
//				char axis = (stringArgs[2]).toCharArray()[0];
//				int index = Integer.parseInt(stringArgs[3]);
//				executor.execute(new DisplayCrossSection(this, mazeName, mazes, axis, index));
//			}
//			break;
//		case "save_maze":
//			if (args.length != 3) {
//				display("save_maze command need 2 arguments:\nsave_maze <MAZE_NAME> <FILE_NAME>");
//			} else {
//				String[] stringArgs = (String[])args;
//				String mazeName = stringArgs[1];
//				String fileName = stringArgs[2];
//				executor.execute(new SaveMaze(mazeName, fileName, mazes, this));
//			}
//			break;
//		case "load_maze":
//			if (args.length != 3) {
//				display("load_maze command need 2 arguments:\nsave_maze <MAZE_NAME> <FILE_NAME>");
//			} else {
//				String[] stringArgs = (String[])args;
//				String mazeName = stringArgs[1];
//				String fileName = stringArgs[2];
//				executor.execute(new LoadMaze(mazeName, fileName, mazes, this));
//			}
//			break;
//		case "exit":
//			try {
//				executor.awaitTermination(5, TimeUnit.SECONDS);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			break;
//		case "add_maze":
//			String mazeName = (String)args[1];
//			Maze3d maze = (Maze3d)args[2];
//			if (mazes.containsKey(mazeName))
//				display("Maze " + mazeName + " already exist!");
//			else
//				mazes.put(mazeName, maze);
//			break;
//		case "add_solution":
//			String solvedMazeName = (String)args[1];
//			Solution<Position> solution = (Solution<Position>)args[2];
//			if (solutions.containsKey(solvedMazeName))
//				display("Solution of maze " + solvedMazeName + " already exist!");
//			else
//				solutions.put(solvedMazeName, solution);
//			break;
//		default:
//			display((String)args[0] + ": command not found");
//			break;
//		}
//	}
	