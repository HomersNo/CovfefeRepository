
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MeshworkRepository;
import domain.Administrator;
import domain.Event;
import domain.Meshwork;

@Service
@Transactional
public class MeshworkService {

	@Autowired
	MeshworkRepository		meshworkRepository;

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	EventService			eventService;

	@Autowired
	Validator				validator;


	public MeshworkService() {

		super();
	}

	public Meshwork create() {

		final Meshwork result = new Meshwork();
		Administrator principal;

		principal = this.administratorService.findByPrincipal();

		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setAdministrator(principal);
		result.setUniqueLabel(this.randomKey());

		return result;

	}

	public Collection<Meshwork> findAll() {

		return this.meshworkRepository.findAll();
	}

	public Meshwork findOne(final int meshworkId) {

		return this.meshworkRepository.findOne(meshworkId);
	}

	public Meshwork save(final Meshwork meshwork) {

		Meshwork result;

		meshwork.setMoment(new Date(System.currentTimeMillis() - 1));

		result = this.meshworkRepository.save(meshwork);

		return result;

	}

	public void delete(final Meshwork meshwork) {

		this.meshworkRepository.delete(meshwork);
	}

	public Meshwork reconstruct(final Meshwork meshwork, final BindingResult binding) {

		Meshwork result;
		result = this.create();
		if (meshwork.getId() != 0)
			result = this.findOne(meshwork.getId());

		result.setDescription(meshwork.getDescription());
		result.setEvent(meshwork.getEvent());
		result.setMoment(meshwork.getMoment());
		result.setAssesment(meshwork.getAssesment());
		result.setTitle(meshwork.getTitle());
		if (meshwork.getId() == 0)
			meshwork.setUniqueLabel(this.randomKey());

		result.setAdministrator(this.administratorService.findByPrincipal());

		this.validator.validate(result, binding);

		return result;
	}
	//Queries

	public Collection<Meshwork> findAllByAdministrator(final Administrator administrator) {

		Collection<Meshwork> result;
		result = new ArrayList<Meshwork>();

		result = this.meshworkRepository.findAllByAdministrator(administrator.getId());

		return result;
	}

	public Meshwork findByEvent(final Event event) {

		Meshwork result;
		result = this.meshworkRepository.findByEvent(event.getId());
		return result;
	}

	public Collection<Meshwork> findAllNotCanceled() {

		return this.meshworkRepository.findAllNotCanceled();
	}

	public Collection<Meshwork> findAllByPrincipal() {

		return this.findAllByAdministrator(this.administratorService.findByPrincipal());
	}

	public void flush() {

		this.meshworkRepository.flush();
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

		result += /* aquí generamos la clave */this.randomNumber() + this.randomNumber() + this.randomNumber() + this.randomNumber() + "-" + this.randomLetter() + this.randomLetter() + this.randomLetter();

		return result;
	}

	public void checkEvent(final Event event) {

		Assert.isTrue(this.eventService.findAllNoMeshwork().contains(event), "message.error.authority");
		Assert.isTrue(event.getMoment().after(new Date()), "message.error.authority");

	}
}
