
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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Meshwork extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Meshwork() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	title;
	private String	description;
	private Date	moment;
	private String	assesment;
	private String	uniqueLabel;
	private String	justification;


	@NotBlank
	@Size(min = 1, max = 10)
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	@Size(min = 1, max = 100)
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	@Pattern(regexp = "^\\d{4}-\\w{3}$")
	@Column(unique = true)
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

	@NotNull
	@Pattern(regexp = "^HIGHLY RECOMMENDED|RECOMMENDED|NOT RECOMMENDED$")
	public String getAssesment() {
		return this.assesment;
	}
	public void setAssesment(final String assesment) {
		this.assesment = assesment;
	}

	public String getJustification() {
		return this.justification;
	}

	public void setJustification(final String justification) {

		this.justification = justification;
	}


	// Relationships ----------------------------------------------------------

	private Administrator	administrator;
	private Event			event;


	@Valid
	@NotNull
	@ManyToOne()
	public Administrator getAdministrator() {
		return this.administrator;
	}
	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
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
