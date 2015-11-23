package controller;

import model.*;
import model.edools.Edools;
import model.edools.Item;
import model.edools.Payment;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import view.ConsoleView;
import view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Vitor on 05/11/2015.
 */
public class Controller {

	//Resource bundles
	private static final String RESOURCE_BUNDLE_NAME = "strings";
	private static final String LANGUAGE_CODE = "pt";
	private static final String COUNTRY_CODE = "BR";

	//File paths
	private static final String CONFIG_FILE_PATH = "./config.properties";
	private static final String PERSISTENCE_FILE_PATH = "./persistence.txt";
	private static final String XML_FILE_PATH = "./";

	//Config properties
	private static final String PROPERTY_EDOOLS_TOKEN = "edoolsToken";
	private static final String PROPERTY_EDOOLS_STATUS = "edoolsStatus";
	private static final String CONFIG_CHECK_INTERVAL = "checkInterval";
	private static final String CONFIG_CNPJ = "cnpj";
	private static final String CONFIG_INSCRICAO_MUNICIPAL= "inscricaoMunicipal";
	private static final String CONFIG_SERIE = "serie";
	private static final String CONFIG_TIPO = "tipo";
	private static final String CONFIG_NATUREZA_OPERACAO = "naturezaOperacao";
	private static final String CONFIG_OPTANTE_SIMPLES_NACIONAL = "optanteSimplesNacional";
	private static final String CONFIG_INCENTIVADOR_CULTURAL = "incentivadorCultural";
	private static final String CONFIG_STATUS = "status";

	//Strings
	private static final String CONFIG_FILE_NOT_FOUND = "configFileNotFound";
	private static final String CONFIG_READ_FAILURE = "configReadFailure";
	private static final String CHECK_INTERVAL_NOT_FOUND = "checkIntervalNotFound";
	private static final String PERSISTENCE_FILE_COULD_NOT_READ = "persistenceFileCouldNotRead";
	private static final String PERSISTENCE_FILE_COULD_NOT_WRITE = "persistenceFileCouldNotWrite";
	private static final String CHECKING_NEW_PAYMENTS = "checkingNewPayments";
	private static final String NEW_PAYMENTS_FOUND = "newPaymentsFound";
	private static final String SHOULD_GENERATE_XML = "shouldGenerateXml";

	//Patterns
	private static final String EDOOLS_API_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String RPSBULK_ID_DATE_PATTERN = "HHmmssddMMyyyy";
	private static final String XML_FILE_NAME_DATE_PATTERN = "dd-MM-yyyy-HH-mm-ss";

	//Constants
	private static final long MILLI_TO_SECONDS_MULTIPLIER = 1000;

	private ResourceBundle labels;
	private ConfigFile configFile;
	private View view;

	private Persistence persistence;
	private Timer timer;

	public String getLabel(String key) {
		return labels.getString(key);
	}

	public void start() {
		labels = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, new Locale(LANGUAGE_CODE, COUNTRY_CODE));

		view = new ConsoleView(this);

		try {
			configFile = new ConfigFile(CONFIG_FILE_PATH);
		} catch (FileNotFoundException e) {
			view.dialog(labels.getString(CONFIG_FILE_NOT_FOUND));
			ConfigFile.createDefaultConfigFile(CONFIG_FILE_PATH);
			return;
		} catch (IOException e) {
			view.dialog(labels.getString(CONFIG_READ_FAILURE));
			return;
		}

		if(configFile.getProperty(CONFIG_CHECK_INTERVAL) == null) {
			view.dialog(labels.getString(CHECK_INTERVAL_NOT_FOUND));
			return;
		}

		persistence = new Persistence(PERSISTENCE_FILE_PATH);

