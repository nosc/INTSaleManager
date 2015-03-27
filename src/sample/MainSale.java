package sample;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by dima on 26.03.15.
 */
public class MainSale implements Initializable {
    @FXML
    DatePicker datapicker;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert datapicker != null;

        datapicker.getCalendarView().todayButtonTextProperty().set("Сегодня");
        datapicker.getCalendarView().setShowWeeks(false);
        datapicker.getStylesheets().add(String.valueOf(getClass().getResource("DatePicker.css")));
    }


}
