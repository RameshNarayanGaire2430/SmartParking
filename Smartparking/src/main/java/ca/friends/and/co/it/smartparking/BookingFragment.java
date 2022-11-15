package ca.friends.and.co.it.smartparking;

//Ramesh Narayan Gaire N01452430, ONA
//Roshan Shrestha N01457532, ONA
// Rushi Bhandari N01464259, ONA
// Komal Bamotra N01426087,ONA

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    Bundle resultBundle = new Bundle();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = getActivity().findViewById(android.R.id.content);
        Snackbar.make(v, "Booking Screen", Snackbar.LENGTH_LONG).show();


        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_booking, container, false);
        Button button = view.findViewById(R.id.book_button);
        EditText fullname = view.findViewById(R.id.full_name);
        EditText contact = view.findViewById(R.id.contact_number);
        EditText date = view.findViewById(R.id.date);
        EditText duration = view.findViewById(R.id.duration);

        date.addTextChangedListener(new TextWatcher() {

            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)){
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i=2; i<=cl && i<6; i+=2){
                        sel++;
                    }

                    if (clean.equals(cleanC)) sel--;

                    if(clean.length()<8){
                        clean =clean + ddmmyyyy.substring(clean.length());
                    }else{
                        int day = Integer.parseInt(clean.substring(0,2));
                        int mon = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year < 1900)?1900: (year>2100)?2100: year;
                        cal.set(Calendar.YEAR, year);

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0,2),
                            clean.substring(2,4),
                            clean.substring(4,8));

                    sel = sel <0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());
                }

            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                resultBundle.putString ("fullname", fullname.getText().toString());
                resultBundle.putString ("contact", contact.getText().toString());
                resultBundle.putString ("date", date.getText().toString());
                resultBundle.putString ("duration", duration.getText().toString());
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "3";
                runner.execute(sleepTime);

/*
                editText.setText("");*/
            }
        });
        return view;
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