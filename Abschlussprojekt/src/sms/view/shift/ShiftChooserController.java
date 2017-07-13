package sms.view.shift;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import sms.MainController;
import sms.model.Employee;
import sms.model.Shift;
import sms.model.ShiftGroup;
import sms.view.shift.day.ShiftGroupController;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Karl on 05.07.2017.
 */
public class ShiftChooserController {

    private final static URL groupVBoxURL = MainController.class.getResource("view/shift/day/shiftGroupVBox.fxml");
    public final static int MONDAY = 0;
    public final static int TUESDAY = 1;
    public final static int WEDNESDAY = 2;
    public final static int THURSDAY = 3;
    public final static int FRIDAY = 4;

    @FXML
    private SplitPane mainSplit;

    @FXML
    private HBox weekBox;
    @FXML
    private VBox mondayV;
    @FXML
    private VBox tuesdayV;
    @FXML
    private VBox wednesdayV;
    @FXML
    private VBox thursdayV;
    @FXML
    private VBox fridayV;

    @FXML
    private FlowPane empList;
    @FXML
    private BorderPane rightSplitBP;

    //Week that is currently selected
    private IntegerProperty currentWeekID = new SimpleIntegerProperty();

    //Employees who can be assigned in current week
    private ObservableList<Employee> selectableEmps = FXCollections.observableArrayList();

    private ObservableList<ShiftGroup> shiftGroups = FXCollections.observableArrayList();

    public MainController mc;

    @FXML
    private void initialize() {
        currentWeekID.addListener((observable, oldValue, newValue) -> {
            //Check for changes then change week

            changeWeek(newValue.intValue());
        });
    }

    public void setMainContoller(MainController mc) {
        this.mc = mc;

        //Add to Employee List
        //TODO:: Implement possibility to mark Emp unavailable for certain weeks
        //mc.getEmployeeForWeekID(currentWeek).forEach(emp -> addEmployeeToSelection(emp));
        mc.getEmpList().forEach((key, employee) -> {
            addEmployeeToSelection(employee);
        });
    }

    public void addEmployeeToSelection(Employee emp) {
        //If Employee was added also add the Label
        if(selectableEmps.add(emp)) {
            Label empLabel = new Label(emp.getDiplayName());
            empLabel.setOnDragDetected(event -> {
                System.out.println("Dragged");
                Dragboard db = empLabel.startDragAndDrop(TransferMode.ANY);
                db.setDragView(empLabel.snapshot(null, null), event.getX(), event.getY());

                ClipboardContent cc = new ClipboardContent();
                cc.putString(emp.getId() + "");
                db.setContent(cc);

                event.consume();
            });
            empList.getChildren().add(empLabel);
        }
    }

    public void removeEmployeeFromSelection(Employee emp) {
        selectableEmps.remove(emp);
    }

    public FlowPane getEmpListFlowPane() {
        return empList;
    }

    public void changeWeek(int newWeekID) {
        //Remove old ShiftGroups, and VBox
        mondayV.getChildren().remove(1, mondayV.getChildren().size());
        tuesdayV.getChildren().remove(1, tuesdayV.getChildren().size());
        wednesdayV.getChildren().remove(1, wednesdayV.getChildren().size());
        thursdayV.getChildren().remove(1, thursdayV.getChildren().size());
        fridayV.getChildren().remove(1, fridayV.getChildren().size());
        shiftGroups = FXCollections.observableArrayList();

        mc.getAllGenerecShifts().forEach(shift -> {
            //init array of the days
            boolean[] days = {shift.isMonday(), shift.isTuesday(), shift.isWednesday(), shift.isThursday(), shift.isFriday()};

            //iterate over days and load shift for each day
            for (int day = 0; day < days.length; day++) {
                if(days[day]) {
                    //Constructor automatically loads EmpIDs from DB
                    ShiftGroup currentShiftGroup = new ShiftGroup(shift, currentWeekID.get(), day);

                    //Set loader location to the Group VBox and load
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(groupVBoxURL);

                    VBox groupBox = null;
                    try {
                        groupBox = (VBox) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Set the controller
                    ShiftGroupController sgc = loader.getController();
                    sgc.setGroupAndController(this, currentShiftGroup);

                    //add to appropriate day
                    switch (day) {
                        case 0:
                            mondayV.getChildren().add(groupBox);
                            break;
                        case 1:
                            tuesdayV.getChildren().add(groupBox);
                            break;
                        case 2:
                            wednesdayV.getChildren().add(groupBox);
                            break;
                        case 3:
                            thursdayV.getChildren().add(groupBox);
                            break;
                        case 4:
                            fridayV.getChildren().add(groupBox);
                            break;
                    }
                }
            }
        });
    }

    public int getCurrentWeekID() {
        return currentWeekID.get();
    }

    public IntegerProperty currentWeekIDProperty() {
        return currentWeekID;
    }

    public void setCurrentWeekID(int currentWeekID) {
        this.currentWeekID.set(currentWeekID);
    }

    public ObservableList<Employee> getSelectableEmps() {
        return selectableEmps;
    }

    public void setSelectableEmps(ObservableList<Employee> selectableEmps) {
        this.selectableEmps = selectableEmps;
    }

    public ObservableList<ShiftGroup> getShiftGroups() {
        return shiftGroups;
    }

    public void setShiftGroups(ObservableList<ShiftGroup> shiftGroups) {
        this.shiftGroups = shiftGroups;
    }
}
