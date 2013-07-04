//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.10.28 at 03:07:58 AM PDT 
//


package com.api.shipping.usps;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Package">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ZipOrigination" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ZipDestination" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Pounds" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="Ounces" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="Container" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="FirstClassMailType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Size" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Width" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="Length" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="Height" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="Girth" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="Machinable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Zone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Restrictions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Postage" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="MailService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="CLASSID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "_package"
})
@XmlRootElement(name = "RateV3Response")
public class RateV3Response {

    @XmlElement(name = "Package")
    protected List<RateV3Response.Package> _package;

    /**
     * Gets the value of the package property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the package property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPackage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RateV3Response.Package }
     * 
     * 
     */
    public List<RateV3Response.Package> getPackage() {
        if (_package == null) {
            _package = new ArrayList<RateV3Response.Package>();
        }
        return this._package;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ZipOrigination" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ZipDestination" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Pounds" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="Ounces" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="Container" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="FirstClassMailType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Size" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Width" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="Length" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="Height" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="Girth" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="Machinable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Zone" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Restrictions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Postage" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="MailService" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="CLASSID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "zipOrigination",
        "zipDestination",
        "pounds",
        "ounces",
        "container",
        "firstClassMailType",
        "size",
        "width",
        "length",
        "height",
        "girth",
        "machinable",
        "zone",
        "restrictions",
        "postage"
    })
    public static class Package {

        @XmlElement(name = "ZipOrigination", required = true)
        protected String zipOrigination;
        @XmlElement(name = "ZipDestination", required = true)
        protected String zipDestination;
        @XmlElement(name = "Pounds", required = true)
        protected BigInteger pounds;
        @XmlElement(name = "Ounces", required = true)
        protected BigDecimal ounces;
        @XmlElement(name = "Container")
        protected String container;
        @XmlElement(name = "FirstClassMailType")
        protected String firstClassMailType;
        @XmlElement(name = "Size", required = true)
        protected String size;
        @XmlElement(name = "Width")
        protected BigDecimal width;
        @XmlElement(name = "Length")
        protected BigDecimal length;
        @XmlElement(name = "Height")
        protected BigDecimal height;
        @XmlElement(name = "Girth")
        protected BigDecimal girth;
        @XmlElement(name = "Machinable")
        protected String machinable;
        @XmlElement(name = "Zone", required = true)
        protected String zone;
        @XmlElement(name = "Restrictions")
        protected String restrictions;
        @XmlElement(name = "Postage", required = true)
        protected List<RateV3Response.Package.Postage> postage;
        @XmlAttribute(name = "ID", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "NMTOKEN")
        protected String id;

        /**
         * Gets the value of the zipOrigination property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZipOrigination() {
            return zipOrigination;
        }

        /**
         * Sets the value of the zipOrigination property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZipOrigination(String value) {
            this.zipOrigination = value;
        }

        /**
         * Gets the value of the zipDestination property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZipDestination() {
            return zipDestination;
        }

        /**
         * Sets the value of the zipDestination property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZipDestination(String value) {
            this.zipDestination = value;
        }

        /**
         * Gets the value of the pounds property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getPounds() {
            return pounds;
        }

        /**
         * Sets the value of the pounds property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPounds(BigInteger value) {
            this.pounds = value;
        }

        /**
         * Gets the value of the ounces property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getOunces() {
            return ounces;
        }

        /**
         * Sets the value of the ounces property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setOunces(BigDecimal value) {
            this.ounces = value;
        }

        /**
         * Gets the value of the container property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContainer() {
            return container;
        }

        /**
         * Sets the value of the container property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContainer(String value) {
            this.container = value;
        }

        /**
         * Gets the value of the firstClassMailType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFirstClassMailType() {
            return firstClassMailType;
        }

        /**
         * Sets the value of the firstClassMailType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFirstClassMailType(String value) {
            this.firstClassMailType = value;
        }

        /**
         * Gets the value of the size property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSize() {
            return size;
        }

        /**
         * Sets the value of the size property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSize(String value) {
            this.size = value;
        }

        /**
         * Gets the value of the width property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getWidth() {
            return width;
        }

        /**
         * Sets the value of the width property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setWidth(BigDecimal value) {
            this.width = value;
        }

        /**
         * Gets the value of the length property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getLength() {
            return length;
        }

        /**
         * Sets the value of the length property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setLength(BigDecimal value) {
            this.length = value;
        }

        /**
         * Gets the value of the height property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getHeight() {
            return height;
        }

        /**
         * Sets the value of the height property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setHeight(BigDecimal value) {
            this.height = value;
        }

        /**
         * Gets the value of the girth property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getGirth() {
            return girth;
        }

        /**
         * Sets the value of the girth property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setGirth(BigDecimal value) {
            this.girth = value;
        }

        /**
         * Gets the value of the machinable property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMachinable() {
            return machinable;
        }

        /**
         * Sets the value of the machinable property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMachinable(String value) {
            this.machinable = value;
        }

        /**
         * Gets the value of the zone property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZone() {
            return zone;
        }

        /**
         * Sets the value of the zone property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZone(String value) {
            this.zone = value;
        }

        /**
         * Gets the value of the restrictions property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRestrictions() {
            return restrictions;
        }

        /**
         * Sets the value of the restrictions property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRestrictions(String value) {
            this.restrictions = value;
        }

        /**
         * Gets the value of the postage property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the postage property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPostage().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RateV3Response.Package.Postage }
         * 
         * 
         */
        public List<RateV3Response.Package.Postage> getPostage() {
            if (postage == null) {
                postage = new ArrayList<RateV3Response.Package.Postage>();
            }
            return this.postage;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getID() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setID(String value) {
            this.id = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="MailService" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *       &lt;/sequence>
         *       &lt;attribute name="CLASSID" type="{http://www.w3.org/2001/XMLSchema}integer" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "mailService",
            "rate"
        })
        public static class Postage {

            @XmlElement(name = "MailService", required = true)
            protected String mailService;
            @XmlElement(name = "Rate", required = true)
            protected BigDecimal rate;
            @XmlAttribute(name = "CLASSID", required = true)
            protected BigInteger classid;

            /**
             * Gets the value of the mailService property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMailService() {
                return mailService;
            }

            /**
             * Sets the value of the mailService property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMailService(String value) {
                this.mailService = value;
            }

            /**
             * Gets the value of the rate property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getRate() {
                return rate;
            }

            /**
             * Sets the value of the rate property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setRate(BigDecimal value) {
                this.rate = value;
            }

            /**
             * Gets the value of the classid property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getCLASSID() {
                return classid;
            }

            /**
             * Sets the value of the classid property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setCLASSID(BigInteger value) {
                this.classid = value;
            }

        }

    }

}
