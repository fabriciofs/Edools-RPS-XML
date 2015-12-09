package model.edools;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.HttpHeaders;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vitor on 05/11/2015.
 */
public class Edools {

	//EDOOLS API
	private static final String EDOOLS_CORE_API = "https://core.edools.com/";
	private static final String EDOOLS_ECOMMERCE_API = "https://ecommerce.edools.com/";
	private static final String AUTHORIZATION_STRING = "Token token=";
	private static final String ACCEPT_STRING = "application/vnd.edools.core.v1+json";

	//SCHOOL_PRODUCTS
	private static final String SCHOOL_PRODUCTS = "school_products/";

	//PAYMENTS
	private static final String PAYMENTS = "payments/";
	private static final String PARAM_START_DATE = "start_date";
	private static final String PARAM_STATUS = "status";
	private static final String RESULT_PAYMENTS = "payments";

	//CUSTOMERS
	private static final String CUSTOMERS = "customers/";
	private static final String PARAM_SCHOOL_GUID = "school_guid";

	private String token;
	private String schoolGuid;

	public Edools(String token, String schoolGuid) {
		this.token = token;
		this.schoolGuid = schoolGuid;
	}

	/**
	 * Gets a product with a certain ID.
	 * @param id A product ID.
	 * @return A Product.
	 */
	public Product getProduct(String id) {
		HttpResponse<JsonNode> response;
		try {
			response = Unirest.get(EDOOLS_CORE_API + SCHOOL_PRODUCTS + id)
					.header(HttpHeaders.AUTHORIZATION, "Token token=" + token)
					.header(HttpHeaders.ACCEPT, "application/vnd.edools.core.v1+json")
					.asJson();
		} catch (UnirestException e) {
			return null;
		}

		Gson gResponse = new Gson();
		return gResponse.fromJson(response.getBody().toString(), Product.class);
	}

	/**
	 * Checks if there are payments after a certain start date.
	 * @param startDate A date in the ISO8601 format without timezone. E.g.: "2015-11-04T22:47:10".
	 * @param status A payment status. E.g.: "authorized, done". Nullable.
	 * @return True if there are payments, false otherwise.
	 */
	public boolean checkPayments(String startDate, String status) {
		HttpResponse<JsonNode> response;
		try {
			GetRequest request;
			if(status != null) {
				request = Unirest.get(EDOOLS_ECOMMERCE_API + PAYMENTS + "?" + PARAM_START_DATE + "=" + startDate + "&" + PARAM_STATUS + "=" + status + "&per_page=1");
			}
			else {
				request = Unirest.get(EDOOLS_ECOMMERCE_API + PAYMENTS + "?" + PARAM_START_DATE + "=" + startDate + "&per_page=1");
			}
			response = request
					.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_STRING + token)
					.header(HttpHeaders.ACCEPT, ACCEPT_STRING)
					.asJson();
		} catch (UnirestException e) {
			return false;
		}

		JSONObject responseJSON = new JSONObject(response.getBody().toString());
		JSONArray payments = responseJSON.getJSONArray(RESULT_PAYMENTS);

		Gson gResponse = new Gson();
		if(gResponse.fromJson(payments.toString(), Payment[].class).length == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Gets payments after a certain start date.
	 * @param startDate A date in the ISO8601 format without timezone. E.g.: "2015-11-04T22:47:10".
	 * @param status A payment status. E.g.: "authorized, done". Nullable.
	 * @return A List of Payments.
	 */
	public List<Payment> getPayments(String startDate, String status) {

		int currentPage = 1;
		int totalPages = 0;
		List<Payment> result = new ArrayList<>();
		do {
			HttpResponse<JsonNode> response;
			try {
				GetRequest request;
				if (status != null) {
					request = Unirest.get(EDOOLS_ECOMMERCE_API + PAYMENTS + "?" + PARAM_START_DATE + "=" + startDate + "&" + PARAM_STATUS + "=" + status + "&page=" + currentPage + "&per_page=100");
				} else {
					request = Unirest.get(EDOOLS_ECOMMERCE_API + PAYMENTS + "?" + PARAM_START_DATE + "=" + startDate + "&page=" + currentPage + "&per_page=100");
				}
				response = request
						.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_STRING + token)
						.header(HttpHeaders.ACCEPT, ACCEPT_STRING)
						.asJson();
			} catch (UnirestException e) {
				return null;
			}

			JSONObject responseJSON = new JSONObject(response.getBody().toString());
			totalPages = responseJSON.getInt("total_pages");
			JSONArray payments = responseJSON.getJSONArray(RESULT_PAYMENTS);

			Gson gResponse = new Gson();
			result.addAll(Arrays.asList(gResponse.fromJson(payments.toString(), Payment[].class)));

			currentPage++;

		} while(currentPage < totalPages);

		return result;
	}

	/**
	 * Gets a customer with a certain GUID.
	 * @param guid A customer GUID.
	 * @return A Customer.
	 */
	public Customer getCustomer(String guid) {
		HttpResponse<JsonNode> response;
		try {
			response = Unirest.get(EDOOLS_ECOMMERCE_API + CUSTOMERS + guid + "?" + PARAM_SCHOOL_GUID + "=" + schoolGuid)
					.header(HttpHeaders.AUTHORIZATION, "Token token=" + token)
					.header(HttpHeaders.ACCEPT, "application/vnd.edools.core.v1+json")
					.asJson();
		} catch (UnirestException e) {
			return null;
		}

		Gson gResponse = new Gson();
		return gResponse.fromJson(response.getBody().toString(), Customer.class);
	}
}
