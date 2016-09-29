package presenter;
/**
 * Command interface
 * @author Ben Surkiss and Yovel Shchori
 */
public interface Command {
	/**
	 * Do the command
	 * @param args to pass to command
	 */
	void doCommand(String[] args);
}
