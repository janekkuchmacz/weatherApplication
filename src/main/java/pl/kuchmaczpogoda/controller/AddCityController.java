package pl.kuchmaczpogoda.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.kuchmaczpogoda.model.Cities;
import pl.kuchmaczpogoda.model.WeatherInfo;
import pl.kuchmaczpogoda.view.ViewFactory;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class AddCityController extends BaseController {

    @FXML
    private TextField cityField;

    @FXML
    private Label errorLabel;





    @FXML
    void returnButtonAction() {
        viewFactory.closeStage((Stage) cityField.getScene().getWindow());
        viewFactory.unableMainWindow();
    }

    @FXML
    void findButtonAction() throws MalformedURLException {
        String cityName = cityField.getText();
        if(checkCorrectnessOfCityName(cityName)){
            if(checkRepetitionOfCityName(cityName)){
                Cities.cities.add(cityName);
                errorLabel.setText("Miejscowość dodana do Twoich miejscowości");
            }else{
                errorLabel.setText("Miejscowość już dodana");
            }
        }
    }

    private boolean checkRepetitionOfCityName(String cityName) {
        ArrayList<String> cities = Cities.getCities();
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).equals(cityName)) {
                return false;
            }
        }
       return true;
    }

    private boolean checkCorrectnessOfCityName(String cityName) throws MalformedURLException {
        WeatherInfo weatherInfo = new WeatherInfo(cityName);
        if(weatherInfo.getResponseCode()!=200){
            errorLabel.setText("Brak miejscowości w bazie danych");
            return false;
        } else {
            return true;
        }

    }

    public AddCityController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void closeWindowByX() {
        viewFactory.closeStage((Stage) cityField.getScene().getWindow());
        viewFactory.unableMainWindow();
    }


}
