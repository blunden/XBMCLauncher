package se.blunden.xbmclauncher;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private String activity = "org.xbmc.xbmc/org.xbmc.xbmc.Splash"; 
	
	private void launch()
	{
		Intent activityIntent = Intent.makeMainActivity(ComponentName.unflattenFromString(activity));
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
