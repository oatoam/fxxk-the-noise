package com.toddmo.apps.capture.ui.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.toddmo.apps.capture.R;
import com.toddmo.apps.capture.databinding.FragmentMainBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomePageFragment extends Fragment {
    private static final String LOG_TAG = HomePageFragment.class.getCanonicalName();

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PlaceHolderPageViewModel placeHolderPageViewModel;
    private FragmentMainBinding binding;

    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
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
        View root = inflater.inflate(R.layout.fragment_homepage, container, false); //  binding.getRoot();

//        final TextView textView = root.findViewById(R.id.section_label); // binding.sectionLabel;
//        textView.setText("hello from home");

        final Button btn = root.findViewById(R.id.permissionbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] requiredPermissions = {
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                };
                for (String perm: requiredPermissions) {
                    if (ContextCompat.checkSelfPermission(getContext(), perm) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[] {perm}, 0);
                    }
                }
                Snackbar.make(view, "Permission grant done.", Snackbar.LENGTH_LONG)
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