package controller;

import model.*;
import model.edools.Customer;
import model.edools.Edools;
import model.edools.Item;
import model.edools.Payment;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import view.ConsoleView;
import view.TaskbarView;
import view.View;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.*;
import java.util.List;

/**
 * Created by Vitor on 05/11/2015.
 */
public class Controller {

	//Resource bundles
	private static final String RESOURCE_BUNDLE_NAME = "strings";
	private static final String LANGUAGE_CODE = "pt";
	private static final String COUNTRY_CODE = "BR";

	//File paths
	private static final String CONFIG_FILE_PATH = "config.properties";
	private static final String PERSISTENCE_FILE_PATH = "persistence.txt";
	private static final String ICON_FILE_PATH = "resources/icon.png";
	private static final String ICON_FILE_PATH_JAR = "/icon.png";

	//Config properties
	private static final String PROPERTY_EDOOLS_TOKEN = "edoolsToken";
	private static final String PROPERTY_SCHOOL_GUID = "schoolGuid";
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
	private static final String CONFIG_DEDUCOES = "deducoes";
	private static final String CONFIG_PIS = "pis";
	private static final String CONFIG_COFINS = "cofins";
	private static final String CONFIG_INSS = "inss";
	private static final String CONFIG_IR = "ir";
	private static final String CONFIG_CSLL = "csll";
	private static final String CONFIG_ISS_RETIDO = "issRetido";
	private static final String CONFIG_ISS = "iss";
	private static final String CONFIG_OUTRAS_RETENCOES = "outrasRetencoes";
	private static final String CONFIG_ALIQUOTA = "aliquota";
	private static final String CONFIG_DESCONTO_INCONDICIONADO = "descontoIncondicionado";
	private static final String CONFIG_DESCONTO_CONDICIONADO = "descontoCondicionado";
	private static final String CONFIG_CODIGO_TRIBUTACAO_MUNICIPIO = "codigoTributacaoMunicipio";
	private static final String CONFIG_SERVICO_CODIGO_MUNICIPIO = "servicoCodigoMunicipio";

	//Strings
	private static final String ERROR_TITLE = "errorTitle";
	private static final String CONFIG_FILE_NOT_FOUND = "configFileNotFound";
	private static final String CONFIG_READ_FAILURE = "configReadFailure";
	private static final String CHECK_INTERVAL_NOT_FOUND = "checkIntervalNotFound";
	private static final String PERSISTENCE_FILE_COULD_NOT_READ = "persistenceFileCouldNotRead";
	private static final String PERSISTENCE_FILE_COULD_NOT_WRITE = "persistenceFileCouldNotWrite";
	private static final String ALERT_TITLE = "alertTitle";
	private static final String CHECKING_NEW_PAYMENTS = "checkingNewPayments";
	private static final String NEW_PAYMENTS_FOUND = "newPaymentsFound";
	private static final String QUESTION_TITLE = "questionTitle";
	private static final String SHOULD_GENERATE_XML = "shouldGenerateXml";
	private static final String XML_FILE_COULD_NOT_WRITE = "xmlFileCouldNotWrite";
	private static final String WELCOME_STRING = "welcomeString";
	private static final String YES_CHAR = "yesChar";
	private static final String NO_CHAR = "noChar";
	private static final String YES = "yes";
	private static final String NO = "no";
	private static final String ABOUT_TEXT = "aboutText";
	private static final String ABOUT = "about";
	private static final String VERIFY_NOW = "verifyNow";
	private static final String VERIFY = "verify";
	private static final String EXIT = "exit";

	//Patterns
	private static final String EDOOLS_API_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String RPSBULK_ID_DATE_PATTERN = "HHmmssddMMyyyy";
	private static final String XML_FILE_NAME_DATE_PATTERN = "dd-MM-yyyy-HH-mm-ss";
	private static final String XML_FILE_EXTENSION = ".xml";

	//Constants
	private static final long MILLI_TO_SECONDS_MULTIPLIER = 1000;

	private boolean isJar;
	private ResourceBundle labels;
	private ConfigFile configFile;
	private View view;

	private Persistence persistence;
	private Timer timer;

	private String baseDir = "./";
	private boolean timerEnabled = true;

	public void start() {
		String iconPath;
		URL resource = Main.class.getResource("Main.class");
		if(resource.toString().startsWith("jar")) {
			CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
			File jarFile = null;
			try {
				jarFile = new File(codeSource.getLocation().toURI().getPath());
			} catch (URISyntaxException e) {
				System.exit(1);
			}
			baseDir = jarFile.getParentFile().getPath() + "/";
			iconPath = ICON_FILE_PATH_JAR;
		}
		else {
			baseDir = "./";
			iconPath = ICON_FILE_PATH;
		}
		labels = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, new Locale(LANGUAGE_CODE, COUNTRY_CODE));

