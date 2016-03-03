package slogo.view;

import java.util.Map;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import slogo.model.Memory;


public class Variables extends TableView<IVariable> implements IVariables {
    private Memory memory;

    public Variables (double paneWidth, double paneHeight) {

        // set dimensions for window
        this.setWidth(paneWidth);
        this.setHeight(paneHeight);

        // Initialize instances
        memory = Memory.getMemoryInstance();

        initializeTable();
    }

    @SuppressWarnings("unchecked")
    private void initializeTable () {
        this.setEditable(true);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<IVariable, String> nameColumn = new TableColumn<IVariable, String>("Name");
        TableColumn<IVariable, Double> valueColumn = new TableColumn<IVariable, Double>("Value");
        
        // set where to read values from
        nameColumn.setCellValueFactory(new PropertyValueFactory<IVariable, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<IVariable, Double>("value"));
        
        // set editable column (valueColumn)
        valueColumn.setCellFactory(TextFieldTableCell
                .<IVariable, Double> forTableColumn(new DoubleStringConverter()));
        
        // Set listener to execute when on edit commit runs
        valueColumn.setOnEditCommit( (CellEditEvent<IVariable, Double> event) -> setVariableValue(event));
        valueColumn.setEditable(true);

        this.getColumns().addAll(nameColumn, valueColumn);
    }

    private void setVariableValue (CellEditEvent<IVariable, Double> event) {
        IVariable changedVariable = event.getTableView().getItems().get(event.getTablePosition().getRow());
        ((Variable) changedVariable).setValue(event.getNewValue());
        
        memory.writeVariable(changedVariable.getName(), changedVariable.getValue());
        
        System.out.println(changedVariable);
    }

    private void getVariablesFromMap () {
        Map<String, Double> unmodifiableVariableMap = memory.getVariableMap();

        for (String variable : unmodifiableVariableMap.keySet()) {
            addVariableToTable(variable, unmodifiableVariableMap.get(variable));
        }
    }

    private void addVariableToTable (String name, double value) {
        this.getItems().add(new Variable(name, value));
    }

    @Override
    public void updateVariables () {
        this.getItems().clear();
        getVariablesFromMap();
    }

}
