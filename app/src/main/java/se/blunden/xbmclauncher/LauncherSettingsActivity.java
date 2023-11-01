package se.blunden.xbmclauncher;

import android.app.Activity;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

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

			EditTextPreference customVariantPref = (EditTextPreference) findPreference("xbmc_custom_variant");
			customVariantPref.setSummary(getPreferenceManager().getSharedPreferences().getString("xbmc_custom_variant", ""));
			customVariantPref.setOnPreferenceChangeListener((preference, newValue) -> {
				customVariantPref.setSummary((String) newValue);
				return true;
			});

			customVariantPref.setEnabled(getPreferenceManager().getSharedPreferences().getBoolean("override_xbmc_variant", false));
			SwitchPreference customVariantTogglePref = (SwitchPreference) findPreference("override_xbmc_variant");
			customVariantTogglePref.setOnPreferenceChangeListener((preference, newValue) -> {
				customVariantPref.setEnabled((Boolean) newValue);
				return true;
			});
	    }   
	}
}
