package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;

public class Maze3DDisplay extends MazeDisplayer {

	private void paintCube(double[] p,double h,PaintEvent e){
		int[] f=new int[p.length];
		for(int k=0;k<f.length;f[k]=(int)Math.round(p[k]),k++);

		e.gc.drawPolygon(f);

		int[] r=f.clone();
		for(int k=1;k<r.length;r[k]=f[k]-(int)(h),k+=2);


		int[] b={r[0],r[1],r[2],r[3],f[2],f[3],f[0],f[1]};
		e.gc.drawPolygon(b);
		int[] fr={r[6],r[7],r[4],r[5],f[4],f[5],f[6],f[7]};
		e.gc.drawPolygon(fr);

		e.gc.fillPolygon(r);

	}
	public Maze3DDisplay(Composite parent, int style, Maze3d maze, Position curPosition) {
		super(parent, style, maze, curPosition);

		final Color white=new Color(null, 255, 255, 255);
		final Color black=new Color(null, 150,150,150);
		final Color something=new Color(null, 255,10,10);
		final Color something2=new Color(null, 50,50,50);
		
		setBackground(white);
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (curFloor != curPosition.getZ()) {
					curFloor = curPosition.getZ();
					mazeData = maze.getCrossSectionByZ(curFloor);
				}
				
				e.gc.setForeground(new Color(null,0,0,0));
				e.gc.setBackground(new Color(null,0,0,0));

				int width=getSize().x;
				int height=getSize().y;

				int mx=width/2;

				double w=(double)width/mazeData[0].length;
				double h=(double)height/mazeData.length;

				for(int i=0;i<mazeData.length;i++){
					double w0=0.7*w +0.3*w*i/mazeData.length;
					double w1=0.7*w +0.3*w*(i+1)/mazeData.length;
					double start=mx-w0*mazeData[i].length/2;
					double start1=mx-w1*mazeData[i].length/2;
					for(int j=0;j<mazeData[i].length;j++){
						double []dpoints={start+j*w0,i*h,start+j*w0+w0,i*h,start1+j*w1+w1,i*h+h,start1+j*w1,i*h+h};
						double cheight=h/2;
						if(mazeData[i][j]!=0)
							paintCube(dpoints, cheight,e);

						if(i==curPosition.getY() && j==curPosition.getX()){
							e.gc.setBackground(new Color(null,200,0,0));
							e.gc.fillOval((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
							e.gc.setBackground(new Color(null,255,0,0));
							e.gc.fillOval((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
							e.gc.setBackground(new Color(null,0,0,0));				        	  
						}
					}
					
					if (curPosition == maze.getGoalPosition()) {
						//TODO tell user it's the end of the maze
					}
					
				}

			}
		});
	}

	private void moveCharacter(Position pos){
		if(maze.isValidPosition(pos)){
			curPosition = pos;
			getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					redraw();
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveUp()
	 */
	@Override
	public void moveUp() {
		Position p = maze.getAbovePosition(curPosition);
		if (p != null) {
			moveCharacter(p);
		} //TODO maybe return error msg (?)
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveDown()
	 */
	@Override
	public void moveDown() {
		Position p = maze.getBelowPosition(curPosition);
		if (p != null) {
			moveCharacter(p);
		} //TODO maybe return error msg (?)
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveLeft()
	 */
	@Override
	public void moveLeft() {
		Position p = maze.getLeftPosition(curPosition);
		if (p != null) {
			moveCharacter(p);
		} //TODO maybe return error msg (?)
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveRight()
	 */
	@Override
	public void moveRight() {
		Position p = maze.getRightPosition(curPosition);
		if (p != null) {
			moveCharacter(p);
		} //TODO maybe return error msg (?)
	}
	
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveForward()
	 */
	@Override
	public void moveForward() {
		Position p = maze.getForwardPosition(curPosition);
		if (p != null) {
			moveCharacter(p);
		} //TODO maybe return error msg (?)
	}
	
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveBackward()
	 */
	@Override
	public void moveBackward() {
		Position p = maze.getBackwardPosition(curPosition);
		if (p != null) {
			moveCharacter(p);
		} //TODO maybe return error msg (?)
	}

	@Override
	public void setCharacterPosition(int row, int col, int flo) {
		Position p = new Position(row, col, flo);
		if (p != null) {
			moveCharacter(p);
		} //TODO maybe return error msg (?)
	}

}
