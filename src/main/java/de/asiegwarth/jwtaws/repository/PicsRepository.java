package de.asiegwarth.jwtaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.asiegwarth.jwtaws.entity.Pics;

@Repository
public interface PicsRepository extends JpaRepository<Pics, String> {

    @Query(nativeQuery = true, value = "select * from v_pics where email = :email")
    Pics findByEmail(@Param("email") String email);
}
