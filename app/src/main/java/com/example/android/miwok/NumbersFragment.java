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
public class NumbersFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    //Handles audio focus when playing a sound file
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

    public NumbersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);
        //Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<>();
        //String [] words = new String[10]; //words[0] = "one";
        /*
        for (int index=0; index<words.size(); index++) {
            TextView wordView = new TextView(this);
            wordView.setText(words.get(index));
            rootView.addView(wordView);
        }
        */
        words.add(new Word("one", "lutti", R.mipmap.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.mipmap.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.mipmap.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.mipmap.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka",R.mipmap.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.mipmap.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.mipmap.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.mipmap.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.mipmap.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.mipmap.number_ten, R.raw.number_ten));
        //LinearLayout rootView = (LinearLayout)findViewById(R.id.rootView);
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_numbers);
        //Spinner, ListView and GridView
        //GridView add android:numColumns="2" in activity_number.xml
        ListView listView = (ListView) rootView.findViewById(R.id.list_numbers);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);
                //Release the media player if it currently exists because we are about to play a
                //different sound file
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
        //When the activity is stopped, release the media player resources because we won't be
        //playing any more sounds
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
