package com.omni.cadastro_cnpj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omni.cadastro_cnpj.entity.Cnpj;

@Repository
public interface CnpjRepository extends JpaRepository<Cnpj, Long> {
	Optional<Cnpj> findByCnpj(String cnpj);
	boolean existsByCnpj(String cnpj);
}
