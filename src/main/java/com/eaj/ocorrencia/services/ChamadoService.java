package com.eaj.ocorrencia.services;

import com.eaj.ocorrencia.models.Chamado;
import com.eaj.ocorrencia.models.User;
import com.eaj.ocorrencia.repositories.ChamadoRepository;
import com.eaj.ocorrencia.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class ChamadoService {

    ChamadoRepository repository;


    @Autowired
    private void setChamadoRepository(ChamadoRepository repository){ this.repository = repository; }

    public Chamado insert(Chamado chamado){
        SimpleDateFormat data = new SimpleDateFormat(Constantes.FORMATADATA);
        String date = data.format(new Date());
        chamado.setData(date);
        chamado.setStatus(Constantes.STATUS_ABERTO);
        return repository.save(chamado);
    }

    public void atualizarStatus(Chamado chamado){
        repository.save(chamado);
    }

    public Chamado update(Chamado chamado){
        chamado.setStatus(Constantes.STATUS_ANDAMENTO);
        return repository.save(chamado);
    }


    public Chamado finalizar(Chamado chamado){
        chamado.setStatus(Constantes.STATUS_CONCLUIDO);
        return repository.save(chamado);
    }


    public void delete(Long id){
        Date date = new Date();
        Chamado chamado = repository.getById(id);
        chamado.setDelete(date);
        repository.save(chamado);
    }

    public Chamado findById(Long id){
        return repository.getById(id);
    }

    public Chamado findByUsuarioAndId(User user, Long id){
        return repository.findByUserOpenAndId(user, id);
    }

    public List<Chamado> getAll(){
        List<Chamado> chamados = repository.findAllByDeleteIsNull();
        chamados.forEach(obj -> {
            try {
                SimpleDateFormat data = new SimpleDateFormat(Constantes.FORMATADATA);
                String date = data.format(new Date());
                Date date1 = new SimpleDateFormat(Constantes.FORMATADATA).parse(date);
                Date date2 = new SimpleDateFormat(Constantes.FORMATADATA).parse(obj.getData());
                long diff = date1.getTime() - date2.getTime();
                TimeUnit time = TimeUnit.DAYS;
                long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
                if ( (diffrence >= 30) && (!obj.getStatus().equals(Constantes.STATUS_ATRASADO))){
                    obj.setStatus(Constantes.STATUS_ATRASADO);
                    atualizarStatus(obj);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        return  chamados;
    }

    public List<Chamado> chamadosEmAberto(){
        return repository.findAllByUserCloseIsNull();
    }

    public List<Chamado> meusChamados(User user,String status){
        return repository.getByUserOpenAndDeleteIsNullAndStatus(user,status);
    }

    public List<Chamado> meusAtendimentos(User user, String status){
        return repository.findAllByUserCloseAndDeleteIsNullAndStatus(user, status);
    }

    public Integer totalConcluidos(){
        return repository.countAllByStatusLike(Constantes.STATUS_CONCLUIDO);
    }

    public Integer totalAbertos(){
        return repository.countAllByStatusLike(Constantes.STATUS_ABERTO);
    }

    public Integer totalEmAndamento(){
        return repository.countAllByStatusLike(Constantes.STATUS_ANDAMENTO);
    }

    public Integer totalEmAtraso(){
        return repository.countAllByStatusLike(Constantes.STATUS_ATRASADO);
    }

    public List<Chamado> findByStatus(String status){
        return repository.findAllByStatus(status);
    }

    public Page<Chamado> findPaginated(Pageable pageable, String status){
        final List<Chamado> verbas = repository.findAllByStatus(status);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Chamado> list;
        if(verbas.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem + pageSize, verbas.size());
            list = verbas.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), verbas.size());
    }

}
