
package forms;

import org.hibernate.validator.constraints.NotBlank;

import domain.Meshwork;

public class CancelMeshwork {

	public CancelMeshwork() {
		super();
	}


	private String		justification;
	private Meshwork	meshwork;


	@NotBlank
	public String getJustification() {
		return this.justification;
	}

	public void setJustification(final String justification) {
		this.justification = justification;
	}

	public Meshwork getMeshwork() {
		return this.meshwork;
	}

	public void setMeshwork(final Meshwork meshwork) {
		this.meshwork = meshwork;
	}
}
