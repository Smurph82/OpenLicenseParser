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
		
		setListAdapter(new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, 
				android.R.id.text1, 
				new String[] { "Load from assets", "Load from files", "Load from xml string" }));
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
