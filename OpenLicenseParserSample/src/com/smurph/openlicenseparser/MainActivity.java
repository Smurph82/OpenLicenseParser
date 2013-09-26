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

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		

		String packageName = getPackageName();
		mClazzIndex = new SparseArray<String>(3);
		mClazzIndex.put(0, packageName + ".LoadFromAssetsActivity");
		mClazzIndex.put(1, packageName + ".LoadFromFilesActivity");
		mClazzIndex.put(2, packageName + ".LoadFromXmlStringActivity");
		mClazzIndex.put(3, packageName + ".ThemedActivity");
		
		setListAdapter(new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, 
				android.R.id.text1, 
				new String[] { "Load from assets", "Load from files", "Load from xml string", "Themed activity" }));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		try {
			Class<?> load = Class.forName(mClazzIndex.get(position));
			
			Intent intent = new Intent(MainActivity.this, load);
			startActivity(intent);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/** {@link SparseArray}{@code <String>} for when you click on and list item.*/
	private SparseArray<String> mClazzIndex;
}
