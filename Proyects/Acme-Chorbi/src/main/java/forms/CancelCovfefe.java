
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class CancelCovfefe {

	public CancelCovfefe() {
		super();
	}


	private String	justification;


	@NotBlank
	public String getJustification() {
		return this.justification;
	}

	public void setJustification(final String justification) {
		this.justification = justification;
	}

}
