package hu.illesjosh.pairwise.view;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

import hu.illesjosh.pairwise.TextFieldArray;

public class ProductAttributesView extends WizardStep {

	@Override
	public Component getBodyContent() {
		return new Panel("Key product attributes", new TextFieldArray());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getViewName() {
		return "productAttributes";
	}

}
