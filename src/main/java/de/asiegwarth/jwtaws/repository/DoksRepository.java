package de.asiegwarth.jwtaws.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.asiegwarth.jwtaws.entity.Doks;

@Repository
public interface DoksRepository extends JpaRepository<Doks, Doks> {

    Page<Doks> findByEmail(String email, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM v_doks WHERE email = :email")
    List<Doks> listAllFiles(@Param("email") String email);

}
