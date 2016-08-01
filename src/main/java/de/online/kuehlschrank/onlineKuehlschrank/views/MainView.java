package de.online.kuehlschrank.onlineKuehlschrank.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.lapi.Lapi;
import de.online.kuehlschrank.onlineKuehlschrank.container.StorageFood;
import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.controle.FoodControle;
import de.online.kuehlschrank.onlineKuehlschrank.controle.UserControle;
import de.online.kuehlschrank.onlineKuehlschrank.utils.LapiKeynames;
import de.online.kuehlschrank.onlineKuehlschrank.utils.ViewKeynames;

public class MainView extends VerticalLayout implements View {

	private Lapi lapi;

	private FoodControle foodControle;

	private UserControle userControle;

	private static final long serialVersionUID = -6421990200334024396L;

	private Table storageTable;

	private User signedUpUser;

	private String selectedId;

	private static EditFoodOfUserStorageWindow window;

	@Override
	public void enter(ViewChangeEvent event) {
		lapi = Lapi.getInstance();
		foodControle = FoodControle.getInstance();
		userControle = UserControle.getInstance();
		storageTable = new Table(lapi.getText(LapiKeynames.STORAGE_TABLE_TITEL));
		signedUpUser = UserControle.getCurrentUser();
		final HorizontalLayout kopfleiste = generateSiteHeader();
		Table storageTable = generateTable();
		storageTable.addItemClickListener(new ItemClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {
				selectedId = event.getItemId().toString();
				if (event.isDoubleClick()) {
					StorageFood f = getFoodFromTable();
					generateFoodWindow(f,
							lapi.getText(LapiKeynames.WINDOW_UPDATE));
					storageTable.select(null);
					selectedId = null;
				}
			}

			private StorageFood getFoodFromTable() {
				StorageFood storageFood = new StorageFood();
				if (StringUtils.isNoneBlank(selectedId)) {
					try {
						storageFood = userControle.getFoodOfStorage(signedUpUser,
								selectedId);
					} catch (Exception e) {
					}
				}
				return storageFood;
			}

		});
		Button addButton = new Button(lapi.getText(LapiKeynames.FOOD),
				FontAwesome.PLUS);
		addButton.addClickListener(e -> {
			generateFoodWindow(null, lapi.getText(LapiKeynames.WINDOW_NEW));
		});
		addComponents(kopfleiste, storageTable, addButton);
		setComponentAlignment(addButton, Alignment.MIDDLE_CENTER);
		setMargin(true);
		setSpacing(true);

	}

	private static void generateFoodWindow(StorageFood f, String titel) {
		window = new EditFoodOfUserStorageWindow(titel, f);
		if (!window.isAttached()) {
			UI.getCurrent().addWindow(window);
		}
	}

	private Table generateTable() {
		storageTable.setPageLength(15);
		storageTable.addContainerProperty(lapi.getText(LapiKeynames.INFO),
				HorizontalLayout.class, null);
		storageTable.addContainerProperty(lapi.getText(LapiKeynames.NAME),
				String.class, null);
		storageTable.addContainerProperty(lapi.getText(LapiKeynames.AMOUNT),
				Integer.class, null);
		storageTable.addContainerProperty(lapi.getText(LapiKeynames.UNIT),
				String.class, null);
		String text = " " + lapi.getText(LapiKeynames.EXPIRE_DATE);
		storageTable.addContainerProperty(text, Date.class, null);
		storageTable.setColumnIcon(text, FontAwesome.CALENDAR);
		storageTable.setConverter(text, new StringToDateConverter() {
			private static final long serialVersionUID = 1L;

			@Override
			public DateFormat getFormat(Locale locale) {
				return new SimpleDateFormat(lapi
						.getText(LapiKeynames.DATE_FORMAT));
			}

		});

		storageTable.addContainerProperty(lapi.getText(LapiKeynames.EDIT),
				Button.class, null);
		setFoodInTable();

		storageTable.setSizeFull();
		storageTable.setPageLength(signedUpUser.getUserStorage().size());
		storageTable.setSelectable(true);
		return storageTable;
	}

	private void setFoodInTable() {
		storageTable.removeAllItems();
		Map<String, StorageFood> userStorage = signedUpUser.getUserStorage();
		if (userStorage == null) {
			return;
		}
		for (Entry<String, StorageFood> entry : userStorage.entrySet()) {

			StorageFood storageFood = entry.getValue();
			String id = entry.getKey();
			final HorizontalLayout symbols = getSymbols(storageFood);
			Button deleteButton = new Button(FontAwesome.TRASH);

			deleteButton.setId(id);
			deleteButton.addClickListener(e -> {
				if (StringUtils.isNoneBlank(deleteButton.getId())) {
					signedUpUser.setUserStorage(userControle
							.deleteFoodInUserSorage(signedUpUser,
									deleteButton.getId()));
					setFoodInTable();
					try {
						userControle.saveUser(signedUpUser);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			});

			storageTable.addItem(
					new Object[] { symbols, storageFood.getFood().getName(),
							storageFood.getAmount(),
							storageFood.getUnit().getUnitName(),
							storageFood.getExpireDate(), deleteButton }, id);
		}
	}

	private HorizontalLayout getSymbols(StorageFood storageFood) {
		boolean expired = foodControle.isExpiredDateReached(storageFood);
		boolean expiredInAWeek = foodControle
				.willBeExpiredDateReachedInAWeek(storageFood);
		final HorizontalLayout symbols = new HorizontalLayout();
		symbols.setSizeFull();
		Label expiredSymbol = new Label();
		if (expired) {
			expiredSymbol = new Label(
					FontAwesome.EXCLAMATION_TRIANGLE.getHtml(),
					ContentMode.HTML);
		} else if (expiredInAWeek) {
			expiredSymbol = new Label(FontAwesome.INFO_CIRCLE.getHtml(),
					ContentMode.HTML);
		}
		symbols.addComponents(expiredSymbol);
		symbols.setComponentAlignment(expiredSymbol, Alignment.TOP_CENTER);
		return symbols;
	}

	private HorizontalLayout generateSiteHeader() {
		final HorizontalLayout kopfleiste = new HorizontalLayout();
		kopfleiste.setSizeFull();
		Label welcome = new Label(lapi.getText(LapiKeynames.WELCOME) + " "
				+ FontAwesome.USER.getHtml() + " (" + signedUpUser.getName()
				+ ")");
		welcome.setContentMode(ContentMode.HTML);
		Button signOutButton = new Button(lapi.getText(LapiKeynames.LOGOUT),
				FontAwesome.SIGN_OUT);
		signOutButton.addClickListener(e -> {
			try {
				if (window != null) {
					window.close();
				}
				userControle.saveUser(signedUpUser);
				UserControle.setCurrentUser(null);
				ViewKeynames.gotoView(ViewKeynames.LOGIN);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		});
		kopfleiste.addComponents(welcome, signOutButton);
		kopfleiste.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

		kopfleiste.setComponentAlignment(signOutButton, Alignment.MIDDLE_RIGHT);
		return kopfleiste;
	}

}
