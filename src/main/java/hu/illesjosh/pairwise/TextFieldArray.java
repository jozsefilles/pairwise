package hu.illesjosh.pairwise;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class TextFieldArray extends VerticalLayout {

	private List<TextFieldArrayElement> elems = null;

	public TextFieldArray() {
		super();

		elems = new ArrayList<TextFieldArrayElement>(5);
		for (int i = 0; i < 5; ++i) {
			final TextFieldArrayElement e = new TextFieldArrayElement(Integer.toString(i));
			elems.add(e);
			addComponent(e);
			// TODO: create add button, too
			// TODO: there could be an auto-add on start of field use
			e.getRemoveButton().addClickListener(new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					removeComponent(e);
					elems.remove(e);
					// TODO: Number from 1
					int j = 0;
					for (TextFieldArrayElement f : elems) {
						f.getLabel().setValue(Integer.toString(j++));
					}
				}
			});
		}
	}

	public static class TextFieldArrayElement extends HorizontalLayout {

		private Label label = null;
		private TextField textField = null;
		private Button removeButton = null;

		public TextFieldArrayElement() {
			super();
			setSpacing(true);

			label = new Label((String) null);
			textField = new TextField();
			removeButton = new Button("Remove");

			addComponent(label);
			addComponent(textField);
			addComponent(removeButton);
		}

		public TextFieldArrayElement(String caption) {
			this();

			label.setValue(caption);
		}

		public Label getLabel() {
			return label;
		}

		public TextField getTextField() {
			return textField;
		}

		public Button getRemoveButton() {
			return removeButton;
		}

	}

}
