/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.form;

import com.dao.ProductImage;
import com.dao.Product;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author ecolak
 */
public class ProductSetupForm extends ValidatorForm {
    private String cmd;
    private String productName;
    private String details;
    private String weight;
    private String length;
    private String height;
    private String width;
    private FormFile image1;
    private FormFile image2;
    private FormFile image3;
    private FormFile image4;
    private FormFile image5;
    private String primaryImageNumber = "1";
    private String pid;
    private String did;
    private int categoryId;
    private long productId;
    private long deletedImageId;
    private List<ProductImage> images;

    public ProductSetupForm(){}

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public FormFile getImage1() {
        return image1;
    }

    public void setImage1(FormFile image1) {
        this.image1 = image1;
    }

    public FormFile getImage2() {
        return image2;
    }

    public void setImage2(FormFile image2) {
        this.image2 = image2;
    }

    public FormFile getImage3() {
        return image3;
    }

    public void setImage3(FormFile image3) {
        this.image3 = image3;
    }

    public FormFile getImage4() {
        return image4;
    }

    public void setImage4(FormFile image4) {
        this.image4 = image4;
    }

    public FormFile getImage5() {
        return image5;
    }

    public void setImage5(FormFile image5) {
        this.image5 = image5;
    }

    public String getPrimaryImageNumber() {
        return primaryImageNumber;
    }

    public void setPrimaryImageNumber(String primaryImageNumber) {
        this.primaryImageNumber = primaryImageNumber;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public long getDeletedImageId() {
        return deletedImageId;
    }

    public void setDeletedImageId(long deletedImageId) {
        this.deletedImageId = deletedImageId;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
        ActionErrors errors = new ActionErrors();
        if("create".equals(cmd) || "update".equals(cmd))
            errors = super.validate(mapping, request);

        if(pid != null && pid.length() > 0) {
            try {
                setProductId(Long.parseLong(pid));
                setImages(Product.getById(productId).getImages());
            } catch(NumberFormatException ne){
                errors.add(Globals.ERROR_KEY,
                        new ActionMessage("ProductSetupForm.errors.invalidProductId"));
            }
        }

        if(errors.size() == 0) {
            if("create".equals(cmd) || "update".equals(cmd)){
                try {
                    setCategoryId(categoryId);
                } catch(NumberFormatException ne){
                    errors.add(Globals.ERROR_KEY,
                            new ActionMessage("ProductSetupForm.errors.invalidCategoryId"));
                }
            }
        }

        if(errors.size() == 0) {
            if("deleteImage".equals(cmd)){
                try {
                    setDeletedImageId(Long.parseLong(did));
                } catch(NumberFormatException ne){
                    errors.add(Globals.ERROR_KEY,
                            new ActionMessage("ProductSetupForm.errors.invalidImageId"));
                }
            }
        }

        return errors;
    }
}
