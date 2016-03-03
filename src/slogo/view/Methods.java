package slogo.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import slogo.model.Memory;


public class Methods extends TableView<Method> {
    private Memory memory;
    private EventHandler<ActionEvent> event;

    public Methods(double paneWidth, double paneHeight, EventHandler<ActionEvent> event) {

        // set dimensions for window
        this.setWidth(paneWidth);
        this.setHeight(paneHeight);
        
        this.event = event;

        // Initialize instances
        memory = Memory.getMemoryInstance();

        initializeTable();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void initializeTable() {
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Method, String> nameColumn = new TableColumn<Method, String>("Name");
        TableColumn valueColumn = new TableColumn<Method, Button>("Action");
        
        // set where to read values from
        nameColumn.setCellValueFactory(new PropertyValueFactory<Method, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        
        // establish button callback in value column
        Callback<TableColumn<Method, String>, TableCell<Method, String>> cellFactory =
                new Callback<TableColumn<Method, String>, TableCell<Method, String>>() {
					@Override
                    public TableCell call(TableColumn<Method, String> parameter)
                    {
                        final TableCell<Method, String> cell = new TableCell<Method, String>()
                        {

                            final Button button = new Button("Callback");

                            @Override
                            public void updateItem(String item, boolean empty)
                            {
                            	super.updateItem(item, empty);
                                if(empty) {
                                	// if nothing in the row, don't create a button
                                	setGraphic(null);
                                	setText(null);
                                } else  {
                                	Method method = getTableView().getItems().get(getIndex());
                                	button.setId("Callback " + method.getValue());
                                	button.setOnAction(event);
                                	setGraphic(button);
                                	setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        valueColumn.setCellFactory(cellFactory);

        // add columns to table
        this.getColumns().addAll(nameColumn, valueColumn);
    }

    private void getMethodsFromMap () {
    	/*
        Map<String, String> unmodifiableMethodMap = memory.readMethod(methodName)

        for (String variable : unmodifiableVariableMap.keySet()) {
            addVariableToTable(variable, unmodifiableVariableMap.get(variable));
        }
        */
    }

    void addMethodToTable (String name, String value) {
        this.getItems().add(new Method(name, value));
    }

    public void updateMethods() {
        this.getItems().clear();
        getMethodsFromMap();
    }

}
