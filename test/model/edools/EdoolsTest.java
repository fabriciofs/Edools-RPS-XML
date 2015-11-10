package model.edools;

import model.ConfigFile;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by Vitor on 05/11/2015.
 */
public class EdoolsTest {

	public static final String CONFIG_FILE_PATH = "./config.properties";

	private ConfigFile configFile;

	@BeforeTest
	public void getConfig() {
		try {
			configFile = new ConfigFile(CONFIG_FILE_PATH);
		} catch (IOException e) {
			fail("Failed to load config.properties file.");
		}
	}

	@Test
	public void singleProductTest() {
		Edools edools = new Edools(configFile.getProperty("edoolsToken"));
		Product product = edools.getProduct(7099L);
		assertEquals(product.getTitle(), "OAB 2 Fase - Direito Processual Trabalhista");

		String expectedDate = "2015-11-05T13:31:10.574Z";
		assertEquals(
				ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC).parseDateTime(product.getCreated_at()),
				ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC).parseDateTime(expectedDate)
		);
	}

	@Test
	public void paymentsTest() {
		Edools edools = new Edools(configFile.getProperty("edoolsToken"));
		List<Payment> payments = edools.getPayments("2015-11-04T22:47:10", null);
		assertFalse(payments.isEmpty());
	}
}