		//Check the SystemTray is supported
		if (SystemTray.isSupported()) {
			view = new TaskbarView(this, labels.getString(WELCOME_STRING), labels.getString(YES), labels.getString(NO), labels.getString(ABOUT_TEXT), iconPath, labels.getString(ABOUT), labels.getString(VERIFY_NOW), labels.getString(VERIFY), labels.getString(EXIT));
		}
		else {
			view = new ConsoleView(labels.getString(WELCOME_STRING), labels.getString(YES), labels.getString(NO), labels.getString(YES_CHAR), labels.getString(NO_CHAR));
		}

		try {
			configFile = new ConfigFile(baseDir + CONFIG_FILE_PATH);
		} catch (FileNotFoundException e) {
			view.dialog(labels.getString(ERROR_TITLE), labels.getString(CONFIG_FILE_NOT_FOUND));
			ConfigFile.createDefaultConfigFile(baseDir + CONFIG_FILE_PATH);
			return;
		} catch (IOException e) {
			view.dialog(labels.getString(ERROR_TITLE), labels.getString(CONFIG_READ_FAILURE));
			return;
		}

		if(configFile.getProperty(CONFIG_CHECK_INTERVAL) == null) {
			view.dialog(labels.getString(ERROR_TITLE), labels.getString(CHECK_INTERVAL_NOT_FOUND));
			return;
		}

		persistence = new Persistence(PERSISTENCE_FILE_PATH);

