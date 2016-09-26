package view;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.search.Solution;
import algorithms.search.State;
import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;

public class MazeWindow extends BasicWindow implements View{

	private Maze3d maze;
	private View view = this;
	private MazeDisplayer mazeDisplayer;
	private String mazeName;
	
	public MazeWindow(String title, int width, int height, Maze3d maze) {
		super(title, width, height);
		this.maze = maze;
	}
	
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		
		//
		Button generateMaze=new Button(shell, SWT.PUSH);
		generateMaze.setText("Generate Maze");
		generateMaze.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		
		//
		mazeDisplayer=new Maze3DDisplay(shell, SWT.BORDER, maze, maze.getStartPosition());
		mazeDisplayer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,3));
		
//		setVisibleFalse(mazeDisplayer);

		
		Button solveMaze=new Button(shell, SWT.PUSH);
		solveMaze.setText("Solve Maze");
		solveMaze.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		
		Button getHint=new Button(shell, SWT.PUSH);
		getHint.setText("Get Hint");
		getHint.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		
		
		//
		generateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeWindow win = new GenerateMazeWindow(view);
				win.start(display);
				//TODO
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		//
		solveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				maze.setStartPosition(mazeDisplayer.curPosition);
				solveMaze("full_solution");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		//
		getHint.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				maze.setStartPosition(mazeDisplayer.curPosition);
				solveMaze("hint");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
				
	}
	
	private void setVisibleTrue(MazeDisplayer mazeDisplayer) {
		mazeDisplayer.setVisible(true);
	}
	private void setVisibleFalse(MazeDisplayer mazeDisplayer) {
		mazeDisplayer.setVisible(false);
	}
	
	@Override
	public void display(String s) {
		// TODO Auto-generated method stub
		System.out.println(s);
	}

	@Override
	public void generateMaze(String name, int rows, int cols, int flos, String alg) {
//		setVisibleTrue(mazeDisplayer);
		mazeName = name;
		setChanged();
		notifyObservers("generate_maze " + name + " " + rows + " " + cols + " " + flos + " " + alg);
	}
	
	@Override
	public void solveMaze(String method) {
		//TODO change start position to cur position
		setChanged();
		notifyObservers("solve " + mazeName + " DFS " + method);
	}
	

	@Override
	public void displayMaze(Maze3d maze3d) {
		maze = maze3d;
		mazeDisplayer.setMaze(maze);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		run();
	}

	@Override
	public void displayHint(Solution<Position> solution) {
		String direction = getDirection(mazeDisplayer.curPosition, solution.getSolution().get(1).getState());
		System.out.println("Move " + direction); //TODO: show in message box
//		MessageBox msg = new MessageBox(shell, SWT.OK);
//		msg.setText("Hint");
//		msg.setMessage("Move " + direction + " :)");
//		msg.open();
	}

	@Override
	public void displaySolution(Solution<Position> solution) {
		List<State<Position>> solutionList = solution.getSolution();
		for (int i = 0 ; i < solutionList.size() - 1 ; i++) {
			switch (getDirection(solutionList.get(i).getState(), solutionList.get(i+1).getState())) {
			case "Left":
				mazeDisplayer.moveLeft();
				break;
			case "Right":
				mazeDisplayer.moveRight();
				break;
			case "Up":
				mazeDisplayer.moveBackward();
				break;
			case "Down":
				mazeDisplayer.moveForward();
				break;
			case "Above":
				mazeDisplayer.moveUp();
				break;
			case "Below":
				mazeDisplayer.moveDown();
				break;
			default:
				break;
			}
		}
	}
	
	private String getDirection(Position p1, Position p2) {
		if (p1.getX() == p2.getX() - 1) {
			return "Down";
		}
		else if (p1.getX() == p2.getX() + 1) {
			return "Up";
		}
		else if (p1.getY() == p2.getY() - 1) {
			return "Right";
		}
		else if (p1.getY() == p2.getY() + 1) {
			return "Left";
		}
		else if (p1.getZ() == p2.getZ() + 1) {
			return "Below";
		}
		else if (p1.getZ() == p2.getZ() - 1) {
			return "Above";
		}
		return null;
	}
	
}
