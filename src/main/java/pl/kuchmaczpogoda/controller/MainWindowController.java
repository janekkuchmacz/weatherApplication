package pl.kuchmaczpogoda.controller;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pl.kuchmaczpogoda.model.Cities;
import pl.kuchmaczpogoda.view.ViewFactory;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;



public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private ChoiceBox<String> firstCityChoiceBox;


    @FXML
    private ChoiceBox<String> secondCityChoiceBox;

    @FXML
    private ImageView weatherCityOneImg;

    @FXML
    private Label temperatureCityOne;

    @FXML
    private Label pressureCityOne;

    @FXML
    private Label humidityCityOne;

    @FXML
    private Label humidityCityOne1;

    @FXML
    private ImageView tomorrowIconCityOne;

    @FXML
    private Label tommorrowTempCityOne;

    @FXML
    private ImageView plusTwoIconCityOne;

    @FXML
    private Label plusTwoTemperatureCityOne;

    @FXML
    private ImageView plusThreeIconCityOne;

    @FXML
    private Label plusThreeTemperatureCityOne;

    @FXML
    private ImageView plusFourIconCityOne;

    @FXML
    private Label plusFourTemperatureCityOne;

    @FXML
    private ChoiceBox<String> timeZone;

    @FXML
    private ChoiceBox<String> timeZoneCityTwo;

    @FXML
    private ImageView weatherCityTwoImg;

    @FXML
    private Label temperatureCityTwo;

    @FXML
    private Label pressureCityTwo;

    @FXML
    private Label humidityCityTwo;

    @FXML
    private ImageView tomorrowCityTwo;

    @FXML
    private Label tomorrowTempCityTwo;

    @FXML
    private ImageView plusTwoIconCityTwo;

    @FXML
    private Label plusTwoTmperatureCityTwo;

    @FXML
    private ImageView plusThreeIconCityTwo;

    @FXML
    private Label plusThreeTmperatureCityTwo;

    @FXML
    private ImageView plusFourIconCityTwo;

    @FXML
    private Label plusFourTmperatureCityTwo;

    @FXML
    void addCityAction() {
        viewFactory.showAddCityWindow();
        viewFactory.disableStage((Stage) temperatureCityOne.getScene().getWindow());
    }

    @FXML
    public void butonAction(){
        updateWeatherInFirstCity();
        Cities.setSelectedCity(firstCityChoiceBox.getValue());
        Cities.setSelectedTimezone(timeZone.getValue());
    }
    @FXML
    void buttonActionTwo() {
        updateWeatherInSecondCity();
       Cities.setSelectedCityTwo(secondCityChoiceBox.getValue());
       Cities.setSelectedTimezoneTwo(timeZoneCityTwo.getValue());
    }

    @FXML
    void closeAction() {
        viewFactory.closeStage((Stage)firstCityChoiceBox.getScene().getWindow());
    }

    @FXML
    void infoAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aplikacja pogodowa by Janek Kuchmacz");
        alert.setHeaderText("Dzięki za skorzystanie z mojej apki");
        alert.setContentText("Autor: Jan Kuchmacz\nhttps://jankuchmacz.pl/");
        alert.showAndWait();
    }
    @FXML
    void refreshCityAction() {
        updateChoiceBoxes();
    }


    private Service<Void> service;
    WeatherController weatherController;

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {

        super(viewFactory, fxmlName);
        weatherController = new WeatherController();
    }

    @Override
    public void closeWindowByX() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateChoiceBoxes();
        updateTimeZoneChoiceBoxes();
        updateWeatherInFirstCity();
        updateWeatherInSecondCity();
    }


    private void updateTimeZoneChoiceBoxes() {
        ArrayList<String> timeZones = Cities.getTimeZones();
        Collections.sort(timeZones);
        for(int i=0; i<timeZones.size(); i++){
            timeZone.getItems().add(timeZones.get(i));
            timeZoneCityTwo.getItems().add(timeZones.get(i));
        }
        if(Cities.getSelectedTimezone()!=null){
            timeZone.setValue(Cities.getSelectedTimezone());
        }else{
            timeZone.setValue("Europe/Warsaw");
        }
        if(Cities.getSelectedTimezoneTwo()!=null){
            timeZoneCityTwo.setValue(Cities.getSelectedTimezoneTwo());
        }else{
            timeZoneCityTwo.setValue("Europe/London");
        }

    }

    private void updateChoiceBoxes() {
       ArrayList<String> cities = Cities.getCities();
       firstCityChoiceBox.getItems().clear();
       secondCityChoiceBox.getItems().clear();
        for(int i= cities.size()-1; i>=0; i--){
            firstCityChoiceBox.getItems().add(cities.get(i));
            secondCityChoiceBox.getItems().add(cities.get(i));
        }
        if(Cities.getSelectedCity()!=null){
            firstCityChoiceBox.setValue(Cities.getSelectedCity());
        }else{
            firstCityChoiceBox.setValue("Warszawa");
        }
        if(Cities.getSelectedCityTwo()!=null){
            secondCityChoiceBox.setValue(Cities.getSelectedCityTwo());
        }else{
            secondCityChoiceBox.setValue("Londyn");
        }

    }

    private void updateWeatherInFirstCity() {
        service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(()-> {
                            try {
                                getWeatherForFirstCity();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
    }
    private void updateWeatherInSecondCity() {
        service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(()-> {
                            try {
                                getWeatherForSecondCity();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    private void getWeatherForFirstCity() throws IOException, ParseException {
        weatherController.fillCurrentWeatherData(getCityName(firstCityChoiceBox), getTimeZone(timeZone), temperatureCityOne, pressureCityOne, humidityCityOne, weatherCityOneImg);
        weatherController.fillWeatherDataForNextDays(getCityName(firstCityChoiceBox), getTimeZone(timeZone), tomorrowIconCityOne, tommorrowTempCityOne, plusTwoIconCityOne, plusTwoTemperatureCityOne, plusThreeIconCityOne,
                plusThreeTemperatureCityOne, plusFourIconCityOne, plusFourTemperatureCityOne);
    }
    private void getWeatherForSecondCity() throws IOException, ParseException {
        weatherController.fillCurrentWeatherData(getCityName(secondCityChoiceBox), getTimeZone(timeZoneCityTwo), temperatureCityTwo, pressureCityTwo, humidityCityTwo, weatherCityTwoImg);
        weatherController.fillWeatherDataForNextDays(getCityName(secondCityChoiceBox), getTimeZone(timeZoneCityTwo), tomorrowCityTwo, tomorrowTempCityTwo, plusTwoIconCityTwo, plusTwoTmperatureCityTwo, plusThreeIconCityTwo,
                plusThreeTmperatureCityTwo, plusFourIconCityTwo, plusFourTmperatureCityTwo);
    }

    private String getTimeZone(ChoiceBox<String> timeZone) {
        return timeZone.getValue();
    }

    private String getCityName(ChoiceBox<String> cityChoiceBox) {
        return cityChoiceBox.getValue();
    }



}
