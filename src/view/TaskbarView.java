package view;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Vitor on 05/11/2015.
 */
public class TaskbarView implements View, ActionListener {

	private final Controller controller;

	private final String yesText;
	private final String noText;

	private final String about;
	private final String verifyNow;
	private final String verify;
	private final String exit;

	private final PopupMenu popup;
	private final TrayIcon trayIcon;
	private final SystemTray tray;

	private MenuItem aboutItem;
	private MenuItem verifyItem;
	private CheckboxMenuItem verifyCheckBox;
	private MenuItem exitItem;

	public TaskbarView(Controller controller, String tooltipTitle, String yesText, String noText, String iconFilePath, String about, String verifyNow, String verify, String exit) {
		this.controller = controller;
		this.yesText = yesText;
		this.noText = noText;
		this.about = about;
		this.verifyNow = verifyNow;
		this.verify = verify;
		this.exit = exit;

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}

		popup = new PopupMenu();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(iconFilePath));
		} catch (IOException e) {
			System.exit(1);
		}
		trayIcon = new TrayIcon(img, tooltipTitle);
		trayIcon.setImageAutoSize(true);
		tray = SystemTray.getSystemTray();
	}

	@Override
	public void dialog(String title, String text) {
		if(trayIcon != null) {
			trayIcon.displayMessage(title, text, TrayIcon.MessageType.INFO);
		}
	}

	@Override
	public void showMainView() {

		// Create a pop-up menu components
		verifyItem = new MenuItem(verifyNow);
		verifyCheckBox = new CheckboxMenuItem(verify);
		verifyCheckBox.setState(true);
		aboutItem = new MenuItem(about);
		exitItem = new MenuItem(exit);

		//Add listeners to components
		verifyItem.addActionListener(this);
		verifyCheckBox.addActionListener(this);
		aboutItem.addActionListener(this);
		exitItem.addActionListener(this);

		//Add components to pop-up menu
		popup.add(verifyItem);
		popup.add(verifyCheckBox);
		popup.addSeparator();
		popup.add(aboutItem);
		popup.addSeparator();
		popup.add(exitItem);

		trayIcon.setPopupMenu(popup);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.exit(1);
		}
	}

	@Override
	public boolean booleanInput(String title, String text) {
		Object[] options = {
				yesText,
				noText
		};
		int n = JOptionPane.showOptionDialog(
				null, //do not use a frame
				text,
				title,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null, //do not use a custom Icon
				options, //the titles of buttons
				options[0]); //default button title

		if(n == JOptionPane.YES_OPTION) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == verifyItem) {
			//TODO: Implement verify button.
		}
		else if(e.getSource() == verifyCheckBox) {
			//TODO: Implement verify checkbox.
		}
		else if(e.getSource() == aboutItem) {
			//TODO: Implement about screen.
		}
		else if(e.getSource() == exitItem) {
			tray.remove(trayIcon);
			controller.quit();
		}
	}
}
