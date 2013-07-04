/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api;

import com.constants.HasteerConstants;
import com.dao.ProductImage;
import com.util.ConfigUtil;
import com.util.AuthUtil;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ecolak
 */
public class Jsp {

    public static String getUrlBase() {
        return ConfigUtil.getString("Hasteer.urlBase", HasteerConstants.DEFAULT_HASTEER_URLBASE);
    }

    /**
     * @return Root folder for Hasteer images
     */
    public static String getImagesDirectory() {
        return ConfigUtil.getString("Hasteer.images.folder", HasteerConstants.DEFAULT_IMAGES_FOLDER);
    }

    public static String getProductImagesDirectory() {
        return ConfigUtil.getString("Hasteer.productImages.directory", HasteerConstants.DEFAULT_PRODUCT_IMAGES_FOLDER);
    }

    public static String getProductThumbnailsDirectory() {
        return ConfigUtil.getString("Hasteer.productThumbnails.directory", HasteerConstants.DEFAULT_PRODUCT_THUMBNAILS_FOLDER);
    }

    public static String getResizedProductImagesDirectory() {
        return ConfigUtil.getString("Hasteer.resizedProductImages.directory", HasteerConstants.DEFAULT_RESIZED_PRODUCT_IMGS_FOLDER);
    }

    public static String getProductImagesUploadDirectory() {
        return ConfigUtil.getString("Hasteer.productImages.upload.directory", HasteerConstants.DEFAULT_PRODUCT_IMAGES_UPLOAD_FOLDER);
    }

    public static String getProductThumbnailsUploadDirectory() {
        return ConfigUtil.getString("Hasteer.productThumbnails.upload.directory", HasteerConstants.DEFAULT_PRODUCT_THUMBNAILS_UPLOAD_FOLDER);
    }

    public static String getResizedProductImagesUploadDirectory() {
        return ConfigUtil.getString("Hasteer.resizedProductImages.upload.directory", HasteerConstants.DEFAULT_RESIZED_PRODUCT_IMGS_UPLOAD_FOLDER);
    }

    public static String getThumbnailUrl(long imageId) {
        String result = null;
        ProductImage image = ProductImage.getById(imageId);

        if(image != null) {
            result = getUrlBase() + getProductThumbnailsDirectory() + "/" + image.getProductId() + "/" + image.getFilename();
        }

        return result;
    }

    public static String getResizedImageUrl(long imageId) {
        String result = null;
        ProductImage image = ProductImage.getById(imageId);

        if(image != null) {
            result = getUrlBase() + getResizedProductImagesDirectory() + "/" + image.getProductId() + "/" + image.getFilename();
        }

        return result;
    }

    public static String getImageUrl(long imageId) {
        String result = null;
        ProductImage image = ProductImage.getById(imageId);

        if(image != null) {
            result = getUrlBase() + getProductImagesDirectory() + "/" + image.getProductId() + "/" + image.getFilename();
        }

        return result;
    }

    public static String getUnsubscribeUrl(String email, String name) {
        return getUrlBase() + "/Hasteer/auth/unsubscribe.jsp?e=" + email + "&n=" + AuthUtil.encodeString(name);
    }

    public static String getServiceContractUrl(String type) {
        return "/Hasteer/common/legal/ServiceContract.do?view=" + type;
    }

    public static String getFavIconUrl() {
        return getUrlBase() + "/Hasteer/images/favicon.ico";
    }

    /**
     * @return a string like "Hasteer.com.<directory>.<class-name>.<suffix>"
     */
    public static String getProperty(Class c, String suffix) {
        return getPropertyPrefix(c) + suffix;
    }

    /**
     * @return a string like "Hasteer.web.<directory>.<jspName>.<suffix>"
     */
    public static String getProperty(PageContext pageContext, String suffix) {
        return getPropertyPrefix(pageContext) + suffix;
    }

    private static String getPropertyPrefix(Class c) {
            return "Hasteer." + c.getName() + org.apache.commons.lang.ClassUtils.PACKAGE_SEPARATOR;
    }

    private static String getPropertyPrefix(PageContext pageContext) {
        String currentPage = null;
        currentPage = pageContext.getPage().toString();
        currentPage = StringUtils.replaceChars(currentPage, '/', '.');
        currentPage = StringUtils.substringBeforeLast(currentPage, "@");
        if ( currentPage == null || "".equals(currentPage)) {
                currentPage = "unknown";
        }
        currentPage = "Hasteer.web" + ( currentPage.startsWith(".") ? "" : "." ) + currentPage;
        
        String fullName = StringUtils.removeEnd(StringUtils.remove(currentPage, ".org.apache.jsp"), "_jsp");
        
        return fullName + org.apache.commons.lang.ClassUtils.PACKAGE_SEPARATOR;
    }
    
}
