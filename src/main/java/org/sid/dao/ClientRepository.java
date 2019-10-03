package org.sid.dao;

import org.sid.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository  extends JpaRepository<Client,Long> {
	
	
	@Query("Select c from Client c where c.code=:x ")
	public Page<Client> listClients(@Param("x")Long code,Pageable pageable);

	
	public Page<Client> findByNomContains(String mc,Pageable pageable);

}
