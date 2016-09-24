package view;

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
	}

	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}
	
	public void setCurPosition(Position curPosition) {
		this.curPosition = curPosition;
	}
	
	public abstract  void setCharacterPosition(int row,int col, int flors);

	public abstract void moveUp();

	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();

}