
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;
import domain.Ondaleck;

@Repository
public interface OndaleckRepository extends JpaRepository<Ondaleck, Integer> {

	@Query("select c from Ondaleck c where c.admin.id = ?1")
	Collection<Ondaleck> findAllByAdmin(int adminId);

	@Query("select c from Ondaleck c where c.event.id = ?1")
	Ondaleck findByEvent(int eventId);

	@Query("select count(o)*1.0/(select count(e)*1.0 from Event e) from Ondaleck o")
	Double ratioEventsOndaleck();

	@Query("select o.event.organiser from Ondaleck o group by o.event.organiser order by count(o.event) DESC")
	Collection<Manager> managerWithMoreEventsWithOndaleck();

	@Query("select c from Ondaleck c where c.id = ?1")
	Ondaleck findOne(int covfefeId);

	@Query("select c from Ondaleck c where c.justification = '' or c.justification is null")
	Collection<Ondaleck> findAllNotCanceled();

}
