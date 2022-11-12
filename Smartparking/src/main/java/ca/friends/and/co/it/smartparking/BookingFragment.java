package ca.friends.and.co.it.smartparking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

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
        Button button = view.findViewById(R.id.button);
        EditText fullname = view.findViewById(R.id.full_name);
        EditText contact = view.findViewById(R.id.contact_number);
        EditText date = view.findViewById(R.id.date);
        EditText duration = view.findViewById(R.id.duration);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle result = new Bundle();
                result.putString ("fullname", fullname.getText().toString());
                result.putString ("contact", contact.getText().toString());
                result.putString ("date", date.getText().toString());
                result.putString ("duration", duration.getText().toString());


                BookingDetailFragment bookingDetailFragment = new BookingDetailFragment();
                bookingDetailFragment.setArguments(result);


                getFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, bookingDetailFragment).commit();
/*
                editText.setText("");*/
            }
        });
        return view;
    }
}