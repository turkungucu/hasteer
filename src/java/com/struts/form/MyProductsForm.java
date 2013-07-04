/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.form;

import com.dao.Product;
import java.util.List;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author ecolak
 */
public class MyProductsForm extends ValidatorForm {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
}
