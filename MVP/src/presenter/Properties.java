/**
 * 
 */
package presenter;

import java.io.Serializable;

/**
 * Properties object to represent 
 * @author Ben Surkiss & Yovel Shchori
 */
public class Properties implements Serializable {
	private static final long serialVersionUID = 1L;
	private int numOfThreads;
	private String generateMazeAlgorithm;
	private String solveMazeAlgorithm;
	/**
	 * Getter for numOfThreads
	 * @return number of threads
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}
	/**
	 * Setter for numOfThreads
	 * @param numOfThreads to set
	 */
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}
	/**
	 * Getter for generateMazeAlgorithm
	 * @return generateMazeAlgorithm
	 */
	public String getGenerateMazeAlgorithm() {
		return generateMazeAlgorithm;
	}
	/**
	 * Setter for generateMazeAlgorithm
	 * @param generateMazeAlgorithm to set
	 */
	public void setGenerateMazeAlgorithm(String generateMazeAlgorithm) {
		this.generateMazeAlgorithm = generateMazeAlgorithm;
	}
	/**
	 * Getter for solveMazeAlgorithm
	 * @return solveMazeAlgorithm
	 */
	public String getSolveMazeAlgorithm() {
		return solveMazeAlgorithm;
	}
	/**
	 * Setter for solveMazeAlgorithm
	 * @param solveMazeAlgorithm to set
	 */
	public void setSolveMazeAlgorithm(String solveMazeAlgorithm) {
		this.solveMazeAlgorithm = solveMazeAlgorithm;
	}
}