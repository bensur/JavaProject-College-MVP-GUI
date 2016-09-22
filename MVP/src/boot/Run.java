/**
 * 
 */
package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
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
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
				
		MyView view = new MyView(in, out);
		MyModel model = new MyModel();
		
		Presenter presenter = new Presenter(view, model);
		model.addObserver(presenter);
		view.addObserver(presenter);
				
		view.start();

	}

}
