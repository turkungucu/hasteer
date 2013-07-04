/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

import com.dao.AdminString;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

/**
 *
 * @author ecolak
 */
public class ConfigUtil {
   // private static final String INSTANCE_PROPERTIES = "properties.instance";
   private static final String INSTANCE_PROPERTIES = 
           AdminString.getValueByName("properties_file", "properties.instance");

    private static ResourceBundle bundle = null;

    public static void runStaticInitializers(){
        try{
            bundle = ResourceBundle.getBundle(INSTANCE_PROPERTIES);
        } catch(Exception e){
            e.printStackTrace();
            // log
        }
    }

    public static String getString(String key, String defaultValue){
        String result = defaultValue;
        if(bundle != null) {
            try {
                result = bundle.getString(key);
            } catch (MissingResourceException e) {}

            if(result == null)
                result = defaultValue;
        }
        return result;
    }

    public static long getLong(String key, long defaultValue){
        long result = defaultValue;
        if(bundle != null) {
            String s = null;

            try {
                s = bundle.getString(key);
            } catch (MissingResourceException e) {}

            if(s != null) {
                try {
                    result = Long.parseLong(s);
                } catch(Exception e){
                    result = defaultValue;
                }
            }
        }
        return result;
    }

    public static int getInt(String key, int defaultValue){
        int result = defaultValue;
        if(bundle != null) {
            String s = null;

            try {
                s = bundle.getString(key);
            } catch (MissingResourceException e) {}

            if(s != null) {
                try {
                    result = Integer.parseInt(s);
                } catch(Exception e){
                    result = defaultValue;
                }
            }
        }
        return result;
    }

    public static boolean getBoolean(String key, boolean defaultValue){
        boolean result = defaultValue;
        if(bundle != null) {
            String s = null;

            try {
                s = bundle.getString(key);
            } catch (MissingResourceException e) {}

            if(s != null) {
                try {
                    result = Boolean.parseBoolean(s);
                } catch(Exception e){
                    result = defaultValue;
                }
            }
        }
        return result;
    }

}
