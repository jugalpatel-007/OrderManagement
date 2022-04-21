package com.maheshvalue.order_management;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.maheshvalue.order_management.bean.jbpmprocess.ProcessRequestVO;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

/**
 *
 * @author jugalpatel
 *
 */
public class Utilities implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Properties properties = null;

	public static String getProperty(String propertiesFile, String key)
			throws MissingResourceException {
		String result = null;
		try {
			if (properties == null)
				properties = new Properties();

			if (properties.getProperty(key) == null)
				properties.load(new FileInputStream(propertiesFile));

		} catch (Exception e) {
			properties = null;
			throw new MissingResourceException("ERROR loading property file",
					propertiesFile, key);
		}
		result = properties.getProperty(key);
		return result;
	}

	public static Map<String, Object> readRequest(Object processRequest) {
		System.out.println("requestJson:" + processRequest);
		Map<String, Object> paramMap = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String respData = objectMapper.writeValueAsString(processRequest);
			Map<String, Object> map = objectMapper.readValue(respData,
					Map.class);
			if (map.get(ICommonConstants.CUSTOMER) != null) {
				Map<String, String> customerMap = (Map<String, String>) map
						.get("customer");
				paramMap.put(ICommonConstants.CUSTOMER_NO,
						customerMap.get(ICommonConstants.CUSTOMER_NO));
				paramMap.put(ICommonConstants.COMPANY_CODE,
						customerMap.get(ICommonConstants.COMPANY_CODE));
				paramMap.put(ICommonConstants.CUSTOMER_DETAILS, objectMapper
						.writeValueAsString(customerMap
								.get(ICommonConstants.CUSTOMER_DETAILS)));
			} else {
				System.out.println("No customer data found.!");
			}

			if (map.get(ICommonConstants.SALE_ORDER) != null) {
				paramMap.put(ICommonConstants.SALE_ORDER, objectMapper
						.writeValueAsString(map
								.get(ICommonConstants.SALE_ORDER)));
				Map<String, String> saleOrder = (Map<String, String>) map
						.get(ICommonConstants.SALE_ORDER);
				Map<String, String> orderHeaderIn = objectMapper.readValue(
						objectMapper.writeValueAsString(saleOrder
								.get(ICommonConstants.ORDER_HEADER_IN)),
						Map.class);
				paramMap.put(ICommonConstants.SALE_ORG,
						orderHeaderIn.get(ICommonConstants.SALE_ORG));
			} else {
				System.out.println("No sales order data found.!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramMap;
	}

	public static String makeCheckCustomerBalanceJson(
			ProcessRequestVO processRequestVO, String sdDoc) {
		System.out.println("sdDoc:" + sdDoc);
		String requestJson = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode rootNode = mapper.createObjectNode();
			rootNode.put("customer", processRequestVO.getCustomerNo());
			rootNode.put("companyCode", processRequestVO.getCompanyCode());
			rootNode.put("salesOrganization", processRequestVO.getSaleOrder()
					.getOrderHeaderIn().getSale_org());
			rootNode.put("sdDoc", sdDoc);

			requestJson = mapper.writeValueAsString(rootNode);
			System.out.println("makeCheckCustomerBalanceJson requestJson"
					+ requestJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestJson;
	}

	public static String objectToJson(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setCreatedCustomer(ProcessRequestVO processRequestVO,
			String createdCustomerNo) {
		try {
			processRequestVO.setCustomerNo(createdCustomerNo);
			processRequestVO.getSaleOrder().getOrderPartners()
					.forEach(partner -> {
						partner.setPartn_numb(createdCustomerNo);
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String str = "{\"customer\": {\"companyCode\": \"MVPL\",\"customerNo\": \"0000100915\",\"customerDetails\": {\"piAddress\": {\"name\": \"Jugal Patel\",\"street\": \"Test Street\",\"postl_code\": \"654534\",\"city\": \"Vadodara\",\"region\": \"22\",\"country\": \"IN\",\"countrniso\": \"IN\",\"telephone\": \"8877665544\",\"langu\": \"EN\",\"langu_iso\": \"EN\",\"currency\": \"INR\",\"currency_iso\": \"INR\",\"countryiso\": \"IN\"},\"piCopyReference\": {\"salesOrg\": \"MVSO\",\"refCustmr\": \"0000100915\",\"division\": \"SD\",\"distrChan\": \"DS\"}}},\"saleOrder\": {\"orderHeaderIn\": {\"doc_type\": \"ZOR1\",\"sale_org\": \"MVSO\",\"dist_chan\": \"DS\",\"division\": \"SD\",\"date_type\": 1,\"purch_date\": \"2022-01-24\"},\"orderItemsIn\": [{\"item_number\": \"00010\",\"material\": \"000000000000500010\",\"qty\": 400},{\"item_number\": \"00020\",\"material\": \"000000000000500020\",\"qty\": 80}],\"orderPartners\": [{\"partn_role\": \"AG\",\"partn_numb\": \"0000100915\",\"itm_number\": \"000000\"}]}}";
		readRequest(str);
	}

	public Utilities() {
	}
}