package view;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;


// this is (1) the common type, and (2) a type of widget
// (1) we can switch among different MazeDisplayers
// (2) other programmers can use it naturally
public abstract class MazeDisplayer extends Canvas{
	
	protected Maze3d maze;
	protected Position curPosition;
	protected int curFloor;
	protected int[][] mazeData;
	
	public MazeDisplayer(Composite parent, int style, Maze3d maze, Position curPosition) {
		super(parent, style);
		this.maze = maze;
		this.curPosition = curPosition;
		this.curFloor = curPosition.getZ();
		this.mazeData = maze.getCrossSectionByZ(curFloor);
				
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.keyCode) {
				case SWT.ARROW_RIGHT:					
					moveRight();
					redraw();
					break;
				
				case SWT.ARROW_LEFT:
					moveLeft();
					redraw();
					break;
					
				case SWT.ARROW_UP:					
					moveBackward();
					redraw();
					break;
				
				case SWT.ARROW_DOWN:					
					moveForward();
					redraw();
					break;
				
				case SWT.PAGE_UP:					
					moveUp();
					redraw();
					break;
					
				case SWT.PAGE_DOWN:					
					moveDown();
					redraw();
					break;
				}
			}
		});
		
	}

	public void setMaze(Maze3d maze) {
		this.maze = maze;
		this.curPosition = this.maze.getStartPosition();
		this.curFloor = this.curPosition.getZ();
		this.mazeData = this.maze.getCrossSectionByZ(this.curFloor);
		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				redraw();
			}
		});
	}
	
	public void setCurPosition(Position curPosition) {
		this.curPosition = curPosition;
	}
	
	public abstract  void setCharacterPosition(int row,int col, int flors);
	
	public abstract void moveUp();

	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();
	
	public  abstract void moveForward();
	
	public  abstract void moveBackward();
	
}