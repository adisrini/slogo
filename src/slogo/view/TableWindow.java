package slogo.view;

import java.util.Map;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import slogo.model.Memory;

public abstract class TableWindow extends TableView<Object> {
	
	private Memory memory;
	
	public TableWindow(double paneWidth, double paneHeight) {
		// set dimensions for window
        this.setWidth(paneWidth);
        this.setHeight(paneHeight);

        // Initialize instances
        memory = Memory.getMemoryInstance();

        initializeTable();
        addVariableToTable("dp",78);
	}

	@SuppressWarnings("unchecked")
    private void initializeTable () {
        this.setEditable(true);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Object, String> nameColumn = new TableColumn<Object, String>("Name");
        TableColumn<Object, Double> valueColumn = new TableColumn<Object, Double>("Value");
        
        // set where to read values from
        nameColumn.setCellValueFactory(new PropertyValueFactory<Object, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Object, Double>("value"));
        
        // set editable column (valueColumn)
        valueColumn.setCellFactory(TextFieldTableCell.<Object, Double> forTableColumn(new DoubleStringConverter()));
        
        // Set listener to execute when on edit commit runs
        valueColumn.setOnEditCommit( (CellEditEvent<Object, Double> event) -> setVariableValue(event));
        valueColumn.setEditable(true);

        this.getColumns().addAll(nameColumn, valueColumn);
    }

    protected abstract void setVariableValue(CellEditEvent<Object, Double> event);

    private void getValuesFromMap () {
        Map<String, Double> unmodifiableVariableMap = memory.getStorageMemory().getVariableMap();

        for (String variable : unmodifiableVariableMap.keySet()) {
            addVariableToTable(variable, unmodifiableVariableMap.get(variable));
        }
    }

    private void addVariableToTable (String name, double value) {
        this.getItems().add(new Variable(name, value));
    }

}
