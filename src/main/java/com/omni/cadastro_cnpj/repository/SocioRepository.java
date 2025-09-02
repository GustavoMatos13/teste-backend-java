package com.omni.cadastro_cnpj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omni.cadastro_cnpj.entity.Socio;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {

}
