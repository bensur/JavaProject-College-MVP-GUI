/**
 * 
 */
package presenter;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Singleton for Properties object 
 * @author Ben Surkiss and Yovel Shchori
 */
public class PropertiesLoader {
	private static PropertiesLoader instance;
	private Properties properties;
	/**
	 * Getter for properties
	 * @return properties object
	 */
	public Properties getProperties() {
		return properties;
	}
	/**
	 * C'tor
	 */
	private PropertiesLoader() 
	{
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream("resources/properties.xml"));
			properties = (Properties)decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Get instance for singleton
	 * @return instance of this object
	 */
	public static PropertiesLoader getInstance() {
		if (instance == null) 
			instance = new PropertiesLoader();
		return instance;
	}
}
