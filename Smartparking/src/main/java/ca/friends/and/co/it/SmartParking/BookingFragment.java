package ca.friends.and.co.it.SmartParking;

//Ramesh Narayan Gaire N01452430, ONA
//Roshan Shrestha N01457532, ONA
// Rushi Bhandari N01464259, ONA
// Komal Bamotra N01426087,ONA


// single responsibility principle used
// This java class is only related to booking

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import ca.friends.and.co.it.SmartParking.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {

    Button fromTimeButton;
    Button toTimeButton;
    TextView fromTimeTV;
    TextView toTimeTV;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int MY_PERMISSIONS_REQUEST_SMS = 1;
    View view;
    Button button;
    EditText fullname;
    EditText contact;
    EditText date;
    EditText duration;
    String message;
    String bookingName;
    String bookingContact;
    String bookingDate;
    Boolean spotSelected = false;
TextView datePicker;
    String bookingDuration;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Bundle resultBundle = new Bundle();
    TextView selectedDate;
    ImageButton datePickerIv;
    ImageView parkingSpotPicker;
    Button confirmParkingSpotButton;
    TextView parkingSpot1;
    TextView parkingSpot2;
    TextView parkingSpot3;
    TextView parkingSpot4;
    TextView parkingSpot5;
    TextView parkingSpot6;
    TextView parkingSpotNumber;
    String[] durationsTime ={"1","2","3","4","5"};
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int numClicksS1 = 1;
    int numClicksS2 = 1;
    int numClicksS3 = 1;
    int numClicksS4 = 1;
    int numClicksS5 = 1;
    int numClicksS6 = 1;
    int parkingDuration;
    int fromHour, fromMinute, toHour, toMinute;
    private int mYear, mMonth, mDay, mHour, mMinute;

    String spot1SelectedDB = "false";
    String spot2SelectedDB="false";
    String spot3SelectedDB="false";
    String spot4SelectedDB="false";
    String spot5SelectedDB = "false";
    String spot6SelectedDB = "false";
    boolean spot1Selectable;
    boolean spot6Selectable;
    boolean spot2Selectable;
    boolean spot3Selectable;
    boolean spot4Selectable;
    boolean spot5Selectable;
    Boolean spot1= true;
    Boolean spot2;
    Boolean spot3;
    Boolean spot4;
    Boolean spot5;
    Boolean spot6;
    public BookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingFragment newInstance() {
        BookingFragment fragment = new BookingFragment();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullname = view.findViewById(R.id.full_name);
        contact = view.findViewById(R.id.contact_number);
        date = view.findViewById(R.id.date);
        duration = view.findViewById(R.id.duration);
        sharedPreferences = getActivity().getSharedPreferences("Booking details", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        button = view.findViewById(R.id.book_button);
        parkingSpotPicker = view.findViewById(R.id.parking_spot_image);
        selectedDate = view.findViewById(R.id.selected_date);
        datePicker = view.findViewById(R.id.selected_date);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Booking Details");
        datePickerIv =view.findViewById(R.id.imageButton);
        spotSelected = false;
        parkingSpotNumber = view.findViewById(R.id.parking_spot_number);
        Spinner durationSpinner = view.findViewById(R.id.durationSpinner);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,durationsTime);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        durationSpinner.setAdapter(adapter);
        fromTimeButton = view.findViewById(R.id.parking_from_time);
        fromTimeTV = view.findViewById(R.id.fromTimeTV);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spot1SelectedDB = snapshot.child("User Data").child("spot1Selected").toString();
                spot2SelectedDB = snapshot.child("User Data").child("spot2Selected").toString();
                spot3SelectedDB = snapshot.child("User Data").child("spot3Selected").toString();
                spot4SelectedDB = snapshot.child("User Data").child("spot4Selected").toString();
                spot5SelectedDB = snapshot.child("User Data").child("Spot5Selected").toString();
                spot6SelectedDB = snapshot.child("User Data").child("spot6Selected").toString();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(spot1SelectedDB.equalsIgnoreCase("true")) {
            spot1Selectable = false;
            parkingSpot1.setBackgroundColor(Color.parseColor("#fa2832"));
        }
        if(spot2SelectedDB.equalsIgnoreCase("true")) {
            spot2Selectable = false;
            parkingSpot2.setBackgroundColor(Color.parseColor("#fa2832"));
        }
        if(spot3SelectedDB.equalsIgnoreCase("true")) {
            spot3Selectable = false;
            parkingSpot3.setBackgroundColor(Color.parseColor("#fa2832"));
        }
        if(spot4SelectedDB.equalsIgnoreCase("true")) {
            spot4Selectable = false;
            parkingSpot4.setBackgroundColor(Color.parseColor("#fa2832"));
        }
        if(spot5SelectedDB.equalsIgnoreCase("true")) {
            spot5Selectable = false;
            parkingSpot5.setBackgroundColor(Color.parseColor("#fa2832"));
        }
        if(spot6SelectedDB.equalsIgnoreCase("true")) {
            spot6Selectable = false;
            parkingSpot6.setBackgroundColor(Color.parseColor("#fa2832"));
        }
       // spot1SelectedDB = reference.child("User Data").getValue("spot1Selected",false);
//        spot2SelectedDB = sharedPreferences.getBoolean("spot2Selected",false);
//        spot3SelectedDB = sharedPreferences.getBoolean("spot3Selected",false);
//        spot4SelectedDB = sharedPreferences.getBoolean("spot4Selected",false);
//        spot5SelectedDB = sharedPreferences.getBoolean("Spot5Selected",false);
//        spot6SelectedDB = sharedPreferences.getBoolean("spot6Selected",false);



        fromTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                fromHour = hourOfDay;
                                fromMinute = minute;
                                fromTimeTV.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();


            }
        });
