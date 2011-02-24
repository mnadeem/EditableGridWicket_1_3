package nadeem.app.grid.datagrid.column;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public class EditableGridActionsColumn<T> extends AbstractEditableGridColumn<T> {


	public EditableGridActionsColumn(IModel displayModel) {
		super(displayModel, "");		
	}

	private static final long serialVersionUID = 1L;

	
	public void populateItem(Item cellItem, String componentId, IModel rowModel) {

		cellItem.add(new EditableGridActionsPanel<T>(componentId, cellItem, getGrid()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSave(AjaxRequestTarget target) {
				EditableGridActionsColumn.this.onSave(target);				
			}

			@Override
			protected void onError(AjaxRequestTarget target) {				
				EditableGridActionsColumn.this.onError(target);				
			}

			@Override
			protected void onCancel(AjaxRequestTarget target) {

			}

			@Override
			protected void onDelete(AjaxRequestTarget target) {				
				EditableGridActionsColumn.this.onDelete(target);		
			}
			
		});		
	}

	protected void onDelete(AjaxRequestTarget target) {				
		
	}

	protected void onSave(AjaxRequestTarget target) {
				
	}

	protected void onError(AjaxRequestTarget target) {
				
	}
}
