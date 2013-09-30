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

package com.smurph.openlicenseparserlib.frags;

import java.util.List;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.smurph.openlicenseparserlib.R;
import com.smurph.openlicenseparserlib.utils.LicenseAdapter;
import com.smurph.openlicenseparserlib.utils.LicenseInfo;
import com.smurph.openlicenseparserlib.utils.LicenseInterface;
import com.smurph.openlicenseparserlib.utils.LicenseLoader;

public class LicenseFragment extends ListFragment implements
		LoaderCallbacks<List<LicenseInfo>>, LicenseInterface {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setListShown(false);
		setEmptyText(getString(R.string.no_licenses_found));
		
		ListView list = getListView();
		// Set selector to transparent so the user does not see the clicking of a list item
		list.setSelector(R.drawable.list_selector_transparent);
		// Create adapter
		mAdapter = new LicenseAdapter(getActivity(), this);
		// Set list adapter
		list.setAdapter(mAdapter);
		// Show no divider
		list.setDividerHeight(0);
		// List is not clickable
		list.setClickable(false);
		// Set on scroll listener so we no when the list is scrolled
		list.setOnScrollListener(new OnScrollListener() {			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_FLING:
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					if (mAdapter!=null)
						// If user had clicked on the link textView 
						// and then scrolled while still holding down
						// the textView this show it being released.
						mAdapter.onListScrolled(true);
					break;

				default:
					break;
				}
			}			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) { // Nothing
			}
		});
		// Load the list with the loader
		Bundle args = getArguments();
		int type = 0;
		if (args!=null) {
			type = args.getInt(FILES_LOCATION_TYPE, FileLocationType.TYPE_ASSETS);
		} else {
			type = FileLocationType.TYPE_ASSETS;
		}
		getLoaderManager().restartLoader(type, args, this);
	}

	@Override
	public Loader<List<LicenseInfo>> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case FileLocationType.TYPE_ASSETS:
			return new LicenseLoader(getActivity());
		case FileLocationType.TYPE_STRING_FILE_PATHS:
			return new LicenseLoader(getActivity(), args.getStringArray(FILES_PATHS));
		case FileLocationType.TYPE_STRING_XML:
			return new LicenseLoader(getActivity(), args.getString(FILES_PATHS));

		default:
			stopListAnimation();
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<LicenseInfo>> loader, List<LicenseInfo> data) {
		mAdapter.addAll(data);
		stopListAnimation();
	}

	@Override
	public void onLoaderReset(Loader<List<LicenseInfo>> loader) {
		mAdapter.clear();
	}

	@Override
	public void onLinkClicked(Uri uri) {
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
	
	/**
	 * Called when the loading is complete 
	 */
	private void stopListAnimation() {
		if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
	}
	
	/** The way you want the ListFragment to load your license files */
	public interface FileLocationType {
		/** Load from assets folder */
		final int TYPE_ASSETS = 			1;
		/** {@code String[]} of the files full path to load */
		final int TYPE_STRING_FILE_PATHS = 	1<<1;
		/** {@code List<File>} of all the license files to load */
		final int TYPE_LIST_FILES =			1<<2;
		/** {@code String} of an xml file that can be loaded */
		final int TYPE_STRING_XML =			1<<3;
	}

	/** The adapter for this {@link ListFragment} */
	private LicenseAdapter mAdapter;
	
	/** Tag to add to the bundle sent to this fragment when loading from a string xml */
	public static final String XML_STRING = "xml_string";
	
	/** Tag to add to the bundle sent to this fragment when loading from a {@code String[]} */
	public static final String FILES_PATHS = "file_paths";
	
	/** An integer from the {@link FileLocationType} interface*/
	public static final String FILES_LOCATION_TYPE = "file_location_type";
	
	/** Tag of this Fragment for locating in the {@link FragmentManager} */
	public static final String FRAG_TAG = "fragment_license";
}
