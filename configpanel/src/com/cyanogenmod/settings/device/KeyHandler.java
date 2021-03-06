/*
* Copyright (C) 2017 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package com.cyanogenmod.settings.device;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.database.ContentObserver;
import android.os.Handler;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.os.UserHandle;

import com.android.internal.os.DeviceKeyHandler;
import com.android.internal.util.ArrayUtils;

public class KeyHandler implements DeviceKeyHandler {

    private static final String TAG = KeyHandler.class.getSimpleName();
    private static final boolean DEBUG = true;

    private static final int KEY_HOME = 102;

    protected final Context mContext;
    private Handler mHandler = new Handler();
    private SettingsObserver mSettingsObserver;
    private static boolean mHomeButtonWakeEnabled;

    private class SettingsObserver extends ContentObserver {
        SettingsObserver(Handler handler) {
            super(handler);
        }

        void observe() {
            update();
        }

        @Override
        public void onChange(boolean selfChange) {
            update();
        }


        public void update() {
            if (DEBUG) Log.i(TAG, "update called" );
            setButtonSetting(mContext);
        }
    }

    public KeyHandler(Context context) {
        if (DEBUG) Log.i(TAG, "KeyHandler called");
        mContext = context;
        mSettingsObserver = new SettingsObserver(mHandler);
        mSettingsObserver.observe();
    }

    @Override
    public boolean handleKeyEvent(KeyEvent event) {
        if (DEBUG) Log.i(TAG, "handleKeyEvent called - scancode=" + event.getScanCode() + " - keyevent=" + event.getAction());
        return false;
    }

    public boolean canHandleKeyEvent(KeyEvent event) {
        Log.i(TAG, "canHandleKeyEvent called - scancode=" + event.getScanCode() + " - keyevent=" + event.getAction());
        return false;
    }

    public boolean isDisabledKeyEvent(KeyEvent event) {
        return false;
    }

    public static void setButtonSetting(Context context) {
        if (DEBUG) Log.i(TAG, "mHomeButtonWakeEnabled=" + mHomeButtonWakeEnabled);
    }

    public boolean isCameraLaunchEvent(KeyEvent event) {
        return false;
    }

    public boolean isWakeEvent(KeyEvent event){
        if (DEBUG) Log.i(TAG, "isWakeEvent called - scancode=" + event.getScanCode() + " - keyevent=" + event.getAction());
        if (event.getAction() != KeyEvent.ACTION_UP) {
            return false;
        }
        if (DEBUG) Log.i(TAG, "mHomeButtonWakeEnabled=" + mHomeButtonWakeEnabled);
        if (mHomeButtonWakeEnabled){
            if (DEBUG) Log.i(TAG, "bHomeButtonWake == true");
            if (event.getScanCode() == KEY_HOME) {
                if (DEBUG) Log.i(TAG, "KEY_HOME pressed");
                return true;
            }
        } else {
            if (DEBUG) Log.i(TAG, "bHomeButtonWake == false");
        }
        return false;
    }

    public Intent isActivityLaunchEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {
            return null;
        }
        return null;
    }
}
