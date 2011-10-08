package nadeem.app.grid.datagrid.column;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public abstract class AbstractEditablePropertyColumn<T> extends AbstractEditableGridColumn<T> implements IEditableGridColumn {

	private static final long serialVersionUID 	= 1L;
	private boolean isEditable 					= true;


	public AbstractEditablePropertyColumn(IModel displayModel, String propertyExpression) {
		super(displayModel, propertyExpression);		
	}
	
	public AbstractEditablePropertyColumn(IModel displayModel, String propertyExpression, boolean isEditable) {
		super(displayModel, propertyExpression);
		this.isEditable = isEditable;
	}

	@Override
	public final void populateItem(Item item, String componentId, IModel rowModel) {
		ListItem rowItem = (ListItem) item.findParent(ListItem.class);
		ListItem selectedItem = getGrid().getSelectedItem();
		
		if (isEditable && rowItem.equals(selectedItem)) {
			EditableCellPanel provider 		= getEditableCellPanel(componentId);
			FormComponent editorComponent 	= provider.getEditableComponent();
			editorComponent.setModel(createLabelModel(rowModel));
			addBehavior(editorComponent, rowModel);
			item.add(provider);
		} else {
			super.populateItem(item, componentId, rowModel);
		}		
	}
	 
	protected void addBehavior(FormComponent editorComponent, IModel rowModel) {

	}	
}
