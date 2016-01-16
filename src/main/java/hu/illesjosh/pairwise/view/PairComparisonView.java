package hu.illesjosh.pairwise.view;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

import hu.illesjosh.pairwise.ross.OptimumPairs;

public class PairComparisonView extends WizardStep {

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public Component getBodyContent() {
		return new Label(getViewName());
	}

	@Override
	public String getViewName() {
		return "pairComparison";
	}
}
