package ca.friends.and.co.it.SmartParking;

//Ramesh Narayan Gaire N01452430, ONA
//Roshan Shrestha N01457532, ONA
// Rushi Bhandari N01464259, ONA
// Komal Bamotra N01426087,ONA



import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

import ca.friends.and.co.it.SmartParking.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView gateArm;

    TextView bookHome;
    TextView feedbackHome;
    TextView supportHome;
    TextView shareHome;

    TextView textView18;

    FloatingActionButton button;

    private Fragment fragmentBooking;
    Handler handler = new Handler();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String value = "Detected";

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = getActivity().findViewById(android.R.id.content);
        Snackbar.make(v, "Home Screen", Snackbar.LENGTH_LONG).show();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        imageView1 = view.findViewById(R.id.book_imagebtn);
        imageView2 = view.findViewById(R.id.support_imagebtn);
        imageView3 = view.findViewById(R.id.share_imagebtn);
        imageView4 = view.findViewById(R.id.feedback_imagebtn);
        gateArm = view.findViewById(R.id.gatearm);
        bookHome = view.findViewById(R.id.book_home);
        feedbackHome = view.findViewById(R.id.feedback_home);
        supportHome = view.findViewById(R.id.support_home);
        shareHome = view.findViewById(R.id.share_home);

        textView18 = view.findViewById(R.id.textView18);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("IR sensor/Space");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Long s = (Long) snapshot.getValue();
                textView18.setText(""+s);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final TextView rushiSensorTV = new TextView(getActivity().getApplicationContext());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef2 = firebaseDatabase.getReference("Motion sensor");

        dbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    String dbValue = snapshot.getValue().toString();
                    value = dbValue;
                    Toast.makeText(getContext(), "Motion Sensor: "+value, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Cannot read data from database", Toast.LENGTH_SHORT).show();
                    //value ="undetected";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        gateArm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Open Parking Entrance");
                if(value.equalsIgnoreCase("detected") && value != null){
                    builder.setMessage("Your car is detecting in Sensor, arm will open now!");
                    dbRef2.child("Servo").setValue("HIGH");
                    Toast.makeText(getContext(), "ARM OPEN", Toast.LENGTH_SHORT).show();

                    try {
                        TimeUnit.SECONDS.sleep(5);
                        dbRef2.child("Servo").setValue("LOW");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                } else if (value.equalsIgnoreCase("undetected")) {
                    builder.setMessage("Your car is not detected in sensor. \nPlease make your car move forward or backward and then try!");

                }else{
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

                //rushiSensorTV.setText("not detected");
                //String motionSensor = rushiSensorTV.getText().toString();

                builder.setPositiveButton("Ok",(DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        button=view.findViewById(R.id.fab_btn);

        bookHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new BookingFragment()).commit();
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new BookingFragment()).commit();
            }
        });


        supportHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new SupportFragment()).commit();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new SupportFragment()).commit();
            }
        });


        shareHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new ShareFragment()).commit();
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new ShareFragment()).commit();
            }
        });


        feedbackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new FeedbackFragment()).commit();
            }
        });


        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, new FeedbackFragment()).commit();
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Logging Out", Toast.LENGTH_SHORT).show();

                SharedPreferences preferences1 = getActivity().getSharedPreferences("checkbox1", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("rememberme", "false");
                editor.apply();

                startActivity(new Intent(getContext(), LoginActivity.class));

            }
        });


        return view;
    }

}