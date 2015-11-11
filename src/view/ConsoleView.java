package view;

import controller.Controller;

import java.util.Scanner;

/**
 * Created by Vitor on 11/11/2015.
 */
public class ConsoleView implements View {

	//Strings
	private static final String YES_TEXT = "yes";
	private static final String NO_TEXT = "no";
	private static final String WELCOME_STRING = "welcomeString";

	//Local strings
	private static final String OUTPUT_MARK = "> ";

	private final Controller controller;

	public ConsoleView(Controller controller) {
		this.controller = controller;
	}
	@Override
	public void dialog(String text) {
		System.out.println(OUTPUT_MARK + text);
	}

	@Override
	public void showMainView() {
		System.out.println(controller.getLabel(WELCOME_STRING));
	}

	@Override
	public boolean booleanInput(String text) {
		System.out.println(text);
		Scanner keyboard = new Scanner(System.in);
		while(true) {
			String input = keyboard.nextLine().toLowerCase().trim();
			if(input.startsWith(controller.getLabel(YES_TEXT))) {
				return true;
			}
			else if(input.startsWith(controller.getLabel(NO_TEXT))) {
				return false;
			}
		}
	}
}
