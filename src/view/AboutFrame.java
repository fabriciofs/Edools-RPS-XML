package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Vitor on 08/12/2015.
 */
public class AboutFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Color BACKGROUND_COLOR = new Color(0.7f, 0.7f, 0.7f);
	private static final int FONT_SIZE = 15;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 150;

	public AboutFrame(Image img, String text) {

		getContentPane().setBackground(BACKGROUND_COLOR);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		//Layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;

		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel(new ImageIcon(img)), c);

		JTextArea right = new JTextArea(text);
		right.setBackground(BACKGROUND_COLOR);
		right.setEditable(false);
		right.setFont(new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE));
		right.setLineWrap(true);
		right.setWrapStyleWord(true);

		c.gridx = 1;
		c.gridy = 0;
		add(right, c);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}