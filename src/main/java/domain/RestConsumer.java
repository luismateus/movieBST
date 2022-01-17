package domain;

import org.json.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;


public class RestConsumer {
    private static RestConsumer instance = null;

    public static RestConsumer getInstance(){
        if(instance == null){
            instance = new RestConsumer();
        }
        return instance;
    }



    private String getJSONString(String parameter, String parameterValue, String apikey) throws UnsupportedEncodingException {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("apikey",apikey);
        parameters.put(parameter,parameterValue);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        return postData.toString();
    }

    private String postResponse(String input) {
        String response="";
        int status=0;
        try {
            URL url = new URL("http://www.omdbapi.com/?"+input);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setDoOutput(true);
            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            os.flush();
            os.close();

            status = connection.getResponseCode();
            DataInputStream inputStream = new DataInputStream(connection.getInputStream());

            String readed;
            while((readed = inputStream.readLine())!= null) {
                response += readed;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (status == 200){
            return response;
        }
        else {
            return null;
        }
    }

    public List<Movie> listMoviesByTitle(String director) throws IOException {
        String apikey="";
        Properties properties =  new Properties();
        try {
            FileInputStream ip= new FileInputStream("src/main/resources/config.properties");
            properties.load(ip);
            apikey=properties.getProperty("apikey");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        List<Movie> movies = new ArrayList<Movie>();
        String input = getJSONString("s",director,apikey);
        String response = postResponse(input);
        if (response!=null){
            JSONArray jsonResponse = new JSONObject(response).getJSONArray("Search");
            for(int i  = 0; i < jsonResponse.length(); ++i ){
                JSONObject jsonObj = jsonResponse.getJSONObject(i);
                String type = jsonObj.getString("Type");
                if(type.equals("movie")){
                    String title = jsonObj.getString("Title");
                    int year = Integer.parseInt(jsonObj.getString("Year"));
                    String imdbId = jsonObj.getString("imdbID");
                    String poster = jsonObj.getString("Poster");
                    Movie movie = new Movie(title,imdbId,year,poster);
                    movies.add(movie);
                }
            }
        }

        return movies;
    }

}
