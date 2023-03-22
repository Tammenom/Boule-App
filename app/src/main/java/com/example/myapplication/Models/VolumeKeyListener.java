package com.example.myapplication.Models;

import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.myapplication.GameRoundActivity;





public class VolumeKeyListener implements KeyListener {

    private GameRoundActivity gameRoundActivity = null;

    public VolumeKeyListener(GameRoundActivity nGameRoundActivity){
        gameRoundActivity = nGameRoundActivity;
    }

    @Override
    public int getInputType() {
        return 0;
    }

    @Override
    public boolean onKeyDown(View view, Editable editable, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_VOLUME_DOWN || i == KeyEvent.KEYCODE_VOLUME_UP){
            Log.d("KeyListener", "Volume Key Pressed");

        }

        return true;
    }

    @Override
    public boolean onKeyUp(View view, Editable editable, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyOther(View view, Editable editable, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void clearMetaKeyState(View view, Editable editable, int i) {

    }
}
