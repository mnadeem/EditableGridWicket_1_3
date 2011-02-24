package nadeem.app.grid.datagrid.toolbar;

import nadeem.app.grid.datagrid.EditableGrid;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class AbstractEditableGridToolbar<T> extends Panel {
	private static final long serialVersionUID = 1L;

	private EditableGrid<T> grid;

	public AbstractEditableGridToolbar(IModel model, EditableGrid<T> newGrid) {
		super(EditableGrid.TOOLBAR_COMPONENT_ID, model);
		this.grid = newGrid;
	}

	public AbstractEditableGridToolbar(EditableGrid<T> newGrid)	{
		super(EditableGrid.TOOLBAR_COMPONENT_ID);
		this.grid = newGrid;
	}

	protected EditableGrid<T> getGrid()	{
		return grid;
	}
}
