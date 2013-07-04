/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.constants;

import java.util.regex.Pattern;

/**
 *
 * @author ecolak
 */
public interface ValidationConstants {
    Pattern phonePattern1 = Pattern.compile("^[0-9]{3}$");
    Pattern phonePattern2 = Pattern.compile("^[0-9]{4}$");
}
