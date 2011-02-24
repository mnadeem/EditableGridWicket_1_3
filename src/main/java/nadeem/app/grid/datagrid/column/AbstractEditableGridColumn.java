package nadeem.app.grid.datagrid.column;

import nadeem.app.grid.datagrid.EditableGrid;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;

public abstract class AbstractEditableGridColumn<T> extends PropertyColumn {

	private static final long serialVersionUID = 1L;
	private EditableGrid<T> editableGrid;

	public AbstractEditableGridColumn(IModel displayModel,	String propertyExpression) {
		super(displayModel, propertyExpression);
	}

	
	public void setGrid(EditableGrid<T> dataTable) {
		if (this.editableGrid != null && this.editableGrid != dataTable) {
			throw new IllegalStateException(
					"One column instance can not be used with multiple grid instances.");
		}
		this.editableGrid = dataTable;
	}

	public EditableGrid<T> getGrid() {
		return editableGrid;
	}
}
