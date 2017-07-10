package sms.view.shift;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
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
    private int currentWeek = 1;

    //Employees who can be assigned in current week
    private ObservableList<Employee> selectableEmps = FXCollections.observableArrayList();

    private ObservableList<ShiftGroup> shiftGroups = FXCollections.observableArrayList();
    private ObservableList<Shift> allGenericShifts = Shift.getAllGenericShift();


    public MainController mc;

    @FXML
    private void initialize() {
    }

    public void setMainContoller(MainController mc) {
        this.mc = mc;

        //Add to Employee List
        //TODO:: Implement possibility to mark Emp unavailable for certain weeks
        //mc.getEmployeeForWeekID(currentWeek).forEach(emp -> addEmployeeToSelection(emp));
        mc.getEmpList().forEach((key, employee) -> {
            addEmployeeToSelection(employee);
        });
        changeWeek(1);
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

    private void changeWeek(int newWeekID) {
        //First change week
        currentWeek = newWeekID;
        //and remove old ShiftGroups
        shiftGroups = FXCollections.observableArrayList();

        allGenericShifts.forEach(shift -> {

            //Load ShiftGroups for each Day
            if(shift.isMonday()) {
                //Constructor automatically loads EmpIDs from DB
                ShiftGroup currentShiftGroup = new ShiftGroup(shift, currentWeek, MONDAY);

                //Set loader location to the Group VBox and load
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(groupVBoxURL);

                VBox groupBox = null;
                try {
                    groupBox = (VBox) loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ShiftGroupController sgc = loader.getController();
                sgc.setGroupAndController(this, currentShiftGroup);

                mondayV.getChildren().add(groupBox);
            }
            if(shift.isTuesday()) {
                ShiftGroup currentShiftGroup = new ShiftGroup(shift, currentWeek, TUESDAY);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(groupVBoxURL);

                VBox groupBox = null;
                try {
                    groupBox = (VBox) loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ShiftGroupController sgc = loader.getController();
                sgc.setGroupAndController(this, currentShiftGroup);

                tuesdayV.getChildren().add(groupBox);
            }
        });
    }
}
