package view;

import javax.swing.*;

/**
 * Created by Vitor on 05/11/2015.
 */
public class TaskbarView implements View {

	private final String yesText;
	private final String noText;

	private JFrame frame; //TODO: Initialize this frame.

	public TaskbarView(String yesText, String noText) {
		this.yesText = yesText;
		this.noText = noText;
	}

	@Override
	public void dialog(String title, String text) {
		Object[] options = {
			yesText,
			noText
		};
		int n = JOptionPane.showOptionDialog(
				frame,
				text,
				title,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
	}

	@Override
	public void showMainView() {

		//TODO: Implement main view.

	}

	@Override
	public boolean booleanInput(String text) {

		//TODO: Implement boolean input dialog.

		return false;
	}
}
