package controller;

import model.ConfigFile;
import view.TaskbarView;
import view.View;

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

	private ConfigFile configFile;
	private View view;

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

		 view.showMainView();

	}

	//TODO: Create Timer. It will check for new payments, and alert the view of this.

	public void startTimer() {

		//TODO: Method to start the timer. To be used by the view, after generating an XML.

	}

	public void stopTimer() {

		//TODO: Method to stop the timer. To be used by the view, after receiving an alert of new payments.

	}

	public boolean checkNewPayments() {

		//TODO: Implement a check for new payments from Edools.

		return false;

	}

	public void generateXML() {

		//TODO: Implement the payment fetching and XML generation.

	}
}
