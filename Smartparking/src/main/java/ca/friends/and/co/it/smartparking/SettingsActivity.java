package ca.friends.and.co.it.smartparking;

//Ramesh Narayan Gaire N01452430, ONA
//Roshan Shrestha N01457532, ONA
// Rushi Bhandari N01464259, ONA
// Komal Bamotra N01426087,ONA

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FragmentSettings())
                .commit();


        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

    }

    public void onBackPressed(){
        super.onBackPressed();
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
}



}