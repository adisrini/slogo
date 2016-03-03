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
import slogo.model.Memory;


public class History extends TableView<HistoricalItem> implements Updatable {
    private Memory memory;
    private EventHandler<ActionEvent> event;

    public History(double paneWidth, double paneHeight, EventHandler<ActionEvent> event) {

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
        
        TableColumn<HistoricalItem, String> nameColumn = new TableColumn<HistoricalItem, String>("Name");
        TableColumn valueColumn = new TableColumn<HistoricalItem, Button>("Action");
        
        // set where to read values from
        nameColumn.setCellValueFactory(new PropertyValueFactory<HistoricalItem, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        
        // establish button callback in value column
        Callback<TableColumn<HistoricalItem, String>, TableCell<HistoricalItem, String>> cellFactory =
                new Callback<TableColumn<HistoricalItem, String>, TableCell<HistoricalItem, String>>() {
					@Override
                    public TableCell call(TableColumn<HistoricalItem, String> parameter)
                    {
                        final TableCell<HistoricalItem, String> cell = new TableCell<HistoricalItem, String>()
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
                                	HistoricalItem history = getTableView().getItems().get(getIndex());
                                	button.setId("Callback " + history.getValue());
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

	@Override
	public void update(Object object) {
		// TODO Auto-generated method stub
		Pair pair = (Pair) object;
		this.getItems().add(new HistoricalItem((String)pair.getFirst(), (String)pair.getLast()));
	}

}