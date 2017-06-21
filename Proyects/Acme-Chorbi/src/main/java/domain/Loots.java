
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(indexes = {
	@Index(columnList = "justification"), @Index(columnList = "administrator_id"), @Index(columnList = "event_id")
})
public class Loots extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Loots() {
		super();
	}


	public static final String	BEST	= "Highly Recommended";
	public static final String	OK		= "Recommended";
	public static final String	BAD		= "Not Recommended";

	// Attributes -------------------------------------------------------------

	private String				title;
	private String				description;
	private Date				moment;
	private String				assessment;
	private String				uniqueTracer;
	private String				justification;


	@NotBlank
	@Size(min = 1, max = 20)
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
	@Pattern(regexp = "^\\d{5}-\\d{5}$")
	public String getUniqueTracer() {
		return this.uniqueTracer;
	}
	public void setUniqueTracer(final String uniqueTracer) {
		this.uniqueTracer = uniqueTracer;
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

	@Pattern(regexp = "^" + Loots.BEST + "|" + Loots.OK + "|" + Loots.BAD + "$")
	@NotNull
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
