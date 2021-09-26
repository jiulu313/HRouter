package com.helloworld.hrouter.core;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * 传真，界面跳转时的中转
 */
public class Fax {
    private Context mContext;
    private String mPath;
    private Bundle mBundle;
    private int enterAnim = -1;
    private int exitAnim = -1;

    public Fax(String path){
        mPath = path;
        mBundle = new Bundle();
    }

    public void go(){
        go(null,this);
    }

    public void go(Context context){
        mContext = context;
        go(context,this);
    }

    private void go(Context context,Fax fax){
        HRouter.getInstance().go(context,fax);
    }

    public String getPath(){
        return mPath;
    }

    public Bundle getBundle(){
        return mBundle;
    }

    // Follow api copy from #{Bundle}
    public Fax withString(@Nullable String key, @Nullable String value) {
        mBundle.putString(key, value);
        return this;
    }

    public Fax withBoolean(@Nullable String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public Fax withShort(@Nullable String key, short value) {
        mBundle.putShort(key, value);
        return this;
    }

    public Fax withInt(@Nullable String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public Fax withLong(@Nullable String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }

    public Fax withDouble(@Nullable String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }

    public Fax withByte(@Nullable String key, byte value) {
        mBundle.putByte(key, value);
        return this;
    }

    public Fax withChar(@Nullable String key, char value) {
        mBundle.putChar(key, value);
        return this;
    }

    public Fax withFloat(@Nullable String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public Fax withCharSequence(@Nullable String key, @Nullable CharSequence value) {
        mBundle.putCharSequence(key, value);
        return this;
    }

    public Fax withParcelable(@Nullable String key, @Nullable Parcelable value) {
        mBundle.putParcelable(key, value);
        return this;
    }

    public Fax withParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        mBundle.putParcelableArray(key, value);
        return this;
    }

    public Fax withParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
        mBundle.putParcelableArrayList(key, value);
        return this;
    }

    public Fax withSparseParcelableArray(@Nullable String key, @Nullable SparseArray<? extends Parcelable> value) {
        mBundle.putSparseParcelableArray(key, value);
        return this;
    }

    public Fax withIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        mBundle.putIntegerArrayList(key, value);
        return this;
    }

    public Fax withStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
        return this;
    }

    public Fax withCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
        mBundle.putCharSequenceArrayList(key, value);
        return this;
    }

    public Fax withSerializable(@Nullable String key, @Nullable Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    public Fax withByteArray(@Nullable String key, @Nullable byte[] value) {
        mBundle.putByteArray(key, value);
        return this;
    }

    public Fax withShortArray(@Nullable String key, @Nullable short[] value) {
        mBundle.putShortArray(key, value);
        return this;
    }

    public Fax withCharArray(@Nullable String key, @Nullable char[] value) {
        mBundle.putCharArray(key, value);
        return this;
    }

    public Fax withFloatArray(@Nullable String key, @Nullable float[] value) {
        mBundle.putFloatArray(key, value);
        return this;
    }

    public Fax withCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
        mBundle.putCharSequenceArray(key, value);
        return this;
    }

    public Fax withBundle(@Nullable String key, @Nullable Bundle value) {
        mBundle.putBundle(key, value);
        return this;
    }

    public Fax withTransition(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

}
