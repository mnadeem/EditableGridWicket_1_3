package nadeem.app.grid.datagrid.column;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class EditableCellPanel extends Panel {

	private static final long serialVersionUID = 1L;
	public abstract FormComponent getEditableComponent();

	public EditableCellPanel(String id) {
		super(id);
	}
}
