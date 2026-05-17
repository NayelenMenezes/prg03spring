package br.com.ifba.curso.controller;

import br.com.ifba.curso.entity.Curso;
import br.com.ifba.curso.service.CursoIService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementação do Controlador de Cursos.
 * Esta classe é um Bean gerenciado pelo Spring (@Component) que delega as chamadas da tela para os serviços.
 * @author mealf
 */
@Component
public class CursoController implements CursoIController {

    // O Spring se encarrega de injetar o componente de serviço correto aqui
    private final CursoIService cursoService;

    // A anotação @Autowired realiza a Injeção de Dependência do Bean de Serviço
    @Autowired
    public CursoController(CursoIService cursoService) {
        this.cursoService = cursoService;
    }
    
    @Override
    public void save(Curso curso) {
       // Repassa a ordem de salvar para a camada de serviço
       cursoService.save(curso);
    }

    @Override
    public void update(Curso curso) {
       // Repassa a ordem de atualizar para a camada de serviço
       cursoService.update(curso);
    }

    @Override
    public void delete(Long id) throws Exception {
        // Repassa a remoção por ID para a camada de serviço
        this.cursoService.delete(id);
    }

    @Override
    public List<Curso> findAll() {
        // Solicita a listagem completa de cursos para o serviço
        return cursoService.findAll();
    }

    @Override
    public List<Curso> findByNomeOrDescricao(String termo) {
        // Solicita a busca por termo para o serviço
        return cursoService.findByNomeOrDescricao(termo);
    }
    
    @Override
    public List<Curso> findByText(String termo) throws Exception {
        // Solicita a busca textual filtrada com regras de negócio para o serviço
        return this.cursoService.findByText(termo);
    }
}