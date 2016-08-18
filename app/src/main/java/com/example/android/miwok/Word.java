package com.example.android.miwok;

/**
 * Created by Maximiliano Valencia on 11/08/2016.
 */
public class Word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mSourceImage= NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int mAudioResourceID;

    public Word(String defaultTranslation, String miwokTranslation, int sourceImage, int audioResourceID) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mSourceImage = sourceImage;
        mAudioResourceID = audioResourceID;
    }
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceID) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceID = audioResourceID;
    }
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }
    public int getSourceImage(){
        return mSourceImage;
    }
    public boolean hasImage(){
        return mSourceImage != NO_IMAGE_PROVIDED;
    }
    public int getmAudioResourceID() { return mAudioResourceID; }
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mSourceImage=" + mSourceImage +
                ", mAudioResourceID=" + mAudioResourceID +
                '}';
    }
}
