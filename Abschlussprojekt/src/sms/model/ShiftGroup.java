package sms.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sms.view.shift.ShiftChooserController;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Karl on 03.07.2017.
 */
public class ShiftGroup {
    private Shift shift;
    private int calenderWeekID;

    //number between 0 and 6
    private int day;

    private ObservableList<Integer> empIds = FXCollections.observableArrayList();
    private ArrayList<Integer> originalEmpIds;

    //Automatically fills empIds from DB
    public ShiftGroup(Shift shift, int cwID, int day) {
        this.shift = shift;
        calenderWeekID = cwID;
        this.day = day;

        //Load all EmployeeID from DB
        fillEmployee();

        //Save all empIds so later, check for changes is possible
        originalEmpIds = new ArrayList<>();
        empIds.forEach(i -> originalEmpIds.add(i));
    }

    private void fillEmployee() {
        //Dummy Data add emp with id 1
        if(calenderWeekID == LocalDate.now().get(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear()) && day == ShiftChooserController.MONDAY && shift.getId() == 1) empIds.add(1);
        //SQL and such

    }

    public boolean changed() {
        System.out.println("in Changed");
        if(originalEmpIds.size() != empIds.size()) return true;

        for(Integer i : originalEmpIds) {
            if(!empIds.contains(i)) return true;
        }

        return false;
    }

    public void saveToDB() {
        //Save assigned Emps to db
        System.out.println(shift.getName() + " on " + getDay() + " saved.");
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
