
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LootsRepository;
import domain.Administrator;
import domain.Event;
import domain.Loots;
import domain.Manager;

@Service
@Transactional
public class LootsService {

	@Autowired
	LootsRepository			lootsRepository;

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	Validator				validator;


	public LootsService() {

		super();
	}

	public Loots create() {

		final Loots result = new Loots();
		Administrator principal;

		principal = this.administratorService.findByPrincipal();

		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setAdministrator(principal);
		result.setUniqueTracer(this.randomKey());

		return result;

	}

	public Collection<Loots> findAll() {

		return this.lootsRepository.findAll();
	}

	public Loots findOne(final int lootsId) {

		return this.lootsRepository.findOne(lootsId);
	}

	public Loots save(final Loots loots) {

		Loots result;

		loots.setMoment(new Date(System.currentTimeMillis() - 1));

		Assert.assertTrue(loots.getEvent().getMoment().after(new Date(System.currentTimeMillis() - 1)));

		result = this.lootsRepository.save(loots);

		return result;

	}

	public void delete(final Loots loots) {

		this.lootsRepository.delete(loots);
	}

	public Loots reconstruct(final Loots loots, final BindingResult binding) {

		Loots result;
		result = this.create();
		if (loots.getId() != 0)
			result = this.findOne(loots.getId());

		result.setDescription(loots.getDescription());
		result.setEvent(loots.getEvent());
		result.setMoment(loots.getMoment());
		result.setAssessment(loots.getAssessment());
		result.setTitle(loots.getTitle());
		result.setUniqueTracer(loots.getUniqueTracer());

		result.setAdministrator(this.administratorService.findByPrincipal());

		this.validator.validate(result, binding);

		return result;
	}
	//Queries

	public Collection<Loots> findAllByAdministrator(final Administrator administrator) {

		Collection<Loots> result;
		result = new ArrayList<Loots>();

		result = this.lootsRepository.findAllByAdministrator(administrator.getId());

		return result;
	}

	public Loots findByEvent(final Event event) {

		Loots result;
		result = this.lootsRepository.findByEvent(event.getId());
		return result;
	}

	public Double ratioLootsNotCanceledEvents() {

		Double result;
		result = this.lootsRepository.ratioLootsNotCanceledEvents();
		return result;

	}

	public Manager managerWithMoreLoots() {

		List<Manager> aux;
		Manager result = null;

		aux = new ArrayList<Manager>();
		aux.addAll(this.lootsRepository.managerWithMoreLoots());

		if (!aux.isEmpty())
			result = aux.get(0);

		return result;
	}

	public Collection<Loots> findAllNotCanceled() {

		return this.lootsRepository.findAllNotCanceled();
	}

	public Collection<Loots> findAllByPrincipal() {

		return this.findAllByAdministrator(this.administratorService.findByPrincipal());
	}

	public void flush() {

		this.lootsRepository.flush();
	}

	//Random methods

	public String randomAlphaNumeric() {
		char result;
		final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final Random random = new Random();
		result = alphabet.charAt(random.nextInt(62));
		return Character.toString(result);
	}

	public String randomLetter() {
		char result;
		final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random random = new Random();
		result = alphabet.charAt(random.nextInt(52));
		return Character.toString(result);
	}

	public String randomNumber() {
		char result;
		final String alphabet = "1234567890";
		final Random random = new Random();
		result = alphabet.charAt(random.nextInt(10));
		return Character.toString(result);
	}

	public String dateKeyGenerator(final String format) {

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		final String moment = simpleDateFormat.format(new Date());

		return moment;
	}

	public String randomKey() {

		String result = "";

		result += this.randomNumber() + this.randomNumber() + this.randomNumber() + this.randomNumber() + this.randomNumber() + "-" + this.randomNumber() + this.randomNumber() + this.randomNumber() + this.randomNumber() + this.randomNumber();

		return result;
	}
}
