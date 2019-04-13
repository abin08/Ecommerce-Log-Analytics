/**
 * 
 */
package com.kafka.models;

import java.util.List;

/**
 * @author Abin K. Antony
 * 13-Apr-2019
 * @version 1.0
 */
public class Orders {
    List<String> category;
    String currency;
    String customerFirstName;
    String customerFullName;
    String customerGender;
    String customerId;
    String customerLastName;
    String customerPhone;
    String dayOfWeek;
    int dayOfWeekNumber;
    String email;
    String geoIpCityName;
    String geoIpContinentName;
    String geoIpCountryIsoCode;
    String geoIpRegionName;
    String manufacturer;
    String orderDate;
    String orderId;
    String products;
    String productsCreatedOn;
    String sku;
    int taxfulTotalPrice;
    int taxlessTotalPrice;
    int totalQuantity;
    int totalUniqueProducts;
    String type;
    String user;
    
    
    public Orders(String value) {
	
    }
    
    public List<String> getCategory() {
        return category;
    }
    public void setCategory(List<String> category) {
        this.category = category;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getCustomerFirstName() {
        return customerFirstName;
    }
    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }
    public String getCustomerFullName() {
        return customerFullName;
    }
    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }
    public String getCustomerGender() {
        return customerGender;
    }
    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getCustomerLastName() {
        return customerLastName;
    }
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
    public String getCustomerPhone() {
        return customerPhone;
    }
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public int getDayOfWeekNumber() {
        return dayOfWeekNumber;
    }
    public void setDayOfWeekNumber(int dayOfWeekNumber) {
        this.dayOfWeekNumber = dayOfWeekNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGeoIpCityName() {
        return geoIpCityName;
    }
    public void setGeoIpCityName(String geoIpCityName) {
        this.geoIpCityName = geoIpCityName;
    }
    public String getGeoIpContinentName() {
        return geoIpContinentName;
    }
    public void setGeoIpContinentName(String geoIpContinentName) {
        this.geoIpContinentName = geoIpContinentName;
    }
    public String getGeoIpCountryIsoCode() {
        return geoIpCountryIsoCode;
    }
    public void setGeoIpCountryIsoCode(String geoIpCountryIsoCode) {
        this.geoIpCountryIsoCode = geoIpCountryIsoCode;
    }
    public String getGeoIpRegionName() {
        return geoIpRegionName;
    }
    public void setGeoIpRegionName(String geoIpRegionName) {
        this.geoIpRegionName = geoIpRegionName;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getProducts() {
        return products;
    }
    public void setProducts(String products) {
        this.products = products;
    }
    public String getProductsCreatedOn() {
        return productsCreatedOn;
    }
    public void setProductsCreatedOn(String productsCreatedOn) {
        this.productsCreatedOn = productsCreatedOn;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public int getTaxfulTotalPrice() {
        return taxfulTotalPrice;
    }
    public void setTaxfulTotalPrice(int taxfulTotalPrice) {
        this.taxfulTotalPrice = taxfulTotalPrice;
    }
    public int getTaxlessTotalPrice() {
        return taxlessTotalPrice;
    }
    public void setTaxlessTotalPrice(int taxlessTotalPrice) {
        this.taxlessTotalPrice = taxlessTotalPrice;
    }
    public int getTotalQuantity() {
        return totalQuantity;
    }
    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    public int getTotalUniqueProducts() {
        return totalUniqueProducts;
    }
    public void setTotalUniqueProducts(int totalUniqueProducts) {
        this.totalUniqueProducts = totalUniqueProducts;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    @Override
    public String toString() {
	return "Orders [category=" + category + ", currency=" + currency
		+ ", customerFirstName=" + customerFirstName
		+ ", customerFullName=" + customerFullName + ", customerGender="
		+ customerGender + ", customerId=" + customerId
		+ ", customerLastName=" + customerLastName + ", customerPhone="
		+ customerPhone + ", dayOfWeek=" + dayOfWeek
		+ ", dayOfWeekNumber=" + dayOfWeekNumber + ", email=" + email
		+ ", geoIpCityName=" + geoIpCityName + ", geoIpContinentName="
		+ geoIpContinentName + ", geoIpCountryIsoCode="
		+ geoIpCountryIsoCode + ", geoIpRegionName=" + geoIpRegionName
		+ ", manufacturer=" + manufacturer + ", orderDate=" + orderDate
		+ ", orderId=" + orderId + ", products=" + products
		+ ", productsCreatedOn=" + productsCreatedOn + ", sku=" + sku
		+ ", taxfulTotalPrice=" + taxfulTotalPrice
		+ ", taxlessTotalPrice=" + taxlessTotalPrice
		+ ", totalQuantity=" + totalQuantity + ", totalUniqueProducts="
		+ totalUniqueProducts + ", type=" + type + ", user=" + user
		+ "]";
    }
    
    
}
