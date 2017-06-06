
package services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Event;
import domain.Meshwork;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MeshworkServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private MeshworkService	meshworkService;

	@Autowired
	private EventService	eventService;

	Calendar				calendarValida	= new GregorianCalendar(2017, 05, 05);
	Date					fechaValida		= this.calendarValida.getTime();


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Event.
				"admin1", "event8", "nombre", "bien", this.fechaValida, "1234-ABC", "HIGHLY RECOMMENDED", null
			}, {		// Creación correcta de un Event.
				"admin1", "event8", "nombre", "bien", this.fechaValida, "1235-ABC", "HIGHLY RECOMMENDED", null
			}, {		// Creación correcta de un Event.
				"admin1", "event8", "nombre", "bien", this.fechaValida, "1236-ABC", "HIGHLY RECOMMENDED", null
			}, {		// Creación correcta de un Event.
				"admin1", "event8", "nombre", "bien", this.fechaValida, "1237-ABC", "HIGHLY RECOMMENDED", null
			}, {		// Creación correcta de un Event.
				"admin1", "event8", "nombre", "bien", this.fechaValida, "1238-ABC", "HIGHLY RECOMMENDED", null
			}, {		// Creación correcta de un Event.
				"admin1", "event5", "nombre", "bien", this.fechaValida, "1239-ABC", "HIGHLY RECOMMENDED", IllegalArgumentException.class
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String event, final String title, final String description, final Date moment, final String uniqueLabel, final String assesment, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Meshwork m = this.meshworkService.create();

			m.setDescription(description);
			m.setTitle(title);
			m.setAssesment(assesment);

			final Event e = this.eventService.findOne(this.extract(event));

			this.meshworkService.checkEvent(e);

			m.setEvent(e);

			final Meshwork saved = this.meshworkService.save(m);
			this.meshworkService.delete(saved);
			this.unauthenticate();
			this.meshworkService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
