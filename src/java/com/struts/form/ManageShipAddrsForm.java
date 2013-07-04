
package com.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.dao.ShippingAddress;
import com.constants.States.USStates;

import com.dao.User;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alinur Goksel
 */
public class ManageShipAddrsForm extends ValidatorForm {
    
    private String cmd;
    private List<ShippingAddress> addresses;
    private String company;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phone1;
    private String phone2;
    private String phone3;
    private USStates[] states = USStates.values();
    //private boolean useExisting;
    private long wAddrId;
    private long deleteId;
    private boolean doNotSave;

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

    public List<ShippingAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<ShippingAddress> addresses) {
        this.addresses = addresses;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
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

    public long getwAddrId() {
        return wAddrId;
    }

    public void setwAddrId(long wAddrId) {
        this.wAddrId = wAddrId;
    }

    public long getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(long deleteId) {
        this.deleteId = deleteId;
    }

    public boolean isDoNotSave() {
        return doNotSave;
    }

    public void setDoNotSave(boolean doNotSave) {
        this.doNotSave = doNotSave;
    }

    /**
     *
     */
    public ManageShipAddrsForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if("add".equalsIgnoreCase(cmd) || "update".equalsIgnoreCase(cmd) ||
          ("next".equalsIgnoreCase(cmd) && getwAddrId() == 0)) {
            errors = super.validate(mapping, request);
            if(errors.size() > 0)
                return errors;
        }

        return errors;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if(user != null)
            setAddresses(ShippingAddress.getByUserId(user.getUserId()));
    }
}
