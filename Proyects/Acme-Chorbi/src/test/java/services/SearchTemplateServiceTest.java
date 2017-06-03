
package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.SearchTemplate;
import domain.SystemConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SearchTemplateServiceTest extends AbstractTest {

	//The SUT

	@Autowired
	private SearchTemplateService		searchTemplateService;

	@Autowired
	private ChorbiService				chorbiService;

	@Autowired
	private SystemConfigurationService	scService;


	// Tests
	/*
	 * A chorbi who is authenticated must be able to
	 * 
	 * o Change his or her search template.
	 * o Browse the results of his or her search template as long as he or she's registered a valid credit card.
	 * Note that the validity of the credit card must be checked every
	 * time the results of the search template are displayed.
	 * The results of search templates must be cached for at least 12 hours.
	 */

	@Test
	public void driverEdition() {
		final Object testingData[][] = {
			{	// Creaci�n de un nuevo search template para el chorbi que no tiene
				"chorbi3", null, null, null, null, null, null, null, null, null
			}, { // Edici�n del search template reci�n creado, pero sin editar
				"chorbi3", null, null, null, null, null, null, null, null, null
			}, { // Edici�n del search template por un usuario sin tarjeta de cr�dito
				"chorbi4", null, null, null, null, null, null, null, null, IllegalArgumentException.class
			}, { // B�squeda correcta por edad
				"chorbi1", 18, null, null, null, null, null, null, null, null
			}, { // B�squeda correcta por edad
				"chorbi1", 655535, null, null, null, null, null, null, null, null
			}, { // B�squeda correcta por Ciudad
				"chorbi1", null, "Sevilla", null, null, null, null, null, null, null
			}, { // B�squeda correcta por Ciudad
				"chorbi1", null, "", null, null, null, null, null, null, null
			}, { // B�squeda correcta por Pa�s
				"chorbi1", null, null, "Espa�a", null, null, null, null, null, null
			}, { // B�squeda correcta por G�nero
				"chorbi1", null, null, null, "WOMAN", null, null, null, null, null
			}, { // B�squeda correcta por G�nero
				"chorbi1", null, null, null, "MAN", null, null, null, null, null
			}, { // B�squeda correcta por Palabra clave
				"chorbi1", null, null, null, null, "mira", null, null, null, null
			}, { // B�squeda correcta por Provincia
				"chorbi1", null, null, null, null, null, "Andaluc�a", null, null, null
			}, { // B�squeda correcta por Tipo de relaci�n
				"chorbi1", null, null, null, null, null, null, "ACTIVITIES", null, null
			}, { // B�squeda correcta por Tipo de relaci�n
				"chorbi1", null, null, null, null, null, null, "FRIENDSHIP", null, null
			}, { // B�squeda correcta por Tipo de relaci�n
				"chorbi1", null, null, null, null, null, null, "LOVE", null, null
			}, { // B�squeda correcta por Estado
				"chorbi1", null, null, null, null, null, null, null, "Kansas", null
			}, { // B�squeda correcta completa
				"chorbi1", 18, "Sevilla", "Espa�a", "WOMAN", "mira", "Sevilla", "LOVE", "Andaluc�a", null
			}, { // B�squeda incorrecta completa, usuario sin tarjeta de cr�dito 
				"chorbi4", 18, "Sevilla", "Espa�a", "WOMAN", "mira", "Sevilla", "LOVE", "Andaluc�a", IllegalArgumentException.class
			}, { // B�squeda incorrecta por G�nero
				"chorbi1", null, null, null, "ELEFANTE", null, null, null, null, ConstraintViolationException.class
			}, { // B�squeda incorrecta por Tipo de relaci�n
				"chorbi1", null, null, null, null, null, null, "Incorrecto", null, ConstraintViolationException.class
			}, { // B�squeda incorrecta por edad
				"chorbi1", 17, null, null, null, null, null, null, null, ConstraintViolationException.class
			}, { // B�squeda incorrecta completa, edad
				"chorbi1", 17, "Sevilla", "Espa�a", "WOMAN", "mira", "Sevilla", "LOVE", "Andaluc�a", ConstraintViolationException.class
			}, { // B�squeda incorrecta completa, g�nero
				"chorbi1", 18, "Sevilla", "Espa�a", "HIPOP�TAMO", "mira", "Sevilla", "LOVE", "Andaluc�a", ConstraintViolationException.class
			}, { // B�squeda incorrecta completa, relaci�n
				"chorbi1", 18, "Sevilla", "Espa�a", "WOMAN", "mira", "Sevilla", "YU-GI-OH", "Andaluc�a", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSearch((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);

	}

	protected void templateSearch(final String username, final Integer age, final String city, final String country, final String genre, final String keyword, final String province, final String relationshipType, final String state, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final SystemConfiguration system = this.scService.findMain();

		final DateTime now = DateTime.now();
		try {
			this.authenticate(username);
			final Chorbi chorbi = this.chorbiService.findByPrincipal();
			SearchTemplate result;
			SearchTemplate search;
			search = this.searchTemplateService.create();
			search.setAge(age);
			search.setCity(city);
			search.setCountry(country);
			search.setGenre(genre);
			search.setKeyword(keyword);
			search.setProvince(province);
			search.setRelationshipType(relationshipType);
			search.setState(state);
			final Boolean sameFields = this.searchTemplateService.checkCache(search);
			SearchTemplate dbOne = this.searchTemplateService.findSearchTemplateByChorbi(chorbi);
			if (dbOne != null) {
				dbOne.setAge(age);
				dbOne.setCity(city);
				dbOne.setCountry(country);
				dbOne.setGenre(genre);
				dbOne.setKeyword(keyword);
				dbOne.setProvince(province);
				dbOne.setRelationshipType(relationshipType);
				dbOne.setState(state);
			} else
				dbOne = search;
			final DateTime last = new DateTime(dbOne.getMoment());
			if (!sameFields || !now.minus(system.getCacheTime().getTime()).isBefore(last)) {
				result = this.searchTemplateService.save(dbOne);
				Assert.isTrue(result.getMoment().after(new Date(System.currentTimeMillis() - 43200000)));
			}

			this.searchTemplateService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

			try {
				this.searchTemplateService.flush();
			} catch (final Throwable oops2) {
				caught = oops2.getClass();

			}
		}
		this.checkExceptions(expected, caught);

	}

}
