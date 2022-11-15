package ca.friends.and.co.it.smartparking;



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSettings extends Fragment {


    SharedPreferences sharedPreference;

    private Switch enableNightMode;
    private RadioGroup orientation;
    private RadioButton potrait;
    private RadioButton landscape;
    private CheckBox enableNotification;
    private Button changePassword;
    private Button feedbackButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public FragmentSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SettingsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSettings newInstance() {
        FragmentSettings fragment = new FragmentSettings();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        sharedPreference = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();

        enableNightMode = view.findViewById(R.id.night_mode_switch);
        enableNotification = view.findViewById(R.id.notification_switch);
        orientation = view.findViewById(R.id.orientation);
        potrait = view.findViewById(R.id.potrait);
        landscape = view.findViewById(R.id.landscape);
        feedbackButton = view.findViewById(R.id.give_feedback_button);
        changePassword = view.findViewById(R.id.settings_change_password_button);
        enableNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(enableNightMode.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        orientation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (potrait.isChecked()){
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                else if(landscape.isChecked()){
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                }
                else
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);

            }
        });

        enableNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(enableNotification.isChecked()){
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("NotificationEnable",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("Notify",true);
                    createNotification();
                    Toast.makeText(getContext(), "Notification Enabled!", Toast.LENGTH_SHORT).show();
                }
            }
        });





        return view;
    }
    public void createNotification(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("NotificationEnabled","Notify", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("You have now enrolled in notification you will receive further notifications from Smart Parking application");
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        sendNotificationProcess();
    }

    public void sendNotificationProcess(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),"NotificationEnabled");
        builder.setContentTitle("Smart Parking Notification enabled!")
                .setContentText("You have now enrolled in notification you will receive further notifications from Smart Parking application")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat mangerCompat = NotificationManagerCompat.from(getContext());
        mangerCompat.notify(1,builder.build());
    }
}