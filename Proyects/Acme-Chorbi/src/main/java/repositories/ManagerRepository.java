
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	//Queries de la dashboard
	@Query("select e.organiser from Event e group by e.organiser order by count(e) DESC")
	Collection<Manager> findManagersOrderByEvent();

	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccountId(int userAcountId);

	@Query("select e.organiser from Event e where e in (select c.event from Meshwork c) group by e.organiser order by count(e) DESC")
	Collection<Manager> managerWithMoreMeshworksWith5();

}