		view.showMainView();
		startTimer();

	}

	class checkPaymentsTask extends TimerTask {

		@Override
		public void run() {
			view.dialog(labels.getString(CHECKING_NEW_PAYMENTS));
			if(checkNewPayments()) {
				stopTimer();

				view.dialog(labels.getString(NEW_PAYMENTS_FOUND));
				if(view.booleanInput(labels.getString(SHOULD_GENERATE_XML))) {
					generateXML();
				}
			}
		}
	}

	public void startTimer() {

		if(timer != null) {
			timer.cancel();
		}
		timer = new Timer(false);
		timer.scheduleAtFixedRate(new checkPaymentsTask(), 0, Long.parseLong(configFile.getProperty(CONFIG_CHECK_INTERVAL)) * MILLI_TO_SECONDS_MULTIPLIER);

	}

	public void stopTimer() {

		timer.cancel();

	}

	private DateTime fetchDateFromPersistence() {
		DateTime dateTime;
		try {
			persistence.fetch();
			dateTime = persistence.getDate();
		} catch (IOException e) {
			stopTimer();
			view.dialog(labels.getString(PERSISTENCE_FILE_COULD_NOT_READ));
			startTimer();
			return null;
		}
		return dateTime;
	}

	public boolean checkNewPayments() {

		DateTime dateTime = fetchDateFromPersistence();
		if(dateTime == null) {
			return false;
		}

		Edools edools = new Edools(configFile.getProperty(PROPERTY_EDOOLS_TOKEN));
		DateTimeFormatter dtf = DateTimeFormat.forPattern(EDOOLS_API_DATE_PATTERN);
		return !edools.getPayments(dateTime.toString(dtf), configFile.getProperty(PROPERTY_EDOOLS_STATUS)).isEmpty();

	}

	public void generateXML() {

		DateTime dateTime = fetchDateFromPersistence();
		if(dateTime == null) {
			return;
		}

		Edools edools = new Edools(configFile.getProperty(PROPERTY_EDOOLS_TOKEN));
		DateTimeFormatter dtf = DateTimeFormat.forPattern(EDOOLS_API_DATE_PATTERN);
		List<Payment> payments = edools.getPayments(dateTime.toString(dtf), configFile.getProperty(PROPERTY_EDOOLS_STATUS));

		List<RPS> rpsList = new ArrayList<RPS>();

		for(Payment payment : payments) {

			Item[] items = payment.order.items;
			for(int i=0; i < items.length; i++) {
				Item item = items[i];

				RPS rps = new RPS();

				rps.setId("rps" + i+1 + "serie" + configFile.getProperty(CONFIG_SERIE) + configFile.getProperty(CONFIG_TIPO));
				rps.setNumero(Integer.toString(i+1));
				rps.setSerie(configFile.getProperty(CONFIG_SERIE));
				rps.setTipo(configFile.getProperty(CONFIG_TIPO));
				rps.setDataEmissao(new DateTime(payment.updated_at).toString(dtf));
				rps.setNaturezaOperacao(configFile.getProperty(CONFIG_NATUREZA_OPERACAO));
				rps.setOptanteSimplesNacional(configFile.getProperty(CONFIG_OPTANTE_SIMPLES_NACIONAL));
				rps.setIncentivadorCultural(configFile.getProperty(CONFIG_INCENTIVADOR_CULTURAL));
				rps.setStatus(configFile.getProperty(CONFIG_STATUS));

				rps.setItemListaServico(Long.toString(item.id));

				rps.setDiscriminacao(item.product.description);

				rps.setRazaoSocial(payment.customer.first_name + payment.customer.last_name);

				rps.setEmail(payment.customer.email);

				//TODO: Populate the remaining RPS values.

			}
		}

		DateTime currentDate = new DateTime(DateTimeZone.UTC);

		RPSBulk rpsBulk = new RPSBulk();

		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(RPSBULK_ID_DATE_PATTERN);
		rpsBulk.setId(currentDate.toString(dateTimeFormatter));
		rpsBulk.setNumeroLote(Long.toString(persistence.getNumeroLote()));
		rpsBulk.setCnpj(configFile.getProperty(CONFIG_CNPJ));
		rpsBulk.setInscricaoMunicipal(configFile.getProperty(CONFIG_INSCRICAO_MUNICIPAL));
		rpsBulk.setListaRps(rpsList);

		XMLWriter.generateXML(rpsBulk, XML_FILE_PATH + dateTime.withZone(DateTimeZone.UTC).toString(XML_FILE_NAME_DATE_PATTERN));

		try {
			persistence.persist(currentDate);
		} catch (IOException e) {
			view.dialog(labels.getString(PERSISTENCE_FILE_COULD_NOT_WRITE));
		}
		startTimer();

	}
}
