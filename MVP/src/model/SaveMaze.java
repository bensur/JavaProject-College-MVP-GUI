/**
 * 
 */
package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import controller.Controller;
import io.MyCompressorOutputStream;
import mazeGenerators.algorithms.Maze3d;

/**
 * @author Ben Surkiss & Yovel Shchori
 *
 */
public class SaveMaze implements Runnable {
	private String mazeName;
	private String fileName;
	private HashMap<String, Maze3d> mazes;
	private Model model;
	
	/**
	 * C'tor
	 * @param mazeName to save
	 * @param fileName to save to
	 * @param mazes to get maze from
	 * @param model to use
	 */
	public SaveMaze(String mazeName, String fileName, HashMap<String, Maze3d> mazes, Model model) {
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
		if (!mazes.containsKey(mazeName))
			model.display("No such maze " + mazeName);
		else {
			OutputStream out;
			try {
				out = new MyCompressorOutputStream(new FileOutputStream(fileName));
				out.write(mazes.get(mazeName).toByteArray());
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				model.display("Cannot access '" + fileName + "': No such file");
				e.printStackTrace();
			} catch (IOException e) {
				model.display("IOException occured while saving to '" + fileName + "'");
				e.printStackTrace();
			}
		}
	}
}