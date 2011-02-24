package nadeem.app.grid.datagrid.column;

import org.apache.wicket.MetaDataKey;

public interface IEditableGridColumn {
	
	final static MetaDataKey EDITING 	= new MetaDataKey(Boolean.class) {
		private static final long serialVersionUID = 1L;
	};

	EditableCellPanel getEditableCellPanel(String componentId);
}
