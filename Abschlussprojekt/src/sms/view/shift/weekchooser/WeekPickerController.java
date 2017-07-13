package sms.view.shift.weekchooser;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import sms.MainController;
import sms.view.shift.ShiftChooserController;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Karl on 10.07.2017.
 */
public class WeekPickerController {

    private MainController mc;
    private ShiftChooserController scc;

    @FXML
    private DatePicker datePicker;
    @FXML
    private Button prefWeekButt;
    @FXML
    private Button nextWeekButt;

    private IntegerProperty week = new SimpleIntegerProperty();

    @FXML
    private void initialize() {
    }

    public void setMainControllerAndShiftController(MainController mc, ShiftChooserController scc) {
        this.mc = mc;
        this.scc = scc;
        week = scc.currentWeekIDProperty();
        datePicker.valueProperty().addListener((observable, oldValue, newDate) -> {
            //If not monday change to monday
            int dayOfWeek = newDate.getDayOfWeek().getValue();
            if(dayOfWeek != 1)
                datePicker.setValue(datePicker.getValue().minusDays( dayOfWeek - 1 ));

            //Set weekID
            week.set(newDate.get(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear()));
        });

        datePicker.setOnShown(event -> {
            //Get the content
            DatePickerContent content = (DatePickerContent)((DatePickerSkin)datePicker.getSkin()).getPopupContent();
            //All Cells
            content.lookupAll(".day-cell").forEach(cell -> {
                int weekOfCell = ((DateCell)cell).getItem().get(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear());
                //Of selected Week
                if(week.get() == weekOfCell){
                    //And add selected if monday-friday
                    if( ((DateCell)cell).getItem().getDayOfWeek().getValue() < 6)
                       cell.getStyleClass().add("selected");
                    //Otherwise remove selected
                } else cell.getStyleClass().remove("selected");
            });
        });

        prefWeekButt.setOnAction(event -> prefWeek());
        nextWeekButt.setOnAction(event -> nextWeek());


        datePicker.setValue(LocalDate.now().minusDays( LocalDate.now().getDayOfWeek().getValue() - 1 ));
    }

    private void nextWeek() {
        datePicker.setValue(datePicker.getValue().plusWeeks(1));
    }
    private void prefWeek() {
        datePicker.setValue(datePicker.getValue().minusWeeks(1));
    }
}
