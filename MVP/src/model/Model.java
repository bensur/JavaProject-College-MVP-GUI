/**
 * 
 */
package model;

/**
 * @author bensu
 *
 */
public interface Model {
	/**
	 * 
	 * @param args
	 */
	public void doSomething(Object[] args);
	
	/**
	 * 
	 * @param string
	 */
	public void notifyObservers(String string);
}
