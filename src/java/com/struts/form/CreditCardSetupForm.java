/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.form;

import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

import com.dao.CreditCardDetail.CreditCardType;
import com.constants.States.USStates;
import com.dao.CreditCardDetail;
import com.dao.User;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ecolak
 */
public class CreditCardSetupForm extends ValidatorForm {
    private String cmd;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String cardholderName;
    private String cardType;
    private String creditCardNumber;
    private String expMonth;
    private String expYear;
    private CreditCardType[] cardTypes = CreditCardType.values();
    private USStates[] states = USStates.values();
    private List<CreditCardDetail> ccDetails;
    private long wccId;
    private boolean useExisting;
    private boolean sameAsShippingAddr;
    private long deleteId;
    private String cvv;
    private boolean doNotSave;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public CreditCardType[] getCardTypes() {
        return cardTypes;
    }

    public void setCardTypes(CreditCardType[] cardTypes) {
        this.cardTypes = cardTypes;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public USStates[] getStates() {
        return states;
    }

    public void setStates(USStates[] states) {
        this.states = states;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<CreditCardDetail> getCcDetails() {
        return ccDetails;
    }

    public void setCcDetails(List<CreditCardDetail> ccDetails) {
        this.ccDetails = ccDetails;
    }

    public long getWccId() {
        return wccId;
    }

    public void setWccId(long wccId) {
        this.wccId = wccId;
    }

    public boolean isUseExisting() {
        return useExisting;
    }

    public void setUseExisting(boolean useExisting) {
        this.useExisting = useExisting;
    }

    public boolean isSameAsShippingAddr() {
        return sameAsShippingAddr;
    }

    public void setSameAsShippingAddr(boolean sameAsShippingAddr) {
        this.sameAsShippingAddr = sameAsShippingAddr;
    }

    public long getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(long deleteId) {
        this.deleteId = deleteId;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public boolean isDoNotSave() {
        return doNotSave;
    }

    public void setDoNotSave(boolean doNotSave) {
        this.doNotSave = doNotSave;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if("create".equalsIgnoreCase(cmd) || "update".equalsIgnoreCase(cmd) ||
           ("next".equalsIgnoreCase(cmd) && getWccId() == 0)) {
            errors = super.validate(mapping, request);
            if(errors.size() > 0)
                return errors;

            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(expYear), Integer.parseInt(expMonth), 1);
            if(cal.getTime().before(new Date())) {
                errors.add(Globals.ERROR_KEY,
                            new ActionMessage("CreditCardSetup.errors.invalid.exp.date"));
            }
        }
        return errors;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if(user != null)
            setCcDetails(CreditCardDetail.getByUserId(user.getUserId()));
    }
}
