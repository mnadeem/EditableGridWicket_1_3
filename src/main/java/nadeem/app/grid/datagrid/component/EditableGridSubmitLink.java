package nadeem.app.grid.datagrid.component;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;

public abstract class EditableGridSubmitLink extends AjaxSubmitLink {

	private static final long serialVersionUID = 1L;
	private WebMarkupContainer encapsulatingContainer;
	
	protected abstract void onSuccess(AjaxRequestTarget target);
	protected abstract void onError(AjaxRequestTarget target);

	public EditableGridSubmitLink(String id, WebMarkupContainer encapsulatingComponent) {
		super(id);
		this.encapsulatingContainer = encapsulatingComponent;
	}
	


	@Override
	protected final void onSubmit(AjaxRequestTarget target, Form form) {
		
		final Boolean[] error = { false };
		
		// first iteration - validate components
		encapsulatingContainer.visitChildren(FormComponent.class, new IVisitor() {
			public Object component(Component component) {

				FormComponent formComponent = (FormComponent) component;
				if (formComponentActive(formComponent)) {
					formComponent.validate();
					if (formComponent.isValid()) {
						if (formComponent.processChildren()) {
							return CONTINUE_TRAVERSAL;
						} else {
							return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
						}
					} else {
						error[0] = true;
						return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
					}
				}
				return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
			}
		});

		// second iteration - update models if the validation passed
		if (error[0] == false) {
			encapsulatingContainer.visitChildren(FormComponent.class, new IVisitor() {
				public Object component(Component component) {

					FormComponent formComponent = (FormComponent) component;
					if (formComponentActive(formComponent)) {

						formComponent.updateModel();

						if (formComponent.processChildren()) {
							return CONTINUE_TRAVERSAL;
						} else {
							return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
						}
					}
					return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
				}
			});
			
			onSuccess(target);
		} else {
			EditableGridSubmitLink.this.onError(target);
		}

	}
	@Override
	protected final void onError(AjaxRequestTarget target, Form form) {
		EditableGridSubmitLink.this.onError(target);
	}
	
	private boolean formComponentActive(FormComponent formComponent) {
		return formComponent.isVisibleInHierarchy() && formComponent.isValid() && formComponent.isEnabled()
				&& formComponent.isEnableAllowed();
	}


}
