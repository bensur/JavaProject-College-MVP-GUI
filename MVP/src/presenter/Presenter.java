/**
 * 
 */
package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import controller.Command;
import model.Model;
import view.View;

/**
 * @author bensu
 *
 */
public class Presenter implements Observer {
	private View view;
	private Model model;
	private CommandsManager commandsManager;
	private HashMap<String, Command> commands;
	
	public Presenter(View view, Model model) {
		this.view = view;
		this.model = model;
		
		commandsManager = new CommandsManager(model, view);
		commands = commandsManager.getCommands();
	}
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		String commandLine = (String)arg;
		String arr[] = commandLine.split(" ");
		String command = arr[0];	

		if(!commands.containsKey(command)) {
			view.display(command + ": command not found");
		}
		else {
			String[] args = null;
			if (arr.length > 1) {
				String commandArgs = commandLine.substring(
						commandLine.indexOf(" ") + 1);
				args = commandArgs.split(" ");							
			}
			Command cmd = commands.get(command);
			cmd.doCommand(args);	
		}
		
	}

}
