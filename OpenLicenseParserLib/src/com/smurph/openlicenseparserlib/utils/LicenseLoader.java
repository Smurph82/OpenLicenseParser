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

package com.smurph.openlicenseparserlib.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

public class LicenseLoader extends AsyncTaskLoader<List<LicenseInfo>> {

	private List<LicenseInfo> mEntries;
	private List<File> mFiles;
	private String mXml;
	private final boolean mFromAssets;
	
	private final String mPathSeparator = System.getProperty("file.separator");
	
	public LicenseLoader(Context context) {
		super(context);
		mFromAssets = true;		
	}
	
	/**
	 * 
	 * @param context
	 * @param filePaths
	 */
	public LicenseLoader(Context context, String[] filePaths) {
		super(context);
		mFromAssets = false;
		List<File> files = new ArrayList<File>(filePaths.length);
		for (String path : filePaths)
			if (!files.contains(path))
				files.add(new File(path));
		mFiles = files;
	}
	
	/**
	 * 
	 * @param context
	 * @param files
	 */
	public LicenseLoader(Context context, List<File> files) {
		super(context);
		mFromAssets = false;
		mFiles = files;
	}
	
	/**
	 * 
	 * @param context
	 * @param xml
	 */
	public LicenseLoader(Context context, String xml) {
		super(context);
		mFromAssets = false;
		mXml = xml;
	}

	@Override
	public List<LicenseInfo> loadInBackground() {
		LicenseParser parser=null; 
		try {
			if (mEntries==null)
				mEntries = new ArrayList<LicenseInfo>();
			
			parser = new LicenseParser();
			
			if (mFromAssets) {
				AssetManager am = getContext().getAssets();
				String path = "licenses";
				String[] files = am.list(path);
				
				for (String file : files) {
					mEntries.addAll(parser.parseLicenseFile(am.open(path + mPathSeparator + file)));
				}
			}
			
			// If we have xml files
			if (mFiles!=null && !mFromAssets) {
				for (File file : mFiles) {
					if (!file.exists())
						continue;
					
					mEntries.addAll(parser.parseLicenseFile(file));
				}
			}
			
			// If a xml string was provided
			if (!TextUtils.isEmpty(mXml) && !mFromAssets) {
				mEntries.addAll(parser.parseLicenseFile(mXml));
			}
			return mEntries;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			parser=null;
		}
		return null;
	}
	
	@Override
	public void deliverResult(List<LicenseInfo> data) {
		if (isReset()) {
			if (mEntries != null) {
				mEntries.clear();
			}
		}
		
		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mEntries != null)
			deliverResult(mEntries);
		
		if (takeContentChanged() || mEntries == null)
			forceLoad();
	}
	
	@Override
    protected void onStopLoading() {
        cancelLoad();
    }

	@Override
	public void onCanceled(List<LicenseInfo> data) {
		super.onCanceled(data);
		if (mEntries != null) {
			mEntries.clear();
		}
	}

	@Override
	protected void onReset() {
		super.onReset();
		
		onStopLoading();
		
		if (mEntries!=null)
			mEntries.clear();
		mEntries = null;
		
		if (mFiles!=null)
			mFiles.clear();
		mFiles=null;
	}
	
	/** @return The number of items in collection. If collection is null then 0. */
	public int getCount() {
		return mEntries != null ? mEntries.size() : 0;
	}
}
