package nadeem.app.grid.datagrid.column;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;

public class EditableRequiredDropDownCellPanel extends EditableCellPanel {

	private static final long serialVersionUID = 1L;


	public EditableRequiredDropDownCellPanel(final String id, final PropertyColumn column, @SuppressWarnings("rawtypes") final List choices) {
		super(id);
		
		DropDownChoice field = new DropDownChoice("dropdown", choices);
		field.setLabel(column.getDisplayModel());
		addBehaviors(field);
		add(field);	
	}	
	

	@Override
	public FormComponent getEditableComponent() {
		return (FormComponent) get("dropdown");
	}


	private void addBehaviors(DropDownChoice field) {
		field.setRequired(true);		
	}

}
