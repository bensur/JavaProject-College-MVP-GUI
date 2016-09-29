/**
 * 
 */
package boot;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;

import mazeGenerators.algorithms.GrowingTreeGenerator;
import mazeGenerators.algorithms.RandomPositionChooser;
import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import presenter.PropertiesLoader;
import view.MazeWindow;

/**
 * @author yschori
 *
 */
public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		PrintWriter out = new PrintWriter(System.out);
		
		Properties properties = new Properties();
		properties.setGenerateMazeAlgorithm("GrowingTreeRand");
		properties.setSolveMazeAlgorithm("DFS");
		properties.setNumOfThreads(3);
		
		try {
			XMLEncoder os = new XMLEncoder(new FileOutputStream("properties.xml"));
			os.writeObject(properties);
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Properties properties2 = PropertiesLoader.getInstance().getProperties();
		
		
		MazeWindow view=new MazeWindow("maze example", 500, 300, new GrowingTreeGenerator(new RandomPositionChooser()).generate(5, 5, 5));		
//		MyView view = new MyView(in, out);
		MyModel model = new MyModel();
		
		Presenter presenter = new Presenter(view, model);
		model.addObserver(presenter);
		view.addObserver(presenter);
		
		view.start();
		

//		public static void main(String[] args) {
//			MazeWindow win=new MazeWindow("maze example", 500, 300, new GrowingTreeGenerator(new randomCellChooser()).generate(5, 5, 5));
//			win.run();
//		}
		
	}

}
