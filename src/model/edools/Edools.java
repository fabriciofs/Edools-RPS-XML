package model.edools;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpHeaders;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Vitor on 05/11/2015.
 */
public class Edools {

	//EDOOLS API
	private static final String EDOOLS_API = "https://core.edools.com/";
	private static final String AUTHORIZATION_STRING = "Token token=";
	private static final String ACCEPT_STRING = "application/vnd.edools.core.v1+json";

	//SCHOOL_PRODUCTS
	private static final String SCHOOL_PRODUCTS = "school_products/";

	//PAYMENTS
	private static final String PAYMENTS = "payments/";
	private static final String PARAM_START_DATE = "start_date";
	private static final String RESULT_PAYMENTS = "payments";

	private String token;

	public Edools(String token) {
		this.token = token;
	}

	public Product getProduct(int id) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.get(EDOOLS_API + SCHOOL_PRODUCTS + id)
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
	 * Gets payments after a certain start date.
	 * @param startDate A date in the ISO8601 format without timezone. E.g.: 2015-11-04T22:47:10.
	 * @return A List of Payments.
	 */
	public List<Payment> getPayments(String startDate) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.get(EDOOLS_API + PAYMENTS + "?" + PARAM_START_DATE + "=" + startDate)
					.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_STRING + token)
					.header(HttpHeaders.ACCEPT, ACCEPT_STRING)
					.asJson();
		} catch (UnirestException e) {
			return null;
		}

		JSONObject responseJSON = new JSONObject(response.getBody().toString());
		JSONArray payments = responseJSON.getJSONArray(RESULT_PAYMENTS);

		Gson gResponse = new Gson();
		return Arrays.asList(gResponse.fromJson(payments.toString(), Payment[].class));
	}

	//TODO: Implement all Edools API methods.
}
