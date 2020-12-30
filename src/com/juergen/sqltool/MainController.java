package com.juergen.sqltool;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MainController implements Initializable {
    
    private DbAccess dbAccess;
    
    private List<String> columns;
    
    @FXML
    private TextField tfQuery;

    @FXML
    private TableView<Books> resultTable;

    @FXML
    private Button btnExecute;
    
    @FXML
    private Label lblOutput;
    
    @FXML
    private TextArea txtOutput;

    @FXML
    void handleExecuteButtonAction(ActionEvent event) {
        dbAccess.removeAllColumns();
        resultTable.getColumns().clear();
        Query query = new Query(tfQuery.getText(), dbAccess.getConnection());
        System.out.println("Query to execute: "+query.getQuery());
        if (!query.isValid()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Just for your information...");
            alert.setHeaderText("Your Query is invalid!");
            alert.setContentText("Query '" + query.getQuery() + "' is invalid!");
            alert.showAndWait();
        } else {
            try {
                ObservableList<Books> bookList = dbAccess.executeQuery(query);
                columns = dbAccess.getColumns();
                String output = "Query executed successfully!";
                if (columns != null && !columns.isEmpty()) {
                    columns.stream().map((col) -> {
                        TableColumn tbCol = new TableColumn(col);
                        tbCol.setCellValueFactory(new PropertyValueFactory<>(col));
                        return tbCol;
                    }).forEachOrdered((tbCol) -> {
                        resultTable.getColumns().add(tbCol);
                    });
                }
                if (dbAccess.getStatus() != null) {
                    txtOutput.setText(output + " "+ dbAccess.getStatus());
                } else {
                    txtOutput.setText(output);
                }
                resultTable.setItems(bookList);
                txtOutput.setStyle("");
            } catch (Exception ex) {
                System.out.println(">>>>>> "+ex.getMessage());
                txtOutput.setText(ex.getMessage());
                // Make the error text red, activate auto line break
                txtOutput.setStyle("-fx-text-fill: red ;");
                txtOutput.setWrapText(true);
            }
        }
    }

    @FXML
    void handleHistoryButtonAction(ActionEvent event) {
        System.out.println("com.juergen.sqltool.MainController.handleHistoryButtonAction()");
    }

    @FXML
    void handleMouseTableAction(MouseEvent event) {
        System.out.println("com.juergen.sqltool.MainController.handleMouseTableAction()");
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        dbAccess = new DbAccess();
    }
}