package sms;/**
 * Created by Karl on 20.06.2017.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sms.model.Employee;
import sms.view.shift.ShiftChooserController;

import java.io.IOException;

public class MainController extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableMap<Integer, Employee> empList = FXCollections.observableHashMap();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("TITLE");

        loadRootLayout();

        //load all Employees
        for(Employee e : Employee.loadAllEmployee()) {
            empList.put(e.getId(), e);
        }

        showShiftChooser();
    }

    public void loadRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("view/root/rootBorderlayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showShiftChooser() {
        try {
            // Load shift chooser
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("view/shift/shiftSplitPane.fxml"));
            SplitPane shiftPane = (SplitPane) loader.load();
            // with Contoller
            ShiftChooserController scc = loader.getController();
            scc.setMainContoller(this);


            // Show the scene containing the root layout.
            rootLayout.setCenter(shiftPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeWithID(int id) {
        return empList.get(id);
    }

    public ObservableMap<Integer, Employee> getEmpList() {
        return empList;
    }

    public void setEmpList(ObservableMap<Integer, Employee> empList) {
        this.empList = empList;
    }


    public static boolean isInteger(String s) {
        if (s == null) {
            return false;
        }
        int length = s.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (s.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
