package nadeem.app.grid.datagrid.toolbar;

import nadeem.app.grid.datagrid.EditableGrid;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;

public class EditableGridHeadersToolbar<T> extends AbstractEditableGridToolbar<T> {
	private static final long serialVersionUID = 1L;


	public EditableGridHeadersToolbar(final EditableGrid<T> table) {
		super(table);


		RepeatingView headers = new RepeatingView("headers");
		add(headers);
		
		for (IColumn column : table.getColumns()) {

			WebMarkupContainer item = new WebMarkupContainer(headers.newChildId());
			headers.add(item);
			WebMarkupContainer header = new WebMarkupContainer("header");
			item.add(header);
			item.setRenderBodyOnly(true);
			header.add(column.getHeader("label"));

		}
	}
	
}
