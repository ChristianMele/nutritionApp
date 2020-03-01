package edu.quinnipiac.ser210.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.widget.ShareActionProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/*
Christian Mele
February 29, 2020
MainActivity Class for the action bar on the first screen, and retrieving and displaying the nutrition facts.
 */

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private ShareActionProvider mshareActionProvider;
    Handler handler = new Handler();
    String urlStr = "https://api.edamam.com/api/food-database/parser?session=40&app_key=69d004c3e3c5b967261e625baea627fc&app_id=85b409cf&ingr=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        //changes screen when go button is clicked
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NutritionActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchInfo().execute("");
            }
        });
    }

    private class FetchInfo extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            //creates an array facts to store nutrition data retrieved from the API
            ArrayList<String> facts = new ArrayList<String>();
            //coverts user's food text input to a string stored in the variable userInput to be appended to the search URL
            TextView input = findViewById(R.id.foodInput);
            String userInput = input.getText().toString();

            try {
                //creates a url from the API url with user's food input appended for specific food selection
                URL url = new URL(urlStr + userInput);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key", "69d004c3e3c5b967261e625baea627fc");
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();

                if (in == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(in));
                String JsonString = getBufferStringFromBuffer(reader).toString();
                facts.add(handler.getCalories(JsonString));
                facts.add(handler.getFoodName(JsonString));
                facts.add(handler.getFat(JsonString));
                facts.add(handler.getPro(JsonString));
                facts.add(handler.getCarb(JsonString));

            } catch (Exception e) {
                Log.e(LOG_TAG, "Error" + e.getMessage());
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error" + e.getMessage());
                        return null;
                    }
                }
            }
            return facts;
        }

        protected void onPostExecute(ArrayList<String> data) {
            Intent intent = new Intent(MainActivity.this, NutritionActivity.class);
            intent.putExtra("ENERC_KCAL", data.get(0));
            intent.putExtra("text", data.get(1));
            intent.putExtra("FAT", data.get(2));
            intent.putExtra("PROCNT", data.get(3));
            intent.putExtra("CHOCDF", data.get(4));
            startActivity(intent);
        }
    }

    private StringBuffer getBufferStringFromBuffer(BufferedReader br) throws Exception {
        StringBuffer buffer = new StringBuffer();

        String line;
        while ((line = br.readLine()) != null) {
            buffer.append(line + '\n');
        }

        if (buffer.length() == 0)
            return null;

        return buffer;
    }
    //share action
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mshareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent("My food data.");
        return super.onCreateOptionsMenu(menu);
    }

    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        mshareActionProvider.setShareIntent(intent);
    }
    //info and about function in action bar to assist user in understanding what to do on the first activity and info about how the app was made
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Toast.makeText(this, "Enter a food name and click Go, the app will return basic nutrition facts about your entered food.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_info:
                Toast.makeText(this, "This app uses edamam API with RapidAPI to access a food database and returns the most popular result from each query.", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}