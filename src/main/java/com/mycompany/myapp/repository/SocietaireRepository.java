package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Societaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Societaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocietaireRepository extends JpaRepository<Societaire, Long> {}
