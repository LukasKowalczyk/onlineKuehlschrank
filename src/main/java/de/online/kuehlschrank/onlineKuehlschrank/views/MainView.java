package de.online.kuehlschrank.onlineKuehlschrank.views;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.online.kuehlschrank.onlineKuehlschrank.container.Food;

public class MainView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String selectedId;
	
	@Override
	public void enter(ViewChangeEvent event) {

		Label welcome = new Label("Hallo User " + "!!!!");
		BeanContainer<Integer, Food> storage = new BeanContainer<Integer, Food>(
				Food.class);
		storage.setBeanIdProperty("code");
		storage.removeAllItems();
		storage.addAll(getUserStorage());

		Table storageTable = new Table("Voratskammer", storage);
		storageTable.setSizeFull();
		storageTable.setPageLength(storage.size());
		storageTable.setSelectable(true);
		storageTable.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				selectedId = event.getItemId().toString();
				System.out.println(selectedId);
			}
		});

		addComponents(welcome, storageTable);
		setMargin(true);
		setSpacing(true);

	}

	private Collection<? extends Food> getUserStorage() {
		ArrayList<Food> ausg = new ArrayList<Food>();
		Food food1 = new Food("Apfel",1,"Stk","Obst1");
		ausg.add(food1);
		return ausg;
	}

}
