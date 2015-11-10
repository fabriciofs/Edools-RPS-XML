package model;

import org.joda.time.DateTime;

import java.io.File;

/**
 * Created by Vitor on 10/11/2015.
 */
public class Persistence {

	private File persistenceFile;

	public Persistence(String persistenceFilePath) {

		persistenceFile = new File(persistenceFilePath);

	}

	public DateTime fetchDate() {

		//TODO: Implement date fetching from file.

		return null;
	}

	public void persistDate(DateTime date) {

		//TODO: Implement date writing to file.

	}

}
