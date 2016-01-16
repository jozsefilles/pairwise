package hu.illesjosh.pairwise.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public abstract class WizardStep extends VerticalLayout implements View {

	private Button nextButton = null;
	private Button backButton = null;

	public WizardStep() {
		super();

		setSpacing(true);
		setMargin(true);

		addComponent(getBodyContent());

		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		backButton = new Button("< Back");
		hl.addComponent(backButton);
		nextButton = new Button("Next >");
		hl.addComponent(nextButton);
		addComponent(hl);
	}

	public abstract Component getBodyContent();
	
	public abstract String getViewName();

	public Button getNextButton() {
		return nextButton;
	}

	public Button getBackButton() {
		return backButton;
	}
}
