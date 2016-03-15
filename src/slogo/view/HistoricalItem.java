package slogo.view;

import javafx.beans.property.SimpleStringProperty;

/**
 * An item used in the history table
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class HistoricalItem {

    private SimpleStringProperty name;
    private SimpleStringProperty value;

    /**
     * Initializes the historical item object with a given
     * name and value.
     * @param name
     * @param value
     */
    public HistoricalItem(String name, String value) {
        this.name = new SimpleStringProperty();
        this.value = new SimpleStringProperty();

        this.name.set(name);
        setValue(value);
    }

    /**
     * Gets the String name.
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
     * Returns the item as a String sequence.
     */
    public String toString () {
        return getName() + " " + getValue();
    }


}