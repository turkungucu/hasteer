/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.shipping.usps;

import java.util.List;
import java.util.LinkedList;
import java.io.StringWriter;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;

import com.api.shipping.usps.RateV3Request.Package;
import com.api.shipping.usps.RateV3Response.Package.Postage;
import com.api.Pair;
import com.util.HttpUtil;
import com.dao.Product;

import org.xml.sax.InputSource;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Alinur Goksel
 */
public class USPSWebTools {

    public final static String URL = "http://production.shippingapis.com/ShippingAPI.dll";
    
    /** 
     * <link>http://www.usps.com/webtools/htm/Rate-Calculators-v2-3.htm#_Toc220744000</link>
     * @see RateV3Response / Package / Postage / @CLASSID
     */
    public final static int PRIORITY_MAIL_CLASS_ID = 1;
    public final static int PRIORITY_MAIL_SMALL_FLAT_RATE_BOX_CLASS_ID = 28;
    public final static int PRIORITY_MAIL_MEDIUM_FLAT_RATE_BOX_CLASS_ID = 17;
    public final static int PRIORITY_MAIL_LARGE_FLAT_RATE_BOX_CLASS_ID = 22;
    public final static int[] INCLUDE_CLASS_IDS = {1, 4, 17, 22, 23, 28};

    public static LinkedList<Pair<String, Double>> getShippingMethodsAndCost(String fromZipCode, String toZipCode,
            Product product, int quantity, boolean verbose) {

        LinkedList<Pair<String, Double>> shippingMethodsAndCost = new LinkedList<Pair<String, Double>>();

        try {
            //Create JAXBContext and marshaller for Request object
            JAXBContext rateV3RequestJAXBC = JAXBContext.newInstance(RateV3Request.class.getPackage().getName());
            Marshaller rateV3RequestMarshaller = rateV3RequestJAXBC.createMarshaller();
            ObjectFactory rateV3RequestObjectFactory = new ObjectFactory();
            RateV3Request rateV3Request = rateV3RequestObjectFactory.createRateV3Request();

            Package p = new Package();
            p.setID("1");
            p.setService("ALL");
            p.setZipOrigination(fromZipCode);
            p.setZipDestination(toZipCode);

            // convert weight to pounds and ounces
            double weight = product.getWeight() * quantity;
            int pounds = (int)weight;
            double ounces = (weight % pounds) * 16;
            p.setPounds(BigInteger.valueOf(pounds));
            p.setOunces(BigDecimal.valueOf(ounces));

            double length = product.getLength() * quantity;
            double width = product.getWidth();
            double height = product.getHeight();
            p.setWidth(BigDecimal.valueOf(width));
            p.setLength(BigDecimal.valueOf(length));
            p.setHeight(BigDecimal.valueOf(height));

            p.setGirth(BigDecimal.valueOf(product.getGirth()));
            p.setSize(getSize(product.getLength(), product.getGirth()));
            p.setMachinable(Boolean.TRUE);
            p.setContainer("RECTANGULAR");

            rateV3Request.setUSERID("128HASTE5005");
            rateV3Request.setPackage(p);

            //Get String out of rate request objects.
            StringWriter strWriter = new StringWriter();
            rateV3RequestMarshaller.marshal(rateV3Request, strWriter);
            strWriter.flush();
            strWriter.close();

            //HTTP POST
            String response = HttpUtil.sendHttpPost(URL, "API=RateV3&XML=" + strWriter.getBuffer().toString(), 0, verbose);
            response = response.trim().replaceFirst("^([\\W]+)<","<");
            ByteArrayInputStream in = new ByteArrayInputStream(response.getBytes());
            InputSource is = new InputSource();
            is.setByteStream(in);

            //Create JAXBContext and unmarshaller for Response object
            JAXBContext rateV3ResponseJAXBC = JAXBContext.newInstance(RateV3Response.class.getPackage().getName());
            Unmarshaller rateV3ResponseUnmarshaller = rateV3ResponseJAXBC.createUnmarshaller();

            //Unmarshall response into java object
            RateV3Response rateV3Response = (RateV3Response) rateV3ResponseUnmarshaller.unmarshal(is);

            //Convert response object into a List of applicable services and rates
            List<com.api.shipping.usps.RateV3Response.Package> packages = rateV3Response.getPackage();
            for (com.api.shipping.usps.RateV3Response.Package pa : packages) {
                List<Postage> postages = pa.getPostage();
                int indexOfPriorityMail = -1;
                
                for (Postage postage : postages) {
                    int classId = postage.getCLASSID().intValue();

                    // Filter out inapplicable shipping methods and flat-rate boxes that are too small
                    if (!ArrayUtils.contains(INCLUDE_CLASS_IDS, classId) ||
                            (isFlatRateBox(classId) && !fitsFlatRateBox(length, width, height, classId))) {
                        continue;
                    }

                    //Keep only 1 Priority type (cheapest)
                    if (isTypePriorityMail(classId)) {
                        if (indexOfPriorityMail > -1) {
                            Pair<String, Double> oldPair = shippingMethodsAndCost.get(indexOfPriorityMail);
                            //If cheaper rate is found, and the product fits in the box, replace it.
                            if (oldPair.getSecond().doubleValue() > postage.getRate().doubleValue()) {
                                oldPair.setFirst(getMailServiceShortName(classId));
                                oldPair.setSecond(postage.getRate().doubleValue());
                            }
                            continue;
                        } else {
                            //Mark where flat rate box option is inserted in the Linked List
                            indexOfPriorityMail = shippingMethodsAndCost.size();
                        }
                    }

                    Pair serviceAndRate = new Pair(getMailServiceShortName(classId), postage.getRate().doubleValue());
                    shippingMethodsAndCost.add(serviceAndRate);
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shippingMethodsAndCost;
    }

    /*
     * Defined as follows:
     * REGULAR: package length plus girth is 84 inches or less;
     * LARGE: package length plus girth measure more than 84 inches but not more than 108 inches;
     * OVERSIZE: package length plus girth is more than 108 but not more than 130 inches.
     */
    private static String getSize(double length, double girth) {
       double size = length + girth;
       if (size <= 84) return "REGULAR";
       else if (size <= 108) return "LARGE";
       else return "OVERSIZE";
    }

    private static boolean isSmallFlatRateBox(int classId) {
        return classId == PRIORITY_MAIL_SMALL_FLAT_RATE_BOX_CLASS_ID;
    }

    private static boolean isMediumFlatRateBox(int classId) {
        return classId == PRIORITY_MAIL_MEDIUM_FLAT_RATE_BOX_CLASS_ID;
    }

    private static boolean isLargeFlatRateBox(int classId) {
        return classId == PRIORITY_MAIL_LARGE_FLAT_RATE_BOX_CLASS_ID;
    }

    private static boolean isPriorityMail(int classId) {
        return classId == PRIORITY_MAIL_CLASS_ID;
    }

    private static boolean isTypePriorityMail(int classId) {
        return isPriorityMail(classId) ||
               isSmallFlatRateBox(classId) ||
               isMediumFlatRateBox(classId) ||
               isLargeFlatRateBox(classId);
    }

    private static boolean isFlatRateBox(int classId) {
        return isSmallFlatRateBox(classId) ||
               isMediumFlatRateBox(classId) ||
               isLargeFlatRateBox(classId);
    }

    private static boolean fitsFlatRateBox(double length, double width, double height, int classId) {
        if (isSmallFlatRateBox(classId)) {
            return fitsSmallFlatRateBox(length, width, height);
        } else if (isMediumFlatRateBox(classId)) {
            return fitsMediumFlatRateBox(length, width, height);
        } else if (isLargeFlatRateBox(classId)) {
            return fitsLargeFlatRateBox(length, width, height);
        } else {
            return false;
        }
    }

    private static boolean fitsSmallFlatRateBox(double length, double width, double height) {
        return length <= (8 + (5/8)) && width <= (5 + (3/8)) && height <= (1 + (5/8));
    }

    private static boolean fitsMediumFlatRateBox(double length, double width, double height) {
        return (length <= (13 + (5/8)) && width <= (11 + (7/8)) && height <= (3 + (3/8))) ||
               (length <= (11) && width <= (8 + (1/2)) && height <= (5 + (1/2)));
    }

    private static boolean fitsLargeFlatRateBox(double length, double width, double height) {
        return length <= (12) && width <= (12) && height <= (5 + (1/2));
    }

    private static String getMailServiceShortName(int classId) {
        //1, 4, 17, 22, 23, 28
        switch(classId) {
            case 1:
            case 17:
            case 22:
            case 28:
                return "Priority Mail";
            case 4:
                return "Parcel Post";
            case 23:
                return "Express Mail";
            default:
                return "";
        }
    }
}
