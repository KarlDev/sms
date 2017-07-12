package sms;/**
 * Created by Karl on 20.06.2017.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sms.model.Employee;
import sms.model.Shift;
import sms.view.shift.ShiftChooserController;
import sms.view.shift.weekchooser.WeekPickerController;
import sms.view.shifteditor.ShiftEditorController;

import java.io.IOException;

public class MainController extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ButtonBar buttonBar;

    private ObservableList<Shift> allGenerecShifts;
    private ObservableMap<Integer, Employee> empList = FXCollections.observableHashMap();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("TITLE");

        allGenerecShifts = Shift.getAllGenericShift();

        loadRootLayout();

        //load all Employees
        for(Employee e : Employee.loadAllEmployee()) {
            empList.put(e.getId(), e);
        }
    }

    public void loadRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("view/root/rootBorderlayout.fxml"));
            rootLayout = (BorderPane) loader.load();


            //Add shiftChooser and Shifteditor buttons
            buttonBar = (ButtonBar)rootLayout.getTop();
            buttonBar.getButtons().remove(0, buttonBar.getButtons().size());

            Button b = new Button("Schicht zuweisen");
            b.setOnAction(e -> {
                enableAllButtonInBar();
                /*allGenerecShifts.forEach(a -> {
                    System.out.println("Mo: " + a.isMonday() + " Di: " + a.isTuesday() + " Rest: " + a.isWednesday() + a.isThursday() + a.isFriday());
                });*/
                showShiftChooser();
                b.setDisable(true);
            });
            Button b1 = new Button("Schicht Editieren");
            b1.setOnAction(e -> {
                enableAllButtonInBar();
                showShiftEditor();
                b1.setDisable(true);
            });

            buttonBar.getButtons().addAll(b, b1);

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
            SplitPane splitPane = (SplitPane) loader.load();
            // with Contoller
            ShiftChooserController scc = loader.getController();
            scc.setMainContoller(this);

            //Add splitPane to Center
            rootLayout.setCenter(splitPane);

            //Load week picker
            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(MainController.class.getResource("view/shift/weekchooser/weekPickerAnchor.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader1.load();

            WeekPickerController wpc = loader1.getController();
            wpc.setMainControllerAndShiftController(this, scc);

            //Add Week picker to Left
            rootLayout.setLeft(anchorPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showShiftEditor() {
        try {
            // Load shift editor
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("view/shifteditor/shiftEditorAnchor.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            // with Contoller
            ShiftEditorController sec = loader.getController();
            sec.setMainController(this);

            // Show the scene containing the root layout.
            rootLayout.setCenter(anchorPane);
            rootLayout.setLeft(null);
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

    private void enableAllButtonInBar() {
        if(buttonBar != null) {
            buttonBar.getButtons().stream()
                    .filter(n -> n.isDisabled())
                    .forEach(n -> n.setDisable(false));
        }
    }
    public ObservableList<Shift> getAllGenerecShifts() {
        return allGenerecShifts;
    }

    public void setAllGenerecShifts(ObservableList<Shift> allGenerecShifts) {
        this.allGenerecShifts = allGenerecShifts;
    }
}
