package ca.friends.and.co.it.SmartParking;

//Ramesh Narayan Gaire N01452430, ONA
//Roshan Shrestha N01457532, ONA
// Rushi Bhandari N01464259, ONA
// Komal Bamotra N01426087,ONA

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import ca.friends.and.co.it.SmartParking.R;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;



    private int PHONE_CODE = 1;

    private Fragment fragmentHome;
    static Fragment fragmentBooking;
    Fragment fragmentBookingDetail;
    Fragment fragmentSupport;
    Fragment fragmentShare;
    Fragment fragmentSettings;
    private Fragment fragmentCollaborator;
    private Fragment fragmentInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configurationNavigationView();

        //Open home fragment on starting the app
        Fragment fragment = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, fragment).commit();

    }


    private void configureToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

    }



    private void configurationNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.setCheckedItem(R.id.nav_home);
    }

    private void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            //Opening home fragment
            case R.id.nav_home:

                if (this.fragmentHome == null) this.fragmentHome = HomeFragment.newInstance();
                this.startTransactionFragment(this.fragmentHome);
                break;

            //Opening booking fragment
            case R.id.nav_booking:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new BookingFragment()).commit();

                break;

            //Opening booking detail fragment
            case R.id.nav_booking_detail:
                if (this.fragmentBookingDetail == null) this.fragmentBookingDetail = BookingDetailFragment.newInstance();
                this.startTransactionFragment(this.fragmentBookingDetail);
                break;

            //Opening support fragment
            case R.id.nav_support:
                if (this.fragmentSupport == null) this.fragmentSupport = SupportFragment.newInstance();
                this.startTransactionFragment(this.fragmentSupport);
                break;

            //Opening share fragment
            case R.id.nav_share:
                if (this.fragmentShare == null) this.fragmentShare = ShareFragment.newInstance();
                this.startTransactionFragment(this.fragmentShare);
                break;

            //Opening settings fragment
            case R.id.nav_settings:
                if (this.fragmentSettings == null) this.fragmentSettings = FragmentSettings.newInstance();
                this.startTransactionFragment(this.fragmentSettings);
                break;

            //Opening logout fragment
            case R.id.nav_logout:
                SharedPreferences preferences1 = getSharedPreferences("checkbox1", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("rememberme", "false");
                editor.apply();

                startActivity(new Intent(MainActivity.this, LoginActivity.class));

                break;

            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @SuppressLint("RestrictedApi")
    public boolean onCreateOptionsMenu(Menu menu){

        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    // keep it simple and stupid used
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.help:
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                if (this.fragmentSupport == null) this.fragmentSupport = SupportFragment.newInstance();
                this.startTransactionFragment(this.fragmentSupport);
                break;

            case R.id.collaborators:
                Toast.makeText(this, "Collaborators", Toast.LENGTH_SHORT).show();
                if (this.fragmentCollaborator == null) this.fragmentCollaborator = CollaboratorsFragment.newInstance();
                this.startTransactionFragment(this.fragmentCollaborator);
                break;

            case R.id.about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                if (this.fragmentInformation == null) this.fragmentInformation = InformationFragment.newInstance();
                this.startTransactionFragment(this.fragmentInformation);
                break;

            case R.id.setting:
                if (this.fragmentSettings == null) this.fragmentSettings = FragmentSettings.newInstance();
                this.startTransactionFragment(this.fragmentSettings);
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;

            default:

        }

        return super.onOptionsItemSelected(item);
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Want to connect with us?")
                .setMessage("Call us at +1 4379875581.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PHONE_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, fragment).commit();
        }}



    @Override

    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_message)
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finish();
                        Toast.makeText(getApplicationContext(),"Leaving the page!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Toast.makeText(getApplicationContext(),"Permission Denied!",
                                Toast.LENGTH_SHORT).show();

                    }
                });
        builder.setIcon(android.R.drawable.btn_dialog);
        builder.setTitle(R.string.alert_title);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void requestPhonePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed !!")
                    .setMessage("This permission needed to access your phone")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE}, PHONE_CODE);

                        }
                    })
                    .setNegativeButton("Deny", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PHONE_CODE );
        }
    }
}