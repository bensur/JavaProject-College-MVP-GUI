/**
 * 
 */
package model;

import java.beans.XMLDecoder;
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
import presenter.Properties;
import presenter.PropertiesLoader;

/**
 * @author Ben Surkiss & Yovel Shchori
 * 
 */
public class MyModel extends Observable implements Model {
	String generateAlg = PropertiesLoader.getInstance().getProperties().getGenerateMazeAlgorithm();
	String solveAlg = PropertiesLoader.getInstance().getProperties().getSolveMazeAlgorithm();
	private HashMap<String, Maze3d> mazes = new HashMap<String, Maze3d>();
	private HashMap<String, Solution<Position>> solutions = new HashMap<String, Solution<Position>>();
	private HashMap<Maze3d, Solution<Position>> solutionsForMazes = new HashMap<Maze3d, Solution<Position>>();
	private ExecutorService executor = Executors.newFixedThreadPool(PropertiesLoader.getInstance().getProperties().getNumOfThreads());

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
	public void generateMaze(String name, int floors, int rows, int cols) {
		executor.submit(new Callable<Maze3d>() { //TODO return Future<V>

			@Override
			public Maze3d call() throws Exception {
				Maze3dGenerator gen;
				Maze3d maze;
				// Choose algorithm based on given alg
				switch (generateAlg) {
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
					throw new IllegalArgumentException("No such algorithm '" + generateAlg + "'");
				}
				// Generate maze and add to model
				maze = gen.generate(rows, cols, floors);
				mazes.put(name, maze);
				setChanged();
				notifyObservers("maze_ready " + name);
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
	public void solveMaze(String mazeName, String method) {
		Future<Solution<Position>> fSolution = executor.submit(new Callable<Solution<Position>>() { //TODO return Future<V>

			@Override
			public Solution<Position> call() throws Exception {
				Searcher<Position> search;
				switch (solveAlg) {
				case "BFS":
					search = new BFS<Position>();
					break;
				case "DFS":
					search = new DFS<Position>();
					break;
				default:
					throw new IllegalArgumentException("No such algorithm '" + solveAlg + "'");
				}
				Solution<Position> sol = search.search(new SearchableMaze3d(mazes.get(mazeName)));

				solutions.put(mazeName, sol);
				solutionsForMazes.put(mazes.get(mazeName), sol);
				setChanged();
				notifyObservers("solution_ready_for " + mazeName + " " + method);	

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
			executor.awaitTermination(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void openXML(String file) {
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));
			Properties loadedProperties = (Properties)decoder.readObject();
			decoder.close();
			Properties globalProperties = PropertiesLoader.getInstance().getProperties();
			globalProperties.setGenerateMazeAlgorithm(loadedProperties.getGenerateMazeAlgorithm());
			globalProperties.setNumOfThreads(loadedProperties.getNumOfThreads());
			globalProperties.setSolveMazeAlgorithm(loadedProperties.getSolveMazeAlgorithm());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}