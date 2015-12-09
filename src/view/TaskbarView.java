package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

/**
 * Created by Vitor on 05/11/2015.
 */
public class TaskbarView implements View, ActionListener, ItemListener {

	private final Controller controller;

	private final String yesText;
	private final String noText;
	private final String okText;
	private final String aboutText;

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

	private Image img = null;

	private boolean isWaiting = false;

	public TaskbarView(Controller controller, String tooltipTitle, String yesText, String noText, String okText, String aboutText, String iconFilePath, String about, String verifyNow, String verify, String exit) {
		this.controller = controller;
		this.yesText = yesText;
		this.noText = noText;
		this.okText = okText;
		this.aboutText = aboutText;
		this.about = about;
		this.verifyNow = verifyNow;
		this.verify = verify;
		this.exit = exit;

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}

		popup = new PopupMenu();
		URL imgURL = getClass().getResource(iconFilePath);
		if(imgURL != null) {
			img = Toolkit.getDefaultToolkit().getImage(imgURL);
		}
		else {
			img = Toolkit.getDefaultToolkit().getImage(iconFilePath);
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
	public void okDialog(String title, String text) {
		Object[] options = {
				okText
		};

		isWaiting = true;
		JOptionPane.showMessageDialog(null, text);
		System.out.println("here");
		isWaiting = false;
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
		verifyCheckBox.addItemListener(this);
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

		isWaiting = true;
		int n = JOptionPane.showOptionDialog(
				null, //do not use a frame
				text,
				title,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null, //do not use a custom Icon
				options, //the titles of buttons
				options[0]); //default button title
		isWaiting = false;

		return n == JOptionPane.YES_OPTION;
	}

	@Override
	public boolean isViewWaiting() {
		return isWaiting;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == verifyItem) {
			controller.checkPayments();
		}
		else if(e.getSource() == aboutItem) {
			AboutFrame aboutFrame = new AboutFrame(img, aboutText);
			aboutFrame.setVisible(true);
			aboutFrame.setLocationRelativeTo(null);
		}
		else if(e.getSource() == exitItem) {
			tray.remove(trayIcon);
			controller.quit();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == verifyCheckBox) {
			controller.setTimer(verifyCheckBox.getState());
		}
	}
}
