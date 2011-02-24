package nadeem.app.grid.datagrid.component;

import java.util.List;

import nadeem.app.grid.datagrid.decorator.ConfirmationDecorator;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public abstract class RemoveItemLink<T> extends AjaxLink {

	private static final long serialVersionUID = 1L;
	private ListItem rowItem;
	
	protected abstract void onRemove(AjaxRequestTarget target);

	public RemoveItemLink(String id, ListItem rowItem) {
		super(id);
		this.rowItem = rowItem;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(AjaxRequestTarget target) {
		List<T> list = getListView().getList();
		int index = rowItem.getIndex();
		if (index != -1) {
			list.remove(index);
			onRemove(target);
		}
	}
	@Override
	protected IAjaxCallDecorator getAjaxCallDecorator() {
		return new ConfirmationDecorator(super.getAjaxCallDecorator(), "Do you really want to Delete?");
	}
	private final ListView getListView() {
		return (ListView) rowItem.getParent();
	}
}
