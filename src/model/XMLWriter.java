package model;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

import java.io.*;

/**
 * Created by Vitor on 05/11/2015.
 */
public class XMLWriter {

	public static final String ENVIAR_LOTE_RPS_ENVIO = "EnviarLoteRpsEnvio";
	public static final String LOTE_RPS = "LoteRps";

	private XMLWriter() {}

	public static void generateXML(RPSBulk rpsBulk, String path) throws IOException {

		Element enviarLoteRpsEnvio = new Element(ENVIAR_LOTE_RPS_ENVIO);

		Element loteRps = new Element(LOTE_RPS);

		//TODO: Implement the rest of the XML generator.

		enviarLoteRpsEnvio.appendChild(loteRps);

		FileOutputStream fos = new FileOutputStream(path, false);
		Document doc = new Document(enviarLoteRpsEnvio);
		Serializer serializer = new Serializer(fos, "ISO-8859-1");
		serializer.setIndent(4);
		serializer.setMaxLength(64);
		serializer.write(doc);
	}
}
