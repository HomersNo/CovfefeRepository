
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Loots;
import domain.Manager;

@Repository
public interface LootsRepository extends JpaRepository<Loots, Integer> {

	@Query("select l from Loots l where l.administrator.id = ?1")
	Collection<Loots> findAllByAdministrator(int administratorId);

	@Query("select l from Loots l where l.event.id = ?1")
	Loots findByEvent(int eventId);

	@Query("select count(l)*1.0/(select count(e)*1.0 from Event e) from Loots l where l.justification = '' or l.justification is null")
	Double ratioLootsNotCanceledEvents();

	@Query("select l.event.organiser from Loots l group by l.event.organiser order by count(l.event.organiser) DESC")
	Collection<Manager> managerWithMoreLoots();

	@Query("select l from Loots l where l.id = ?1")
	Loots findOne(int lootsId);

	@Query("select l from Loots l where l.justification = '' or l.justification is null")
	Collection<Loots> findAllNotCanceled();

}
