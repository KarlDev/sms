package test;/**
 * Created by Karl on 05.07.2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sms.MainController;

public class TestApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("hi");
        BorderPane bp = new BorderPane();

        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(MainController.class.getResource("view/shift/day/shiftGroupVBox.fxml"));
            //loader.load();
            loader.setLocation(MainController.class.getResource("view/shift/shiftSplitPane.fxml"));
            loader.load();
        } catch (Exception e) { e.printStackTrace(); }

        Label myDrag = new Label("Drag me");
        myDrag.setOnDragDetected(e -> {
            Dragboard db = myDrag.startDragAndDrop(TransferMode.ANY);
            db.setDragView(new Image("abby-und-brittany.jpg"), e.getX(), e.getY());
            ClipboardContent cc = new ClipboardContent();
            cc.putString("");
            db.setContent(cc);
        });

        myDrag.setOnDragOver(e -> {
            e.acceptTransferModes(TransferMode.MOVE);
            e.consume();
        });
        bp.setCenter(myDrag);

        primaryStage.setScene(new Scene(bp));
        primaryStage.show();
    }
}
