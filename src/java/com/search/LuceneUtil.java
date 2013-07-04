/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.search;

import com.constants.HasteerConstants;
import com.dao.Product;
import com.util.ConfigUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author ecolak
 */
public class LuceneUtil {

    private static Log log = LogFactory.getLog(LuceneUtil.class);
    private static String luceneIndexDir = ConfigUtil.getString("Hasteer.LuceneIndex.directory",
            HasteerConstants.DEFAULT_LUCENE_INDEX_DIR);

    public static List<Product> searchProductsByName(String queryString)
            throws IOException, ParseException {
        return searchProducts(HasteerConstants.PRODUCT_NAME, queryString, -1, -1);
    }

    public static List<Product> searchProductsByName(String queryString, int startDoc, int endDoc)
            throws IOException, ParseException {
        return searchProducts(HasteerConstants.PRODUCT_NAME, queryString, startDoc, endDoc);
    }

    public static List<Product> searchProductsByDescription(String queryString)
            throws IOException, ParseException {
        return searchProducts(HasteerConstants.PRODUCT_DESCRIPTION, queryString, -1, -1);
    }

    public static List<Product> searchProductsByDescription(String queryString, int startDoc, int endDoc)
            throws IOException, ParseException {
        return searchProducts(HasteerConstants.PRODUCT_DESCRIPTION, queryString, startDoc, endDoc);
    }

    public static List<Product> searchProductsBySummary(String queryString)
            throws IOException, ParseException {
        return searchProducts(HasteerConstants.PRODUCT_SUMMARY, queryString, -1, -1);
    }

    public static List<Product> searchProducts(String columnName, String queryString) throws IOException, ParseException {
        return searchProducts(columnName, queryString, -1, -1);
    }

    public static List<Product> searchProducts(String columnName, String queryString, int startDoc, int endDoc)
            throws IOException, ParseException {
        List<Product> result = new ArrayList<Product>();
        Directory directory = null;
        IndexSearcher searcher = null;

        try {
            //directory = new RAMDirectory();
            directory = FSDirectory.open(new File(luceneIndexDir));
            searcher = new IndexSearcher(directory, true);
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);

            QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, columnName, analyzer);
            Query query = parser.parse(queryString);
            //ScoreDoc[] hits = searcher.search(query, null, startDoc + endDoc).scoreDocs;

            HitCollector collector = new HitCollector();
            try {
                searcher.search(query, collector);
            } catch (Exception e) {
                log.error(e, e);
            }
            List<Document> hits = null;

            if (startDoc >= 0 && endDoc >= 0) {
                hits = collector.getHits(startDoc, endDoc);
            } else {
                hits = collector.getHits();
            }

            if (hits != null) {
                for (Document hitDoc : hits) {
                    Product product = null;
                    try {
                        product = Product.getById(Long.parseLong(hitDoc.get("productId")));
                    } catch (NumberFormatException ne) {
                        log.error(ne, ne);
                    }
                    if (product != null) {
                        result.add(product);
                    }
                }
            }
        } finally {
            if (searcher != null) {
                searcher.close();
            }
            if (directory != null) {
                directory.close();
            }
        }
        return result;
    }

    public static void buildIndex() throws IOException {
        Directory directory = null;
        IndexWriter writer = null;
        try {
            directory = FSDirectory.open(new File(luceneIndexDir));
            //directory = new RAMDirectory();
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);

            // Make an writer to create the index
            writer = new IndexWriter(directory, analyzer, true, new IndexWriter.MaxFieldLength(
                    HasteerConstants.LUCENE_INDEX_MAX_FIELD_LENGTH));

            writer.deleteAll();
            List<Product> allProducts = Product.getAll();
            for (Product product : allProducts) {
                writer.addDocument(LuceneUtil.createProductDocument(String.valueOf(product.getProductId()),
                        product.getProductName(), product.getDetails()));
            }

            // Optimize and close the writer to finish building the index
            writer.optimize();
        } finally {
            if(writer != null)
                writer.close();
            if(directory != null)
                directory.close();
        }
    }

    public static Document createProductDocument(String productId, String productName, String details) {
        Document document = new Document();
        document.add(new Field("productId", productId, Field.Store.YES, Field.Index.NO));
        document.add(new Field("productName", productName, Field.Store.NO, Field.Index.ANALYZED));
        document.add(new Field("details", details, Field.Store.NO, Field.Index.ANALYZED));
        return document;
    }
}
