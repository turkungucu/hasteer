/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import com.dao.Deal;
import com.dao.DealPricingOption;
import com.dao.User;
import com.struts.form.DealSetupForm;
import com.util.StringUtil;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.HibernateException;

/**
 *
 * @author ecolak
 */
public class DealSetupAction extends Action {
    private static Logger log = Logger.getLogger(DealSetupAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        DealSetupForm jForm = (DealSetupForm) form;
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();

        User user = User.getUserFromSession(request);
        if (user == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("userDoesNotExist"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        ActionForward forward = mapping.findForward("success");
        String cmd = jForm.getCmd();

        if("back".equalsIgnoreCase(cmd)) {
            forward = mapping.findForward("back");
        } else if(jForm.getDealId() > 0) {
            Deal deal = Deal.getById(jForm.getDealId());
            if("update".equalsIgnoreCase(cmd)) {
                forward = save(jForm, mapping, request, user, deal);
            } else if("delete".equalsIgnoreCase(cmd)) {
                if(jForm.getDoid() > 0) {
                    DealPricingOption option = DealPricingOption.getById(jForm.getDoid());
                    if(option != null) {
                        try {
                            option.delete();
                        } catch(Exception e) {
                            errors.add(Globals.ERROR_KEY, new ActionMessage("shared.errors.common.error", "deleting pricing option"));
                            saveErrors(request, errors);
                            return mapping.findForward("failure");
                        }
                        messages.add(Globals.MESSAGE_KEY, new ActionMessage("shared.forms.deleted.success", "Option"));
                        saveMessages(request, messages);
                        setForm(jForm, jForm.getDealId());
                        forward = (mapping.findForward("success"));
                    }
                }
            } else {
                setForm(jForm, jForm.getDealId());
            }
        } else if("create".equalsIgnoreCase(cmd)) {
            forward = save(jForm, mapping, request, user, new Deal());
        }

        return forward;
    }

    public ActionForward save(DealSetupForm jForm, ActionMapping mapping, HttpServletRequest request, User user, Deal d){
        if(d == null)
            mapping.findForward("failure");

        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        
        ActionForward forward = null;
        boolean success = true;
        try{
            d.setDealName(jForm.getDealName());
            try {
                d.setStartDate(Deal.dateFormatter.parse(jForm.getStartDate()));
                d.setEndDate(Deal.dateFormatter.parse(jForm.getEndDate()));
            } catch(Exception e) {}
            d.setSellerId(user.getUserId());
            d.setProductId(jForm.getProductId());
            d.setModifiedDate(new Date());
            d.setModifiedBy(user.getUsername());
            d.setRetailPrice(Double.parseDouble(jForm.getRetailPrice()));
            d.setShippingPolicyId(jForm.getShippingPolicyId());
            d.setReturnPolicyId(jForm.getReturnPolicyId());
            if (d.getCreateDate() == null) {
                d.setCreateDate(new Date());
            }
            if (StringUtils.isBlank(d.getCreatedBy())) {
                d.setCreatedBy(user.getUsername());
            }
            d.save();

            List<DealPricingOption> options = DealPricingOption.getByDealId(d.getDealId());
            if(options != null) {
                DealPricingOption o = options.size() > 0 ? options.get(0) : null;
                if(!StringUtils.isBlank(jForm.getMinNum1()) && !StringUtils.isBlank(jForm.getPrice1())) {
                    if(o != null) {
                        o.setMinNumParticipants(Integer.parseInt(jForm.getMinNum1()));
                        o.setPrice(Double.parseDouble(jForm.getPrice1()));
                        o.save();
                    } else {
                        o = new DealPricingOption(d.getDealId(), Integer.parseInt(jForm.getMinNum1()),
                                    Double.parseDouble(jForm.getPrice1()));
                        o.save();
                    }
                }

                if(!StringUtils.isBlank(jForm.getMinNum2()) && !StringUtils.isBlank(jForm.getPrice2())) {
                    o = options.size() > 1 ? options.get(1) : null;
                    if(o != null) {
                        o.setMinNumParticipants(Integer.parseInt(jForm.getMinNum2()));
                        o.setPrice(Double.parseDouble(jForm.getPrice2()));
                        o.save();
                    } else {
                        o = new DealPricingOption(d.getDealId(), Integer.parseInt(jForm.getMinNum2()),
                                    Double.parseDouble(jForm.getPrice2()));
                        o.save();
                    }
                }

                if(!StringUtils.isBlank(jForm.getMinNum3()) && !StringUtils.isBlank(jForm.getPrice3())) {
                    o = options.size() > 2 ? options.get(2) : null;
                    if(o != null) {
                        o.setMinNumParticipants(Integer.parseInt(jForm.getMinNum3()));
                        o.setPrice(Double.parseDouble(jForm.getPrice3()));
                        o.save();
                    } else {
                        o = new DealPricingOption(d.getDealId(), Integer.parseInt(jForm.getMinNum3()),
                                    Double.parseDouble(jForm.getPrice3()));
                        o.save();
                    }
                }

                if(!StringUtils.isBlank(jForm.getMinNum4()) && !StringUtils.isBlank(jForm.getPrice4())) {
                    o = options.size() > 3 ? options.get(3) : null;
                    if(o != null) {
                        o.setMinNumParticipants(Integer.parseInt(jForm.getMinNum4()));
                        o.setPrice(Double.parseDouble(jForm.getPrice4()));
                        o.save();
                    } else {
                        o = new DealPricingOption(d.getDealId(), Integer.parseInt(jForm.getMinNum4()),
                                    Double.parseDouble(jForm.getPrice4()));
                        o.save();
                    }
                }

                if(!StringUtils.isBlank(jForm.getMinNum5()) && !StringUtils.isBlank(jForm.getPrice5())) {
                    o = options.size() > 4 ? options.get(4) : null;
                    if(o != null) {
                        o.setMinNumParticipants(Integer.parseInt(jForm.getMinNum5()));
                        o.setPrice(Double.parseDouble(jForm.getPrice5()));
                        o.save();
                    } else {
                        o = new DealPricingOption(d.getDealId(), Integer.parseInt(jForm.getMinNum5()),
                                    Double.parseDouble(jForm.getPrice5()));
                        o.save();
                    }
                }
            }

            setForm(jForm, d.getDealId());
        }
        catch(HibernateException he) {
            log.error(he, he);
            errors.add(Globals.ERROR_KEY, new ActionMessage("DealSetupAction.errors.unsuccessfulDealSetup"));
            saveErrors(request, errors);
            forward = (mapping.findForward("failure"));
            success = false;
        }

        if(success) {
            messages.add(Globals.MESSAGE_KEY, "create".equals(jForm.getCmd())
                                                ? new ActionMessage("shared.forms.created.success", "Deal")
                                                : new ActionMessage("shared.forms.updated.success", "Deal"));
            saveMessages(request, messages);
            forward = (mapping.findForward("success"));
        } else {
            forward = (mapping.findForward("failure"));
        }

        return forward;
    }

    private void setForm(DealSetupForm jForm, long dealId) {
        Deal deal = Deal.getById(dealId);
        if(deal != null) {
            jForm.setDealName(deal.getDealName());
            jForm.setDealId(dealId);
            jForm.setStartDate(Deal.dateFormatter.format(deal.getStartDate()));
            jForm.setEndDate(Deal.dateFormatter.format(deal.getEndDate()));
            jForm.setProductId(deal.getProductId());
            jForm.setRetailPrice(StringUtil.getFormattedPrice(deal.getRetailPrice()));
            jForm.setReturnPolicyId(deal.getReturnPolicyId());
            jForm.setShippingPolicyId(deal.getShippingPolicyId());
            
            List<DealPricingOption> options = DealPricingOption.getByDealId(dealId);
            if(options != null) {
                for(int i = 0; i < options.size(); i++) {
                    DealPricingOption o = options.get(i);
                    if(i == 0) {
                        jForm.setMinNum1(String.valueOf(o.getMinNumParticipants()));
                        jForm.setPrice1(String.valueOf(o.getPrice()));
                        jForm.setOid1(o.getOptionId());
                    } else if(i == 1) {
                        jForm.setMinNum2(String.valueOf(o.getMinNumParticipants()));
                        jForm.setPrice2(String.valueOf(o.getPrice()));
                        jForm.setOid2(o.getOptionId());
                    } else if(i == 2) {
                        jForm.setMinNum3(String.valueOf(o.getMinNumParticipants()));
                        jForm.setPrice3(String.valueOf(o.getPrice()));
                        jForm.setOid3(o.getOptionId());
                    } else if(i == 3) {
                        jForm.setMinNum4(String.valueOf(o.getMinNumParticipants()));
                        jForm.setPrice4(String.valueOf(o.getPrice()));
                        jForm.setOid4(o.getOptionId());
                    } else if(i == 4) {
                        jForm.setMinNum5(String.valueOf(o.getMinNumParticipants()));
                        jForm.setPrice5(String.valueOf(o.getPrice()));
                        jForm.setOid5(o.getOptionId());
                    }
                }
            }
        }
    }
}
