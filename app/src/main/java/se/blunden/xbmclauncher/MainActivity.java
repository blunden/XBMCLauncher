package se.blunden.xbmclauncher;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
	
	private static final String TAG = "XBMCLauncher";

    private void launch() {
		// Load preferences
		SharedPreferences settings = getSharedPreferences("se.blunden.xbmclauncher_preferences", Context.MODE_PRIVATE);
		String xbmcActivity = settings.getString("xbmc_variant", getString(R.string.xbmc_activity_default));

		if (settings.getBoolean("override_xbmc_variant", false) && !settings.getString("xbmc_custom_variant", "").isEmpty()) {
			String customActivityString = settings.getString("xbmc_custom_variant", getString(R.string.xbmc_activity_default));
			if (customActivityString != null && !customActivityString.isEmpty() && validateActivityString(customActivityString)) {
				xbmcActivity = customActivityString;
			} else {
				Toast.makeText(this, "Entered custom activity has an invalid format", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "Failed to find suitable launch activity for specified package name");

				launchSettings();
				return;
			}
		}

		// Build the Kodi/XBMC intent
        Intent activityIntent;
        activityIntent = new Intent(Intent.ACTION_MAIN);
        activityIntent.setComponent(ComponentName.unflattenFromString(xbmcActivity));
        /*
         * The launched activity will be considered the HOME activity and will be on top of the
         * stack as needed for the system to broadcast BOOT_COMPLETED.
         */
        activityIntent.addCategory(Intent.CATEGORY_HOME);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

		try {
			startActivity(activityIntent);
        }
		catch (ActivityNotFoundException e)	{
			Log.d(TAG, "Activity " + xbmcActivity + " not found. Launching settings activity...");
			
			launchSettings();
		}
		catch (Exception e) {
			Log.d(TAG, "Unable to launch " + xbmcActivity + " due to an exception. Launching settings activity...");

			launchSettings();
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		launch();
		finish();
	}

	private void launchSettings() {
		Intent launchSettings = new Intent(this, LauncherSettingsActivity.class);
		startActivity(launchSettings);
	}

	private static boolean validateActivityString(String activityString) {
		// Try to make a rough regex validation of the provided activity string
		final Pattern pattern = Pattern.compile("[A-Za-z0-9]+\\.[A-Za-z0-9]+\\.[A-Za-z0-9]+/[A-Za-z0-9]+\\.[A-Za-z0-9]+\\.[A-Za-z0-9]+\\.[A-Za-z0-9]+", Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(activityString);

		return matcher.matches();
	}
}
