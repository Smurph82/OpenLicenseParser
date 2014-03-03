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
