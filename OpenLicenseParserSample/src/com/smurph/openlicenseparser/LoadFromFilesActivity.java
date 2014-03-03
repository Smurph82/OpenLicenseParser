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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.smurph.openlicenseparserlib.frags.LicenseFragment;
import com.smurph.openlicenseparserlib.utils.Constants;
import com.smurph.openlicenseparserlib.utils.FileLocationType;

public class LoadFromFilesActivity extends BaseActivity {

	@Override
	void loadFragment() {
		new CreateFile().execute();
	}

	/**
	 * Builds an directory in internal storage to save the license files to
	 */
	private void loadFrag(String[] files) {
		Fragment frag = 		new LicenseFragment();
		Bundle args = 			new Bundle(2);
		
		args.putStringArray(Constants.FILES_PATHS, files);
		args.putInt(Constants.FILES_LOCATION_TYPE, FileLocationType.TYPE_STRING_FILE_PATHS);
		
		frag.setArguments(args);

		if (frag != null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, frag, LicenseFragment.FRAG_TAG)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
					.commit();
		}
	}
	
	/** For creating the files. */
	private class CreateFile extends AsyncTask<Void, Integer, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			String path = 		"licenses";
			File dir = 			getFilesDir();
			String dirPath = 	dir.getAbsolutePath() + File.separator + path;
			dir = null;
			dir = new File(dirPath);
			if (!dir.exists())
				dir.mkdir();
			
			AssetManager am = 	null;
			InputStream in =	null;
			OutputStream out = 	null;
			
			try {
				am = 				getAssets();
				String[] files = 	am.list(path);
				byte[] buffer = 	new byte[4096];
				String[] result = 	new String[files.length];
				
				int i=0;
				for (String file : files) {
					in = am.open(path + File.separator + file);					
					out = new FileOutputStream(dirPath + File.separator + file);
					
					for (int n; (n = in.read(buffer)) != -1;)
						out.write(buffer, 0, n);
					
					in.close();
					out.flush();
					out.close();
					result[i++] = dirPath + File.separator + file;
				}
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Clean up
				if (am!=null)
					am=null;
				
				try {
					if (in!=null) {
						in.close();
						in=null;
					}
					if (out!=null) {
						out.flush();
						out.close();
						out=null;
					}
				} catch (Exception e) { /*Nothing I don't care*/ };
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] files) {
			super.onPostExecute(files);
			loadFrag(files);
		}
		
	}
}
