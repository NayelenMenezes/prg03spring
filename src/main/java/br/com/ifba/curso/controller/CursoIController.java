package br.com.ifba.curso.controller;

import br.com.ifba.curso.entity.Curso;
import java.util.List;

/**
 * Interface que define o contrato do Controlador para a entidade Curso.
 * Funciona como o ponto de entrada da View para acessar as regras de negócio.
 * @author mealf
 */
public interface CursoIController {
    
    public void save(Curso curso);
    
    public void update(Curso curso);
    
    void delete(Long id) throws Exception;
    
    public List<Curso> findAll();
    
    public List<Curso> findByNomeOrDescricao(String termo);
    
    List<Curso> findByText(String termo) throws Exception;
}