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

	private DateTime date = null;
	private long numeroLote = -1L;

	public Persistence(String persistenceFilePath) {

		persistenceFile = new File(persistenceFilePath);

	}

	public DateTime getDate() {
		return date;
	}

	public long getNumeroLote() {
		return numeroLote;
	}

	/**
	 * Fetches a date and batch number (NumeroLote) from the persistence file.
	 * @throws IOException If the file cannot be read.
	 */
	public void fetch() throws IOException {

		FileInputStream fis;
		try {
			fis = new FileInputStream(persistenceFile);
		} catch (FileNotFoundException e) {
			date = new DateTime(0);
			numeroLote = 1L;
			return;
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));

		String read = in.readLine().trim();
		date = ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC).parseDateTime(read);
		read = in.readLine();
		if(read == null) {
			numeroLote = 1L;
		}
		else {
			read = read.trim();
			numeroLote = Long.parseLong(read);
		}

		in.close();
	}

	/**
	 * This method will persist a date to the file and increase the batch number (NumeroLote).
	 * @param date New date to be persisted.
	 * @throws FileNotFoundException If the file is not found or cannot be created.
	 * @throws IOException If the file cannot be written to.
	 */
	public void persist(DateTime date) throws IOException {
		if(date == null || numeroLote < 1L) {
			fetch();
		}
		FileOutputStream fos;
		fos = new FileOutputStream(persistenceFile, false);

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

		out.write(date.toString() + "\n");
		numeroLote++;
		out.write(Long.toString(numeroLote));

		out.close();
	}

}
