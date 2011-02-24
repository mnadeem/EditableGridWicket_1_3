package nadeem.app.grid.datagrid.column;

import nadeem.app.grid.datagrid.EditableGrid;
import nadeem.app.grid.datagrid.component.EditableGridSubmitLink;
import nadeem.app.grid.datagrid.component.RemoveItemLink;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;

public abstract class EditableGridActionsPanel<T> extends Panel {

	private static final long serialVersionUID = 1L;
	
	protected abstract void onSave(AjaxRequestTarget target);
	protected abstract void onError(AjaxRequestTarget target);
	protected abstract void onCancel(AjaxRequestTarget target);
	protected abstract void onDelete(AjaxRequestTarget target);

	public EditableGridActionsPanel(String id, final Item cellItem, final EditableGrid<T> dataTable) {
		super(id);
		
		final ListItem rowItem = (ListItem) cellItem.findParent(ListItem.class);
		
		AjaxLink editLink = new AjaxLink("edit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {							
				dataTable.setSelectedItem(rowItem);
				target.addComponent(dataTable);
			}
			@Override
			public boolean isVisible() {
				return !isThisRowBeingEdited(rowItem, dataTable);
			}
		};
		AjaxLink cancelLink = new AjaxLink("cancel") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				dataTable.setSelectedItem(null);
				target.addComponent(dataTable);
				onCancel(target);
			}
			@Override
			public boolean isVisible() {
				return isThisRowBeingEdited(rowItem, dataTable);
			}
		};
		AjaxLink deleteLink = new RemoveItemLink<T>("delete", rowItem) {

			private static final long serialVersionUID = 1L;
			@Override
			protected void onRemove(AjaxRequestTarget target) {
				dataTable.setSelectedItem(null);
				target.addComponent(dataTable);	
				onDelete(target);
			}
		};
		
		AjaxSubmitLink saveLink = new EditableGridSubmitLink("save", rowItem) {

			private static final long serialVersionUID = 1L;
		
			@Override
			public boolean isVisible() {
				return isThisRowBeingEdited(rowItem, dataTable);
			}
			@Override
			protected void onSuccess(AjaxRequestTarget target) {
				dataTable.setSelectedItem(null);
				target.addComponent(dataTable);
				onSave(target);
				
			}
			@Override
			protected void onError(AjaxRequestTarget target) {
				EditableGridActionsPanel.this.onError(target);
			}
		};
		
		add(editLink);
		add(saveLink);
		add(cancelLink);
		add(deleteLink);
	}
	
	private boolean inEditingMode(EditableGrid<T> dataTable) {
		return dataTable.getSelectedItem()!=null;
	}
	
	private boolean isThisRowBeingEdited(ListItem rowItem, EditableGrid<T> dataTable) {
		return inEditingMode(dataTable)  && rowItem.equals(dataTable.getSelectedItem());
	}
}
