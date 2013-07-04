/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.struts.action;

import com.constants.HasteerConstants;
import com.dao.Product;
import com.dao.ProductImage;
import com.dao.User;
import com.search.LuceneUtil;
import com.struts.form.ProductSetupForm;
import com.util.ConfigUtil;
import com.util.IOUtil;
import com.api.Jsp;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author ecolak
 */
public class ProductSetupAction extends Action {

    private Log log = LogFactory.getLog(ProductSetupAction.class);
    private static String LUCENE_INDEX_DIR = ConfigUtil.getString("Hasteer.LuceneIndex.directory",
                                                                    HasteerConstants.DEFAULT_LUCENE_INDEX_DIR);
    private static String[] ALLOWED_MIME_TYPES = new String[]{"image/gif","image/jpeg","image/pjpeg","image/png","image/x-png"};
    private static String ALLOWED_MIME_TYPES_STR;
    static {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i<ALLOWED_MIME_TYPES.length; i++) {
            String mt = ALLOWED_MIME_TYPES[i];
            sb.append(mt);
            if(i < ALLOWED_MIME_TYPES.length-1)
                sb.append(", ");
        }
        ALLOWED_MIME_TYPES_STR = sb.toString();
    }

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        ProductSetupForm jForm = (ProductSetupForm) form;
        ActionErrors errors = new ActionErrors();
        
        User user = User.getUserFromSession(request);
        if (user == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("userDoesNotExist"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        Product product = Product.getById(jForm.getProductId());
        ActionForward forward = mapping.findForward("success");
        String cmd = jForm.getCmd();

        if ("create".equals(cmd)) {
            Product p = new Product();
            p.setCreatedBy(user.getUsername());
            p.setCreateDate(new Date());
            forward = save(jForm, mapping, request, user, p);
        } else if ("update".equals(cmd)) {
            forward = save(jForm, mapping, request, user, product);
        } else if("deleteImage".equals(cmd)) {
            forward = deleteImage(jForm, mapping, request, product);
        } else if ("back".equals(cmd)) {
            return mapping.findForward("back");
        }

        if (product != null) {
            jForm.setProductName(product.getProductName());
            jForm.setDetails(product.getDetails());
            jForm.setCategoryId(product.getCategoryId());
            jForm.setImages(product.getImages());
            jForm.setWeight(String.valueOf(product.getWeight()));
            jForm.setLength(String.valueOf(product.getLength()));
            jForm.setHeight(String.valueOf(product.getHeight()));
            jForm.setWidth(String.valueOf(product.getWidth()));
        }

        return forward;
    }

    public ActionForward save(ProductSetupForm jForm, ActionMapping mapping, HttpServletRequest request, User user, Product p){
        if(p == null)
            mapping.findForward("failure");

        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        
        ActionForward forward = null;
        p.setProductName(jForm.getProductName());
        p.setDetails(jForm.getDetails());
        p.setCategoryId(jForm.getCategoryId());
        p.setSellerId(user.getUserId());
        p.setWeight(Double.parseDouble(jForm.getWeight()));
        p.setLength(Double.parseDouble(jForm.getLength()));
        p.setHeight(Double.parseDouble(jForm.getHeight()));
        p.setWidth(Double.parseDouble(jForm.getWidth()));
        p.setModifiedBy(user.getUsername());
        p.setModifiedDate(new Date());
        p.save();

        boolean success = true;
        try {
            FormFile image1 = jForm.getImage1();
            if(image1 != null && image1.getFileSize() > 0) {
                success &= uploadFile(image1, p.getProductId());
                saveProductImage(image1, p.getProductId(), "1".equals(jForm.getPrimaryImageNumber()));
            }

            FormFile image2 = jForm.getImage2();
            if(image2 != null && image2.getFileSize() > 0) {
                success &= uploadFile(image2, p.getProductId());
                saveProductImage(image2, p.getProductId(), "2".equals(jForm.getPrimaryImageNumber()));
            }

            FormFile image3 = jForm.getImage3();
            if(image3 != null && image3.getFileSize() > 0) {
                success &= uploadFile(image3, p.getProductId());
                saveProductImage(image3, p.getProductId(), "3".equals(jForm.getPrimaryImageNumber()));
            }

            FormFile image4 = jForm.getImage4();
            if(image4 != null && image4.getFileSize() > 0) {
                success &= uploadFile(image4, p.getProductId());
                saveProductImage(image4, p.getProductId(), "4".equals(jForm.getPrimaryImageNumber()));
            }

            FormFile image5 = jForm.getImage5();
            if(image5 != null && image5.getFileSize() > 0) {
                success &= uploadFile(image5, p.getProductId());
                saveProductImage(image5, p.getProductId(), "5".equals(jForm.getPrimaryImageNumber()));
            }

            // update lucene index
            //updateLuceneIndex(p);
        }
        catch(IllegalArgumentException ie) {
            ie.printStackTrace();
            log.error(ie, ie);
            errors.add(Globals.ERROR_KEY, new ActionMessage("shared.errors.generic.error", ie.getMessage()));
            saveErrors(request, errors);
            forward = (mapping.findForward("failure"));
            success = false;
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            log.error(ioe, ioe);
            errors.add(Globals.ERROR_KEY, new ActionMessage("ProductSetupAction.errors.unsuccessfulFileUpload"));
            saveErrors(request, errors);
            forward = (mapping.findForward("failure"));
            success = false;
        }

        if(success) {
            messages.add(Globals.MESSAGE_KEY, "create".equals(jForm.getCmd()) 
                                                ? new ActionMessage("ProductSetupAction.messages.createSuccessful")
                                                : new ActionMessage("ProductSetupAction.messages.updateSuccessful"));
            saveMessages(request, messages);
            forward = (mapping.findForward("success"));
        } else {
            forward = (mapping.findForward("failure"));
        }

        return forward;
    }

    public ActionForward deleteImage(ProductSetupForm jForm, ActionMapping mapping, HttpServletRequest request, Product p) {
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        
        ProductImage image = ProductImage.getById(jForm.getDeletedImageId());
        if(image != null) {
            image.setProductId(0);
            image.save();

            messages.add(Globals.MESSAGE_KEY, new ActionMessage("ProductSetupAction.messages.deleteSuccessful"));
            saveMessages(request, messages);
            return mapping.findForward("success");
        } else {
            errors.add(Globals.ERROR_KEY, new ActionMessage("ProductSetupAction.errors.unsuccessfulFileDelete"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }
    }

    private boolean uploadFile(FormFile file, long productId) throws IOException {
        String uploadPath = Jsp.getProductImagesUploadDirectory() + "/" + String.valueOf(productId);
        return IOUtil.uploadFile(file, uploadPath, true, ALLOWED_MIME_TYPES, true, 
                (Jsp.getResizedProductImagesUploadDirectory() + "/" + String.valueOf(productId)),
                HasteerConstants.MAX_WIDTH_FOR_HOT_DEALS, HasteerConstants.MAX_HEIGHT_FOR_HOT_DEALS, true, true,
                (Jsp.getProductThumbnailsUploadDirectory() + "/" + String.valueOf(productId)),
                HasteerConstants.MAX_WIDTH_FOR_THUMBNAIL, HasteerConstants.MAX_HEIGHT_FOR_THUMBNAIL, false);
    }

    private void saveProductImage(FormFile file, long productId, boolean isPrimary){
        ImageIcon icon = new ImageIcon(Jsp.getProductImagesUploadDirectory() + "/" + String.valueOf(productId) + "/" + file.getFileName());

        ProductImage im = new ProductImage();
        im.setProductId(productId);
        im.setFilename(file.getFileName());
        im.setHeight(icon != null ? icon.getIconHeight() : 0);
        im.setWidth(icon != null ? icon.getIconWidth() : 0);
        im.setIsPrimary(isPrimary);
        im.save();
    }

    private void updateLuceneIndex(Product product) throws IOException {
        Directory directory = null;
        IndexWriter writer = null;
        Analyzer analyzer =  new StandardAnalyzer(Version.LUCENE_CURRENT);
        try {
            directory = FSDirectory.open(new File(LUCENE_INDEX_DIR));
            //directory = new RAMDirectory();

            // Make an writer to create the index
            writer = new IndexWriter(directory,analyzer,true,new IndexWriter.MaxFieldLength(
                                    HasteerConstants.LUCENE_INDEX_MAX_FIELD_LENGTH));

            writer.addDocument(LuceneUtil.createProductDocument(String.valueOf(product.getProductId()),
                        product.getProductName(), product.getDetails()));

            // Optimize and close the writer to finish building the index
            writer.optimize();
        } finally {
            if(writer != null)
                writer.close();
            if(directory != null)
                directory.close();
        }
    }
}
