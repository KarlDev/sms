package sms.view.shift.day;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DataFormat;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import sms.model.Employee;
import sms.model.ShiftGroup;
import sms.view.shift.ShiftChooserController;

/**
 * Created by Karl on 03.07.2017.
 */
public class ShiftGroupController {

    @FXML
    private Label groupNameLabel;
    private StringExpression groupNameExpression;
    private StringProperty groupCount = new SimpleStringProperty(" (0)");

    @FXML
    private VBox groupVBox;

    private ShiftGroup shiftGroup;

    private ShiftChooserController sc;

    private SimpleBooleanProperty areEmpsVisible = new SimpleBooleanProperty(true);

    public ShiftGroupController() {
    }

    @FXML
    private void initialize() {
        // Initialize onClick.
        groupVBox.setOnMouseClicked(e -> {
            areEmpsVisible.set(!areEmpsVisible.get());
        });

        areEmpsVisible.addListener((observable, oldValue, newValue) ->
                groupVBox.getChildren().forEach(child -> {
                    if(child != groupNameLabel) {
                        child.setManaged(newValue);
                        child.setVisible(newValue);
                    }
                })
        );

        //Check if dragged Object ist Person who qualifies for this shift
        groupVBox.setOnDragOver(e -> {
            //If source is not a label don't accept
            if(!(e.getGestureSource() instanceof Label)) e.acceptTransferModes(TransferMode.NONE);

            //If source parent is ShiftChooserController.empListVBox accept
            if(( (Label) e.getGestureSource() ).getParent() == sc.getEmpListFlowPane()) e.acceptTransferModes(TransferMode.ANY);

            e.consume();
        });

        //Employee was dragged into ShiftGroup
        groupVBox.setOnDragDropped(e -> {
            boolean success = false;

            //Get Employee ID from Dragboard, saved as String in the ClipboardContent
            int empID = Integer.valueOf(e.getDragboard().getContent(DataFormat.PLAIN_TEXT).toString());

            //Add Employee to ShiftGroup
            success = shiftGroup.addEmpID(empID);

            //success and consume
            System.out.println(success);
            System.out.println(shiftGroup.getDay());
            e.setDropCompleted(success);
            e.consume();
        });


    }

    public void setGroupAndController(ShiftChooserController sc, ShiftGroup sg) {
        this.sc = sc;
        this.shiftGroup = sg;

        //Add Employees already in shift group
        loadInitialEmpLabel();
        //Add ChangeListener... Lambda needs to know witch of the to methods it's supposed to exec
        sg.getEmpIds().addListener((ListChangeListener.Change<? extends Integer> c) -> {
            while (c.next()) {
                c.getAddedSubList().forEach(empID -> {
                    addEmpLabel(empID);
                });
                c.getRemoved().forEach(empID -> {
                    removeEmpLabel(empID);
                });

                //Set GroupCount for the GroupNameLabel
                groupCount.set(" (" + sg.getEmpIds().size() + ")");
                groupNameLabel.setText(groupNameExpression.getValue());
            }
        });



        //Set the Group name text
        groupCount.set(" (" + sg.getEmpIds().size() + ")");
        groupNameExpression = sg.getShift().nameProperty().concat(groupCount);
        groupNameLabel.setText(groupNameExpression.getValue());
    }

    public void addEmpLabel(int empID) {
        //Get employee from main controller
        Employee emp = sc.mc.getEmployeeWithID(empID);

        //Set the Label text
        String labelText = (emp == null) ? "No Employee" : emp.getDiplayName();

        Label empLabel = new Label(labelText);
        Button b = new Button();
        b.setOnAction(event -> {
            System.out.println("hi");
            shiftGroup.getEmpIds().removeAll(empID);
        });

        //Create BorderPane, add children and set ID appropriate to empID
        BorderPane borderPane = new BorderPane();
        b.setAlignment(Pos.CENTER_RIGHT);
        borderPane.setLeft(empLabel);
        borderPane.setRight(b);

        groupVBox.getChildren().add(borderPane);
        borderPane.setId(empID + "");

        borderPane.setManaged(areEmpsVisible.getValue());
        borderPane.setVisible(areEmpsVisible.getValue());
        System.out.println("labeladd");
    }

    Node labelToRemove;
    private void removeEmpLabel(int empID) {
        groupVBox.getChildren().forEach(child -> {
            String id = child.getId();
            if (sc.mc.isInteger(id)) {
                Integer idInt = Integer.valueOf(child.getId());
                if (idInt == empID) {
                    labelToRemove = child;
                }
            }
        });
        groupVBox.getChildren().removeAll(labelToRemove);
    }


    private void loadInitialEmpLabel() {
        shiftGroup.getEmpIds().forEach(empID -> {
            addEmpLabel(empID);
        });
    }
}

