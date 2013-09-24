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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.util.Log;

public class LicenseParser {

	final String tag = "LicenseParser";
	
	// Xml Tags
	final String TAG_TITLE = 	"title";
	final String TAG_LOCATION = "location";
	final String TAG_LICENSE = 	"license";
	final String TAG_TEXT = 	"text";
	
	// Xml Attributes
	final String ATTR_NAME = 	"name";
	final String ATTR_URL = 	"url";
	
	/**
	 * 
	 */
	public LicenseParser() {
    }
	
	/**
	 * 
	 * @param xml
	 * @return
	 */
	public List<LicenseInfo> parseLicenseFile(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return parseLicenseFile(doc);
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public List<LicenseInfo> parseLicenseFile(File file) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(file);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return parseLicenseFile(doc);
	}
	
	/**
	 * 
	 * @param license
	 * @return
	 */
	public List<LicenseInfo> parseLicenseFile(Document license) {
		if (license==null) {
			Log.e(tag, "Invalid document. Cannot be null");
			return null;
		}
		
		try {
			Element root = license.getDocumentElement();
			root.normalize();
			
			NodeList children = root.getChildNodes();
			
			int count = children.getLength();
			
			if (count<=0)
				return null;
			
			List<LicenseInfo> list = new ArrayList<LicenseInfo>(count);
			
			int rootIndex=0;
			for (int i=0;i<count;i++) {
				Node child = children.item(i);
				
				if (child.getNodeName().equalsIgnoreCase(TAG_LICENSE)) {
					list.add(new LicenseInfo(getValue(root, TAG_TITLE, ATTR_NAME, rootIndex), 
					getValue(root, TAG_LOCATION, ATTR_URL, rootIndex), 
					getValue(root, TAG_LICENSE, rootIndex)));
					rootIndex++;
				}
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	/**
	 * 
	 * @param root
	 * @param tagName
	 * @param attributeName
	 * @param index
	 * @return
	 */
	private String getValue(Element root, String tagName, String attributeName, int index) {
        NamedNodeMap node = root.getElementsByTagName(tagName).item(index).getAttributes();
        if (node == null) {
            return null;
        }
        return node.getNamedItem(attributeName).getNodeValue().trim().replace("\t", "");
    }
	
	/**
	 * 
	 * @param root
	 * @param tagName
	 * @param index
	 * @return
	 */
	private String getValue(Element root, String tagName, int index) {
        Node node = root.getElementsByTagName(tagName).item(index);
        if (node == null) {
            return null;
        }        
        return ((Element)node).getTextContent().trim().replace("\t", "");
    }
}
