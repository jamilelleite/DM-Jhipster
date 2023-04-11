package polytech.info.gl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import polytech.info.gl.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
