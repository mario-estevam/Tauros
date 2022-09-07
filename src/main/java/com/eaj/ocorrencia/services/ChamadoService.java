package com.eaj.ocorrencia.services;

import com.eaj.ocorrencia.models.Chamado;
import com.eaj.ocorrencia.models.User;
import com.eaj.ocorrencia.repositories.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class ChamadoService {

    ChamadoRepository repository;

    @Autowired
    private void setChamadoRepository(ChamadoRepository repository){ this.repository = repository; }

    public Chamado insert(Chamado chamado){
        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
        String date = data.format(new Date());
        chamado.setData(date);
        chamado.setStatus("ABERTO");
        return repository.save(chamado);
    }

    public Chamado atualizarStatus(Chamado chamado){
        return repository.save(chamado);
    }

    public Chamado update(Chamado chamado){
        chamado.setStatus("ANDAMENTO");
        return repository.save(chamado);
    }


    public Chamado finalizar(Chamado chamado){
        chamado.setStatus("CONCLUIDO");
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

    public List<Chamado> getAll(){
        List<Chamado> chamados = repository.findAllByDeleteIsNull();
        chamados.forEach(obj -> {
            try {
                SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
                String date = data.format(new Date());
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(obj.getData());
                long diff = date1.getTime() - date2.getTime();
                TimeUnit time = TimeUnit.DAYS;
                long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
                if ( (diffrence >= 30) && (!obj.getStatus().equals("ATRASADO"))){
                    obj.setStatus("ATRASADO");
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

    public List<Chamado> meusChamados(User user){
        return repository.getByUserOpenAndDeleteIsNull(user);
    }

    public List<Chamado> meusAtendimentos(User user){
        return repository.findAllByUserCloseAndDeleteIsNull(user);
    }

    public Integer totalConcluidos(){
        return repository.countAllByStatusLike("CONCLUIDO");
    }

    public Integer totalAbertos(){
        return repository.countAllByStatusLike("ABERTO");
    }

    public Integer totalEmAndamento(){
        return repository.countAllByStatusLike("ANDAMENTO");
    }

    public Integer totalEmAtraso(){
        return repository.countAllByStatusLike("ATRASADO");
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

        return new PageImpl<Chamado>(list, PageRequest.of(currentPage, pageSize), verbas.size());
    }

}
