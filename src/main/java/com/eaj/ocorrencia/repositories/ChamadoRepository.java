package com.eaj.ocorrencia.repositories;

import com.eaj.ocorrencia.models.Chamado;
import com.eaj.ocorrencia.models.Setor;
import com.eaj.ocorrencia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChamadoRepository extends JpaRepository<Chamado,Long> {
    List<Chamado> findAllByDeleteIsNull();
    List<Chamado> findAllByUserCloseIsNull();
    List<Chamado> findAllByUserCloseAndDeleteIsNullAndStatus(User user,String status);
    List<Chamado> getByUserOpenAndDeleteIsNullAndStatus(User user,String status);
    Integer countAllByStatusLike(String status);
    Integer countAllByStatusLikeAndUserClose(String status, User user);
    List<Chamado> findAllByStatus(String status);
    List<Chamado> findAllByStatusAndUserClose(String status, User user);
    List<Chamado> findAllByStatusAndSetor(String status, Setor setor);
    Chamado findByUserOpenAndId(User user, Long id);
}
