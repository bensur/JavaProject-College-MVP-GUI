package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.ietf.jgss.MessageProp;

import mazeGenerators.algorithms.Maze3d;
import mazeGenerators.algorithms.Position;

public class Maze3DDisplay extends MazeDisplayer {
	/**
	 * 
	 * @param p
	 * @param h
	 * @param e
	 */
	private void paintCube(double[] p, double h, PaintEvent e){
		
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
	/**
	 * 
	 * @param parent
	 * @param style
	 * @param maze3d
	 * @param startPos
	 */
	public Maze3DDisplay(Composite parent, int style, Maze3d maze3d, Position startPos) {
		super(parent, style, maze3d, startPos);

		final Color white = new Color(null, 255, 255, 255);
		final Color black = new Color(null, 0,0,0);
		final Color red = new Color(null,255,0,0);
		final Color darkRed = new Color(null,200,0,0);
		final Image startImage = new Image(getDisplay(), "start.png");
		final Image endImage = new Image(getDisplay(), "end.png");
		
		setBackground(white);
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (curFloor != curPosition.getZ()) {
					curFloor = curPosition.getZ();
					mazeData = maze.getCrossSectionByZ(curFloor);
				}
				e.gc.setForeground(black);
				e.gc.setBackground(black);

				int width=getSize().x;
				int height=getSize().y;

				int mx=width/2;

				double w=(double)width/mazeData[0].length;
				double h=(double)height/mazeData.length;
				
				Position startPos = maze.getStartPosition();
				Position endPos = maze.getGoalPosition();
				for(int i=0;i<mazeData.length;i++){
					double w0=0.7*w +0.3*w*i/mazeData.length;
					double w1=0.7*w +0.3*w*(i+1)/mazeData.length;
					double start=mx-w0*mazeData[i].length/2;
					double start1=mx-w1*mazeData[i].length/2;
					for(int j=0;j<mazeData[i].length;j++){
						double []dpoints={start+j*w0,i*h,start+j*w0+w0,i*h,start1+j*w1+w1,i*h+h,start1+j*w1,i*h+h};
						double cheight=h/2;
						if (mazeData[i][j] != Maze3d.FREE)
							paintCube(dpoints, cheight,e);
						
						if (i==curPosition.getX() && j==curPosition.getY()){
							e.gc.setBackground(darkRed);
							e.gc.fillOval((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), 
									      (int)Math.round((w0+w1)/2), (int)Math.round(h));
							e.gc.setBackground(red);
							e.gc.fillOval((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), 
									      (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
							e.gc.setBackground(black);				        	  
						}
						// Add start image to start position TODO - fix positioning
						if ((i == startPos.getX()) && (j == startPos.getX()) && (curFloor == startPos.getZ())) {
							e.gc.drawImage(startImage, 0, 0, startImage.getBounds().width, startImage.getBounds().height, 
									       (int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), 
									       (int)Math.round((w0+w1)/2), (int)Math.round(h));
						}
						// Add end image to end position TODO - fix positioning
						if ((i == endPos.getX()) && (j == endPos.getX()) && (curFloor == endPos.getZ())) {
							e.gc.drawImage(endImage, 0, 0, endImage.getBounds().width, endImage.getBounds().height, 
								           (int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), 
								           (int)Math.round((w0+w1)/2), (int)Math.round(h));
						}
					}
				}
				
				if (curPosition.equals(maze.getGoalPosition())) {
					//TODO tell user it's the end of the maze
				}
			}
		});
	}
	/**
	 * Move character to given position
	 * @param pos to move character to
	 */
	private void moveCharacter(Position pos) {
		if (this.maze.isValidPosition(pos)) {
			this.curPosition = pos;
			if (pos.equals(maze.getGoalPosition())) {

				Display.getDefault().syncExec(new Runnable() {
				    public void run() {
				    	popUpWinner();
				    }
				});
				popUpWinner();
				
			}		
			
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
	@Override
	public void popUpWinner() {
		System.out.println("winner!!!");
		Shell shell = new Shell(getDisplay());
	    shell.setText("Win");
	    shell.setSize(450, 430);
	    
	    Image img= new Image(getDisplay(),"happy_cat.png");
	    shell.setBackgroundImage(img);
	    
	    shell.open();
		

	}
	
	public void popUpHint(String str) {
		
		DialogWindow dw = new DialogWindow() {

			@Override
			protected void initWidgets() {
				shell.setText("Hint");
			    shell.setSize(380, 380);
			    
			    
			    Image img= new Image(getDisplay(),"grumpy2.jpg");
			    shell.setBackgroundImage(img);
			    
			    final Label label = new Label(shell, SWT.NONE);
			    label.setText(str);
			    //label.setBounds(100, 150, 100, 150);
			    label.setBounds(160, 0, 1000, 1000);

			    
			    shell.open();
				
			}
		};
		Display display = Display.getDefault();
		dw.start(display);
		
		
		System.out.println(str);
//		// create a dialog with ok and cancel buttons and a question icon
//		
//		MessageBox dialog =
//		        new MessageBox(getShell(), SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
//		dialog.setText("My info");
//		dialog.setMessage("Do you really want to do this?");
//
//		// open dialog and await user selection
//		dialog.open();
		
		
//		Display display = Display.getDefault();
//		if (display == null)
//	         display = Display.getDefault();
//		Shell shell = new Shell(display, SWT.APPLICATION_MODAL);
//	    shell.setText("Hint");
//	    shell.setSize(450, 430);
//	    
//	    
//	    Image img= new Image(getDisplay(),"grumpy_cat.jpg");
//	    shell.setBackgroundImage(img);
//	    
//	    final Label label = new Label(shell, SWT.NONE);
//	    label.setText(str);
//	    label.setBounds(100, 150, 100, 150);
//	    
//	    shell.open();
	    
	    System.out.println(str);
	}
	
}
