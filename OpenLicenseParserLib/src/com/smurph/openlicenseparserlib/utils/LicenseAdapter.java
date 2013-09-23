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

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smurph.openlicenseparserlib.R;

public class LicenseAdapter extends ArrayAdapter<LicenseInfo> {
	
	public LicenseAdapter(Context context, int resource, LicenseInterface onClickCallBack) {
		super(context, resource);
		mOnClickCallBack = 	onClickCallBack;
		mInflater = 		LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		Viewholder holder=null;
		
		if (view==null) {
			holder = 	new Viewholder();
			view = 		mInflater.inflate(R.layout.list_item_license, parent, false);
			
			holder.txtNotice = 		(TextView) view.findViewById(R.id.textView1);
			holder.txtLink = 		(TextView) view.findViewById(R.id.textView2);
			holder.txtLink.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mOnClickCallBack!=null)
						mOnClickCallBack.onLinkClicked((Uri) v.getTag(R.id.tag_url));
				}
			});
			holder.txtLink.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					mPressedView = v;
					
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						v.animate().setDuration(mDuration).setInterpolator(mDecelerator).scaleX(.8f).scaleY(.8f);
						break;
					case MotionEvent.ACTION_UP:
						v.animate().setDuration(mDuration).setInterpolator(mAccelerator).scaleX(1f).scaleY(1f);
						v.performClick();
						break;

					default:
						break;
					}
					return true;
				}
			});
			holder.txtLicense = (TextView) view.findViewById(R.id.textView3);
			
			view.setTag(R.id.tag_holder, holder);
		} else
			holder = (Viewholder) view.getTag(R.id.tag_holder);
		
		LicenseInfo info = getItem(position);
		
		if (position==0)
			holder.txtNotice.setVisibility(View.VISIBLE);
		else
			holder.txtNotice.setVisibility(View.GONE);
		
		holder.txtLink.setText(info.getTitle());
		holder.txtLink.setTag(R.id.tag_url, 		info.getUrlAsUri());
		holder.txtLink.setTag(R.id.tag_position, 	position);
		holder.txtLicense.setText(info.getLicense());
		
		return view;
	}

	@Override
	public void clear() {
		super.clear();		
		mPressedView = null;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
	
	@Override
	public long getItemId(int position) {
		return (long) position;
	}
	
	/**
	 * When the list is scrolled animate the text view to it's
	 * original position if available
	 * @param isScrolled {@code true} when the list is scrolled
	 */
	public void onListScrolled(boolean isScrolled) {
		if (isScrolled && mPressedView!=null)
			mPressedView.animate().setDuration(mDuration).setInterpolator(mAccelerator).scaleX(1f).scaleY(1f);
	}
	
	private final long mDuration = 100L;

	private class Viewholder {
		TextView txtNotice, txtLink, txtLicense;
	}

	private View mPressedView;

	private LayoutInflater mInflater;
	private LicenseInterface mOnClickCallBack;
	
	private DecelerateInterpolator mDecelerator = 	new  DecelerateInterpolator();
	private AccelerateInterpolator mAccelerator = 	new AccelerateInterpolator();
}
