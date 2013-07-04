/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Scorer;

/**
 *
 * @author ecolak
 */
public class HitCollector extends Collector {

    private int docBase;
    private IndexReader nextReader;
    private LinkedList<Document> docs;
    private long totalHits;

    public HitCollector() {
        docs = new LinkedList<Document>();
    }

    @Override
    public boolean acceptsDocsOutOfOrder() {
        return true;
    }

    @Override
    public void setNextReader(IndexReader reader, int docBase) {
        this.nextReader = reader;
        this.docBase = docBase;
    }

    @Override
    public void setScorer(Scorer scorer) {
    }

    @Override
    public void collect(int doc) throws CorruptIndexException, IOException {
        totalHits++;
        docs.add(nextReader.document(doc));
    }

    public long getTotalHits(){
        return totalHits;
    }

    protected List<Document> getHits() {
        return docs;
    }

    protected List<Document> getHits(int startDoc, int endDoc) {
        if (endDoc < startDoc) {
            throw new IllegalArgumentException("end index cannot be smaller than start index");
        }
        
        List<Document> result = new ArrayList<Document>(endDoc - startDoc + 1);

        int count = 0;
        for (Iterator<Document> docIter = docs.iterator(); docIter.hasNext();) {
            Document doc = docIter.next();
            if (count >= startDoc && count <= endDoc) {
                result.add(doc);
            }
            count++;
        }

        return result;
    }
}
