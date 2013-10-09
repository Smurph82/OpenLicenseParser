OpenLicenseParser
=================

OpenLicenseParser is an Android library project that allows you to easily add references of licenses from other project's to your project. This porject works by reading a simple xml file that either you or hopefully the other developer has provided. 

I encourage developers to create their own license xml file and add it to their project and VC system. That way anyone who uses this library can just copy the license xml into their projects assets folder and be done with it.

###Xml File
The xml file can contain one to many references in one file. You could also have one xml file per license referenced. You can even mix and match meaning you can have multiple xml files with multiple licenses in each of them. All license files must currently be placed in the assets folder of your project in a new folder called "licenses". /assets/licenses/"license xml file name"

#####An example of a single license reference xml file.
```
<?xml version="1.0" encoding="utf-8"?>
<root>
	<license>
		<title name="OpenLicenseParserLib" />
		<location url="https://github.com/Smurph82/OpenLicenseParser" />
		<text>Copyright 2013 Ben Murphy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.</text>
	</license>
</root>
```

#####An example of a multiple license reference xml file.
```
<?xml version="1.0" encoding="utf-8"?>
<root>
	<license>
		<title name="OpenLicenseParserLib" />
		<location url="https://github.com/Smurph82/OpenLicenseParser" />
		<text>Copyright 2013 Ben Murphy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.</text>
	</license>
	<license>
		<title name="LicenseTestOne" />
		<location url="https://google.com" />
		<text>Copyright 2013 [Creators Name]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.</text>		
	</license>
</root>
```

###The library project
It is very easy to use the library project, as it uses a standard ListFragment. 
######Note: 
> This project was built for Android 4+, so the android.support.v4.app.ListFragment was not used. If you wish to use this library in a project that uses the android.support.v4.app.FragmentActivity then you will need to clone this project and update it yourself.

You can use the LicenseFragment class like any other fragment by adding it to your Activity in code or in the xml.
#####Example of fragment in code:
```
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
```

#####Example of fragment in xml
```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <fragment
        android:id="@+id/listFragment"
        android:name="com.smurph.openlicenseparserlib.frags.LicenseFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</FrameLayout>
```

######Note:
>Currently if you reference the fragment in the layout xml file you must place the license xml files in the assets folder as listed [above](https://github.com/Smurph82/OpenLicenseParser#xml-file).

The following image shows what the LicenseFragment will look like by default.

![Default LicenseFragment](/ScreenShots/license_list_default_theme.png "Default themed LicenseFragment")

And yes if you want the LicenseFragment to fit to your apps color style, you can theme the LicenseFragment as well using the styles.xml file in your apps projects res/values* folder.

![Themed LicenseFragment](/ScreenShots/license_list_orange_theme.png "Orange themed LicenseFragment")
