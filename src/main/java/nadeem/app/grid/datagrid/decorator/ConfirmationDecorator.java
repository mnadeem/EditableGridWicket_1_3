package nadeem.app.grid.datagrid.decorator;

import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxPreprocessingCallDecorator;

public class ConfirmationDecorator extends AjaxPreprocessingCallDecorator {

	private static final long serialVersionUID = 1L;
	private String confirmationMessage;

	public ConfirmationDecorator(IAjaxCallDecorator delegate, String message) {
		super(delegate);
		this.confirmationMessage = message;
	}
	
	@Override
	public CharSequence preDecorateScript(CharSequence existingScript) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("if(!confirm('").append(confirmationMessage).append("')){return false;}").append(existingScript);
		return buffer.toString();
	}

}
