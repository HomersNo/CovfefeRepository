
package forms;

import org.hibernate.validator.constraints.NotBlank;

import domain.Loots;

public class CancelLoots {

	public CancelLoots() {
		super();
	}


	private String	justification;
	private Loots	loots;


	@NotBlank
	public String getJustification() {
		return this.justification;
	}

	public void setJustification(final String justification) {
		this.justification = justification;
	}

	public Loots getLoots() {
		return this.loots;
	}

	public void setLoots(final Loots loots) {
		this.loots = loots;
	}
}
