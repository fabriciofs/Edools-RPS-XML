package view;

import controller.Controller;

/**
 * Created by Vitor on 05/11/2015.
 */
public class TaskbarView implements View {

	private final Controller controller;

	public TaskbarView(Controller controller) {
		this.controller = controller;
	}

	@Override public void dialog(String text) {
		//TODO: Implement simple text dialog.
	}

	@Override
	public void showMainView() {
		//TODO: Implement main view.
	}
}
