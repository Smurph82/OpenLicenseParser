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

public class LoadFromXmlStringActivity extends BaseActivity {

	@Override
	void loadFragment() {
		Fragment frag = new LicenseFragment();
		Bundle args = 	new Bundle(2);
		
		args.putInt(LicenseFragment.FILES_LOCATION_TYPE, 	FileLocationType.TYPE_STRING_XML);
		args.putString(LicenseFragment.FILES_PATHS, 		mXml);
		
		frag.setArguments(args);

		if (frag != null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, frag, LicenseFragment.FRAG_TAG)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
					.commit();
		}
	}

	final String mXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root><license><title name=\"LicenseTestOne\" /><location url=\"https://google.com\" /><text>Copyright 2013 [Creators Name] Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at  http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.</text>		</license><license><title name=\"LicenseTestTwo\" /><location url=\"https://google.com\" /><text>Copyright 2013 [Creator Name] Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at  http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.</text></license><license><title name=\"LicenseTestThree\" /><location url=\"https://google.com\" /><text>Copyright 2013 [Creator Name] Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at  http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.</text></license><license><title name=\"OpenLicenseParserLib\" /><location url=\"https://github.com/Smurph82/OpenLicenseParser\" /><text>Copyright 2013 Ben Murphy Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at  http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.</text></license></root>"; 
}