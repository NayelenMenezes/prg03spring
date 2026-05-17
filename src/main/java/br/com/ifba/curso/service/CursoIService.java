package br.com.ifba.curso.service;

import br.com.ifba.curso.entity.Curso;
import java.util.List;

/**
 * Interface que define o contrato de regras de negócio para a entidade Curso.
 * @author mealf
 */
public interface CursoIService {
    
    public void save(Curso curso);
    
    public void update(Curso curso);
    
    void delete(Long id) throws Exception;
    
    public List<Curso> findAll();
    
    public List<Curso> findByNomeOrDescricao(String termo);
    
    List<Curso> findByText(String termo) throws Exception;
}