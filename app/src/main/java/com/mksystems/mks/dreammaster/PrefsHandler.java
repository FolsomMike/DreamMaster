package com.mksystems.mks.dreammaster;

//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
// class PrefsHandler
//
// Various tools for reading and writing values to the prefs file.
//

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsHandler {

//-----------------------------------------------------------------------------------------------
// PrefsHandler::getContext
//
// Gets the MainActivities context.
//

    private static Context getContext() {

        return (MainActivity.getAppContext());

    }

//end of PrefsHandler::getSharedPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// PrefsHandler::getSharedPrefs
//
// Returns a SharedPreferences reference to the app's share preferences file.
//
// A named prefs file is used to allow sharing between activities.
//

    public static SharedPreferences getSharedPrefs() {

        return (getContext().getSharedPreferences(
                "com.mksystems.dreammaster.APP_PREFERENCES", Context.MODE_PRIVATE));

    }

//end of PrefsHandler::getSharedPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// PrefsHandler::readLongFromPrefs
//
// Reads a long value for pKey from the prefs file using pDefault as the default.
//
// A named prefs file is used to allow sharing between activities.
//

    public static long readLongFromPrefs(String pKey, long pDefault) {

        return(getSharedPrefs().getLong(pKey, pDefault));

    }

//end of PrefsHandler::readLongFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// PrefsHandler::readIntFromPrefs
//
// Reads an int value for pKey from the prefs file using pDefault as the default.
//
// A named prefs file is used to allow sharing between activities.
//

    public static int readIntFromPrefs(String pKey, int pDefault) {

        return(getSharedPrefs().getInt(pKey, pDefault));

    }

//end of PrefsHandler::readIntFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// PrefsHandler::readBooleanFromPrefs
//
// Reads a boolean value for pKey from the prefs file using pDefault as the default.
//
// A named prefs file is used to allow sharing between activities.
//

    public static boolean readBooleanFromPrefs(String pKey, boolean pDefault) {

        return(getSharedPrefs().getBoolean(pKey, pDefault));

    }

//end of PrefsHandler::readBooleanFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// PrefsHandler::readStringFromPrefs
//
// Reads a String value for pKey from the prefs file using pDefault as the default.
//
// A named prefs file is used to allow sharing between activities.
//

    public static String readStringFromPrefs(String pKey, String pDefault) {

        return(getSharedPrefs().getString(pKey, pDefault));

    }

//end of PrefsHandler::readStringFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// PrefsHandler::getPrefsEditor
//
// Returns an editor for the app's prefs file.
//
// A named prefs file is used to allow sharing between activities.
//

    public static SharedPreferences.Editor getPrefsEditor() {

        SharedPreferences sharedPref = getSharedPrefs();

        return(sharedPref.edit());

    }

//end of PrefsHandler::getPrefsEditor
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// PrefsHandler::writeLongToPrefs
//
// Writes long pValue to the prefs file for key pKey.
//
// A named prefs file is used to allow sharing between activities.
//

    public static void writeLongToPrefs(String pKey, long pValue) {

        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putLong(pKey, pValue);
        editor.commit();

    }

//end of PrefsHandler::writeLongToPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// PrefsHandler::writeIntToPrefs
//
// Writes int pValue to the prefs file for key pKey.
//
// A named prefs file is used to allow sharing between activities.
//

    public static void writeIntToPrefs(String pKey, int pValue) {

        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putInt(pKey, pValue);
        editor.commit();

    }

//end of PrefsHandler::writeIntToPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// PrefsHandler::writeBooleanToPrefs
//
// Writes boolean pValue to the prefs file for key pKey.
//
// A named prefs file is used to allow sharing between activities.
//

    public static void writeBooleanToPrefs(String pKey, boolean pValue) {

        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putBoolean(pKey, pValue);
        editor.commit();

    }

//end of PrefsHandler::writeBooleanToPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// PrefsHandler::writeStringToPrefs
//
// Writes String pValue to the prefs file for key pKey.
//
// A named prefs file is used to allow sharing between activities.
//

    public static void writeStringToPrefs(String pKey, String pValue) {

        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putString(pKey, pValue);
        editor.commit();

    }

//end of PrefsHandler::writeStringToPrefs
//-----------------------------------------------------------------------------------------------

}// end of class PrefsHandler
//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
