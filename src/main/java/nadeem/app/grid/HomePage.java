package nadeem.app.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nadeem.app.grid.datagrid.EditableGrid;
import nadeem.app.grid.datagrid.column.AbstractEditablePropertyColumn;
import nadeem.app.grid.datagrid.column.EditableDropDownCellPanel;
import nadeem.app.grid.datagrid.column.EditableCellPanel;
import nadeem.app.grid.datagrid.column.RequiredEditableTextFieldColumn;

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
		columns.add(new RequiredEditableTextFieldColumn<Person>(new Model("Name"), "name", false));
		columns.add(new RequiredEditableTextFieldColumn<Person>(new Model("Address"), "address"));
		columns.add(new AbstractEditablePropertyColumn<Person>(new Model("Age"), "age") {

			private static final long serialVersionUID = 1L;

			public EditableCellPanel getEditableCellPanel(String componentId) {
				return new EditableDropDownCellPanel(componentId, this, Arrays.asList("11-20","21-30","31-40"));
			}
			
		});
		return columns;
	}

	private List<Person> getPersons() {
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person("Person1","12", "Address1"));
		persons.add(new Person("Person2","13", "Address2"));
		persons.add(new Person("Person3","13", "Address3"));
		persons.add(new Person("Person4","13", "Address4"));
		persons.add(new Person("Person5","13", "Address5"));
		persons.add(new Person("Person6","13", "Address6"));
		return persons;
	}

}
