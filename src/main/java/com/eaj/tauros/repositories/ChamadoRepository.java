package com.eaj.tauros.repositories;

import com.eaj.tauros.models.Chamado;
import com.eaj.tauros.models.Setor;
import com.eaj.tauros.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChamadoRepository extends JpaRepository<Chamado,Long> {
    List<Chamado> findAllByDeleteIsNull();
    List<Chamado> findAllByUserCloseIsNull();
    List<Chamado> findAllByUserCloseAndDeleteIsNullAndStatus(User user, String status);
    List<Chamado> getByUserOpenAndDeleteIsNullAndStatus(User user,String status);
    Integer countAllByStatusLike(String status);
    Integer countAllByStatusLikeAndSetor(String status, Setor setor);
    Integer countAllByStatusLikeAndUserClose(String status, User user);
    List<Chamado> findAllByStatus(String status);
    List<Chamado> findAllByStatusAndUserClose(String status, User user);
    List<Chamado> findAllByStatusAndSetor(String status, Setor setor);
    List<Chamado> findAllByStatusAndSetorAndUserClose(String status, Setor setor, User user);
    Chamado findByUserOpenAndId(User user, Long id);
}
