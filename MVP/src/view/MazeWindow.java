package view;

import java.util.Observable;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

import mazeGenerators.algorithms.GrowingTreeGenerator;
import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.randomCellChooser;

public class MazeWindow extends BasicWindow implements View{

	Maze3d maze;
	
	public MazeWindow(String title, int width, int height, Maze3d maze) {
		super(title, width, height);
		this.maze = maze;
	}
	
	private void randomWalk(MazeDisplayer maze){
		Random r=new Random();
		boolean b1,b2;
		b1=r.nextBoolean();
		b2=r.nextBoolean();
		if(b1&&b2)
			maze.moveUp();
		if(b1&&!b2)
			maze.moveDown();
		if(!b1&&b2)
			maze.moveRight();
		if(!b1&&!b2)
			maze.moveLeft();
		
		maze.redraw();
	}
	
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		
		
		//
		Button generateMaze=new Button(shell, SWT.PUSH);
		generateMaze.setText("Generate Maze");
		generateMaze.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		
		//
		MazeDisplayer mazeDisplayer=new Maze3DDisplay(shell, SWT.BORDER, maze, maze.getStartPosition());
		mazeDisplayer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,3));
		
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
				//TODO
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		//
		solveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//TODO
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		//
		getHint.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//TODO
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
	}
	
	public static void main(String[] args) {
		MazeWindow win=new MazeWindow("maze example", 500, 300, new GrowingTreeGenerator(new randomCellChooser()).generate(5, 5, 5));
		win.run();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
