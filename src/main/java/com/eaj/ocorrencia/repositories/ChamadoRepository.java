package com.eaj.ocorrencia.repositories;

import com.eaj.ocorrencia.models.Chamado;
import com.eaj.ocorrencia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado,Long> {
    List<Chamado> findAllByDeleteIsNull();
    List<Chamado> findAllByUserCloseIsNull();
    List<Chamado> getByUserOpen(User user);
}
