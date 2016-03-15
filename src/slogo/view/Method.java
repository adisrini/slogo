package slogo.view;

import javafx.beans.property.SimpleStringProperty;

/**
 * The method item that is part of the method table.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class Method {

    private SimpleStringProperty name;
    private SimpleStringProperty value;

    /**
     * Initializes the method object with a name and value.
     * @param name
     * @param value
     */
    public Method(String name, String value) {
        this.name = new SimpleStringProperty();
        this.value = new SimpleStringProperty();

        this.name.set(name);
        setValue(value);
    }

    /**
     * Get the String name.
     * @return
     */
    public String getName () {
        return name.get();
    }

    /**
     * Gets the String value.
     * @return
     */
    public String getValue () {
        return value.get();
    }

    /**
     * Sets the String value.
     * @param value
     */
    void setValue (String value) {
        this.value.set(value);
    }
    
    /**
     * Returns the item as a sequence string.
     */
    public String toString () {
        return getName() + " " + getValue();
    }


}
