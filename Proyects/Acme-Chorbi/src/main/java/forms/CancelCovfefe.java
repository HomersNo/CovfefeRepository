
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class CancelCovfefe {

	public CancelCovfefe() {
		super();
	}


	private String	justification;
	private int		covfefeId;


	@NotBlank
	public String getJustification() {
		return this.justification;
	}

	public void setJustification(final String justification) {
		this.justification = justification;
	}

	public int getCovfefeId() {
		return this.covfefeId;
	}

	public void setCovfefeId(final int covfefeId) {
		this.covfefeId = covfefeId;
	}

}
