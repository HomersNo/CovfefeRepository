
package forms;

import org.hibernate.validator.constraints.NotBlank;

import domain.Ondaleck;

public class CancelOndalek {

	public CancelOndalek() {
		super();
	}


	private String	justification;
	private Ondaleck	covfefe;


	@NotBlank
	public String getJustification() {
		return this.justification;
	}

	public void setJustification(final String justification) {
		this.justification = justification;
	}

	public Ondaleck getCovfefe() {
		return this.covfefe;
	}

	public void setCovfefe(final Ondaleck covfefe) {
		this.covfefe = covfefe;
	}
}
