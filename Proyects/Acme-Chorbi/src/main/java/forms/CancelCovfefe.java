
package forms;

import org.hibernate.validator.constraints.NotBlank;

import domain.Covfefe;

public class CancelCovfefe {

	public CancelCovfefe() {
		super();
	}


	private String	justification;
	private Covfefe	covfefe;


	@NotBlank
	public String getJustification() {
		return this.justification;
	}

	public void setJustification(final String justification) {
		this.justification = justification;
	}

	public Covfefe getCovfefe() {
		return this.covfefe;
	}

	public void setCovfefe(final Covfefe covfefe) {
		this.covfefe = covfefe;
	}
}
