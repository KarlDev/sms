package sms.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * Created by Karl on 05.07.2017.
 */
public class Employee {
    private SimpleIntegerProperty id;
    private SimpleStringProperty shortName;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty room;
    private SimpleStringProperty diplayName;

    public static ArrayList<Employee> loadAllEmployee() {
        //From DB
        ArrayList<Employee> emps = new ArrayList<>();

        //Dummy Data
        emps.add(new Employee(1, "mun", "Karl", "Frietsch", "1a"));
        emps.add(new Employee(2, "asd", "Peter", "AsdaQwerd", "1a"));
        emps.add(new Employee(3, "d22", "Hans", "D2P2", "1a"));
        emps.add(new Employee(4, "Laa", "Laa", "Laa", "1a"));

        return emps;
    }

    //dummy Constructor
    public Employee() {
        id = new SimpleIntegerProperty();
        shortName = new SimpleStringProperty();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        room = new SimpleStringProperty();

        setId(2);
        setShortName("mun");

        setFirstName("Karl");
        setLastName("Frietsch Musulin");
        setRoom("B1");
    }

    public Employee(int i, String sn, String fn, String ln, String ro) {
        id = new SimpleIntegerProperty(i);
        shortName = new SimpleStringProperty(sn);
        firstName = new SimpleStringProperty(fn);
        lastName = new SimpleStringProperty(ln);
        room = new SimpleStringProperty(ro);
        // firstname (shortname) ex: Karl (mun)
        diplayName = new SimpleStringProperty(fn + " (" + sn + ")");
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

    public String getShortName() {
        return shortName.get();
    }

    public SimpleStringProperty shortNameProperty() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName.set(shortName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getRoom() {
        return room.get();
    }

    public SimpleStringProperty roomProperty() {
        return room;
    }

    public void setRoom(String room) {
            this.room.set(room);
        }

    public String getDiplayName() {
        return diplayName.get();
    }

    public SimpleStringProperty diplayNameProperty() {
        return diplayName;
    }

    public void setDiplayName(String diplayName) {
        this.diplayName.set(diplayName);
    }
}
