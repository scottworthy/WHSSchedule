package com.example.whsschedule;

import android.os.Bundle;
import android.preference.PreferenceFragment;


public class MainPreferences extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_preferences);
	}
	
}
