
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.OndaleckRepository;
import domain.Administrator;
import domain.Event;
import domain.Manager;
import domain.Ondaleck;
import forms.CancelOndalek;

@Service
@Transactional
public class OndaleckService {

	@Autowired
	OndaleckRepository		covfefeRepository;

	@Autowired
	ManagerService			managerService;

	@Autowired
	AdministratorService	adminService;

	@Autowired
	Validator				validator;


	public OndaleckService() {

		super();
	}

	public Ondaleck create(final Event event) {

		final Ondaleck result = new Ondaleck();

		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setEvent(event);
		return result;

	}

	public Collection<Ondaleck> findAll() {

		return this.covfefeRepository.findAll();
	}

	public Ondaleck findOne(final int covfefeId) {

		return this.covfefeRepository.findOne(covfefeId);
	}

	public Ondaleck save(final Ondaleck covfefe) {

		Ondaleck result;

		covfefe.setMoment(new Date(System.currentTimeMillis() - 1));
		Assert.isTrue(this.covfefeRepository.findByEvent(covfefe.getEvent().getId()) == null, "covfefe.event.duplicated");
		if (covfefe.getId() == 0)
			Assert.isTrue(covfefe.getEvent().getMoment().after(new Date()), "covfefe.event.past");
		result = this.covfefeRepository.save(covfefe);

		return result;

	}

	public void delete(final Ondaleck covfefe) {

		this.covfefeRepository.delete(covfefe);
	}

	public Ondaleck reconstruct(final Ondaleck covfefe, final BindingResult binding) {

		Ondaleck result;
		result = this.create(covfefe.getEvent());
		if (covfefe.getId() != 0)
			result = this.findOne(covfefe.getId());

		result.setDescription(covfefe.getDescription());
		result.setEvent(covfefe.getEvent());
		result.setMoment(covfefe.getMoment());
		result.setAssessment(covfefe.getAssessment());
		result.setTitle(covfefe.getTitle());
		if (covfefe.getId() == 0) {
			result.setUniqueLabel(this.randomKey());
			result.setAdmin(this.adminService.findByPrincipal());
		}

		this.validator.validate(result, binding);

		return result;
	}
	//Queries

	public Collection<Ondaleck> findAllByAdmin(final Administrator manager) {

		Collection<Ondaleck> result;
		result = new ArrayList<Ondaleck>();

		result = this.covfefeRepository.findAllByAdmin(manager.getId());

		return result;
	}

	public Ondaleck findByEvent(final Event event) {

		Ondaleck result;
		result = this.covfefeRepository.findByEvent(event.getId());
		return result;
	}

	public Double ratioCovfefesWithMoreThanThree() {

		Double result;
		result = this.covfefeRepository.ratioEventsOndaleck();
		return result;

	}

	public Manager managerWithMoreCovfefesWith5() {

		List<Manager> aux;
		Manager result = null;

		aux = new ArrayList<Manager>();
		aux.addAll(this.covfefeRepository.managerWithMoreEventsWithOndaleck());

		if (!aux.isEmpty())
			result = aux.get(0);

		return result;
	}

	public Collection<Ondaleck> findAllNotCanceled() {

		return this.covfefeRepository.findAllNotCanceled();
	}

	public Collection<Ondaleck> findAllByPrincipal() {

		return this.findAllByAdmin(this.adminService.findByPrincipal());
	}

	public void flush() {

		this.covfefeRepository.flush();
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

		result = this.randomAlphaNumeric() + this.randomAlphaNumeric() + this.randomAlphaNumeric() + this.randomAlphaNumeric() + this.randomAlphaNumeric() + "#" + this.randomNumber() + this.randomNumber() + this.randomNumber() + this.randomNumber()
			+ this.randomNumber();

		return result;
	}

	public Ondaleck cancel(final CancelOndalek canceler) {
		Ondaleck ondaleck;
		Ondaleck result;
		ondaleck = canceler.getCovfefe();

		ondaleck.setJustification(canceler.getJustification());

		result = this.covfefeRepository.save(ondaleck);

		return result;

	}

}
