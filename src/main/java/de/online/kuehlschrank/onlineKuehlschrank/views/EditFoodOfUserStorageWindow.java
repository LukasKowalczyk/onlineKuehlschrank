package de.online.kuehlschrank.onlineKuehlschrank.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.lapi.Lapi;
import de.online.kuehlschrank.onlineKuehlschrank.container.Food;
import de.online.kuehlschrank.onlineKuehlschrank.container.StorageFood;
import de.online.kuehlschrank.onlineKuehlschrank.container.Units;
import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.controle.FoodControle;
import de.online.kuehlschrank.onlineKuehlschrank.controle.UserControle;
import de.online.kuehlschrank.onlineKuehlschrank.utils.LapiKeynames;
import de.online.kuehlschrank.onlineKuehlschrank.utils.SuggestionForTextField;
import de.online.kuehlschrank.onlineKuehlschrank.utils.ViewKeynames;
import de.online.kuehlschrank.onlineKuehlschrank.validation.DateValidator;
import de.online.kuehlschrank.onlineKuehlschrank.validation.EanValidator;
import de.online.kuehlschrank.onlineKuehlschrank.validation.NumberValidator;

public class EditFoodOfUserStorageWindow extends Window {

	private StorageFood selectedFood = null;

	private UserControle userControle;

	private Lapi lapi;

	private boolean isUpdate = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditFoodOfUserStorageWindow() {
		super();
		initWindow();
	}

	public EditFoodOfUserStorageWindow(StorageFood selectedFood) {
		super();
		this.selectedFood = selectedFood;
		initWindow();
	}

	public EditFoodOfUserStorageWindow(String caption, Component content,
			StorageFood selectedFood) {
		super(caption, content);
		this.selectedFood = selectedFood;
		initWindow();
	}

	public EditFoodOfUserStorageWindow(String caption, StorageFood selectedFood) {
		super(caption);
		this.selectedFood = selectedFood;
		initWindow();
	}

	private void initWindow() {
		userControle = UserControle.getInstance();
		lapi = Lapi.getInstance();
		isUpdate = selectedFood != null;

		center();
		setDraggable(false);
		setResizable(false);

		SuggestionForTextField suggestionName = new SuggestionForTextField();

		SuggestionForTextField suggestionCode = new SuggestionForTextField();

		VerticalLayout content = new VerticalLayout();

		final TextField code = new TextField(lapi.getText(LapiKeynames.CODE));
		code.setSizeFull();
		code.addValidator(new EanValidator(lapi
				.getText(LapiKeynames.EAN_VALIDATOR)));
		code.setTextChangeEventMode(TextChangeEventMode.EAGER);

		final TextField name = new TextField(lapi.getText(LapiKeynames.NAME));
		name.setSizeFull();
		name.setTextChangeEventMode(TextChangeEventMode.EAGER);

		name.addTextChangeListener(e -> {
			// System.out.println(e.getText());
			if (StringUtils.isNoneBlank(e.getText())) {
				List<Food> out = FoodControle.getInstance().findFoodsViaName(
						e.getText());
				Map<String, Object> texts = generateMap(out);

				suggestionName.setText(texts, new ClickListener() {

					private static final long serialVersionUID = 8763333971231358856L;

					@Override
					public void buttonClick(ClickEvent event) {
						Food food = (Food) event.getButton().getData();
						name.setValue(food.getName());
						code.setValue(food.getCode());
						suggestionName.close();
					}
				});
			} else {
				suggestionName.close();
			}
		});
		code.addTextChangeListener(e -> {
			// System.out.println(e.getText());
			if (StringUtils.isNoneBlank(e.getText())) {
				List<Food> out = FoodControle.getInstance().findFoodsViaCode(
						e.getText());
				Map<String, Object> texts = generateMap(out);

				suggestionCode.setText(texts, new ClickListener() {

					private static final long serialVersionUID = 8763333971231358856L;

					@Override
					public void buttonClick(ClickEvent event) {
						Food food = (Food) event.getButton().getData();
						name.setValue(food.getName());
						code.setValue(food.getCode());
						suggestionCode.close();
					}
				});
			} else {
				suggestionCode.close();
			}

		});
		HorizontalLayout amountAndUnit = new HorizontalLayout();

		final TextField amount = new TextField(
				lapi.getText(LapiKeynames.AMOUNT));
		amount.setMaxLength(4);
		amount.addValidator(new NumberValidator(lapi
				.getText(LapiKeynames.NUMBER_VALIDATOR)));

		final ComboBox unit = new ComboBox(lapi.getText(LapiKeynames.UNIT));
		unit.setTextInputAllowed(false);
		unit.setNullSelectionAllowed(false);
		unit.addItems((Object[]) Units.values());

		amountAndUnit.addComponents(amount, unit);

		final DateField expireDate = new DateField(
				lapi.getText(LapiKeynames.EXPIRE_DATE) + " ("
						+ lapi.getText(LapiKeynames.DATE_FORMAT_IN_TEXT) + ")");
		expireDate.addValidator(new DateValidator());
		expireDate.setDateFormat(lapi.getText(LapiKeynames.DATE_FORMAT));
		String caption = lapi.getText(LapiKeynames.ADD);
		if (isUpdate) {
			caption = lapi.getText(LapiKeynames.SAVE);
			name.setValue(selectedFood.getFood().getName());
			code.setValue(selectedFood.getFood().getCode());
			amount.setValue(String.valueOf(selectedFood.getAmount()));
			expireDate.setValue(selectedFood.getExpireDate());
			unit.select(selectedFood.getUnit());
		}

		final Button addButton = new Button(caption);
		addButton.addClickListener(e -> {
			try {
				StorageFood food = new StorageFood(new Food(name.getValue(),code.getValue()), Integer.parseInt(amount
						.getValue()), Units.getUnit(unit.getConvertedValue()
						.toString()),  expireDate.getValue());

				User user = UserControle.getCurrentUser();
				if (isUpdate) {
					userControle.updateFoodInUserstorage(user, food,
							selectedFood.getFood().getCode());
				} else {
					userControle.insertFoodInUserstorage(user, food);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			ViewKeynames.gotoView(ViewKeynames.MAIN);
			close();
		});

		content.addComponents(code, suggestionCode, name, suggestionName,
				amountAndUnit, expireDate, new Label(""), addButton);
		content.setMargin(true);
		setContent(content);
	}

	private Map<String, Object> generateMap(List<Food> foodList) {
		if (foodList != null) {
			Map<String, Object> texts = new HashMap<String, Object>();
			for (Food food : foodList) {
				texts.put(food.getName(), food);
			}
			return texts;
		}
		return null;
	}
}
