package com.sz.common.util;

import java.util.ResourceBundle;
/**
 * @author sjz
 */
public class PropUtils {
	public static String getConfigInfo(String itemIndex){
		String configFile="config";
		try {
			ResourceBundle resource = ResourceBundle.getBundle(configFile);
			return resource.getString(itemIndex);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
