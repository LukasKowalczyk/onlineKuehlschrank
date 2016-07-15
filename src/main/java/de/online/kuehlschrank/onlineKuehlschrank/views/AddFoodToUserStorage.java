package de.online.kuehlschrank.onlineKuehlschrank.views;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AddFoodToUserStorage extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddFoodToUserStorage() {
		super();
		initWindow();
	}

	public AddFoodToUserStorage(String caption, Component content) {
		super(caption, content);
		initWindow();
	}

	public AddFoodToUserStorage(String caption) {
		super(caption);
		initWindow();
	}

	private void initWindow() {
		center();
		setDraggable(false);
		setResizable(false);
		
		VerticalLayout content = new VerticalLayout();
		content.addComponent(new Label("Test"));
		content.setMargin(true);
		setContent(content);
	}

}
