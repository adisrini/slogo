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

/**
 * The methods table that shows saved methods.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class Methods extends TableView<Method> {
    @SuppressWarnings("unused")
	private Memory memory;
    private EventHandler<ActionEvent> event;
    
    private static final String CALLBACK = "Callback";
    private static final String NAME_COLUMN = "Name";
    private static final String ACTION_COLUMN = "Action";
    private static final String NAME_KEY = "name";
    private static final String VALUE_KEY = "value";

    public Methods(double paneWidth, double paneHeight, EventHandler<ActionEvent> event) {

        // set dimensions for window
        this.setWidth(paneWidth);
        this.setHeight(paneHeight);
        
        this.event = event;

        // Initialize instances
        memory = Memory.getMemoryInstance();

        initializeTable();
    }

    /**
     * Initializes the table.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void initializeTable() {
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Method, String> nameColumn = new TableColumn<Method, String>(NAME_COLUMN);
        TableColumn valueColumn = new TableColumn<Method, Button>(ACTION_COLUMN);
        
        // set where to read values from
        nameColumn.setCellValueFactory(new PropertyValueFactory<Method, String>(NAME_KEY));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>(VALUE_KEY));
        
        valueColumn.setCellFactory(generateCallback());

        this.getColumns().addAll(nameColumn, valueColumn);
    }
    
    /**
     * Generates the callback to be used by the buttons.
     * @return
     */
    @SuppressWarnings("rawtypes")
	private Callback generateCallback() {
    	return new Callback<TableColumn<Method, String>, TableCell<Method, String>>() {
			@Override
            public TableCell call(TableColumn<Method, String> parameter) {
                final TableCell<Method, String> cell = new TableCell<Method, String>() {
                    final Button button = new Button(CALLBACK);
                    @Override
                    public void updateItem(String item, boolean empty) {
                    	super.updateItem(item, empty);
                        if(empty) {
                        	setGraphic(null);
                        	setText(null);
                        } else  {
                        	Method method = getTableView().getItems().get(getIndex());
                        	button.setId(CALLBACK + " " + method.getValue());
                        	button.setOnAction(event);
                        	setGraphic(button);
                        	setText(null);
                        }
                    }
                };
                return cell;
            }
        };
    }

    /**
     * Adds a method specified to the table.
     * 
     * @param name
     * @param value
     */
    void addMethodToTable (String name, String value) {
        this.getItems().add(new Method(name, value));
    }

}
