package de.online.kuehlschrank.onlineKuehlschrank.views;

import java.util.Date;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.online.kuehlschrank.onlineKuehlschrank.container.Food;
import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.controle.DatabaseControle;
import de.online.kuehlschrank.onlineKuehlschrank.utils.EanValidator;
import de.online.kuehlschrank.onlineKuehlschrank.utils.NumberValidator;
import de.online.kuehlschrank.onlineKuehlschrank.utils.Units;
import elemental.html.Database;

public class AddFoodToUserStorage extends Window {

	private Food selectedFood = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddFoodToUserStorage() {
		super();
		initWindow();
	}

	public AddFoodToUserStorage(Food selectedFood) {
		super();
		this.selectedFood = selectedFood;
		initWindow();
	}

	public AddFoodToUserStorage(String caption, Component content,
			Food selectedFood) {
		super(caption, content);
		this.selectedFood = selectedFood;
		initWindow();
	}

	public AddFoodToUserStorage(String caption, Food selectedFood) {
		super(caption);
		this.selectedFood = selectedFood;
		initWindow();
	}

	private void initWindow() {
		center();
		setDraggable(false);
		setResizable(false);

		VerticalLayout content = new VerticalLayout();
		final TextField name = new TextField("Name");
		name.setSizeFull();
		final TextField code = new TextField("EAN");
		code.setSizeFull();
		code.addValidator(new EanValidator("Bitte gib den EAN ein!"));
		HorizontalLayout amountAndUnit = new HorizontalLayout();
		final TextField amount = new TextField("Anzahl");
		amount.setMaxLength(4);
		amount.addValidator(new NumberValidator("Bitte gib eine Anzahl ein!"));
		final ComboBox unit = new ComboBox("Einheit");
		unit.setNullSelectionAllowed(false);
		unit.addItems(Units.values());
		amountAndUnit.addComponents(amount, unit);
		final DateField expireDate = new DateField("Min. Haltbarkeit");
		expireDate.setDateFormat("dd.MM.yyyy");

		if (selectedFood != null) {
			name.setValue(selectedFood.getName());
			code.setValue(selectedFood.getCode());
			amount.setValue(String.valueOf(selectedFood.getAmount()));
			expireDate.setValue(selectedFood.getExipreDate());
			unit.select(selectedFood.getUnit());
		}
		String caption = "Hinzufügen";
		if (selectedFood != null) {
			caption = "Speichern";
		}
		final Button addButton = new Button(caption);
		addButton.addClickListener(e -> {
			try {
				// System.out.println(unit.getCaption());
				// System.out.println(unit.getConnectorId());
				// System.out.println(unit.getConvertedValue());
				Food food = new Food(name.getValue(), Integer.parseInt(amount
						.getValue()), Units.getUnit(unit.getConvertedValue()
						.toString()), code.getValue(), expireDate.getValue());
				User u = UI.getCurrent().getSession()
						.getAttribute(User.class);
				if (selectedFood == null) {
					u.addFood(food);
					DatabaseControle.getInstance().insertToCollection(u);
				} else {
					u.deleteFoodInUserStorage(selectedFood.getCode());
					u.addFood(food);
					DatabaseControle.getInstance().updateInCollection(u);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			close();
		});

		content.addComponents(code, name, amountAndUnit, expireDate, addButton);
		content.setMargin(true);
		setContent(content);
	}
}
