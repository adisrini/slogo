package slogo.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


public class Variable implements IVariable{

    private SimpleStringProperty name;
    private SimpleDoubleProperty value;

    public Variable (String name, Double value) {
        this.name = new SimpleStringProperty();
        this.value = new SimpleDoubleProperty();

        this.name.set(name);
        setValue(value);
    }

    public String getName () {
        return name.get();
    }

    public Double getValue () {
        return value.get();
    }

    void setValue (double value) {
        this.value.set(value);
    }
    
    public String toString () {
        return getName() + " " + getValue();
    }


}
