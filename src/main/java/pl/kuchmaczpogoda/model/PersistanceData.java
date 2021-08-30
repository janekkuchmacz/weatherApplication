package pl.kuchmaczpogoda.model;

import java.io.*;
import java.util.ArrayList;

public class PersistanceData {

    private String DATA_LOCATION = System.getProperty("user.home") + File.separator + "dataLocation.ser";

    public String getDATA_LOCATION() {
        return DATA_LOCATION;
    }

    public ArrayList<String> loadFromPersistence(){
        ArrayList<String> resultList = new ArrayList<String>();
        try {
            FileInputStream fileInputStream = new FileInputStream(DATA_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<String> persistedList = (ArrayList<String>)  objectInputStream.readObject();
            resultList.addAll(persistedList);
        } catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }

    public void saveToPersistence(ArrayList<String> cities){
        try {
            File file = new File(DATA_LOCATION);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cities);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

