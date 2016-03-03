package slogo.view;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

@SuppressWarnings("serial")
public class OrderedProperties extends Properties {

    public OrderedProperties() {
        super ();

        _names = new Vector<Object>();
    }

    public Enumeration<Object> propertyNames() {
        return _names.elements();
    }

    public Object put(Object key, Object value) {
        if (_names.contains(key)) {
            _names.remove(key);
        }

        _names.add(key);

        return super .put(key, value);
    }

    public Object remove(Object key) {
        _names.remove(key);

        return super .remove(key);
    }

    private Vector<Object> _names;

}
