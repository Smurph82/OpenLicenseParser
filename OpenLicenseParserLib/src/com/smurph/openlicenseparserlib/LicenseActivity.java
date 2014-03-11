/*
 * Copyright (c) 2014 Ben Murphy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smurph.openlicenseparserlib.frags;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.smurph.openlicenseparserlib.R;

public class LicenseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_license);

		Bundle args = getIntent().getExtras();

		FragmentManager fm = getFragmentManager();

		Fragment frag = fm.findFragmentByTag(LicenseFragment.FRAG_TAG);

		if (frag == null) {
			frag = new LicenseFragment();
			frag.setArguments(args);
		} else {
			if (frag.getArguments() == null)
				frag.setArguments(args);
			else {
				if (args != null) {
					Bundle arg = frag.getArguments();
					arg.clear();
					arg.putAll(args);
				}
			}
		}

		fm.beginTransaction()
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.replace(R.id.container, frag, LicenseFragment.FRAG_TAG)
				.commit();
	}
}
