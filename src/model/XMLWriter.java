package model;

import nu.xom.Element;

/**
 * Created by Vitor on 05/11/2015.
 */
public class XMLWriter {

	public static final String ENVIAR_LOTE_RPS_ENVIO = "EnviarLoteRpsEnvio";

	private XMLWriter() {}

	public static void generateXML(RPSBulk rpsBulk, String path) {

		Element enviarLoteRpsEnvio = new Element(ENVIAR_LOTE_RPS_ENVIO);

		//TODO: Implement the rest of the XML generator.

	}
}
