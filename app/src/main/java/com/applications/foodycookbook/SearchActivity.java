package com.applications.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.lang.Object;

import javax.net.ssl.HttpsURLConnection;

public class SearchActivity extends AppCompatActivity {

    ImageView imgSearch;
    EditText etSearch;
    Button searchResult;

    String resultString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Hooks
        imgSearch=findViewById(R.id.imgSearch);
        etSearch=findViewById(R.id.etSearch);
        searchResult=findViewById(R.id.searchResult);


        //Variables
        String searchTerm;
        final String base_url="https://www.themealdb.com/api/json/v1/1/";

        //url : www.themealdb.com/api/json/v1/1/
        searchTerm=etSearch.getText().toString();

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        //
                        try
                        {
                            URL url=new URL("https://www.themealdb.com/api/json/v1/1/search.php?s="+searchTerm);
                            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                            if (connection.getResponseCode() == 200)
                            {
                                // Success
                                System.out.print("Success!");
                                // Further processing here
                                InputStreamReader responseReader=new InputStreamReader(connection.getInputStream());
                                JsonReader jsonReader=new JsonReader(responseReader);
                                jsonReader.beginObject();  //Process the json object

                                while(jsonReader.hasNext())
                                {
                                    String key=jsonReader.nextName();
                                    if(key.equals("strMeal"))
                                    {
                                        resultString=jsonReader.nextString();
                                        searchResult.setText(resultString);
                                        break;
                                    }
                                    else
                                    {
                                        jsonReader.skipValue();
                                    }
                                }

                            } else
                            {
                                // Error handling code goes here
                            }
                        }
                        catch(Exception e){
                            System.out.print("HTTP error");
                        }
                    }
                });
            }
        });

















    }
}