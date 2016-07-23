package de.online.kuehlschrank.onlineKuehlschrank.utils;

import java.util.Map;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class SuggestionForTextField extends Panel {

	private static final long serialVersionUID = 434406933647746833L;

	private VerticalLayout verticalLayout;

	public SuggestionForTextField() {
		super("");
		verticalLayout = new VerticalLayout();
		setVisible(false);
	}

	public void setText(Map<String, Object> suggestions, ClickListener listener) {
		if (suggestions != null) {
			verticalLayout = new VerticalLayout();
			for (String text : suggestions.keySet()) {
				verticalLayout.addComponent(generateButton(text,
						suggestions.get(text), listener));
			}
			setVisible(true);
			setContent(verticalLayout);
		} else {
			close();
		}
	}

	public void close() {
		setVisible(false);
		setContent(null);
	}

	private Button generateButton(String name, Object data,
			ClickListener listener) {
		Button button = new Button(name);
		button.setSizeFull();
		button.setData(data);
		button.addClickListener(listener);
		return button;
	}
}
