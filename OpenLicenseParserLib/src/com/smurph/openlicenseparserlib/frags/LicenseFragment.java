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

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
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
		
		ListView list = getListView();
		list.setSelector(R.drawable.list_selector_transparent);
		mAdapter = new LicenseAdapter(getActivity(), -1, this);
		list.setAdapter(mAdapter);
		list.setDividerHeight(0);
		list.setClickable(false);
		list.setBackgroundColor(Color.WHITE);
		list.setOnScrollListener(new OnScrollListener() {			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_FLING:
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					if (mAdapter!=null)
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
		
		getLoaderManager().restartLoader(0, getArguments(), this);
	}

	@Override
	public Loader<List<LicenseInfo>> onCreateLoader(int id, Bundle args) {
		return new LicenseLoader(getActivity(), args.getStringArray(FILES_PATHS));
	}

	@Override
	public void onLoadFinished(Loader<List<LicenseInfo>> loader, List<LicenseInfo> data) {
		mAdapter.addAll(data);
		
		if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
	}

	@Override
	public void onLoaderReset(Loader<List<LicenseInfo>> loader) {
		mAdapter.clear();
	}

	@Override
	public void onLinkClicked(Uri uri) {
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}

	private LicenseAdapter mAdapter;
	
	/**  */
	public static final String FILES_PATHS = 		"file_paths";
	/**  */
	public static final String FRAG_TAG = 			"fragment_license";
}
