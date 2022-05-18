package com.toddmo.apps.capture.ui.main;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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

    private Thread mRecordThread = null;

    private WaveGraphView waveGraph = null;

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

        waveGraph = root.findViewById(R.id.wavegraph);

        Button rcbtn = (Button)root.findViewById(R.id.recordcontrol);
        rcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread mRecordThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(LOG_TAG, "RecordThread running");
                        int audioSource = MediaRecorder.AudioSource.MIC;
//                int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
                        int audioFormat = AudioFormat.ENCODING_PCM_FLOAT;
                        int sampleRate = 8000;
                        int channelMask = AudioFormat.CHANNEL_IN_MONO;
                        int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelMask, audioFormat);
                        @SuppressLint("MissingPermission")
                        AudioRecord ar = new AudioRecord.Builder()
                                .setAudioSource(audioSource)
                                .setAudioFormat(new AudioFormat.Builder()
                                        .setEncoding(audioFormat)
                                        .setSampleRate(sampleRate)
                                        .setChannelMask(channelMask)
                                        .build())
                                .setBufferSizeInBytes(2*minBufferSize)
                                .build();
                        ar.startRecording();
                        int c = 0;
                        while (true) {
                            float[] buffer = new float[2*minBufferSize];
                            int readSize = ar.read(buffer, 0, buffer.length, AudioRecord.READ_BLOCKING);
                            for (int i=0;i<readSize;i++) {
                                c++;
                                if (c % 91 == 0) {
                                    waveGraph.pushData(buffer[i]);
                                }
                            }
                        }

                    }
                });

                mRecordThread.start();
                Log.d(LOG_TAG, "RecordThread initiated");
            }
        });



        return root;
    }

    @Override
    public void onPause() {
        Log.d(LOG_TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.d(LOG_TAG, "onHiddenChanged " + hidden);
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}