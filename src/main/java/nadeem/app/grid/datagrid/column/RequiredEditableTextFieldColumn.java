package nadeem.app.grid.datagrid.column;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

public class RequiredEditableTextFieldColumn<T> extends AbstractEditablePropertyColumn<T> {

	private static final long serialVersionUID = 1L;

	public RequiredEditableTextFieldColumn(IModel displayModel,	String propertyExpression) {
		super(displayModel, propertyExpression);
	}
	
	public RequiredEditableTextFieldColumn(IModel displayModel,	String propertyExpression, boolean isEditable) {
		super(displayModel, propertyExpression, isEditable);
	}

	public EditableCellPanel getEditableCellPanel(String componentId) {
		return new EditableTextFieldCellPanel(componentId, this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void addBehaviors(FormComponent formComponent) {
				formComponent.setRequired(true);
			}
		};
	}
}
