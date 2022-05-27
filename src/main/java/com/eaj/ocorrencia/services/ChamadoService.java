package com.eaj.ocorrencia.services;

import com.eaj.ocorrencia.models.Chamado;
import com.eaj.ocorrencia.models.User;
import com.eaj.ocorrencia.repositories.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ChamadoService {

    ChamadoRepository repository;

    @Autowired
    private void setChamadoRepository(ChamadoRepository repository){ this.repository = repository; }

    public Chamado insert(Chamado chamado){
        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        chamado.setData(date);
        chamado.setStatus("ABERTO");
        return repository.save(chamado);
    }

    public Chamado update(Chamado chamado){
        chamado.setStatus("ANDAMENTO");
        return repository.save(chamado);
    }

    public void delete(Long id){
        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Chamado chamado = repository.getById(id);
        chamado.setDelete(date);
        repository.save(chamado);
    }

    public Chamado findById(Long id){
        return repository.getById(id);
    }

    public List<Chamado> getAll(){
        return repository.findAllByDeleteIsNull();
    }

    public List<Chamado> chamadosEmAberto(){
        return repository.findAllByUserCloseIsNull();
    }

    public List<Chamado> meusChamados(User user){
        return repository.getByUserOpenAndDeleteIsNull(user);
    }

}