/*
        toTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                toHour = hourOfDay;
                                toMinute = minute;
                                toTimeTV.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

*/

        parkingSpotPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!spotSelected) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.parking_spot_picker_layout);
                    confirmParkingSpotButton = dialog.findViewById(R.id.parking_spot_confirm_button);
                    parkingSpot1 = dialog.findViewById(R.id.parking_spot_location1);
                    parkingSpot2 = dialog.findViewById(R.id.parking_spot_location2);
                    parkingSpot3 = dialog.findViewById(R.id.parking_spot_location3);
                    parkingSpot4 = dialog.findViewById(R.id.parking_spot_location4);
                    parkingSpot5 = dialog.findViewById(R.id.parking_spot_location5);
                    parkingSpot6 = dialog.findViewById(R.id.parking_spot_location6);

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference dbRef = firebaseDatabase.getReference("Booking Details/User Data");
                    dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                spot1SelectedDB = snapshot.child("spot1Selected").getValue().toString();
                                spot2SelectedDB = snapshot.child("spot2Selected").getValue().toString();
                                spot3SelectedDB = snapshot.child("spot3Selected").getValue().toString();
                                spot4SelectedDB = snapshot.child("spot4Selected").getValue().toString();
                                spot5SelectedDB = snapshot.child("Spot5Selected").getValue().toString();
                                spot6SelectedDB = snapshot.child("spot6Selected").getValue().toString();
                                //Toast.makeText(getContext(), "Spot 1: " + spot1SelectedDB, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    if (spot1SelectedDB.equals("false")){
                        parkingSpot1.setBackgroundColor(Color.parseColor("#7af585"));
                        Toast.makeText(getContext(), "false", Toast.LENGTH_SHORT).show();
                    }else{
                        parkingSpot1.setBackgroundColor(Color.parseColor("#fa2832"));
                        Toast.makeText(getContext(), "Setted bg", Toast.LENGTH_SHORT).show();
                    }

                        parkingSpot1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(spot1SelectedDB.equals("false")) {
                                    if (numClicksS1 == 1) {
                                        parkingSpot1.setBackgroundColor(Color.parseColor("#fa2832"));
                                        numClicksS1 = 2;
                                    } else if (numClicksS1 == 2) {
                                        parkingSpot1.setBackgroundColor(Color.parseColor("#7af585"));
                                        numClicksS1 = 1;
                                    }
                                }
                                else{
                                    
                                    Toast.makeText(getContext(), "Spot is already selected!\nPlease select another spot", Toast.LENGTH_SHORT).show();
                                }


                                // Toast.makeText(getContext(), "Spot is booked already", Toast.LENGTH_SHORT).show();

                            }
                        });




                    if (spot2SelectedDB.equals("false")){
                        parkingSpot2.setBackgroundColor(Color.parseColor("#7af585"));
                        Toast.makeText(getContext(), "false", Toast.LENGTH_SHORT).show();
                    }else{
                        parkingSpot2.setBackgroundColor(Color.parseColor("#fa2832"));
                        Toast.makeText(getContext(), "Setted bg", Toast.LENGTH_SHORT).show();
                    }
                    parkingSpot2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(spot2SelectedDB.equals("false")) {
                                if (numClicksS2 == 1) {
                                    parkingSpot2.setBackgroundColor(Color.parseColor("#fa2832"));
                                    numClicksS2 = 2;
                                } else if (numClicksS2 == 2) {
                                    parkingSpot2.setBackgroundColor(Color.parseColor("#7af585"));
                                    numClicksS2 = 1;
                                }}
                            else{

                                Toast.makeText(getContext(), "Spot is already selected!\nPlease select another spot", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



                    if (spot3SelectedDB.equals("false")){
                        parkingSpot3.setBackgroundColor(Color.parseColor("#7af585"));
                        Toast.makeText(getContext(), "false", Toast.LENGTH_SHORT).show();
                    }else{
                        parkingSpot3.setBackgroundColor(Color.parseColor("#fa2832"));
                        Toast.makeText(getContext(), "Setted bg", Toast.LENGTH_SHORT).show();
                    }
                    parkingSpot3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(spot3SelectedDB.equals("false")) {
                                if (numClicksS3 == 1) {
                                    parkingSpot3.setBackgroundColor(Color.parseColor("#fa2832"));
                                    numClicksS3 = 2;
                                } else if (numClicksS3 == 2) {
                                    parkingSpot3.setBackgroundColor(Color.parseColor("#7af585"));
                                    numClicksS3 = 1;
                                }}
                                else{

                            Toast.makeText(getContext(), "Spot is already selected!\nPlease select another spot", Toast.LENGTH_SHORT).show();
                        }

                        }
                    });

                    if (spot4SelectedDB.equals("false")){
                        parkingSpot4.setBackgroundColor(Color.parseColor("#7af585"));
                        Toast.makeText(getContext(), "false", Toast.LENGTH_SHORT).show();
                    }else{
                        parkingSpot4.setBackgroundColor(Color.parseColor("#fa2832"));
                        Toast.makeText(getContext(), "Setted bg", Toast.LENGTH_SHORT).show();
                    }
                    parkingSpot4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(spot4SelectedDB.equals("false")) {
                                if (numClicksS4 == 1) {
                                    parkingSpot4.setBackgroundColor(Color.parseColor("#fa2832"));
                                    numClicksS4 = 2;
                                } else if (numClicksS4 == 2) {
                                    parkingSpot4.setBackgroundColor(Color.parseColor("#7af585"));
                                    numClicksS4 = 1;
                                }
                            }else{

                                    Toast.makeText(getContext(), "Spot is already selected!\nPlease select another spot", Toast.LENGTH_SHORT).show();
                                }

                        }
                    });

                    if (spot5SelectedDB.equals("false")){
                        parkingSpot5.setBackgroundColor(Color.parseColor("#7af585"));
                        Toast.makeText(getContext(), "false", Toast.LENGTH_SHORT).show();
                    }else{
                        parkingSpot5.setBackgroundColor(Color.parseColor("#fa2832"));
                        Toast.makeText(getContext(), "Setted bg", Toast.LENGTH_SHORT).show();
                    }
                    parkingSpot5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(spot5SelectedDB.equals("false")) {
                                if (numClicksS5 == 1) {
                                    parkingSpot5.setBackgroundColor(Color.parseColor("#fa2832"));
                                    numClicksS5 = 2;
                                } else if (numClicksS5 == 2) {
                                    parkingSpot5.setBackgroundColor(Color.parseColor("#7af585"));
                                    numClicksS5 = 1;
                                } }else{

                                Toast.makeText(getContext(), "Spot is already selected!\nPlease select another spot", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



                    if (spot6SelectedDB.equals("false")){
                        parkingSpot6.setBackgroundColor(Color.parseColor("#7af585"));
                        Toast.makeText(getContext(), "false", Toast.LENGTH_SHORT).show();
                    }else{
                        parkingSpot6.setBackgroundColor(Color.parseColor("#fa2832"));
                        Toast.makeText(getContext(), "Setted bg", Toast.LENGTH_SHORT).show();
                    }
                    parkingSpot6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                if(spot6SelectedDB.equals("false")) {
                                if (numClicksS6 == 1) {
                                    parkingSpot6.setBackgroundColor(Color.parseColor("#fa2832"));
                                    numClicksS6 = 2;
                                } else if (numClicksS6 == 2) {
                                    parkingSpot6.setBackgroundColor(Color.parseColor("#7af585"));
                                    numClicksS6 = 1;
                                } }else{

                                    Toast.makeText(getContext(), "Spot is already selected!\nPlease select another spot", Toast.LENGTH_SHORT).show();
                                }


                        }
                    });
                    confirmParkingSpotButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (numClicksS1 == 2 || numClicksS2 == 2 || numClicksS3 == 2 || numClicksS4 == 2 | numClicksS5 == 2 || numClicksS6 == 2) {
                                if (numClicksS1 == 2) {

                                    editor.putInt("Parking Spot Selected", 1);
                                    editor.commit();
                                } else if (numClicksS2 == 2) {
                                    editor.putInt("Parking Spot Selected", 2);
                                    editor.commit();
                                } else if (numClicksS3 == 2) {
                                    editor.putInt("Parking Spot Selected", 3);
                                    editor.commit();
                                } else if (numClicksS4 == 2) {
                                    editor.putInt("Parking Spot Selected", 4);
                                    editor.commit();
                                } else if (numClicksS5 == 2) {
                                    editor.putInt("Parking Spot Selected", 5);
                                    editor.commit();
                                } else if (numClicksS6 == 2) {
                                    editor.putInt("Parking Spot Selected", 6);
                                    editor.commit();
                                }
                                spotSelected = true;
                                int spotnum = sharedPreferences.getInt("Parking Spot Selected", 0);
                                parkingSpotNumber.setText("Spot Number: "+spotnum);
                                Toast.makeText(getContext(), "Parking Sport Selected: " + spotnum, Toast.LENGTH_LONG).show();

                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Please select atleast one spot", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                }else{
                    Toast.makeText(getContext(), "You have already selected a spot", Toast.LENGTH_SHORT).show();
                }
            }
        });
        datePickerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingName = fullname.getText().toString();
                bookingContact = contact.getText().toString();
                bookingDate = selectedDate.getText().toString();
                String durBook = durationSpinner.getSelectedItem().toString().trim();
                bookingDuration = durBook;
