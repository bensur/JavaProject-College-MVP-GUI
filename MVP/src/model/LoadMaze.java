/**
 * 
 */
package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import io.MyDecompressorInputStream;
import mazeGenerators.algorithms.Maze3d;

/**
 * @author Ben Surkiss & Yovel Shchori
 *
 */
public class LoadMaze implements Runnable {
	private String mazeName;
	private String fileName;
	private HashMap<String, Maze3d> mazes;
	private Model model;
	
	/**
	 * C'tor
	 * @param mazeName to add to mazes
	 * @param fileName to load from
	 * @param mazes HashMap of mazes to add the loaded maze to
	 * @param model to use
	 * @param controller to use
	 */
	public LoadMaze(String mazeName, String fileName, HashMap<String, Maze3d> mazes, Model model) {
		this.mazeName = mazeName;
		this.fileName = fileName;
		this.mazes = mazes;
		this.model = model;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (mazes.containsKey(mazeName))
			model.notifyObservers("Maze '" + mazeName + "' already exist!");
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
				
				model.doSomething(new Object[]{"add_maze", mazeName, new Maze3d(b)}); //TODO
			} catch (FileNotFoundException e) {
				model.notifyObservers("Cannot access '" + fileName + "': No such file");
				e.printStackTrace();
			} catch (IOException e) {
				model.notifyObservers("IOException occured while loading from '" + fileName + "'");
				e.printStackTrace();
			}
		}
	}
}
