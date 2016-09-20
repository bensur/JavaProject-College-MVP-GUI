/**
 * 
 */
package model;

import java.io.File;

/**
 * @author yschori
 *
 */
public class Dir implements Runnable {
	private Model model;
	private File file;
	private String[] filesList;
	
	/**
	 * 
	 * @param model
	 * @param file
	 * @param fileslist
	 */
	public Dir(Model model, File file, String[] fileslist) {
		this.model = model;
		this.file = file;
		this.filesList = fileslist;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(file.isDirectory()) {
			filesList = file.list();
			for (int i = 0; i < filesList.length; i++) {
				model.notifyObservers(filesList[i]);
			}
		}
		else {
			model.notifyObservers("Not a directory");
		}
	}
}
