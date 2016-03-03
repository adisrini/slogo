package slogo.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


public class HistoricalItem {

    private SimpleStringProperty name;
    private SimpleStringProperty value;

    public HistoricalItem(String name, String value) {
        this.name = new SimpleStringProperty();
        this.value = new SimpleStringProperty();

        this.name.set(name);
        setValue(value);
    }

    public String getName () {
        return name.get();
    }

    public String getValue () {
        return value.get();
    }

    void setValue (String value) {
        this.value.set(value);
    }
    
    public String toString () {
        return getName() + " " + getValue();
    }


}
