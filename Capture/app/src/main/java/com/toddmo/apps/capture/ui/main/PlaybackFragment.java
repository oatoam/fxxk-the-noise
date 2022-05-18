package com.toddmo.apps.capture.ui.main;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
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
public class PlaybackFragment extends Fragment {
    private static final String LOG_TAG = PlaybackFragment.class.getCanonicalName();

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PlaceHolderPageViewModel placeHolderPageViewModel;
    private FragmentMainBinding binding;

    public static PlaybackFragment newInstance() {
        PlaybackFragment fragment = new PlaybackFragment();
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
        View root = inflater.inflate(R.layout.fragment_playback, container, false); //  binding.getRoot();

//        final TextView textView = root.findViewById(R.id.section_label); // binding.sectionLabel;
//        textView.setText("hello from home");
        Log.d(LOG_TAG, "onCreateView");
        final Button btn = root.findViewById(R.id.playback);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "playback btn clicked");

                Thread playbackThread  = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(LOG_TAG, "playback thread running");
                        int channelMask = AudioFormat.CHANNEL_OUT_MONO;
                        int audioFormat = AudioFormat.ENCODING_PCM_FLOAT;
                        int sampleRate = 8000;
                        int minBufferSizeInBytes = AudioTrack.getMinBufferSize(sampleRate, channelMask, audioFormat);
                        AudioTrack at = new AudioTrack.Builder()
                                .setAudioAttributes(new AudioAttributes.Builder()
                                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                        .setUsage(AudioAttributes.USAGE_MEDIA)
                                        .build())
                                .setAudioFormat(new AudioFormat.Builder()
                                        .setChannelMask(channelMask)
                                        .setEncoding(audioFormat)
                                        .setSampleRate(sampleRate)
                                        .build())
                                .setBufferSizeInBytes(minBufferSizeInBytes)
                                .build();
                        Log.d(LOG_TAG, "AudioTrack initiated");

                        float[] sineData = new float[sampleRate * 360];
                        for (int i=0;i<sineData.length;i++) {
//                            sineData[i] =  255f * (float)Math.sin(Math.PI / 360 / 8000 * 1600 * i);
                            sineData[i] = i % 32; // (float)Math.random();
                        }
                        Log.d(LOG_TAG, "sineData initiated");
                        at.play();
                        int offset = 0;
                        while (true) {
                            int writeSize = at.write(sineData, offset, minBufferSizeInBytes, AudioTrack.WRITE_BLOCKING);
                            offset += writeSize;
                            if (offset > sineData.length) {
                                offset = 0;
                            }
                            Log.v(LOG_TAG, "write " + writeSize);
                        }
                    }
                });

                playbackThread.start();
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