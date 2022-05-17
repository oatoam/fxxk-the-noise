package com.toddmo.apps.capture.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.toddmo.apps.capture.R;
import com.toddmo.apps.capture.databinding.FragmentMainBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConfigurationFragment extends Fragment {
    private static final String LOG_TAG = ConfigurationFragment.class.getCanonicalName();

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PlaceHolderPageViewModel placeHolderPageViewModel;
    private FragmentMainBinding binding;

    public static ConfigurationFragment newInstance() {
        ConfigurationFragment fragment = new ConfigurationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

//        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_configuration, container, false); //  binding.getRoot();

//        final TextView textView = root.findViewById(R.id.section_label); // binding.sectionLabel;
//        textView.setText("hello from home");

        final Button btn = root.findViewById(R.id.helloworldbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello world from home button!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}