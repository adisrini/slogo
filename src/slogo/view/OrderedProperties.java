package slogo.view;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * A class that orders properties alphabetically.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
@SuppressWarnings("serial")
public class OrderedProperties extends Properties {

	/**
	 * Initializes the class.
	 */
    public OrderedProperties() {
        super ();

        _names = new Vector<Object>();
    }

    /**
     * Gets the names in order.
     */
    public Enumeration<Object> propertyNames() {
        return _names.elements();
    }

    /**
     * Puts an object into the properties file.
     */
    public Object put(Object key, Object value) {
        if (_names.contains(key)) {
            _names.remove(key);
        }

        _names.add(key);

        return super .put(key, value);
    }

    /**
     * Removes an object from the properties file.
     */
    public Object remove(Object key) {
        _names.remove(key);

        return super .remove(key);
    }

    private Vector<Object> _names;

}
