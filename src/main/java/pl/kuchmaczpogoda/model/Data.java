package pl.kuchmaczpogoda.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public abstract class Data {

    private URL url;
    private String CityName;
    private String result = "";
    private int responseCode;
    private final static String API_KEY="9ce325d08dc20a19578f05c0d01b7ed0";

    protected abstract String getMainAPIPart();
    protected abstract String getAdditionalAPIPart();

    public Data (String cityName) throws MalformedURLException {
        connect(cityName, getMainAPIPart(), getAdditionalAPIPart());
    }

    public int getResponseCode() {
        return responseCode;
    }

    private void connect(String cityName, String mainAPIPart, String additionalAPIPart) throws MalformedURLException {
        url=new URL(mainAPIPart + cityName + "&appid=" + API_KEY + additionalAPIPart);
        //mainAPIPart+cityName+"&cnt=7&appid="+API_KEY
        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            responseCode=connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getResult() throws IOException {
        if(responseCode!=200){
            throw new RuntimeException("HttpResponseCode: "+responseCode);
        } else {
            Scanner scanner = new Scanner(url.openStream());
            while(scanner.hasNext()){
                result+=scanner.nextLine();
            }
            scanner.close();
            return result;
        }
    }



}
