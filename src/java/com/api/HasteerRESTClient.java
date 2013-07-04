
package com.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import net.sf.json.JSONObject;


/**
 *
 * @author Alinur Goksel
 */
public class HasteerRESTClient {
    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String POST = "POST";

    public HasteerRESTClient() {
    }

    public JSONObject postForJSON(String url) {
        return JSONObject.fromObject(makeRestCall(url, POST));
    }

    public JSONObject getJSON(String url) {
        return JSONObject.fromObject(makeRestCall(url, GET));
    }
    
    private String makeRestCall(String url, String method) {
        try {
            HostnameVerifier hv = new HostnameVerifier() {
                 public boolean verify(String urlHostName, SSLSession session) {
                    return true;
                 }
            };

            URL u = new URL(url);
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            HttpsURLConnection urlConn = (HttpsURLConnection) u.openConnection();
            urlConn.setRequestMethod(method);
            InputStream ist = urlConn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(ist));
            String line = br.readLine();
            br.close();
            return line;
        } catch (MalformedURLException ex) {
            Logger.getLogger(HasteerRESTClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HasteerRESTClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
