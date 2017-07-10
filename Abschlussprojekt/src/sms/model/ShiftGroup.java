package sms.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sms.view.shift.ShiftChooserController;

import java.util.ArrayList;

/**
 * Created by Karl on 03.07.2017.
 */
public class ShiftGroup {
    private Shift shift;
    private int calenderWeekID;

    //number between 0 and 6
    private int day;

    final int initialEmpIdsLength;
    private ObservableList<Integer> empIds = FXCollections.observableArrayList();

    //Automatically fills empIds from DB
    public ShiftGroup(Shift shift, int cwID, int day) {
        this.shift = shift;
        calenderWeekID = cwID;
        this.day = day;


        //Load all EmployeeID from DB
        fillEmployee();
        //Then set the initial employeeIds length, so you can savely delete Shiftgroups without employees assigned to it
        //If length > 0 you need to unassigned employees in the db
        initialEmpIdsLength = empIds.size();
    }

    private void fillEmployee() {
        //Dummy Data add emp with id 1
        if(calenderWeekID == 1 && day == ShiftChooserController.MONDAY && shift.getId() == 1) empIds.add(1);
        //SQL and such

    }

    public ObservableList<Integer> getEmpIds() {
        return empIds;
    }

    public void setEmpIds(ObservableList<Integer> empIds) {
        this.empIds = empIds;
    }

    public Shift getShift() {
        return shift;
    }

    public boolean isThisDay(int cwID, int day) {
        return (cwID == calenderWeekID && this.day == day);
    }

    public boolean addEmpID(int id){
        if(empIds.contains(id)) return false;
        return empIds.add(id);
    }

    public int getDay() {
        return day;
    }
}
