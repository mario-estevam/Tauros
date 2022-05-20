package com.eaj.ocorrencia.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Problema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "problema_id")
    private Long id;

    private String nome;

    @ManyToOne
    Setor setor;
}
