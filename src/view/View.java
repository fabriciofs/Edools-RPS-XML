package view;

/**
 * Created by Vitor on 05/11/2015.
 */
public interface View {

	/**
	 * Displays a simple dialog with text and an OK button.
	 * @param title Title of the dialog.
	 * @param text Text to be displayed in the dialog.
	 */
	void dialog(String title, String text);

	/**
	 * Displays the main view.
	 */
	void showMainView();

	/**
	 * A boolean input to the user.
	 * @param title Title of the dialog.
	 * @param text Text to be displayed in the dialog.
	 * @return A boolean result.
	 */
	boolean booleanInput(String title, String text);
}
