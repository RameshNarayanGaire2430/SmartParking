package ca.friends.and.co.it.smartparking;

//Ramesh Narayan Gaire N01452430, ONA
//Roshan Shrestha N01457532, ONA
// Rushi Bhandari N01464259, ONA
// Komal Bamotra N01426087,ONA


// single responsibility principle used
// This java class is only related to displaying the booking detail

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingDetailFragment extends Fragment {

    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment BookingDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingDetailFragment newInstance() {
        BookingDetailFragment fragment = new BookingDetailFragment();
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
        // Inflate the layout for this fragment

        View v = getActivity().findViewById(android.R.id.content);
        Snackbar.make(v, "Booking Detail Screen", Snackbar.LENGTH_LONG).show();


        view =  inflater.inflate(R.layout.fragment_booking_detail, container, false);

/*        getParentFragmentManager().setFragmentResultListener("namefrombooking", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                String data = result.getString("fullname");
                TextView textView = view.findViewById(R.id.recieving_full_name);
                textView.setText(data);
            }
        });*/

        TextView recievingfullname = view.findViewById(R.id.recieving_full_name);
        TextView recievingContact = view.findViewById(R.id.recieving_contact);
        TextView recievingDate = view.findViewById(R.id.recieving_date);
        TextView recievingDuration = view.findViewById(R.id.recieving_duration);

        Bundle result = this.getArguments();
        String name = result.getString("fullname");
        String contact = result.getString("contact");
        String date = result.getString("date");
        String duration = result.getString("duration");

        recievingfullname.setText(name);
        recievingContact.setText(contact);
        recievingDate.setText(date);
        recievingDuration.setText(duration);


        return view;
    }
}