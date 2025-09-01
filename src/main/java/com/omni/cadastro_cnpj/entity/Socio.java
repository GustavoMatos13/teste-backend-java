package com.omni.cadastro_cnpj.entity;


import com.omni.cadastro_cnpj.enuns.TipoSocio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "socios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Socio {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSocio tipo; // "PF" ou "PJ"

    @Column(nullable = false)
    private String documento; // CPF ou CNPJ do sócio

    @Column(nullable = false)
    private String nome; // PF = nome, PJ = nomeFantasia do CNPJ

    @Column(nullable = false)
    private Double porcentagemParticipacao;

    // CNPJ que este sócio participa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cnpj_id")
    private Cnpj cnpj;

    // Caso o sócio seja um CNPJ, referência ao CNPJ que é sócio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cnpj_socio")
    private Cnpj cnpjSocio;
}
