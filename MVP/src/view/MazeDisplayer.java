package view;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;


/**
 * Canvas to represent Maze game
 * @author Ben Surkiss and Yovel Shchori
 */
public abstract class MazeDisplayer extends Canvas{
	protected Maze3d maze;
	protected Position curPosition;
	protected int curFloor;
	protected int[][] mazeData;
	/**
	 * C'tor
	 * @param parent to use
	 * @param style to use
	 * @param maze to use
	 * @param curPosition to set
	 */
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
	/**
	 * Set maze
	 * @param maze to set
	 */
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
	/**
	 * Set current position
	 * @param curPosition to set
	 */
	public void setCurPosition(Position curPosition) {
		this.curPosition = curPosition;
	}
	/**
	 * Set character position
	 * @param row to set
	 * @param col to set
	 * @param flors to set
	 */
	public abstract  void setCharacterPosition(int row,int col, int flors);
	/**
	 * Move character up
	 */
	public abstract void moveUp();
	/**
	 * Move character down
	 */
	public abstract  void moveDown();
	/**
	 * Move character left
	 */
	public abstract  void moveLeft();
	/**
	 * Move character right
	 */
	public  abstract void moveRight();
	/**
	 * Move character forward
	 */
	public  abstract void moveForward();
	/**
	 * Move character backward
	 */
	public  abstract void moveBackward();
	/**
	 * Popup winner
	 */
	public abstract void popUpWinner();
	/**
	 * Popup hint
	 * @param str hint
	 */
	public abstract void popUpHint(String str);
}