
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Covfefe extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Covfefe() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	title;
	private String	description;
	private Date	moment;
	private Integer	score;
	private String	uniqueLabel;
	private String	justification;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
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

	@Max(5)
	@Min(0)
	@NotNull
	public Integer getScore() {
		return this.score;
	}
	public void setScore(final Integer score) {
		this.score = score;
	}

	public String getJustification() {
		return this.justification;
	}

	public void setJustification(final String justification) {

		this.justification = justification;
	}


	// Relationships ----------------------------------------------------------

	private Manager	manager;
	private Event	event;


	@Valid
	@NotNull
	@ManyToOne()
	public Manager getManager() {
		return this.manager;
	}
	public void setManager(final Manager manager) {
		this.manager = manager;
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
