package br.com.ifba.curso.dao;

import br.com.ifba.curso.entity.Curso;
import br.com.ifba.infrastruture.dao.GenericIDao;
import java.util.List;

/**
 * Interface específica para as operações de banco de dados da entidade Curso.
 * Estende o GenericIDao trazendo as funções básicas de CRUD por herança.
 */
public interface CursoIDao extends GenericIDao<Curso> {
    
    // Define o método personalizado para buscar cursos por termos de texto
    List<Curso> findByNomeOrDescricao(String termo);
}