package slogo.view;

import generic.Pair;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * This is the history table that holds previously executed commands
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class History extends TableView<HistoricalItem> implements Updatable {
    private EventHandler<ActionEvent> event;
    private static final String CALLBACK = "Callback";
    private static final String NAME_COLUMN = "Name";
    private static final String ACTION_COLUMN = "Action";
    private static final String NAME_KEY = "name";
    private static final String VALUE_KEY = "value";

    /**
     * Initializes the table with given height, width, and an event handler.
     * @param paneWidth
     * @param paneHeight
     * @param event
     */
    public History(double paneWidth, double paneHeight, EventHandler<ActionEvent> event) {

        this.setWidth(paneWidth);
        this.setHeight(paneHeight);
        
        this.event = event;

        initializeTable();
    }

    /**
     * Initializes the table with Strings in column one and buttons in column two.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void initializeTable() {
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<HistoricalItem, String> nameColumn = new TableColumn<HistoricalItem, String>(NAME_COLUMN);
        TableColumn valueColumn = new TableColumn<HistoricalItem, Button>(ACTION_COLUMN);
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<HistoricalItem, String>(NAME_KEY));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>(VALUE_KEY));
        
        
        valueColumn.setCellFactory(generateCallback());

        this.getColumns().addAll(nameColumn, valueColumn);
    }
    
    /**
     * Generates the callback function to be called on button click.
     * @return
     */
    private Callback<?, ?> generateCallback() {
    	return new Callback<TableColumn<HistoricalItem, String>, TableCell<HistoricalItem, String>>() {
					@SuppressWarnings("rawtypes")
					@Override
                    public TableCell call(TableColumn<HistoricalItem, String> parameter) {
                        final TableCell<HistoricalItem, String> cell = new TableCell<HistoricalItem, String>() {
                            final Button button = new Button(CALLBACK);
                            @Override
                            public void updateItem(String item, boolean empty) {
                            	super.updateItem(item, empty);
                                if(empty) {
                                	setGraphic(null);
                                } else  {
                                	HistoricalItem history = getTableView().getItems().get(getIndex());
                                	button.setId(CALLBACK + " " + history.getValue());
                                	button.setOnAction(event);
                                	setGraphic(button);
                                }
                            	setText(null);
                            }
                        }; return cell;
                    }};
    }

    /**
     * Updates the history table with the specified object.
     */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Object object) {
		Pair<String, String> pair = (Pair<String, String>) object;
		this.getItems().add(new HistoricalItem((String)pair.getFirst(), (String)pair.getLast()));
	}

}