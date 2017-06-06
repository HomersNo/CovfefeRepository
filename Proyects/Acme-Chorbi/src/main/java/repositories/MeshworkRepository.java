
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Meshwork;

@Repository
public interface MeshworkRepository extends JpaRepository<Meshwork, Integer> {

	@Query("select c from Meshwork c where c.administrator.id = ?1")
	Collection<Meshwork> findAllByAdministrator(int adminId);

	@Query("select c from Meshwork c where c.event.id = ?1")
	Meshwork findByEvent(int eventId);

	@Query("select c from Meshwork c where c.id = ?1")
	Meshwork findOne(int meshworkId);

	@Query("select c from Meshwork c where c.justification = '' or c.justification is null")
	Collection<Meshwork> findAllNotCanceled();

}
