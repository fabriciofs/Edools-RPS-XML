package view;

/**
 * Created by Vitor on 05/11/2015.
 */
public interface View {

	/**
	 * Displays a simple dialog with text and an OK button.
	 * @param text Text to be displayed in the dialog.
	 */
	public void dialog(String text);

	/**
	 * Displays the main view.
	 */
	public void showMainView();
}
