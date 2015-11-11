package view;

import controller.Controller;

import java.awt.event.ActionEvent;
import java.util.Scanner;

/**
 * Created by Vitor on 11/11/2015.
 */
public class ConsoleView implements View {

	private final Controller controller;
	private static final String ALERT_TEXT = "ALERTA: ";
	private static final String NEW_PAYMENTS_FOUND_TEXT = "Novos pagamentos encontrados.";
	private static final String YES_TEXT = "sim";

	public ConsoleView(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void dialog(String text) {
		System.out.println(ALERT_TEXT + text);
	}

	@Override
	public void showMainView() {
		controller.startTimer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(controller.checkNewPayments()) {
			controller.stopTimer();
			Scanner keyboard = new Scanner(System.in);
			System.out.println(NEW_PAYMENTS_FOUND_TEXT);
			String input = keyboard.nextLine().toLowerCase();
			if(input.contains(YES_TEXT)) {
				controller.generateXML();
				controller.startTimer();
			}
			else {
				controller.startTimer();
			}
		}
	}
}
