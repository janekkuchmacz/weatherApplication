package pl.kuchmaczpogoda.controller;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.kuchmaczpogoda.model.Cities;
import pl.kuchmaczpogoda.model.WeatherInfo;
import pl.kuchmaczpogoda.utils.JSONConverter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static pl.kuchmaczpogoda.controller.BaseController.getIconUrl;

public class WeatherController {
    protected void fillCurrentWeatherData(String cityName, String timeZone, Label temperatureCityOne, Label pressureCityOne, Label humidityCityOne, ImageView weatherCityOneImg) throws IOException, ParseException {

       JSONArray jsonArray=getJSONArrayOfWeatherData(cityName);
        //System.out.println(cityName);
        /*ArrayList<String>timeZones = Cities.getTimeZones();
        for(int i=0; i< timeZones.size(); i++){
            System.out.println(timeZones.get(i));
        }*/

        for(int i=0; i<jsonArray.length(); i++){
                SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String futureDate =jsonArray.getJSONObject(i).getString("dt_txt");
                Date futureDateAsDateObject=formatter.parse(futureDate);

                Date date = getTimeInZone(timeZone);

            if (date.before(futureDateAsDateObject)){
                //System.out.println(date);
                //System.out.println(futureDateAsDateObject);
                temperatureCityOne.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp") + "°C");
                pressureCityOne.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("pressure") + " hPa");
                humidityCityOne.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("humidity") + "%");
                weatherCityOneImg.setImage(BaseController.setImageUrl(getIconUrl(jsonArray, i)));
                break;
            }

        }


    }
    private Date getTimeInZone (String timeZone) throws ParseException {
        ZoneId zoneId = ZoneId.of(timeZone);
        //String zone = ZoneId.of("Europe/Moscow").getDisplayName(TextStyle.FULL, Locale.forLanguageTag("rus"));
        //System.out.println(zone);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        String formattedString =formatter1.format(zonedDateTime);
        formattedString=formattedString.substring(0,21);
        SimpleDateFormat formatter2=new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");
        Date date = formatter2.parse(formattedString);
        return date;

    }
    private JSONArray getJSONArrayOfWeatherData(String cityName) throws IOException {
        WeatherInfo weatherInfo = new WeatherInfo(cityName);
        String weatherData = weatherInfo.getResult();
        JSONArray jsonArray = JSONConverter.convertStringObjectToJSONArrayWithWeatherData(weatherData);
        return jsonArray;
    }
    public static Date trim(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 12);

        return calendar.getTime();
    }

    public void fillWeatherDataForNextDays(String cityName, String timeZone, ImageView tomorrowIconCityOne, Label tommorrowTempCityOne, ImageView plusTwoIconCityOne, Label plusTwoTemperatureCityOne,
                                           ImageView plusThreeIconCityOne, Label plusThreeTemperatureCityOne, ImageView plusFourIconCityOne, Label plusFourTemperatureCityOne) throws IOException, ParseException {

        JSONArray jsonArray=getJSONArrayOfWeatherData(cityName);
        final int ONE_DAY_MILISECONDS = 86400000;

        for(int i=0; i<jsonArray.length(); i++) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String futureDate = jsonArray.getJSONObject(i).getString("dt_txt");
            Date futureDateAsDateObject = formatter.parse(futureDate);


            Date date = getTimeInZone(timeZone);
            Date tomorrow = new Date(date.getTime() + ONE_DAY_MILISECONDS);
            tomorrow = trim(tomorrow);
           if(futureDateAsDateObject.equals(tomorrow)){
               tommorrowTempCityOne.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp") + "°C");
               tomorrowIconCityOne.setImage(BaseController.setImageUrl(getIconUrl(jsonArray, i)));
           }
           Date plusTwoDays=new Date(tomorrow.getTime()+ ONE_DAY_MILISECONDS);
            if(futureDateAsDateObject.equals(plusTwoDays)){
                plusTwoTemperatureCityOne.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp") + "°C");
                plusTwoIconCityOne.setImage(BaseController.setImageUrl(getIconUrl(jsonArray, i)));
            }
            Date plusThreeDays=new Date(plusTwoDays.getTime()+ ONE_DAY_MILISECONDS);
            if(futureDateAsDateObject.equals(plusThreeDays)){
                plusThreeTemperatureCityOne.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp") + "°C");
                plusThreeIconCityOne.setImage(BaseController.setImageUrl(getIconUrl(jsonArray, i)));
            }
            Date plusFourDays=new Date(plusThreeDays.getTime()+ ONE_DAY_MILISECONDS);
            if(futureDateAsDateObject.equals(plusFourDays)){
                //System.out.println(plusFourDays);
                //System.out.println(futureDateAsDateObject);
                plusFourTemperatureCityOne.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp") + "°C");
                plusFourIconCityOne.setImage(BaseController.setImageUrl(getIconUrl(jsonArray, i)));
            }

        }
    }
}
