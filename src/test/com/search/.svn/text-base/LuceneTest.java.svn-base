/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.search;

import com.dao.Product;
import com.search.LuceneUtil;
import com.task.LuceneProductIndexerTask;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author ecolak
 */
public class LuceneTest {

    public LuceneTest() {
    }

    @Test
    public void searchProductsByName() throws Exception {
        //List<Product> results = LuceneUtil.searchProducts("productName", "shake");
        List<Product> results = LuceneUtil.searchProductsByName("shake");
        for(Product p : results) {
            System.out.println(p.getProductName());
        }
    }

    //@Test
    public void buildIndex() throws Exception {
        LuceneProductIndexerTask task = new LuceneProductIndexerTask();
    }
}
