package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Conseil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Conseil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConseilRepository extends JpaRepository<Conseil, Long> {}
