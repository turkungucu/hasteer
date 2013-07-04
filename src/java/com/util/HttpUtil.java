package com.util;

import java.net.URL;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {

    public HttpUtil() {
    }

    /**
     * Post to a HTTP URL with the request string.
     *
     * @param url is the url to post to
     * @param sData is the request string to post to the url
     * @return - HTTP server response in xml format
     * @throws IOException
     */
    public static String sendHttpPost(String url, String sData) throws IOException {
        return sendHttpPost(url, sData, 0, false);
    }

    /**
     * Post to a HTTP URL with the request string.
     *
     * @param url is the url to post to
     * @param sData is the request string to post to the url
     * @param timeout - connection timeout
     * @return - HTTP server response in xml format
     * @throws IOException
     */
    public static String sendHttpPost(String url, String sData, int timeout) throws IOException {
        return sendHttpPost(url, sData, timeout, false);
    }

    /**
     * Post to a HTTP URL with the request string.
     *
     * @param url is the url to post to
     * @param sData is the request string to post to the url
     * @param verbose - if true, will print out message to System.out
     * @return - HTTP server response in xml format
     * @throws IOException
     */
    public static String sendHttpPost(String url, String sData, boolean verbose) throws IOException {
        return sendHttpPost(url, sData, 0, verbose);
    }

    /**
     * Post to a HTTP URL with the request string.
     *
     * @param url is the url to post to
     * @param sData is the request string to post to the url
     * @param timeout - connection timeout
     * @param verbose - if true, will print out message to System.out
     * @return - HTTP server response in xml format
     * @throws IOException
     */
    public static String sendHttpPost(String url, String sData, int timeout, boolean verbose) throws IOException {
        if (verbose) {
            System.out.println("URL: " + url);
            System.out.println("Send to host:\r\n" + sData);
        }

        OutputStream ostream = null;
        InputStream istream = null;
        ByteArrayOutputStream baos = null;
        try {
            byte[] data = sData.getBytes();
            // Send data
            URL theUrl = new URL(url);
            URLConnection conn = theUrl.openConnection();
            //  conn.setReadTimeout(timeout);
            //  conn.setConnectTimeout(timeout);
            conn.setDoOutput(true);
            ostream = conn.getOutputStream();
            ostream.write(data);
            ostream.flush();

            // Get the response
            StringBuilder sb = new StringBuilder();
            byte[] b = new byte[1024];
            istream = conn.getInputStream();
            int byteRead = istream.read(b);
            while (byteRead > 0) {
                byte[] buf = new byte[byteRead];
                baos = new ByteArrayOutputStream();
                baos.write(b, 0, byteRead);
                byte[] result = baos.toByteArray();
                sb.append(new String(result));
                byteRead = istream.read(b);
            }
            String sResult = sb.toString();
            if (verbose) {
                System.out.println("Receive from host:\r\n" + sResult);
            }

            return sResult;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
            }
            try {
                if (istream != null) {
                    istream.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ostream != null) {
                    ostream.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * Given a tag, retrieves its value from the given xml
     * @param xml - xml string
     * @param tag - tag name
     * @return - value of the given tag
     */
    public static String retriveFromXMLTag(String xml, String tag) {
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        int startIndx = xml.indexOf(startTag);
        int endIndx = xml.indexOf(endTag);

        if (startIndx == -1 || endIndx == -1) {
            return "";
        }

        int subStrBeginIndx = startIndx + startTag.length();
        int subStrEndIndx = subStrBeginIndx + (endIndx - startIndx - startTag.length());
        String str = xml.substring(subStrBeginIndx, subStrEndIndx);
        return str;
    }

    /**
     * Given a Map of String keys and values, builds a query string for an HTTP request
     * @param paramMap
     * @return - query string where key-value pairs are separated by &
     * and the values are UTF-8 encoded 
     */
    public static String buildParams(Map<String, String> paramMap) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (String key : paramMap.keySet()) {
            if (i > 0) {
                result.append("&");
            }
            try {
                result.append(key).append("=").append(URLEncoder.encode(paramMap.get(key), "UTF-8"));
            } catch(UnsupportedEncodingException e) {
                // ignore - should never happen since encoding name is hardcoded
            }
            i++;
        }
        return result.toString();
    }
}
