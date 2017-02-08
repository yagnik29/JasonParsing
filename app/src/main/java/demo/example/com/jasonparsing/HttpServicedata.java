package demo.example.com.jasonparsing;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by admin on 2/8/2017.
 */

public class HttpServicedata {

    static String stream = null;

    public String getHttpData(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            if(httpURLConnection.getResponseCode() == 200){
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder  builder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null){
                    builder.append(line);
                }
                stream = builder.toString();
                httpURLConnection.disconnect();

            }else{
                //Do nothing
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

        }
        return stream;
    }
}
