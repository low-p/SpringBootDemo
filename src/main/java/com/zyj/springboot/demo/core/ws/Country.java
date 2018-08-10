
package com.zyj.springboot.demo.core.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>country complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="country">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="population" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="capital" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="currency" type="{http://com.zyj.springboot.demo/ws}currency"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "country", namespace = "http://com.zyj.springboot.demo/ws", propOrder = {
        "name",
        "population",
        "capital",
        "currency"
})
public class Country {

    @XmlElement(namespace = "http://com.zyj.springboot.demo/ws", required = true)
    protected String name;
    @XmlElement(namespace = "http://com.zyj.springboot.demo/ws", required = true)
    protected Integer population;
    @XmlElement(namespace = "http://com.zyj.springboot.demo/ws", required = true)
    protected String capital;
    @XmlElement(namespace = "http://com.zyj.springboot.demo/ws", required = true)
    @XmlSchemaType(name = "string")
    protected Currency currency;

    /**
     * 获取name属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取population属性的值。
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     * 设置population属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setPopulation(Integer value) {
        this.population = value;
    }

    /**
     * 获取capital属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCapital() {
        return capital;
    }

    /**
     * 设置capital属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCapital(String value) {
        this.capital = value;
    }

    /**
     * 获取currency属性的值。
     *
     * @return
     *     possible object is
     *     {@link Currency }
     *
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * 设置currency属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link Currency }
     *
     */
    public void setCurrency(Currency value) {
        this.currency = value;
    }

}
