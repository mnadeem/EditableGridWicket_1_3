package nadeem.app.grid;

import java.util.ArrayList;
import java.util.List;

import nadeem.app.grid.datagrid.EditableGrid;
import nadeem.app.grid.datagrid.column.AbstractEditablePropertyColumn;
import nadeem.app.grid.datagrid.column.EditableDropDownCellPanel;
import nadeem.app.grid.datagrid.column.EditableCellPanel;
import nadeem.app.grid.datagrid.column.EditableTextFieldColumn;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	private FeedbackPanel feedbackPanel;
	
	public HomePage(final PageParameters parameters) {
		getPersons();
		feedbackPanel = new FeedbackPanel("feedBack");
		feedbackPanel.setOutputMarkupPlaceholderTag(true);
		
		add(feedbackPanel);
		add(new EditableGrid<Person>("grid", getColumns(), getPersons(), new Person()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target) {
				target.addComponent(feedbackPanel);
			}
		});

	}

	private List<AbstractEditablePropertyColumn<Person>> getColumns() {
		List<AbstractEditablePropertyColumn<Person>> columns = new ArrayList<AbstractEditablePropertyColumn<Person>>();
		columns.add(new EditableTextFieldColumn<Person>(new Model("Name"), "name"));
		columns.add(new AbstractEditablePropertyColumn<Person>(new Model("Age"), "age") {

			private static final long serialVersionUID = 1L;

			public EditableCellPanel getEditableCellPanel(String componentId) {
				return new EditableDropDownCellPanel(componentId, this);
			}
			
		});
		return columns;
	}

	private List<Person> getPersons() {
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person("Person1","12"));
		persons.add(new Person("Person2","13"));
		persons.add(new Person("Person3","13"));
		persons.add(new Person("Person4","13"));
		persons.add(new Person("Person5","13"));
		persons.add(new Person("Person6","13"));
		return persons;
	}

}
