/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.task;

import com.constants.HasteerConstants;
import com.dao.Product;
import com.search.LuceneUtil;
import com.util.ConfigUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author ecolak
 */
public class LuceneProductIndexerTask implements Runnable{
    private static Log log = LogFactory.getLog(LuceneProductIndexerTask.class);

    public LuceneProductIndexerTask() {
    }

    @Override
    public void run() {
        log.info("Start building Lucine index");
        long startTime = System.currentTimeMillis();

        try {
            LuceneUtil.buildIndex();
        } catch(IOException ie) {
            log.error(ie);
        }

        long endTime = System.currentTimeMillis();
        log.info("Lucine index built. Took " + (endTime - startTime) / 1000 + " seconds");
    }

}
