package com.pridepoints.api.repositories;

import com.pridepoints.api.entities.Empresa;
import com.pridepoints.api.entities.Fisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    boolean existsByCnpj(String cnpj);
    Optional<Empresa> findByCnpj(String cnpj);
    Optional<Empresa> findByFuncionariosEmail(String email);

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.empresa.id = :empresaId")
    Optional<Double> calcularMediaAvaliacoes(@Param("empresaId") Long empresaId);
}
