/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.notification;

import org.apache.velocity.app.VelocityEngine;

import java.util.Properties;

/**
 *
 * @author Alinur Goksel
 */
public class VelocityEngineSingleton {
    private static VelocityEngine instance;

    private static void constructVelocityEngine(){
        instance = new VelocityEngine();
        
        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");

        try {
            instance.init(p);
        } catch (Exception e) {}

    }

    public static synchronized VelocityEngine getInstance() {
        if(instance == null)
            constructVelocityEngine();

        return instance;
    }

}
