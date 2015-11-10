package view;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by Vitor on 05/11/2015.
 */
public interface View extends ActionListener {

	/**
	 * Displays a simple dialog with text and an OK button.
	 * @param text Text to be displayed in the dialog.
	 */
	public void dialog(String text);

	/**
	 * Displays the main view.
	 * @param timer Timer that will check for new payments.
	 */
	public void showMainView(Timer timer);
}
