package pl.kuchmaczpogoda;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.kuchmaczpogoda.model.Cities;
import pl.kuchmaczpogoda.model.PersistanceData;
import pl.kuchmaczpogoda.view.ViewFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Launcher extends Application {

    private PersistanceData persistanceData = new PersistanceData();

    @Override
    public void start(Stage stage) throws IOException {
        ViewFactory viewFactory = new ViewFactory();
        File tempFile = new File(persistanceData.getDATA_LOCATION());
        boolean exists = tempFile.exists();
        if(exists){
            ArrayList<String> dataLista = persistanceData.loadFromPersistence();
            /*for(int i=0; i<dataLista.size()-2; i++){
                Cities.cities.add(dataLista.get(i));
            }*/
            Cities.setSelectedCity(dataLista.get(dataLista.size()-4));
            Cities.setSelectedTimezone(dataLista.get(dataLista.size()-3));
            Cities.setSelectedCityTwo(dataLista.get(dataLista.size()-2));
            Cities.setSelectedTimezoneTwo(dataLista.get(dataLista.size()-1));
            Cities.cities = persistanceData.loadFromPersistence();
            for(int i=0; i<4; i++){
                Cities.cities.remove(Cities.cities.size()-1);
            }

        }
        viewFactory.showMainWindow();

    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
       ArrayList<String>cities = new ArrayList<String>();
       cities = Cities.getCities();
       String selectedCity = Cities.getSelectedCity();
       String selectedTimeZone= Cities.getSelectedTimezone();
       String selectedCityTwo = Cities.getSelectedCityTwo();
       String selectedTimeZoneTwo = Cities.getSelectedTimezoneTwo();
       cities.add(selectedCity);
       cities.add(selectedTimeZone);
       cities.add(selectedCityTwo);
       cities.add(selectedTimeZoneTwo);
       persistanceData.saveToPersistence(cities);
    }
}
