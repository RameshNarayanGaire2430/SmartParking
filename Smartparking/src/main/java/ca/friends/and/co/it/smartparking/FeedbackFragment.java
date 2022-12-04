package ca.friends.and.co.it.smartparking;

//Ramesh Narayan Gaire N01452430, ONA
//Roshan Shrestha N01457532, ONA
// Rushi Bhandari N01464259, ONA
// Komal Bamotra N01426087,ONA


// single responsibility principle used
// This java class is only related to giving the feedback

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment {
    EditText reviewEditName;
    EditText reviewEditPhone;
    EditText reviewEditEmail;
    EditText reviewEditText;

    String reveiewName;
    String reveiewPhone;
    String reveiewEmail;
    String reveiewData;
    RatingBar feedbackStars;
    Button sendButton;
    float ratings;


    public FeedbackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = getActivity().findViewById(android.R.id.content);
        Snackbar.make(view, "Feedback Screen", Snackbar.LENGTH_LONG).show();


        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewEditName = view.findViewById(R.id.userReviewName);
        reviewEditPhone = view.findViewById(R.id.userReviewPhone);
        reviewEditEmail = view.findViewById(R.id.userReviewEmail);
        reviewEditText = view.findViewById(R.id.userReviewText);

        sendButton = view.findViewById(R.id.sendFeedback);
        feedbackStars = view.findViewById(R.id.ratingBar);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Feedback");



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reveiewName = reviewEditName.getText().toString();
                reveiewPhone = reviewEditPhone.getText().toString();
                reveiewEmail = reviewEditEmail.getText().toString();
                reveiewData = reviewEditText.getText().toString();

                ratings = feedbackStars.getRating();

                reference.child("Customer Name").setValue(reveiewName);
                reference.child("Customer Phone").setValue(reveiewPhone);
                reference.child("Customer Email").setValue(reveiewEmail);
                reference.child("Customer Review").setValue(reveiewData);
                reference.child("Ratings").setValue(ratings);


            }
        });
    }
}