package com.example.mengl03.chapter11_map_hospitals_restaurants_schools;


        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.io.IOException;
        import java.io.BufferedReader;

public class DownloadURL {
    public String ReadURLs(String placeURL) throws IOException{
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url =  new URL(placeURL);   // creates object to be used to connect to the internet to search for nearby places
            httpURLConnection = (HttpURLConnection) url.openConnection();   // opens the URL object to the internet to search for nearby places
            httpURLConnection.connect();    // connects the URL object to the internet to search for nearby places

            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);

            }
            data =stringBuffer.toString();
            bufferedReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }

        return data;
    }

}
