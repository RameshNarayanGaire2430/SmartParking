package ca.friends.and.co.it.smartparking;

//Ramesh Narayan Gaire N01452430, ONA
//Roshan Shrestha N01457532, ONA
// Rushi Bhandari N01464259, ONA
// Komal Bamotra N01426087,ONA

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SupportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupportFragment extends Fragment {

    ImageView liveChatImageBtn;
    ImageView callImageBtn;
    ImageView EmailImageBtn;

    private static final int REQUEST_CALL = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SupportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SupportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SupportFragment newInstance() {
        SupportFragment fragment = new SupportFragment();
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
        Snackbar.make(v, "Support Screen", Snackbar.LENGTH_LONG).show();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_support, container, false);
        liveChatImageBtn = view.findViewById(R.id.imageView10);
        callImageBtn = view.findViewById(R.id.imageView8);
        EmailImageBtn = view.findViewById(R.id.imageView11);


        liveChatImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Opening Live Chat", Toast.LENGTH_SHORT).show();
                }
        });

        callImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Calling", Toast.LENGTH_SHORT).show();
                makePhoneCall();
            }
        });

        EmailImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Opening Email", Toast.LENGTH_SHORT).show();
               }
        });

        return view;
    }

    private void makePhoneCall() {
        String number = "4379875581";
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    Manifest.permission.CALL_PHONE}
            ,REQUEST_CALL);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

            }
        }
    }
}