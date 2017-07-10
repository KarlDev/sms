package sms.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Karl on 05.07.2017.
 */
public class Shift {
    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty name = new SimpleStringProperty();
    SimpleBooleanProperty monday = new SimpleBooleanProperty();
    SimpleBooleanProperty tuesday = new SimpleBooleanProperty();
    SimpleBooleanProperty wednesday = new SimpleBooleanProperty();
    SimpleBooleanProperty thursday = new SimpleBooleanProperty();
    SimpleBooleanProperty friday = new SimpleBooleanProperty();
    SimpleStringProperty begin = new SimpleStringProperty();
    SimpleStringProperty end = new SimpleStringProperty();


    public static ObservableList<Shift> getAllGenericShift() {
        ObservableList<Shift> allShifts = FXCollections.observableArrayList();
        //Load from db

        //Test data
        Shift s = new Shift();
        s.setId(1);
        s.setName("Chillig");
        s.setMonday(true);
        s.setTuesday(true);
        s.setWednesday(true);
        s.setThursday(true);
        s.setFriday(true);
        allShifts.add(s);

        return allShifts;
    }
    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isMonday() {
        return monday.get();
    }

    public SimpleBooleanProperty mondayProperty() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday.set(monday);
    }

    public boolean isTuesday() {
        return tuesday.get();
    }

    public SimpleBooleanProperty tuesdayProperty() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday.set(tuesday);
    }

    public boolean isWednesday() {
        return wednesday.get();
    }

    public SimpleBooleanProperty wednesdayProperty() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday.set(wednesday);
    }

    public boolean isThursday() {
        return thursday.get();
    }

    public SimpleBooleanProperty thursdayProperty() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday.set(thursday);
    }

    public boolean isFriday() {
        return friday.get();
    }

    public SimpleBooleanProperty fridayProperty() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday.set(friday);
    }

    public String getBegin() {
        return begin.get();
    }

    public SimpleStringProperty beginProperty() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin.set(begin);
    }

    public String getEnd() {
        return end.get();
    }

    public SimpleStringProperty endProperty() {
        return end;
    }

    public void setEnd(String end) {
        this.end.set(end);
    }
}
