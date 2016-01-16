package hu.illesjosh.pairwise;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import hu.illesjosh.pairwise.ross.OptimumPairs;
import hu.illesjosh.pairwise.view.PairComparisonView;
import hu.illesjosh.pairwise.view.ProductAttributesView;

@Theme("valo")
public final class PairWiseUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("PairWise by Joshy");

		Navigator navi = new Navigator(this, this);

		Wizard wizard = new Wizard(navi, new ProductAttributesView(), new PairComparisonView());
	}

}
