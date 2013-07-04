/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import com.constants.HasteerConstants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dao.Deal;
import com.dao.SearchTerm;
import com.struts.form.SearchForm;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 *
 * @author Alinur Goksel
 */
public class SearchAction extends Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        SearchForm searchForm = (SearchForm)form;
        ActionMessages messages = new ActionMessages();
        //List<Product> products = LuceneUtil.searchProductsByName(searchForm.getQ());

        String curPageStr = searchForm.getCurPage();
        int curPage = 0;
        try {
            curPage = Integer.parseInt(curPageStr);
        } catch(Exception e){
            // ignore
        }

        if(curPage == 0) {
            curPage = 1;
            searchForm.setCurPage(String.valueOf(curPage));
        }

        List<Deal> deals = null;

        try {
            String q = searchForm.getQ();
            if(q != null && q.trim().length() > 0) {
                deals = Deal.searchForDeals(q.split(" "));
                // save search query in db
                SearchTerm st = SearchTerm.getBySearchTerm(q);
                if (st == null) {
                    st = new SearchTerm(q, 1);
                    st.save();
                } else {
                    st.setCount(st.getCount() + 1);
                    st.save();
                }
            } else if(searchForm.getCid() != null && searchForm.getCid().trim().length() > 0)
                deals = Deal.getDealsByCategory(Long.parseLong(searchForm.getCid()));
        } catch(Exception e) {
            messages.add(Globals.ERROR_KEY, new ActionMessage("SearchForm.errors.dbError"));
            saveErrors(request, messages);
            return (mapping.findForward("failure"));
        }
        
        if(deals != null) {
            //List<Product> products = LuceneUtil.searchProductsByDescription(searchForm.getQ()+"~");
            searchForm.setTotalHits(deals.size());

            int startIndex = (curPage-1) * HasteerConstants.PRODUCTS_PER_PAGE;
            int endIndex = Math.min(deals.size(), curPage * HasteerConstants.PRODUCTS_PER_PAGE);

            List<Deal> results = new ArrayList<Deal>(HasteerConstants.PRODUCTS_PER_PAGE);
            for(int i = startIndex; i < endIndex; i++) {
                results.add(deals.get(i));
            }
            searchForm.setResults(results);
        }
        return mapping.findForward(SUCCESS);
    }
}
