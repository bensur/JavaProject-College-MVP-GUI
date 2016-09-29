/**
 * 
 */
package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

/**
 * Presenter in MVP architecture
 * @author Ben Surkiss and Yovel Shchori
 */
public class Presenter implements Observer {
	private View view;
	@SuppressWarnings("unused") // Used in commandsManager
	private Model model;
	private CommandsManager commandsManager;
	private HashMap<String, Command> commands;
	/**
	 * C'tor
	 * @param view to use
	 * @param model to use
	 */
	public Presenter(View view, Model model) {
		this.view = view;
		this.model = model;
		this.commandsManager = new CommandsManager(model, view);
		this.commands = commandsManager.getCommands();
	}
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) { //TODO use model and view if
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