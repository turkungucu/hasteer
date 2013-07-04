package com.struts.form;

import java.util.List;

import com.dao.Deal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author Alinur Goksel
 */
public class SearchForm extends ValidatorForm {
    
    private String q;
    private String cid;
    private List<Deal> results;
    private String curPage;
    private int totalHits;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<Deal> getResults() {
        return results;
    }

    public void setResults(List<Deal> results) {
        this.results = results;
    }

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if((getQ() == null || getQ().trim().length() == 0) &&
           (getCid() == null || getCid().trim().length() == 0)) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("SearchForm.errors.blankSearchQuery"));
        }
        return errors;
    }
}
