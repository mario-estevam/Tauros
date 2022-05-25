package com.eaj.ocorrencia.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "setor_id")
    private Long id;

    private String nome;

    private Date delete;


}
