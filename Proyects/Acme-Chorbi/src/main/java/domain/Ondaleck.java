
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Ondaleck extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Ondaleck() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	title;
	private String	description;
	private Date	moment;
	private String	assessment;
	private String	uniqueLabel;
	private String	justification;


	@NotBlank
	@Length(min = 1, max = 20)
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	@Length(min = 1, max = 100)
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^\\w{5}#\\d{5}$")
	public String getUniqueLabel() {
		return this.uniqueLabel;
	}
	public void setUniqueLabel(final String uniqueLabel) {
		this.uniqueLabel = uniqueLabel;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Pattern(regexp = "^HIGH|MEDIUM|LOW$")
	public String getAssessment() {
		return this.assessment;
	}
	public void setAssessment(final String assessment) {
		this.assessment = assessment;
	}

	public String getJustification() {
		return this.justification;
	}

	public void setJustification(final String justification) {

		this.justification = justification;
	}


	// Relationships ----------------------------------------------------------

	private Administrator	admin;
	private Event			event;


	@Valid
	@NotNull
	@ManyToOne()
	public Administrator getAdmin() {
		return this.admin;
	}
	public void setAdmin(final Administrator admin) {
		this.admin = admin;
	}

	@Valid
	@OneToOne(optional = true)
	@NotNull
	public Event getEvent() {
		return this.event;
	}
	public void setEvent(final Event event) {
		this.event = event;
	}

}
