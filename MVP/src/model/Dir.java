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
	public Dir(Model model, String path) {
		this.model = model;
		this.file = new File(path);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(file.isDirectory()) {
			filesList = file.list();
			for (int i = 0; i < filesList.length; i++) {
				model.display(filesList[i]);
			}
		}
		else {
			model.display("Not a directory");
		}
	}
}
