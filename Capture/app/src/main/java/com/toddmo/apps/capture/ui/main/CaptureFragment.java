package com.toddmo.apps.capture.ui.main;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
public class CaptureFragment extends Fragment {
    private static final String LOG_TAG = CaptureFragment.class.getCanonicalName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PlaceHolderPageViewModel placeHolderPageViewModel;
    private FragmentMainBinding binding;

    private SurfaceView surface = null;

    public static CaptureFragment newInstance() {
        CaptureFragment fragment = new CaptureFragment();
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
        View root = inflater.inflate(R.layout.fragment_capture, container, false); //  binding.getRoot();

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

        surface = root.findViewById(R.id.surfaceView);
        SurfaceHolder holder = surface.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                Log.d(LOG_TAG, "surfaceCreated");
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.d(LOG_TAG, "surfaceChanged " + i + ", " + i1 + ", " + i2);
                Canvas c = holder.lockCanvas();
                Paint paint = new Paint();
                paint.setColor(0xff0000ff);
                c.drawRect(new Rect(0, 0, i1, i2), paint);
                holder.unlockCanvasAndPost(c);
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                Log.d(LOG_TAG, "surfaceDestroyed");
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