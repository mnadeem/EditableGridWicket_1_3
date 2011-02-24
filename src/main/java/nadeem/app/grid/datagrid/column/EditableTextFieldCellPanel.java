package nadeem.app.grid.datagrid.column;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;

public class EditableTextFieldCellPanel extends EditableCellPanel {

	private static final long serialVersionUID = 1L;

	public EditableTextFieldCellPanel(String id, PropertyColumn column) {
		super(id);
		
		TextField field = new TextField("textfield");
		field.setOutputMarkupId(true);
		field.setLabel(column.getDisplayModel());
		addBehaviors(field);
		add(field);	
	}

	public FormComponent getEditableComponent() {
		return (FormComponent) get("textfield");
	}

	protected void addBehaviors(FormComponent formComponent) {
		
	}
}
