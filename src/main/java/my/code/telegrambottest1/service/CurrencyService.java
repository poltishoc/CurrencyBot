package my.code.telegrambottest1.service;


import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.ParseException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;



public class CurrencyService {

    public static String getCurrencyRate(String currency) throws IOException, ParseException {
        StringBuilder result = new StringBuilder();
        try {
            String urlString = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/mdl.json";
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


                JSONObject jsonObject = new JSONObject(response.toString());
                JSONObject mdl = jsonObject.getJSONObject("mdl");
                if (!mdl.has(currency.toLowerCase())) {
                    return "Валюту необходимо писать в формате из трех букв на латинице, пример: usd";
                }


                double rate = 1 / mdl.getDouble(currency);

                String formattedDate = new SimpleDateFormat("dd-MM-yyyy")
                        .format(new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("date")));

                result.append("На сегодняшний день: ").append(formattedDate).append("\n");

                result.append("Стоимость 1 ").append(currency.toUpperCase()).append(" = ").append(String.format("%.2f", rate))
                        .append(" лей\n");
            } else {
                result.append("Ошибка HTTP: ").append(responseCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}