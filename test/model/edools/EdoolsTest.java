package model.edools;

import model.ConfigFile;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

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
		Product product = edools.getProduct(7099);
		assertEquals(product.getTitle(), "OAB 2 Fase - Direito Processual Trabalhista");
	}
}
