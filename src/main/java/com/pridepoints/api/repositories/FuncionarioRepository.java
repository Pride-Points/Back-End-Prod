package com.pridepoints.api.repositories;

import com.pridepoints.api.entities.Fisica;
import com.pridepoints.api.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    boolean existsByEmailAndIsAtivoTrue(String email);

    boolean existsByEmail(String email);

    List<Funcionario> findByEmpresaIdAndIsAtivoTrue(Long idEmpresa);

    List<Funcionario> findByEmpresaAndIsAtivoFalse(Long idEmpresa);
    List<Funcionario> findByIsAtivoFalse();

    Optional<Funcionario> findByEmail(String email);

    List<Funcionario> findByEmpresa_Id(Long empresaId);

}
