package se.blunden.xbmclauncher;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private String activity = "org.xbmc.xbmc/org.xbmc.xbmc.Splash"; 
	
	private void launch()
	{
		Intent activityIntent;
		
		// Intent.makeMainActivity is only available in API level 11 and above
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			activityIntent = Intent.makeMainActivity(ComponentName.unflattenFromString(activity));
		} else {
			activityIntent = new Intent(Intent.ACTION_MAIN);
            activityIntent.setComponent(ComponentName.unflattenFromString(activity));
            activityIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		}
		
		try {
			startActivity(activityIntent);
			return;
		}
		catch (ActivityNotFoundException e)
		{
			Toast.makeText(this, "Activity " + activity + " not found", Toast.LENGTH_SHORT).show();
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		launch();
		finish();
	}
}
