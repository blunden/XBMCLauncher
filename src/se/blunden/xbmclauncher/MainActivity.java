package se.blunden.xbmclauncher;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private String xbmcActivity = "org.xbmc.xbmc/org.xbmc.xbmc.Splash"; 
	
	private void launch()
	{
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
			Toast.makeText(this, "Activity " + xbmcActivity + " not found. Resetting default launcher preference...", Toast.LENGTH_SHORT).show();
			
			// Reset the default launcher preference to avoid getting stuck with a non-working launcher
			getPackageManager().clearPackagePreferredActivities(getPackageName());
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		launch();
		finish();
	}
}
