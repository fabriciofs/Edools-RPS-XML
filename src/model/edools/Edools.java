package model.edools;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpHeaders;

/**
 * Created by Vitor on 05/11/2015.
 */
public class Edools {

	private static final String EDOOLS_API = "https://core.edools.com/";
	private static final String SCHOOL_PRODUCTS = "school_products/";
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

	//TODO: Implement Edools API methods.
}
