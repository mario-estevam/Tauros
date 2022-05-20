package com.eaj.ocorrencia.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "setor_id")
    private Long id;

    private String nome;



}
