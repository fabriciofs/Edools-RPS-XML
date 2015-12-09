package view;

import java.util.Scanner;

/**
 * Created by Vitor on 11/11/2015.
 */
public class ConsoleView implements View {

	//Local strings
	private static final String OUTPUT_MARK = "> ";

	private final String welcome;
	private final String yesText;
	private final String noText;
	private final String noChar;
	private final String yesChar;

	public ConsoleView(String welcome, String yesText, String noText, String yesChar, String noChar) {
		this.welcome = welcome;
		this.yesText = yesText;
		this.noText = noText;
		this.yesChar = yesChar.toLowerCase();
		this.noChar = noChar.toLowerCase();
	}
	@Override
	public void dialog(String title, String text) {
		System.out.println(OUTPUT_MARK + "[" + title + "] " + text);
	}

	@Override
	public void okDialog(String title, String text) {
		System.out.println(OUTPUT_MARK + "[" + title + "] " + text);
	}

	@Override
	public void showMainView() {
		System.out.println(welcome);
	}

	@Override
	public boolean booleanInput(String title, String text) {
		System.out.println(OUTPUT_MARK + "[" + title + "] " + text + " [" + yesText + "/" + noText + "]");
		Scanner keyboard = new Scanner(System.in);
		while(true) {
			String input = keyboard.nextLine().toLowerCase().trim();
			if(input.startsWith(yesChar)) {
				return true;
			}
			else if(input.startsWith(noChar)) {
				return false;
			}
		}
	}

	@Override
	public boolean isViewWaiting() {
		return false;
	}
}
