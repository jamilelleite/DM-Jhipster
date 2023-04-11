package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CooperativeLocal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CooperativeLocal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CooperativeLocalRepository extends JpaRepository<CooperativeLocal, Long> {}
