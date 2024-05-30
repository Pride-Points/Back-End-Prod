package com.pridepoints.api.repositories;

import com.pridepoints.api.entities.Fisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FisicaRepository extends JpaRepository<Fisica, Long> {

    boolean existsByEmail(String email);

    Optional<Fisica> findByEmail(String email);
}
