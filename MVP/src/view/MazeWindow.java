package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

import algorithms.search.Solution;
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
		
		setVisibleFalse(mazeDisplayer);

		
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
				solveMaze("full_solution");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		//
		getHint.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				solveMaze("hint");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
				
	}
	
	private void setVisibleTrue(MazeDisplayer mazeDisplayer) {
		mazeDisplayer.setVisible(true);
		System.out.println(mazeDisplayer.isVisible());
	}
	private void setVisibleFalse(MazeDisplayer mazeDisplayer) {
		mazeDisplayer.setVisible(false);
		System.out.println(mazeDisplayer.isVisible());
	}
	
	@Override
	public void display(String s) {
		// TODO Auto-generated method stub
		System.out.println(s);
	}

	@Override
	public void generateMaze(String name, int rows, int cols, int flos, String alg) {
		setVisibleTrue(mazeDisplayer);
		mazeName = name;
		setChanged();
		notifyObservers("generate_maze " + name + " " + rows + " " + cols + " " + flos + " " + alg);
	}
	
	@Override
	public void solveMaze(String method) {
		//TODO change start position to cur position
		setChanged();
		notifyObservers("solve " + mazeName + " DFS");
	}
	

	@Override
	public void displayMaze(Maze3d maze3d) {
		System.out.println("Got new maze to display");
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
		// TODO Auto-generated method stub
		String direction = getDirection(mazeDisplayer.curPosition, solution.getSolution().get(1).getState());
		
	}

	@Override
	public void displaySolution(Solution<Position> solution) {
		// TODO Auto-generated method stub
		
	}
	
	private String getDirection(Position p1, Position p2) {
		if (p1.getX() == p2.getX() - 1) {
			return "Left";
		}
		else if (p1.getX() == p2.getX() + 1) {
			return "Right";
		}
		else if (p1.getY() == p2.getY() - 1) {
			return "Up";
		}
		else if (p1.getY() == p2.getY() + 1) {
			return "Down";
		}
		else if (p1.getZ() == p2.getZ() - 1) {
			return "Above";
		}
		else if (p1.getZ() == p2.getZ() + 1) {
			return "Below";
		}
		return null;
	}
	
}
