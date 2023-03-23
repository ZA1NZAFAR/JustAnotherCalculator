package ovh.zain.calculator.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class StringSaver {
    public static String getResult(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPref.getString(key, "Don't have any result yet");
    }

    public static void saveResult(Context context, String key, String result) {
        SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, result);
        editor.apply();
    }
}
