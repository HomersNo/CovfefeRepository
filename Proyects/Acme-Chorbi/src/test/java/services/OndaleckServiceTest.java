
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Event;
import domain.Ondaleck;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
public class OndaleckServiceTest extends AbstractTest {

	@Autowired
	private OndaleckService			ondaleckService;

	@Autowired
	private EventService			eventService;

	@Autowired
	private AdministratorService	adminService;


	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Likes.
				"admin", "event8", "HIGH", "Pues me parece un evento muy bueno", "Gran evento", null
			}, {	// Creación correcta de un Likes: Text vacío.
				"admin2", "event8", "MEDIUM", "Pues me parece un evento muy mediocre", "Gran evento", null
			}, {	// Creación erronea de un Likes: Segundo like a una segunda persona, el mensaje que de error en la consola es esperado.
				"admin", "event8", "LOW", "Pues me parece un evento muy malo", "Gran evento", null
			}, {	// Creación errónea de un Likes: sin autenticar.
				"admin", "event8", "HIGH", "Pues me parece un evento muy bueno", "Gran evento", null
			}, {	// Creación incorrecta de un Likes: a sí mismo
				"admin2", "event8", "MEDIUM", "Pues me parece un evento muy mediocre", "Gran evento", null
			}, {	// Creación incorrecta de un Likes: estrellas fuera de rango
				"admin", "event5", "HIGH", "Pues me parece que esto va a fallar", "Gran evento", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void templateCreation(final String user, final String event, final String assessment, final String description, final String title, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			final Event event1 = this.eventService.findOne(this.extract(event));
			final Ondaleck c = this.ondaleckService.create(event1);
			c.setAssessment(assessment);
			c.setDescription(description);
			c.setTitle(title);
			c.setUniqueLabel(this.ondaleckService.randomKey());
			c.setAdmin(this.adminService.findByPrincipal());

			final Ondaleck saved = this.ondaleckService.save(c);
			this.ondaleckService.flush();
			this.ondaleckService.delete(saved);
			this.ondaleckService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
