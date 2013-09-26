package com.smurph.openlicenseparser;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		if (savedInstanceState == null)
			loadFragment();

	}

	/** This will be run in the implementing class */
	abstract void loadFragment();
}
