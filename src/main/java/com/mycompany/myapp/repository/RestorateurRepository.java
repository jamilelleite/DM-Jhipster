package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Restorateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Restorateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestorateurRepository extends JpaRepository<Restorateur, Long> {}
