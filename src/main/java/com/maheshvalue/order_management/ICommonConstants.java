package com.maheshvalue.order_management;

import java.io.File;

/**
 * 
 * @author jugalpatel
 *
 */

public interface ICommonConstants {

	public String SERVER_PROPERTY_DIR = System.getProperty("jboss.server.config.dir") + "/maheshValue.properties";
	
	public String API_HOST_URL = Utilities.getProperty(SERVER_PROPERTY_DIR,"apiHostURL");
	
	public String  API_GET_CUSTOMER_DETAILS = API_HOST_URL + "/camelServices/getCustomerDetails";
	
	public String  API_GET_KEY_BALANCE_DETAILS = API_HOST_URL + "/camelServices/getKeyBalanceDetails";
	
	public String  API_CREATE_CUSTOMER = API_HOST_URL + "/camelServices/createCustomer";
	
	public String  API_CREATE_DOWNPAYMENT = API_HOST_URL + "/camelServices/createDownPayment";
	
	public String  API_CREATE_SALE_ORDER = API_HOST_URL + "/camelServices/createSaleOrder";
	
	public String  API_CREATE_DELIVERY = API_HOST_URL + "/camelServices/createDelivery";
	
	public String  API_CHECK_CUSTOMER_BALANCE = API_HOST_URL + "/camelServices/checkCustomerBalance";
	
	public String  API_CREATE_INVOICE = API_HOST_URL + "/camelServices/createInvoice";
	
	String CUSTOMER = "customer";
	String COMPANY_CODE = "companyCode";
	String CUSTOMER_NO = "customerNo";
	String CUSTOMER_DETAILS = "customerDetails";

	String SALE_ORDER = "saleOrder";
	String ORDER_HEADER_IN = "orderHeaderIn";
    String SALE_ORG = "sale_org";
}
