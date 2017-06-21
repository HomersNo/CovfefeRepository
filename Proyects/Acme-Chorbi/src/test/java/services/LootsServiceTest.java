
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
import domain.Loots;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LootsServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private LootsService			lootsService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private EventService			eventService;

	Calendar						calendarValida	= new GregorianCalendar(2017, 05, 14);
	Date							fechaValida		= this.calendarValida.getTime();

	Calendar						calendarFutura	= new GregorianCalendar(2020, 12, 14);
	Date							fechaFutura		= this.calendarFutura.getTime();

	String							tracerValido	= new String("12324-43251");
	String							tracerInvalido	= new String("1234-43251");


	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un loot.
				"title", "description", "Recommended", "event5", null
			}, {	// Creación correcta de un loot.
				"title", "description", "Recommended", "event6", null
			}, {	// Creación correcta de un loot.
				"title", "description", "Recommended", "event7", null
			}, {	// Creación correcta de un loot.
				"title", "description", "Recommended", "event8", null
			}, {	// Creación correcta de un loot.
				"title", "description", "Recommended", "event9", null
			}, {	// Creación incorrecta de un loot ya que el evento ha pasado.
				"title", "description", "Recommended", "event10", AssertionError.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void templateCreation(final String title, final String description, final String assessment, final String event, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate("admin");
			final Loots l = this.lootsService.create();
			l.setTitle(title);
			l.setDescription(description);
			l.setAssessment(assessment);
			l.setEvent(this.eventService.findOne(this.extract(event)));
			l.setJustification(null);
			this.lootsService.save(l);
			this.unauthenticate();
			this.lootsService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
