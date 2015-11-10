package controller;

import model.ConfigFile;
import view.TaskbarView;
import view.View;

import javax.swing.Timer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Vitor on 05/11/2015.
 */
public class Controller {

	public static final String RESOURCE_BUNDLE_NAME = "strings";
	public static final String CONFIG_FILE_PATH = "./config.properties";
	public static final String CONFIG_FILE_NOT_FOUND = "configFileNotFound";
	public static final String CONFIG_READ_FAILURE = "configReadFailure";
	public static final String CONFIG_CHECK_INTERVAL = "checkInterval";
	public static final String CHECK_INTERVAL_NOT_FOUND = "checkIntervalNotFound";

	private ConfigFile configFile;
	private View view;

	private Timer timer;

	public void start() {
		ResourceBundle labels = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);

		view = new TaskbarView(this);

		try {
			configFile = new ConfigFile(CONFIG_FILE_PATH);
		} catch (FileNotFoundException e) {
			view.dialog(labels.getString(CONFIG_FILE_NOT_FOUND));
			//TODO: Create default config.properties file.
			return;
		} catch (IOException e) {
			view.dialog(labels.getString(CONFIG_READ_FAILURE));
			return;
		}

		if(configFile.getProperty(CONFIG_CHECK_INTERVAL) == null) {
			view.dialog(labels.getString(CHECK_INTERVAL_NOT_FOUND));
			return;
		}
		timer = new Timer(Integer.parseInt(configFile.getProperty(CONFIG_CHECK_INTERVAL)), view);

		view.showMainView(timer);

	}

	public void startTimer() {

		timer.start();

	}

	public void stopTimer() {

		timer.stop();

	}

	public boolean checkNewPayments() {

		//TODO: Implement a check for new payments from Edools.

		return false;

	}

	public void generateXML() {

		//TODO: Implement the payment fetching and XML generation.

	}
}
