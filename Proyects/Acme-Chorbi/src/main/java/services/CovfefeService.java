
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CovfefeRepository;
import domain.Covfefe;
import domain.Event;
import domain.Manager;

@Service
@Transactional
public class CovfefeService {

	@Autowired
	CovfefeRepository	covfefeRepository;

	@Autowired
	ManagerService		managerService;


	public CovfefeService() {

		super();
	}

	public Covfefe create() {

		final Covfefe result = new Covfefe();
		Manager principal;

		principal = this.managerService.findByPrincipal();

		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setManager(principal);

		return result;

	}

	public Covfefe findOne(final int covfefeId) {

		return this.covfefeRepository.findOne(covfefeId);
	}

	public Covfefe save(final Covfefe covfefe) {

		Covfefe result;

		covfefe.setMoment(new Date(System.currentTimeMillis() - 1));

		result = this.covfefeRepository.save(covfefe);

		return result;

	}

	public void delete(final Covfefe covfefe) {

		this.covfefeRepository.delete(covfefe);
	}

	//Queries

	public Collection<Covfefe> findAllByManager(final Manager manager) {

		Collection<Covfefe> result;
		result = new ArrayList<Covfefe>();

		result = this.covfefeRepository.findAllByManager(manager.getId());

		return result;
	}

	public Covfefe findByEvent(final Event event) {

		Covfefe result;
		result = this.covfefeRepository.findByEvent(event.getId());
		return result;
	}

	public Double ratioCovfefesWithMoreThanThree() {

		Double result;
		result = this.covfefeRepository.ratioCovfefesWithMoreThanThree();
		return result;

	}

	public Manager managerWithMoreCovfefesWith5() {

		List<Manager> aux;
		Manager result = null;

		aux = new ArrayList<Manager>();
		aux.addAll(this.covfefeRepository.managerWithMoreCovfefesWith5());

		if (!aux.isEmpty())
			result = aux.get(0);

		return result;
	}
}
