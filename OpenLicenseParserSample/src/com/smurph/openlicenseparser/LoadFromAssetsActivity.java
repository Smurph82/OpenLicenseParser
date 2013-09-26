/*
 * Copyright (c) 2013 Ben Murphy
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

package com.smurph.openlicenseparser;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.smurph.openlicenseparserlib.frags.LicenseFragment;
import com.smurph.openlicenseparserlib.frags.LicenseFragment.FileLocationType;

public class LoadFromAssetsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	void loadFragment() {

		Fragment frag = new LicenseFragment();
		Bundle args = 	new Bundle(1);
		
		args.putInt(LicenseFragment.FILES_LOCATION_TYPE, FileLocationType.TYPE_ASSETS);
		
		frag.setArguments(args);

		if (frag != null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, frag, LicenseFragment.FRAG_TAG)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
					.commit();
		}
	}

}
