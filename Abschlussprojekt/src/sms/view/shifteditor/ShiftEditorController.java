package sms.view.shifteditor;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sms.MainController;
import sms.model.Shift;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Karl on 11.07.2017.
 */
public class ShiftEditorController {

    private MainController mc;

    @FXML
    TableView<Shift> tableView;
    @FXML
    TableColumn<Shift, Integer> idColumn;
    @FXML
    TableColumn<Shift, String> nameColumn;
    @FXML
    TableColumn<Shift, String> beginColumn;
    @FXML
    TableColumn<Shift, String> endColumn;
    @FXML
    TableColumn<Shift, Boolean> mondayColumn;
    @FXML
    TableColumn<Shift, Boolean> tuesdayColumn;
    @FXML
    TableColumn<Shift, Boolean> wednesdayColumn;
    @FXML
    TableColumn<Shift, Boolean> thursdayColumn;
    @FXML
    TableColumn<Shift, Boolean> fridayColumn;

    @FXML
    public void initialize() {
        //not editable
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setEditable(false);

        //All editable Columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.<Shift>forTableColumn());


        beginColumn.setCellValueFactory(new PropertyValueFactory<>("begin"));
        beginColumn.setCellFactory(TextFieldTableCell.<Shift>forTableColumn());
        //Change edit so only matching Strings are accepted
        beginColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Shift, String> t) -> {
                    //If string matches set new Value, else set old value
                    if(Pattern.compile("\\d\\d:\\d\\d").matcher(t.getNewValue()).matches()) {
                        t.getRowValue().setBegin(t.getNewValue());
                    } else t.getRowValue().setBegin(t.getOldValue());


                    // workaround for refreshing rendered view
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);
                });

        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        endColumn.setCellFactory(TextFieldTableCell.<Shift>forTableColumn());
        //Change edit so only matching Strings are accepted
        endColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Shift, String> t) -> {
                    //If string matches set new Value, else set old value
                    if(Pattern.compile("\\d\\d:\\d\\d").matcher(t.getNewValue()).matches()) {
                        t.getRowValue().setEnd(t.getNewValue());
                    } else t.getRowValue().setEnd(t.getOldValue());


                    // workaround for refreshing rendered view
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);
                });

        mondayColumn.setCellValueFactory(new PropertyValueFactory<>("monday"));
        mondayColumn.setCellFactory(CheckBoxTableCell.forTableColumn(mondayColumn));

        tuesdayColumn.setCellValueFactory(new PropertyValueFactory<>("tuesday"));
        tuesdayColumn.setCellFactory(CheckBoxTableCell.forTableColumn(tuesdayColumn));

        wednesdayColumn.setCellValueFactory(new PropertyValueFactory<>("wednesday"));
        wednesdayColumn.setCellFactory(CheckBoxTableCell.forTableColumn(wednesdayColumn));

        thursdayColumn.setCellValueFactory(new PropertyValueFactory<>("thursday"));
        thursdayColumn.setCellFactory(CheckBoxTableCell.forTableColumn(thursdayColumn));

        fridayColumn.setCellValueFactory(new PropertyValueFactory<>("friday"));
        fridayColumn.setCellFactory(CheckBoxTableCell.forTableColumn(fridayColumn));

        tableView.setEditable(true);
    }

    public void setMainController(MainController mc) {
        this.mc = mc;
        tableView.setItems(mc.getAllGenerecShifts());
    }
}
