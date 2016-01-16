package hu.illesjosh.pairwise;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import hu.illesjosh.pairwise.view.WizardStep;

public class Wizard {
	private final Navigator navi;
	private final List<WizardStep> steps;

	public Wizard(final Navigator navigator, final WizardStep... wizardSteps) {
		navi = navigator;
		steps = new ArrayList<WizardStep>(wizardSteps.length);

		if (wizardSteps.length > 0) {
			for (int i = 0; i < wizardSteps.length; ++i) {
				final WizardStep ws = wizardSteps[i];

				steps.add(ws);

				if (i > 0) {
					final String prevViewName = wizardSteps[i - 1].getViewName();
					ws.getBackButton().addClickListener(new Button.ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							navi.navigateTo(prevViewName);
						}
					});
				} else {
					ws.getBackButton().setEnabled(false);
				}

				if (i < wizardSteps.length - 1) {
					final String nextViewName = wizardSteps[i + 1].getViewName();
					ws.getNextButton().addClickListener(new Button.ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							navi.navigateTo(nextViewName);
						}
					});
				} else {
					ws.getNextButton().setEnabled(false);
				}

				navi.addView(ws.getViewName(), ws);
			}

			navi.navigateTo(steps.get(0).getViewName());
		}
	}
}
