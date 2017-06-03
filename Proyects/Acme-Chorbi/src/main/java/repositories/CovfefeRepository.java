
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Covfefe;
import domain.Manager;

@Repository
public interface CovfefeRepository extends JpaRepository<Covfefe, Integer> {

	@Query("select c from Covfefe c where c.manager.id = ?1")
	Collection<Covfefe> findAllByManager(int managerId);

	@Query("select c from Covfefe c where c.event.id = ?1")
	Covfefe findByEvent(int eventId);

	@Query("select count(c)*1.0/(select count(c)*1.0 from Covfefe c) from Covfefe c where c.score > 3")
	Double ratioCovfefesWithMoreThanThree();

	@Query("select c.manager from Covfefe c where c.score = 5 group by c.manager order by count(c.score) DESC")
	Collection<Manager> managerWithMoreCovfefesWith5();

	@Query("select c from Covfefe c where c.id = ?1")
	Covfefe findOne(int covfefeId);
}
