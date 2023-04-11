package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CooperativeNational;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CooperativeNational entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CooperativeNationalRepository extends JpaRepository<CooperativeNational, Long> {}
