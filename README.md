OpenLicenseParser
=================

OpenLicenseParser is an Android library project that allows you to easily add references of licenses from other project's to your project. This porject works by reading a simple xml file that either you or hopefully the other developer has provided.

###Xml File
The xml file can contain one to many references in one file. You could also have one xml file per license referenced. You can even mix and match meaning you have multiple xml file with multiple license in each of them. 

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


![Main Smaple app](/ScreenShots/license_list_default_theme.png "Sample app screen shot.")
