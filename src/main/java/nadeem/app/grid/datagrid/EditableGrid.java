package nadeem.app.grid.datagrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nadeem.app.grid.datagrid.column.AbstractEditableGridColumn;
import nadeem.app.grid.datagrid.column.AbstractEditablePropertyColumn;
import nadeem.app.grid.datagrid.column.EditableGridActionsColumn;
import nadeem.app.grid.datagrid.toolbar.AbstractEditableGridToolbar;
import nadeem.app.grid.datagrid.toolbar.EditableGridBottomToolbar;
import nadeem.app.grid.datagrid.toolbar.EditableGridHeadersToolbar;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.util.ArrayIteratorAdapter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class EditableGrid<T> extends Panel {

	private static final long serialVersionUID 		= 1L;
	private static final String CELL_REPEATER_ID 	= "cells";
	private static final String CELL_ITEM_ID 		= "cell";
	public static final String TOOLBAR_COMPONENT_ID = "toolbar";

	private ListView gridBody;
	private List<IColumn> columns;
	private ListItem selectedItem;
	private final RepeatingView topToolbars;
	private final RepeatingView bottomToolbars;

	public EditableGrid(String id, final List<AbstractEditablePropertyColumn<T>> columns, final List<T> items, T newObject) {
		super(id);
		setColumns(columns);

		Form form = new Form("form");
		
		topToolbars	 	= new ToolbarsContainer("topToolbars");
		bottomToolbars 	= new ToolbarsContainer("bottomToolbars");
		gridBody 		= newGridBody(items);
		
		form.setOutputMarkupId(true);
		
		form.add(topToolbars);
		form.add(bottomToolbars);
		form.add(gridBody);
		add(form);
		this.setOutputMarkupId(true);
		addTopToolbar(new EditableGridHeadersToolbar<T>(this));
		addBottomToolbar(new EditableGridBottomToolbar<T>(this, newObject){

			private static final long serialVersionUID = 1L;

			@Override
			protected void onAdd(T newRow, AjaxRequestTarget target) {
				items.add(newRow);
			}
			@Override
			protected void onError(AjaxRequestTarget target) {
				EditableGrid.this.onError(target);
			}			
		});
	}
	
	public void setSelectedItem(ListItem selectedItem) {
		this.selectedItem = selectedItem;
	}


	public ListItem getSelectedItem() {
		return selectedItem;
	}



	public void addBottomToolbar(AbstractEditableGridToolbar<T> toolbar) {
		addToolbar(toolbar, bottomToolbars);
	}

	public void addTopToolbar(AbstractEditableGridToolbar<T> toolbar)	{
		addToolbar(toolbar, topToolbars);
	}

	
	protected Item newCellItem(final String id, int index, final IModel model) {
		return new Item(id, index, model);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	protected void onBeforeRender() {

		if (hasBeenRendered() == false) {
			for (IColumn column : getColumns()) {
				if (column instanceof AbstractEditableGridColumn) {
					((AbstractEditableGridColumn<T>)column).setGrid(this);
				}
			}
		}
		super.onBeforeRender();
	}
	
	protected void onError(AjaxRequestTarget target) {	

	}

	protected void onSave(AjaxRequestTarget target) {

	}

	protected void onDelete(AjaxRequestTarget target) {

	}

	private ListView newGridBody(List<T> items) {
		return new ListView("rows", items) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem rowItem) {
				addCells(rowItem);				
			}
			@Override
			protected ListItem newItem(int index) {
				return new RowItem(index, getListItemModel(getModel(), index));				
			}
		};
	}

	private class RowItem extends ListItem {

		private static final long serialVersionUID = 1L;
		public RowItem(int index, IModel model) {
			super(index, model);			
		}
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (obj != null && obj instanceof EditableGrid.RowItem) {
				return getIndex() == ((RowItem)obj).getIndex();
			}
			return false;
		}
		
	}

	private void addCells(ListItem rowItem) {
		RepeatingView cells = new RepeatingView(CELL_REPEATER_ID);
		rowItem.add(cells);
		Iterator<IColumn> populators = getPopulatorsIterator(columns);

		for (int itemIndex = 0; populators.hasNext(); itemIndex++) {
			IModel populatorModel 	= (IModel)populators.next();
			Item cellItem 			= newCellItem(cells.newChildId(), itemIndex, populatorModel);
			cells.add(cellItem);

			ICellPopulator populator = (ICellPopulator)cellItem.getModelObject();
			populator.populateItem(cellItem, CELL_ITEM_ID, rowItem.getModel());

			validateCellAdded(cellItem, populator);
		}
	}

	private void setColumns(List<AbstractEditablePropertyColumn<T>> newColumns) {
		columns = new ArrayList<IColumn>(newColumns.size() + 1);
		columns.addAll(newColumns);
		columns.add(new EditableGridActionsColumn<T>(new Model("Actions")){

			private static final long serialVersionUID = 1L;
			@Override
			protected void onError(AjaxRequestTarget target) {
				EditableGrid.this.onError(target);
			}
			@Override
			protected void onSave(AjaxRequestTarget target) {
				EditableGrid.this.onSave(target);
			}
			@Override
			protected void onDelete(AjaxRequestTarget target) {
				EditableGrid.this.onDelete(target);
			}
			
		});
	}
	
	private void validateCellAdded(Item cellItem, ICellPopulator populator) {
		if (cellItem.get("cell") == null) {
			throw new WicketRuntimeException (
				populator.getClass().getName() +
					".populateItem() failed to add a component with id [" +
					CELL_ITEM_ID +
					"] to the provided [cellItem] object. Make sure you call add() on cellItem ( cellItem.add(new MyComponent(componentId, rowModel) )");
		}
	}
	
	@SuppressWarnings("unchecked")
	private Iterator<IColumn> getPopulatorsIterator(List<IColumn> columns) {
		return new ArrayIteratorAdapter(columns.toArray(new IColumn[columns.size()])) {

			protected IModel model(Object object) {
				return new Model((Serializable)object);
			}
		};		
	}

	private void addToolbar(AbstractEditableGridToolbar<T> toolbar, RepeatingView container) {
		if (toolbar == null) {
			throw new IllegalArgumentException("argument [toolbar] cannot be null");
		}

		if (!toolbar.getId().equals(TOOLBAR_COMPONENT_ID)) {
			throw new IllegalArgumentException(
				"Toolbar must have component id equal to AbstractDataTable.TOOLBAR_COMPONENT_ID");
		}

		toolbar.setRenderBodyOnly(true);

		// create a container item for the toolbar (required by repeating view)
		WebMarkupContainer item = new ToolbarContainer(container.newChildId());
		item.setRenderBodyOnly(true);
		item.add(toolbar);

		container.add(item);
	}

	private final class ToolbarContainer extends WebMarkupContainer {
		private static final long serialVersionUID = 1L;

		private ToolbarContainer(String id)	{
			super(id);
		}
		@Override
		public boolean isVisible() {
			return ((Component)iterator().next()).isVisible();
		}
	}

	private class ToolbarsContainer extends RepeatingView	{
		private static final long serialVersionUID = 1L;

		private ToolbarsContainer(String id) {
			super(id);
		}

		@Override
		public boolean isVisible() {
			// only visible if at least one child is visible
			final boolean[] visible = new boolean[] { false };
			visitChildren(new IVisitor() {

				public Object component(Component component) {
					if (component.isVisible()) {
						visible[0] = true;
						return STOP_TRAVERSAL;
					}
					else {
						return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
					}
				}
			});
			return visible[0];
		}
	}

	public List<IColumn> getColumns() {
		return columns;
	}
}