		view.showMainView();
		startTimer(0);

	}

	class checkPaymentsTask extends TimerTask {

		@Override
		public void run() {
			view.dialog(labels.getString(ALERT_TITLE), labels.getString(CHECKING_NEW_PAYMENTS));
			checkPayments();
		}
	}

	public void checkPayments() {
		if(checkNewPayments()) {
			stopTimer();

			if(view.booleanInput(labels.getString(QUESTION_TITLE), labels.getString(NEW_PAYMENTS_FOUND) + " " + labels.getString(SHOULD_GENERATE_XML))) {
				generateXML();
			}
			else {
				startTimer(Long.parseLong(configFile.getProperty(CONFIG_CHECK_INTERVAL)) * MILLI_TO_SECONDS_MULTIPLIER);
			}
		}
	}

	/**
	 * Enables or disables the timer.
	 * @param enabled True enables the timer, false disables.
	 */
	public void setTimer(boolean enabled) {
		if(enabled) {
			timerEnabled = true;
			if(!view.isViewWaiting()) {
				startTimer(Long.parseLong(configFile.getProperty(CONFIG_CHECK_INTERVAL)) * MILLI_TO_SECONDS_MULTIPLIER);
			}
		}
		else {
			timerEnabled = false;
			stopTimer();
		}
	}

	public void startTimer(long delay) {
		if(timerEnabled) {
			if (timer != null) {
				timer.cancel();
			}
			timer = new Timer(false);
			timer.scheduleAtFixedRate(new checkPaymentsTask(), delay, Long.parseLong(configFile.getProperty(CONFIG_CHECK_INTERVAL)) * MILLI_TO_SECONDS_MULTIPLIER);
		}
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
			view.dialog(labels.getString(ERROR_TITLE), labels.getString(PERSISTENCE_FILE_COULD_NOT_READ));
			startTimer(Long.parseLong(configFile.getProperty(CONFIG_CHECK_INTERVAL)) * MILLI_TO_SECONDS_MULTIPLIER);
			return null;
		}
		return dateTime;
	}

	public boolean checkNewPayments() {

		DateTime dateTime = fetchDateFromPersistence();
		if(dateTime == null) {
			return false;
		}

		Edools edools = new Edools(configFile.getProperty(PROPERTY_EDOOLS_TOKEN), configFile.getProperty(PROPERTY_SCHOOL_GUID));
		DateTimeFormatter dtf = DateTimeFormat.forPattern(EDOOLS_API_DATE_PATTERN);
		return edools.checkPayments(dateTime.toString(dtf), configFile.getProperty(PROPERTY_EDOOLS_STATUS));

	}

	public void generateXML() {

		DateTime dateTime = fetchDateFromPersistence();
		if(dateTime == null) {
			return;
		}

		Edools edools = new Edools(configFile.getProperty(PROPERTY_EDOOLS_TOKEN), configFile.getProperty(PROPERTY_SCHOOL_GUID));
		DateTimeFormatter dtf = DateTimeFormat.forPattern(EDOOLS_API_DATE_PATTERN);
		List<Payment> payments = edools.getPayments(dateTime.toString(dtf), configFile.getProperty(PROPERTY_EDOOLS_STATUS));

		List<RPS> rpsList = new ArrayList<RPS>();

		int j = 0;
		for(Payment payment : payments) {
			Item[] items = payment.order.items;
			for(int i=0; i < items.length; i++) {
				j++;
				Item item = items[i];

				RPS rps = new RPS();

				rps.setId("rps" + j + "serie" + configFile.getProperty(CONFIG_SERIE) + configFile.getProperty(CONFIG_TIPO));
				rps.setNumero(Integer.toString(j));
				rps.setSerie(configFile.getProperty(CONFIG_SERIE));
				rps.setTipo(configFile.getProperty(CONFIG_TIPO));
				rps.setDataEmissao(new DateTime(payment.updated_at).toString(dtf));
				rps.setNaturezaOperacao(configFile.getProperty(CONFIG_NATUREZA_OPERACAO));
				rps.setOptanteSimplesNacional(configFile.getProperty(CONFIG_OPTANTE_SIMPLES_NACIONAL));
				rps.setIncentivadorCultural(configFile.getProperty(CONFIG_INCENTIVADOR_CULTURAL));
				rps.setStatus(configFile.getProperty(CONFIG_STATUS));
				rps.setValorServicos(getValorString((double)item.amount_to_pay));
				rps.setValorDeducoes(getValorString(Double.parseDouble(configFile.getProperty(CONFIG_DEDUCOES))*item.amount_to_pay));
				rps.setValorPis(getValorString(Double.parseDouble(configFile.getProperty(CONFIG_PIS))*item.amount_to_pay));
				rps.setValorCofins(getValorString(Double.parseDouble(configFile.getProperty(CONFIG_COFINS))*item.amount_to_pay));
				rps.setValorInss(getValorString(Double.parseDouble(configFile.getProperty(CONFIG_INSS))*item.amount_to_pay));
				rps.setValorIr(getValorString(Double.parseDouble(configFile.getProperty(CONFIG_IR))*item.amount_to_pay));
				rps.setValorCsll(getValorString(Double.parseDouble(configFile.getProperty(CONFIG_CSLL))*item.amount_to_pay));
				rps.setIssRetido(configFile.getProperty(CONFIG_ISS_RETIDO));
				rps.setValorIss(getValorString(Double.parseDouble(configFile.getProperty(CONFIG_ISS))*item.amount_to_pay));
				rps.setOutrasRetencoes(getValorString(Double.parseDouble(configFile.getProperty(CONFIG_OUTRAS_RETENCOES))*item.amount_to_pay));
				rps.setAliquota(getValorString(Double.parseDouble(configFile.getProperty(CONFIG_ALIQUOTA))*item.amount_to_pay));
				rps.setDescontoIncondicionado(getValorString(Double.parseDouble(configFile.getProperty(CONFIG_DESCONTO_INCONDICIONADO))*item.amount_to_pay));
				rps.setDescontoCondicionado(getValorString(Double.parseDouble(configFile.getProperty(CONFIG_DESCONTO_CONDICIONADO))*item.amount_to_pay));
				rps.setItemListaServico(Long.toString(item.id));
				rps.setCodigoTributacaoMunicipio(configFile.getProperty(CONFIG_CODIGO_TRIBUTACAO_MUNICIPIO));
				rps.setDiscriminacao(item.product.name);
				rps.setServicos_codigoMunicipio(configFile.getProperty(CONFIG_SERVICO_CODIGO_MUNICIPIO));
				rps.setPrestador_cnpj(configFile.getProperty(CONFIG_CNPJ));
				rps.setInscricaoMunicipal(configFile.getProperty(CONFIG_INSCRICAO_MUNICIPAL));
				Customer customer = edools.getCustomer(payment.customer.guid);
				rps.setTomador_cpf(customer.cpf);
				rps.setRazaoSocial(payment.customer.first_name + " " + payment.customer.last_name);
				rps.setEndereco("");
				rps.setEndereco_numero("");
				rps.setComplemento("");
				rps.setBairro("");
				rps.setEndereco_codigoMunicipio("");
				rps.setUf("");
				rps.setCep("");
				rps.setEmail(payment.customer.email);

				rpsList.add(rps);
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

		try {
			XMLWriter.generateXML(rpsBulk, baseDir + dateTime.withZone(DateTimeZone.UTC).toString(XML_FILE_NAME_DATE_PATTERN) + XML_FILE_EXTENSION);
			try {
				persistence.persist(currentDate);
			} catch (IOException e) {
				view.dialog(labels.getString(ERROR_TITLE), labels.getString(PERSISTENCE_FILE_COULD_NOT_WRITE));
			}
		} catch (IOException e) {
			view.dialog(labels.getString(ERROR_TITLE), labels.getString(XML_FILE_COULD_NOT_WRITE));
		}

		startTimer(Long.parseLong(configFile.getProperty(CONFIG_CHECK_INTERVAL)) * MILLI_TO_SECONDS_MULTIPLIER);

	}

	public void quit() {
		stopTimer();
		System.exit(0);
	}

	/**
	 * Converts a monetary value string to the format used in the RPS file.
	 * @param valor Original monetary value string.
	 * @return RPS format monetary value string.
	 */
	private String getValorString(Double valor) {
		return String.format(Locale.ENGLISH, "%.2f", valor);
	}
}
