package model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

import java.io.*;

/**
 * Created by Vitor on 10/11/2015.
 */
public class Persistence {

	private File persistenceFile;

	public Persistence(String persistenceFilePath) {

		persistenceFile = new File(persistenceFilePath);

	}

	/**
	 * Fetches a date from the persistence file.
	 * @return A date.
	 * @throws FileNotFoundException If the file is not found.
	 * @throws IOException If the file cannot be read.
	 */
	public DateTime fetchDate() throws IOException {

		FileInputStream fis;
		fis = new FileInputStream(persistenceFile);

		BufferedReader in = new BufferedReader(new InputStreamReader(fis));

		String read = in.readLine().trim();

		in.close();

		return ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC).parseDateTime(read);
	}

	/**
	 * Persists a date to the persistence file.
	 * @throws FileNotFoundException If the file is not found or cannot be created.
	 * @throws IOException If the file cannot be written to.
	 */
	public void persistDate(DateTime date) throws IOException {

		FileOutputStream fos;
		fos = new FileOutputStream(persistenceFile, false);

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

		out.write(date.toString());

		out.close();

	}

}
