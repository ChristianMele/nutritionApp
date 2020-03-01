package edu.quinnipiac.ser210.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/*
Christian Mele
February 29, 2020
NutritionActivity Class for the action bar on the second screen, and retrieving and displaying the nutrition facts.
 */

public class NutritionActivity extends AppCompatActivity {

    private ShareActionProvider mshareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        //back button to return to the food selection activity (MainActivity)
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutritionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //replaces the food calorie amount textView with the retrieved calorie data
        TextView calAmt = findViewById(R.id.caloriesUnit);
        calAmt.setText((String) getIntent().getExtras().get("ENERC_KCAL"));
        TextView fName = findViewById(R.id.foodName);
        fName.setText((String) getIntent().getExtras().get("text"));
        TextView cCarbs = findViewById(R.id.carbsUnit);
        cCarbs.setText((String) getIntent().getExtras().get("CHOCDF"));
        TextView fFats = findViewById(R.id.fatsUnit);
        fFats.setText((String) getIntent().getExtras().get("FAT"));
        TextView pPro = findViewById(R.id.proteinUnit);
        pPro.setText((String) getIntent().getExtras().get("PROCNT"));
    }
    //share action button on nutritionActivity screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mshareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent("My food nutrition data.");
        return super.onCreateOptionsMenu(menu);
    }

    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        mshareActionProvider.setShareIntent(intent);
    }
    //info and about actions on nutritionActivity screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Toast.makeText(this, "These are basic nutrition facts about the food you entered. Click back to enter a new food.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_info:
                Toast.makeText(this, "This app uses edamam API with RapidAPI to access a food database and returns the most popular result from each query.", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}