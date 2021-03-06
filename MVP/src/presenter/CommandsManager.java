package presenter;

import java.io.File;
import java.util.HashMap;

import algorithms.search.Solution;
import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;
import model.Model;
import view.View;
/**
 * Hold all commads as inner classes so they can 'know' command manager model and view
 * @author Ben Surkiss and Yovel Shchori
 */
public class CommandsManager {
	protected Model model;
	protected View view;
	/**
	 * C'tor
	 * @param model to set
	 * @param view to set
	 */
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	/**
	 * Gets the commands map
	 * @return commands HashMap
	 */
	public HashMap<String, Command> getCommands() {
		HashMap<String, Command> commands = new HashMap<String, Command>();
		commands.put("dir", new Dir());
		commands.put("generate_maze", new GenerateMaze());
		commands.put("display", new Display());
		commands.put("display_cross_section", new DisplayCrossSection());
		commands.put("save_maze", new SaveMaze());
		commands.put("load_maze", new LoadMaze());
		commands.put("solve", new SolveMaze());
		commands.put("display_solution", new DisplaySolution());
		commands.put("print", new Print());
		commands.put("solution_ready_for", new SolutionReady());
		commands.put("maze_loaded", new MazeLoaded());
		commands.put("maze_ready", new MazeReady());
		commands.put("exit", new Exit());
		commands.put("open_xml", new OpenXML());
		return commands;
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * dir command, displays the folders and files in the given directory
	 */
	public class Dir implements Command {
		private File file;
		private String[] filesList;
		/* (non-Javadoc)
		 * @see controller.Command#doCommand()
		 */
		@Override
		public void doCommand(String[] args) {
			if ((args == null) || (args.length != 1))
				view.display("dir command need 1 argument:\ndir <PATH>");
			else {
				this.file = new File(args[0]);
				if(file.isDirectory()) {
					filesList = file.list();
					for (int i = 0; i < filesList.length; i++) {
						view.display(filesList[i]);
					}
				}
				else {
					view.display("Not a directory");
				}
			}
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * display command, display the maze of the given maze name
	 */
	public class Display implements Command {
		private String mazeName;
		
		/* (non-Javadoc)
		 * @see controller.Command#doCommand()
		 */
		@Override
		public void doCommand(String args[]) {
			if ((args == null) || (args.length != 1))
				view.display("display command need 1 argument:\ndisplay <MAZE_NAME>");
			else {
				this.mazeName = args[0];
				HashMap <String, Maze3d> mazes = model.getMazes();
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
				// Print using controller print method
				view.display(sb.toString());
			}
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * display_cross_section command, displays the cross section maze of the given maze name, axis and index
	 */
	public class DisplayCrossSection implements Command {
		private String mazeName;
		private HashMap<String, Maze3d> mazes;
		private char axis;
		private int index;
		/* (non-Javadoc)
		 * @see controller.Command#doCommand()
		 */
		@Override
		public void doCommand(String args[]) {
			if ((args == null) || (args.length != 3))
				view.display("display_cross_section command need 3 arguments:\ndisplay_cross_section <MAZE_NAME> <AXIS> <INDEX>");
			else {
				this.mazeName = args[0];
				this.mazes = model.getMazes();
				this.axis = (args[1]).toCharArray()[0];
				this.index = Integer.parseInt(args[2]);
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
				view.display(sb.toString());
			}
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * display_solution command, displays the solution of the maze of the given maze name
	 */
	
	public class DisplaySolution implements Command {
		String mazeName;
		/* (non-Javadoc)
		 * @see controller.Command#doCommand()
		 */
		@Override
		public void doCommand(String args[]) {
			if ((args == null) || (args.length != 1))
				view.display("display_solution command need 1 argument:\ndisplay_solution <MAZE_NAME>");
			else {
				this.mazeName = args[0];
				Solution<Position> sol = model.getSolution(model.getMazes().get(mazeName));
				if (sol != null) {
					view.display(sol.toString());
				}
				else {
					view.display("No solution found for maze " + mazeName);
				}
			}
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * exit command, exits the cli
	 */
	public class Exit implements Command {
		/* (non-Javadoc)
		 * @see controller.Command#doCommand()
		 */
		@Override
		public void doCommand(String args[]) {
			model.exit();
		}
	}
	
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * generate_maze command, generates a maze with the given maze name, floors number, rows number, columns number and a generating algorithm
	 */
	public class GenerateMaze implements Command {
		private String mazeName;
		private int floors;
		private int rows;
		private int columns;
		/* (non-Javadoc)
		 * @see controller.Command#doCommand()
		 */
		@Override
		public void doCommand(String args[]) {
			if ((args == null) || (args.length != 4))
				view.display("generate_maze command need 4 arguments:\ngenerate_maze <MAZE_NAME> <FLOORS> <ROWS> <COLUMNS>");
			else {
				this.mazeName = args[0];
				this.floors = Integer.parseInt(args[1]);
				this.rows = Integer.parseInt(args[2]);
				this.columns = Integer.parseInt(args[3]);
				model.generateMaze(mazeName, floors, rows, columns);
			}
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * load_maze command, loads a saved and compressed maze of the given maze name into a given file name
	 */
	public class LoadMaze implements Command {
		private String mazeName;
		private String fileName;
		/* (non-Javadoc)
		 * @see controller.Command#doCommand()
		 */
		@Override
		public void doCommand(String args[]) {
			if ((args == null) || (args.length != 2))
				view.display("load_maze command need 2 arguments:\nload_maze <MAZE_NAME> <FILE_NAME>");
			else {
				this.mazeName = args[0];
				this.fileName = args[1];
				model.loadMaze(mazeName, fileName);
			}
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * save_maze command, compresses and saves a maze of the given maze name into a given file name
	 */
	public class SaveMaze implements Command {
		private String mazeName;
		private String fileName;
		/* (non-Javadoc)
		 * @see controller.Command#doCommand()
		 */
		@Override
		public void doCommand(String args[]) {
			if ((args == null) || (args.length != 2))
				view.display("save_maze command need 2 arguments:\nsave_maze <MAZE_NAME> <FILE_NAME>");
			else {
				this.mazeName = args[0];
				this.fileName = args[1];
				model.saveMaze(mazeName, fileName);
			}
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * solve command, solves the maze of the given maze name using the given algorithm
	 */
	public class SolveMaze implements Command {
		private String mazeName;
		private String method;
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand(String args[]) {
			if ((args == null) || (args.length != 2))
				view.display("solve command need 2 arguments:\nsolve <MAZE_NAME> <METHOD>");
			else {
				this.mazeName = args[0];
				this.method = args[1];
				model.solveMaze(mazeName, method);
			}
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * Print given arguments in view
	 */
	public class Print implements Command {
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand(String[] args) {
			if (args == null) {
				return;
			}
			StringBuilder sb = new StringBuilder();
			for (String s : args)
				sb.append(s + " ");
			view.display(sb.toString());
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * Pass solution to view based on given method
	 */
	public class SolutionReady implements Command {
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand(String[] args) {
			if ((args == null) || (args.length != 2)) {
				view.display("solution_ready_for command need 2 argument:\nsolution_ready_for <MAZE_NAME> <METHOD>");
				return;
			}
			if (args[1].equals("hint")) {
				view.displayHint(model.getSolution(model.getMazes().get(args[0])));
			}
			else {
				view.displaySolution(model.getSolution(model.getMazes().get(args[0])));
			}
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * Display loaded maze in view
	 */
	public class MazeLoaded implements Command {
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand(String[] args) {
			if ((args == null) || (args.length != 1)) {
				view.display("maze_loaded command need 1 argument:\nmaze_loaded <MAZE_NAME>");
				return;
			}
			view.displayMaze(model.getMazes().get(args[0]));
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * Display maze in view
	 */
	public class MazeReady implements Command {
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand(String[] args) {
			if ((args == null) || (args.length != 1)) {
				view.display("maze_ready command need 1 argument:\nmaze_ready <MAZE_NAME>");
				return;
			}
			view.displayMaze(model.getMazes().get(args[0]));
		}
	}
	/**
	 * @author Ben Surkiss and Yovel Shchori
	 * Load given XML to program
	 */
	public class OpenXML implements Command {
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand(String[] args) {
			if ((args == null) || (args.length != 1)) {
				view.display("open_xml command need 1 argument:\nopen_xml <XML_FILE_NAME>");
				return;
			}
			model.openXML(args[0]);
		}
	}
	/**
	 * Private method to convert 2d int maze to String
	 * @param maze 2d int representation of maze
	 */
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
