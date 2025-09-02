package com.omni.cadastro_cnpj.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cnpjs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cnpj {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private String razaoSocial;

    @Column(nullable = false)
    private String nomeFantasia;

    @OneToMany(mappedBy = "cnpj", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Socio> socios;
}
