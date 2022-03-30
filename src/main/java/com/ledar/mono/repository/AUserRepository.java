package com.ledar.mono.repository;

import com.ledar.mono.domain.AUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AUserRepository extends JpaRepository<AUser, Long> {}
