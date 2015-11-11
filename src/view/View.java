package view;

/**
 * Created by Vitor on 05/11/2015.
 */
public interface View {

	/**
	 * Displays a simple dialog with text and an OK button.
	 * @param text Text to be displayed in the dialog.
	 */
	void dialog(String text);

	/**
	 * Displays the main view.
	 */
	void showMainView();

	/**
	 * A boolean input to the user.
	 * @param text Message to be displayed to the user.
	 * @return A boolean result.
	 */
	boolean booleanInput(String text);
}
