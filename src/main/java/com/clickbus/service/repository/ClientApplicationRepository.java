package com.clickbus.service.repository;

import com.clickbus.service.domain.ClientApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClientApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientApplicationRepository extends JpaRepository<ClientApplication, Long> {

}
