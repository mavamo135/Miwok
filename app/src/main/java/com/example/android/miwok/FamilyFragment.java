package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    //This listener gets triggered when the {@link MediaPlayer} has completed playing the audio file
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        //The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                        //short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means
                        //our app is allowed to continue playing sound but at a lower volume.
                        //Pause playback and reset player to the start of the file. That way, we
                        //play the word from the beginning when we resume playback.
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        //The AUDIOFOCUS_GAIN case means we have regained focus and can resume
                        //playback
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        //The AUDIOFOCUS_LOSS case means we've lost audio focus and stop playback
                        //and clean up resources
                        releaseMediaPlayer();
                    }
                }
            };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_family, container, false);
        //Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("father", "әpә", R.mipmap.family_father, R.raw.family_father));
        words.add(new Word("mother", "әṭa" , R.mipmap.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.mipmap.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.mipmap.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.mipmap.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.mipmap.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.mipmap.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.mipmap.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother ", "ama", R.mipmap.family_grandfather, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.mipmap.family_grandfather, R.raw.family_grandfather));
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_family);
        ListView listView = (ListView) rootView.findViewById(R.id.list_family);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                releaseMediaPlayer();
                //Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        //Use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                    //We have audio focus now
                    //Create and setup the {@link MediaPlayer} for the audio resource associated with
                    //the current word
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getmAudioResourceID());
                    //Start the audio file
                    mMediaPlayer.start();
                    //Setup a listener on the media player, so that we can stop and release the media
                    //player once the sounds has finished playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            //Regardless of whether or not we were granted audio focus, abandon it. This also
            //unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
