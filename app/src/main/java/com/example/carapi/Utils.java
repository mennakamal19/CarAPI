package com.example.carapi;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Utils
{
    static String title,author,thumbnil;
    public static List<BookItem>utils(String api)throws Exception
    {
        URL url=createURL(api);
        String json = makeHTTPReguest(url);
        List<BookItem>bk = extaDataFromJson(json);
        return bk;
    }
    public static URL createURL(String api) throws MalformedURLException
    {
        URL url = null;
        url = new URL(api);
        return url;
    }
    public static String makeHTTPReguest(URL url) throws Exception
    {
        String response ="";
        if(url==null)
        {
            return response;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream =null;
        try
        {
            urlConnection =(HttpURLConnection)url.openConnection(); // connection between device and servire
            urlConnection.setRequestMethod("GET");// tell url connection to GET Or POST
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200) // 200 is HTTP_ok code
            {
                inputStream = urlConnection.getInputStream();
                response = readFromStream(inputStream);
            }
        }catch (IOException e)
        {
          e.printStackTrace();
        }
        finally
        {
            if (urlConnection!= null)
            {
                urlConnection.disconnect();
            }
            if (inputStream!=null)
            {
                inputStream.close();
            }
        }
        return response;
    }
    public static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder();
        if(inputStream!= null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));//"UTF-8" is encoding
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // return char to lines
            String line = bufferedReader.readLine();
            while (line!=null)
            {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }
    public static List<BookItem> extaDataFromJson(String responce) throws JSONException {
        if (responce.isEmpty())
        {
            return null;
        }
        List<BookItem>bookItems = new ArrayList<>();
        JSONObject root = new JSONObject(responce); // here exception
        JSONArray items = root.getJSONArray("items");
        for(int i =0;i<items.length();i++)
        {
            JSONObject each_item = items.getJSONObject(i);
            JSONObject vi = each_item.getJSONObject("volumeInfo");
            if(vi.has("title"))
            {
                title = vi.getString("title");
            }else{
                title = "No Title Found";
            }
            if(vi.has("authors"))
            {
                author = vi.getString("title");
            }else{
                author = "No Author Found";
            }
            if(vi.has("imageLinks"))
            {
                thumbnil = vi.getJSONObject("imageLinks").getString("thumbnail");
            }else{
                thumbnil = "";
            }
            BookItem bookItem = new BookItem(title,author,thumbnil);
            bookItems.add(bookItem);
        }
        return bookItems;
    }
}
