/**
 * 
 */
package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import mazeGenerators.algorithms.GrowingTreeGenerator;
import mazeGenerators.algorithms.randomCellChooser;
import model.MyModel;
import presenter.Presenter;
import view.MazeWindow;
import view.MyView;

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
		MazeWindow view=new MazeWindow("maze example", 500, 300, new GrowingTreeGenerator(new randomCellChooser()).generate(5, 5, 5));		
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
