package nadeem.app.grid.datagrid.toolbar;

import java.util.ArrayList;
import java.util.List;

import nadeem.app.grid.datagrid.EditableGrid;
import nadeem.app.grid.datagrid.column.AbstractEditablePropertyColumn;
import nadeem.app.grid.datagrid.column.EditableCellPanel;
import nadeem.app.grid.datagrid.component.EditableGridSubmitLink;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.form.IFormVisitorParticipant;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.Loop.LoopItem;
import org.apache.wicket.model.PropertyModel;

public abstract class EditableGridBottomToolbar<T> extends AbstractEditableGridToolbar<T> {

	private static final long serialVersionUID 	= 1L;
	private static final String CELL_ID 		= "cell";
	private static final String CELLS_ID 		= "cells";
	
	private T newRow							= null;
	
	protected abstract void onAdd(T newRow, AjaxRequestTarget target);

	public EditableGridBottomToolbar(EditableGrid<T> table, T newObject){
		super(table);
		add(new AddToolBarForm("addToolbarForm"));
		createNewInstance(newObject);
		
	}
	
	protected void onError(AjaxRequestTarget target) {	}

	@SuppressWarnings("unchecked")
	private void createNewInstance(T newObject) {
		try {
			newRow = (T) newObject.getClass().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class AddToolBarForm extends Form implements IFormVisitorParticipant {

		private static final long serialVersionUID = 1L;

		public AddToolBarForm(String id) {
			super(id);
			add(newEditorComponents());
			add(newAddButton(this));
		}
		public boolean processChildren() {
			IFormSubmittingComponent submitter = getRootForm().findSubmittingButton();
            return submitter != null && submitter.getForm() == this;
		}		
	}

	private Component newAddButton(WebMarkupContainer encapsulatingContainer) {
		return new EditableGridSubmitLink("add", encapsulatingContainer) {

			private static final long serialVersionUID = 1L;		
			@Override
			protected void onSuccess(AjaxRequestTarget target) {
				onAdd(newRow, target);
				createNewInstance(newRow);
				target.addComponent(getGrid());
				
			}
			@Override
			protected void onError(AjaxRequestTarget target) {				
				EditableGridBottomToolbar.this.onError(target);
			}
		};
	}
	
	private Loop newEditorComponents() {
		final List<AbstractEditablePropertyColumn<T>> columns = getEditableColumns();
		return new Loop(CELLS_ID, columns.size() ) {

			private static final long serialVersionUID 	= 	1L;

			protected void populateItem(LoopItem item) {
				addEditorComponent(item, getEditorColumn(columns, item.getIteration()));
			}
		};
	}

	private void addEditorComponent(LoopItem item, AbstractEditablePropertyColumn<T> toolBarCell) {
		item.add(newCell(toolBarCell));		
	}

	@SuppressWarnings("unchecked")
	private List<AbstractEditablePropertyColumn<T>> getEditableColumns() {
		 List<AbstractEditablePropertyColumn<T>> columns = new ArrayList<AbstractEditablePropertyColumn<T>>();
		 for (IColumn column : getGrid().getColumns()) {
			if (column instanceof AbstractEditablePropertyColumn) {
				columns.add((AbstractEditablePropertyColumn<T>)column);
			}
			
		}
		 
		 return columns;
	}	

	private Component newCell(AbstractEditablePropertyColumn<T> editableGridColumn) {
		EditableCellPanel panel 			= editableGridColumn.getEditableCellPanel(CELL_ID);
		FormComponent editorComponent 		= panel.getEditableComponent();
		editorComponent.setModel(new PropertyModel(newRow , editableGridColumn.getPropertyExpression()));
		return panel;
	}

	private AbstractEditablePropertyColumn<T> getEditorColumn(final List<AbstractEditablePropertyColumn<T>> editorColumn, int index) {
		return editorColumn.get(index);
	}
}
