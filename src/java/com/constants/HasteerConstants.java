/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.constants;

/**
 *
 * @author ecolak
 */
public class HasteerConstants {
    public static final String DEFAULT_HASTEER_URLBASE = "http://www.hasteer.com";
    public static final String DEFAULT_USER_CONFIRMATION_PAGE = "/Hasteer/auth/RegistrationConfirmation.do";
    public static final String DEFAULT_IMAGES_FOLDER = "/Hasteer/images";
    public static final String DEFAULT_PRODUCT_IMAGES_FOLDER = "/images/product";
    public static final String DEFAULT_PRODUCT_THUMBNAILS_FOLDER = "/images/prod_thumb";
    public static final String DEFAULT_RESIZED_PRODUCT_IMGS_FOLDER = "/images/resized_prod_img";

    public static final String DEFAULT_PRODUCT_IMAGES_UPLOAD_FOLDER = "/home/hasteer1/jvm/apache-tomcat-6.0.14/domains/hasteer.com/ROOT/images/product";
    public static final String DEFAULT_PRODUCT_THUMBNAILS_UPLOAD_FOLDER = "/home/hasteer1/jvm/apache-tomcat-6.0.14/domains/hasteer.com/ROOT/images/prod_thumb";
    public static final String DEFAULT_RESIZED_PRODUCT_IMGS_UPLOAD_FOLDER = "/home/hasteer1/jvm/apache-tomcat-6.0.14/domains/hasteer.com/ROOT/images/resized_prod_img";
    public static final String MY_DEALS_PAGE = "/Hasteer/dashboard/buyer/MyDeals.do";
    public static final String LOGIN_PAGE = "/Hasteer/auth/Login.do";
    public static final long DEFAULT_PROCESS_DEALS_TASK_PERIOD = 5*60*1000; // 5 minutes in milliseconds
    public static final long DEFAULT_PROCESS_DEALS_TASK_DELAY = 60*60*1000; // 60 minutes in milliseconds
    public static final long DEFAULT_LUCENE_PRODUCT_INDEXER_TASK_PERIOD = 5*60*1000; // 5 minutes in milliseconds
    public static final long DEFAULT_LUCENE_PRODUCT_INDEXER_TASK_DELAY = 60*60*1000; // 60 minutes in milliseconds
    public static final int MAX_HEIGHT_FOR_THUMBNAIL = 120;
    public static final int MAX_WIDTH_FOR_THUMBNAIL = 140;
    public static final int MAX_WIDTH_FOR_HOT_DEALS = 300;
    public static final int MAX_HEIGHT_FOR_HOT_DEALS = 225;
    public static final int MAX_HEIGHT_FOR_LARGE_IMAGE = 252;
    public static final String DEFAULT_LUCENE_INDEX_DIR = "/lucene_index";
    public static final int PRODUCT_SETUP_IMAGES_PER_ROW = 5;
    public static final int DEFAULT_MAX_UPLOAD_IMG_SIZE = 4*1024*1024; // 4MB
    public static final String DEFAULT_MAIL_STMP_HOST = "mail.hasteer.com";
    public static final String DEFAULT_MAIL_STMP_PORT = "25";
    public static final String DEFAULT_MAIL_PASSWORD = "Hasteer66";
    public static final long DEFAULT_WEEKLY_DEALS_EMAIL_TASK_PERIOD = 7*24*60*60*1000; // 7 day in milliseconds
    public static final double DEFAULT_CA_SALES_TAX = 0.0925;
    public static final String DEFAULT_SUPPORT_PAGE = "mailto:support@hasteer.com";
  
    // SEARCHABLE COLUMNS
    public static final String PRODUCT_NAME = "productName";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_SUMMARY = "summary";

    public static final int PRODUCTS_PER_PAGE = 5;
    public static final int LUCENE_INDEX_MAX_FIELD_LENGTH = 25000;

    public static final String BRAINTREE_MERCHANT_ID_PROP = "hasteer.billing.braintree.merchant.id";
    public static final String BRAINTREE_PUBLIC_KEY_PROP = "hasteer.billing.braintree.public.key";
    public static final String BRAINTREE_PRIVATE_KEY_PROP = "hasteer.billing.braintree.private.key";

    public static final String DEFAULT_BRAINTREE_MERCHANT_ID = "jp8gzb22792vvx96";
    public static final String DEFAULT_BRAINTREE_PUBLIC_KEY = "hxyw4j2kccw9gvbc";
    public static final String DEFAULT_BRAINTREE_PRIVATE_KEY = "jmy63w8jk5sbj5nr";

}
