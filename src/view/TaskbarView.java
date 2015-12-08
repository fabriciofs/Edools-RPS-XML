package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Vitor on 05/11/2015.
 */
public class TaskbarView implements View, ActionListener {

	private final Controller controller;

	private final String yesText;
	private final String noText;

	private final PopupMenu popup;
	private final TrayIcon trayIcon;
	private final SystemTray tray;


	private MenuItem aboutItem;
	private CheckboxMenuItem cb1;
	private CheckboxMenuItem cb2;
	private Menu displayMenu;
	private MenuItem errorItem;
	private MenuItem warningItem;
	private MenuItem infoItem;
	private MenuItem noneItem;
	private MenuItem exitItem;

	public TaskbarView(Controller controller, String yesText, String noText, String iconPath) {
		this.controller = controller;
		this.yesText = yesText;
		this.noText = noText;

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}

		popup = new PopupMenu();
		trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(iconPath));
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
		aboutItem = new MenuItem("About");
		cb1 = new CheckboxMenuItem("Set auto size");
		cb2 = new CheckboxMenuItem("Set tooltip");
		displayMenu = new Menu("Display");
		errorItem = new MenuItem("Error");
		warningItem = new MenuItem("Warning");
		infoItem = new MenuItem("Info");
		noneItem = new MenuItem("None");
		exitItem = new MenuItem("Exit");

		//Add listeners to components
		exitItem.addActionListener(this);

		//Add components to pop-up menu
		popup.add(aboutItem);
		popup.addSeparator();
		popup.add(cb1);
		popup.add(cb2);
		popup.addSeparator();
		popup.add(displayMenu);
		displayMenu.add(errorItem);
		displayMenu.add(warningItem);
		displayMenu.add(infoItem);
		displayMenu.add(noneItem);
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
		if(e.getSource() == exitItem) {
			tray.remove(trayIcon);
			controller.quit();
		}
	}
}
