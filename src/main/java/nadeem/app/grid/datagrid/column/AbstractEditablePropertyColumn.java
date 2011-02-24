package nadeem.app.grid.datagrid.column;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public abstract class AbstractEditablePropertyColumn<T> extends AbstractEditableGridColumn<T> implements IEditableGridColumn {

	private static final long serialVersionUID 	= 1L;


	public AbstractEditablePropertyColumn(IModel displayModel, String propertyExpression) {
		super(displayModel, propertyExpression);		
	}

	@Override
	public final void populateItem(Item item, String componentId, IModel model) {
		ListItem rowItem = (ListItem) item.findParent(ListItem.class);
		ListItem selectedItem = getGrid().getSelectedItem();
		
		if (rowItem.equals(selectedItem)) {
			EditableCellPanel provider 		= getEditableCellPanel(componentId);
			FormComponent editorComponent 	= provider.getEditableComponent();
			editorComponent.setModel(createLabelModel(model));
			addBehavior(editorComponent);
			item.add(provider);
		} else {
			super.populateItem(item, componentId, model);
		}		
	}
	 
	protected void addBehavior(FormComponent editorComponent) {

	}	
}
