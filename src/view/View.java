package view;

/**
 * Created by Vitor on 05/11/2015.
 */
public interface View {

	/**
	 * Displays a simple dialog with text.
	 * @param title Title of the dialog.
	 * @param text Text to be displayed in the dialog.
	 */
	void dialog(String title, String text);

	/**
	 * Displays a simple dialog with text and an OK button.
	 * @param title Title of the dialog.
	 * @param text Text to be displayed in the dialog.
	 */
	void okDialog(String title, String text);

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

	/**
	 * Returns whether the view is waiting for user input or not.
	 * @return True if the view is waiting, false otherwise.
	 */
	boolean isViewWaiting();
}
