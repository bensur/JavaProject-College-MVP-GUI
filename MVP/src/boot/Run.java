/**
 * 
 */
package boot;

import mazeGenerators.algorithms.GrowingTreeGenerator;
import mazeGenerators.algorithms.RandomPositionChooser;
import model.MyModel;
import presenter.Presenter;
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
		
		MazeWindow view=new MazeWindow("maze example", 500, 300, new GrowingTreeGenerator(new RandomPositionChooser()).generate(5, 5, 5));		
		MyModel model = new MyModel();
		
		Presenter presenter = new Presenter(view, model);
		model.addObserver(presenter);
		view.addObserver(presenter);
		
		view.start();
		
	}

}
