package se.blunden.xbmclauncher;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class LauncherSettingsActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
	
	@SuppressWarnings("deprecation")
	public static class SettingsFragment extends PreferenceFragment {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Load the preferences from XML
	        addPreferencesFromResource(R.xml.preferences);
	    }   
	}
}
