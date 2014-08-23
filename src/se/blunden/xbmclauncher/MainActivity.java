package se.blunden.xbmclauncher;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private String xbmcActivity; 
	
	private void launch()
	{
		// Load preferences
		SharedPreferences settings;
		settings = getSharedPreferences("preferences", Context.MODE_PRIVATE);
		
		xbmcActivity = settings.getString("xbmc_variant", getString(R.string.xbmc_activity_default));
		
		Intent activityIntent;
		
		// Intent.makeMainActivity is only available in API level 11 and above
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			activityIntent = Intent.makeMainActivity(ComponentName.unflattenFromString(xbmcActivity));
		} else {
			activityIntent = new Intent(Intent.ACTION_MAIN);
            activityIntent.setComponent(ComponentName.unflattenFromString(xbmcActivity));
            activityIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		}
		
		try {
			startActivity(activityIntent);
			return;
		}
		catch (ActivityNotFoundException e)
		{
			Toast.makeText(this, "Activity " + xbmcActivity + " not found. Launching settings activity...", Toast.LENGTH_SHORT).show();
			
			Intent launchSettings = new Intent(this, LauncherSettingsActivity.class);
			startActivity(launchSettings);
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		launch();
		finish();
	}
}
