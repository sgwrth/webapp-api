package de.asiegwarth.jwtaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.asiegwarth.jwtaws.entity.SystemVars;

@Repository
public interface SystemVarsRepository extends JpaRepository<SystemVars, Long> {
    
    @Query(nativeQuery = true, value = "SELECT get_max_upload_size_in_bytes()")
    public String getMaxUploadSize();
}
