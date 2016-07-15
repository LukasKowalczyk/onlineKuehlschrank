package de.online.kuehlschrank.onlineKuehlschrank.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.online.kuehlschrank.onlineKuehlschrank.container.Food;
import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.controle.DatabaseControle;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.DatenbankException;
import de.online.kuehlschrank.onlineKuehlschrank.utils.KnownView;

public class MainView extends VerticalLayout implements View {

	private static final String MIN_HALTBARKEIT = " Min. Haltbarkeit";
	private Table storageTable = new Table("Voratskammer");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private String selectedId;
	private AddFoodToUserStorage window;

	@SuppressWarnings("unchecked")
	@Override
	public void enter(ViewChangeEvent event) {
		user = UI.getCurrent().getSession().getAttribute(User.class);
		user.setUserStorage((List<Food>) getUserStorage());
		final HorizontalLayout kopfleiste = generateSiteHeader();
		final HorizontalLayout fussleiste = generateSiteFooter();
		Table storageTable = generateTable();
		storageTable.addItemClickListener(new ItemClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {
				selectedId = event.getItemId().toString();
			}
		});
		addComponents(kopfleiste, storageTable, fussleiste);
		setComponentAlignment(fussleiste, Alignment.MIDDLE_CENTER);
		setMargin(true);
		setSpacing(true);

	}

	private HorizontalLayout generateSiteFooter() {
		HorizontalLayout fussleiste = new HorizontalLayout();
		Button addButton = new Button(FontAwesome.PLUS);
		addButton.addClickListener(e -> {

			if (window == null) {
				window = new AddFoodToUserStorage("Add Food");
			}
			if (!window.isAttached()) {
				UI.getCurrent().addWindow(window);
			}
//			try {
//				updateUserInDatabase();
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
		});
		Button deleteButton = new Button(FontAwesome.MINUS);
		deleteButton.addClickListener(e -> {
			if (StringUtils.isNoneBlank(selectedId)) {
				user.deleteFoodInUserStorage(selectedId);
				setFoodInTable();
				selectedId = null;
				try {
					updateUserInDatabase();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		fussleiste.addComponents(addButton, deleteButton);
		fussleiste.setComponentAlignment(addButton, Alignment.MIDDLE_CENTER);
		fussleiste.setComponentAlignment(deleteButton, Alignment.MIDDLE_CENTER);
		return fussleiste;
	}

	private void updateUserInDatabase() throws DatenbankException {

		DatabaseControle.getInstance().insertToCollection(user);
	}

	private Table generateTable() {

		storageTable.addContainerProperty("Name", String.class, null);
		storageTable.addContainerProperty("Anzahl", Integer.class, null);
		storageTable.addContainerProperty("Einheit", String.class, null);
		storageTable.addContainerProperty(MIN_HALTBARKEIT, Date.class, null);
		storageTable.setColumnIcon(MIN_HALTBARKEIT, FontAwesome.CALENDAR);
		storageTable.setConverter(MIN_HALTBARKEIT, new StringToDateConverter() {
			private static final long serialVersionUID = 1L;

			@Override
			public DateFormat getFormat(Locale locale) {
				return new SimpleDateFormat("dd-MM-yyyy");
			}

		});

		setFoodInTable();
		storageTable.setSizeFull();
		storageTable.setPageLength(user.getUserStorage().size());
		storageTable.setSelectable(true);
		return storageTable;
	}

	private void setFoodInTable() {
		storageTable.removeAllItems();
		for (Food f : user.getUserStorage()) {
			storageTable.addItem(
					new Object[] { f.getName(), f.getAmount(), f.getUnit(),
							f.getExipreDate() }, f.getCode());
		}
	}

	private HorizontalLayout generateSiteHeader() {
		final HorizontalLayout kopfleiste = new HorizontalLayout();
		kopfleiste.setSizeFull();

		Label welcome = new Label("Hallo " + FontAwesome.USER.getHtml() + "("
				+ user.getName() + ")");
		welcome.setContentMode(ContentMode.HTML);
		Button signOutButton = new Button("Logout", FontAwesome.SIGN_OUT);
		signOutButton.addClickListener(e -> {
			try {
				window.close();
				updateUserInDatabase();
				UI.getCurrent().getSession().setAttribute(User.class, null);
				UI.getCurrent().getNavigator()
						.navigateTo(KnownView.LOGIN.getName());
				Notification.show("Aufwiedersehen " + user.getName() + "!",
						Notification.Type.TRAY_NOTIFICATION);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		});
		kopfleiste.addComponents(welcome, signOutButton);
		kopfleiste.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

		kopfleiste.setComponentAlignment(signOutButton, Alignment.MIDDLE_RIGHT);
		return kopfleiste;
	}

	private Collection<? extends Food> getUserStorage() {
		ArrayList<Food> ausg = new ArrayList<Food>();
		Food food1 = new Food("Apfel", 1, "Stk", "Obst1", new Date());
		ausg.add(food1);
		Food food2 = new Food("Wurst", 100, "g", "Fleisch1", new Date());
		ausg.add(food2);
		return ausg;
	}

}