//                String datePicker = date.getText().toString();

                reference.child("User Data").child("Name").setValue(fullname.getText().toString());
                reference.child("User Data").child("contact").setValue(contact.getText().toString());
                reference.child("User Data").child("Date").setValue(selectedDate.getText().toString());
                reference.child("User Data").child("SpotNumber").setValue(parkingSpotNumber.getText().toString());
               // parkingDuration = (int)(toHour+(toMinute/100)) - (int)(fromHour+ (fromMinute/100));

                reference.child("Booking Details").child("Duration").setValue(parkingDuration);
              //  Toast.makeText(getContext(), "duration: "+parkingDuration, Toast.LENGTH_SHORT).show();


                //saving data offline
                editor.putString(getString(R.string.booking_name), fullname.getText().toString());
                editor.putString(getString(R.string.booking_contact), contact.getText().toString());
                editor.putString(getString(R.string.bookingdate), selectedDate.getText().toString());
                editor.putString(getString(R.string.booking_duration1), durBook);

                resultBundle.putString("fullname", fullname.getText().toString());
                resultBundle.putString("contact", contact.getText().toString());
                resultBundle.putString("date", selectedDate.getText().toString());
                resultBundle.putString("duration", durBook);
               // sendSMS(contact.getText().toString());

                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "3";
                runner.execute(sleepTime);

            }
        });
    }
    public void sendSMS(String phoneNum){


        if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS) +
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS))
                != PackageManager.PERMISSION_GRANTED) {

// Permission is not granted
// Should we show an explanation?

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),"Manifest.permission.READ_SMS") ||
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),"Manifest.permission.READ_SMS")) {
//                Toast.makeText(getContext(), "Permiss not denied", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), "Permiss denied", Toast.LENGTH_SHORT).show();
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage("4379875581",null,"Thanks for booking with us!",null, null);
//                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                sendIntent.putExtra("sms_body", "default content");
//                sendIntent.setType("vnd.android-dir/mms-sms");
//                startActivity(sendIntent);
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{"Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS"},
//                        1);
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage("4379875581",null,"Thanks for booking with us!",null, null);
//                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                sendIntent.putExtra("sms_body", "default content");
//                sendIntent.setType("vnd.android-dir/mms-sms");
//                startActivity(sendIntent);
                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        else {
            // Permission has already been granted
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage("4379875581",null,"Thanks for booking with us!",null, null);
//            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//            sendIntent.putExtra("sms_body", "default content");
//            sendIntent.setType("vnd.android-dir/mms-sms");
//            startActivity(sendIntent);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = getActivity().findViewById(android.R.id.content);
        Snackbar.make(v, "Booking Screen", Snackbar.LENGTH_LONG).show();


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_booking, container, false);
        button = view.findViewById(R.id.book_button);
        fullname = view.findViewById(R.id.full_name);
        contact = view.findViewById(R.id.contact_number);
        date = view.findViewById(R.id.date);
        duration = view.findViewById(R.id.duration);

//        date.addTextChangedListener(new TextWatcher() {
//
//            private String current = "";
//            private String ddmmyyyy = "DDMMYYYY";
//            private Calendar cal = Calendar.getInstance();
//
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!s.toString().equals(current)) {
//                    String clean = s.toString().replaceAll("[^\\d.]", "");
//                    String cleanC = current.replaceAll("[^\\d.]", "");
//
//                    int cl = clean.length();
//                    int sel = cl;
//                    for (int i = 2; i <= cl && i < 6; i += 2) {
//                        sel++;
//                    }
//
//                    if (clean.equals(cleanC)) sel--;
//
//                    if (clean.length() < 8) {
//                        clean = clean + ddmmyyyy.substring(clean.length());
//                    } else {
//                        int day = Integer.parseInt(clean.substring(0, 2));
//                        int mon = Integer.parseInt(clean.substring(2, 4));
//                        int year = Integer.parseInt(clean.substring(4, 8));
//
//                        if (mon > 12) mon = 12;
//                        cal.set(Calendar.MONTH, mon - 1);
//
//                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
//                        cal.set(Calendar.YEAR, year);
//
//                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
//                        clean = String.format("%02d%02d%02d", day, mon, year);
//                    }
//
//                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
//                            clean.substring(2, 4),
//                            clean.substring(4, 8));
//
//                    sel = sel < 0 ? 0 : sel;
//                    current = clean;
//                    date.setText(current);
//                    date.setSelection(sel < current.length() ? sel : current.length());
//                }
//
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        return view;
    }
    public void sendNotification(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Booking Notification", "Parking Spot Booked", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Thanks for booking parking spot with us");
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        sendNotificationProcess();
    }
    public void sendNotificationProcess(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "Booking Notification");
        builder.setContentTitle("Smart Parking")
                .setContentText("Thanks for booking a parking spot with us!")
                .setSmallIcon((R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());

        managerCompat.notify(2,builder.build());
    }
    public void datePicker(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // on below line we are creating a variable for date picker dialog.
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                // on below line we are passing context.
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our text view.
                        selectedDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                },
                // on below line we are passing year,
                // month and day for selected date in our date picker.
                year, month, day);
        datePickerDialog.show();
    }



        private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;


        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(params[0])*1000;

                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";

            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            sendNotification();
            progressDialog.dismiss();


            BookingDetailFragment bookingDetailFragment = new BookingDetailFragment();
            bookingDetailFragment.setArguments(resultBundle);


            getFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, bookingDetailFragment).commit();
            Toast.makeText(getContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show();

        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getContext(),
                    "Confirming...",
                    "Wait for few seconds");
        }


        @Override
        protected void onProgressUpdate(String... text) {


        }
    }
}