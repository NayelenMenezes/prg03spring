package br.com.ifba.curso.service;

import br.com.ifba.curso.entity.Curso;
import br.com.ifba.curso.repository.CursoRepository;
import br.com.ifba.infrastruture.util.StringUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação da camada de serviço para gerenciamento de Cursos.
 * Esta classe é um Bean gerenciado pelo Spring Boot onde ficam as regras de negócio.
 * @author mealf
 */
@Service
public class CursoService implements CursoIService {

    // Substituímos o antigo CursoIDao pelo CursoRepository
    private final CursoRepository cursoRepository;

    // A anotação @Autowired no construtor faz a Injeção de Dependência automática do Bean de DAO
    @Autowired
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }
    
    @Override
    @Transactional
    public void save(Curso curso) {
        // Regra de negócio: impede que um curso seja salvo sem nome
        if (StringUtil.isEmpty(curso.getNome())){
            throw new RuntimeException("O nome do curso não pode estar vazio!");
        }
        cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void update(Curso curso) {
        // Atualiza os dados utilizando o ecossistema do Spring
        cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        try {
            //JpaRepository deleta direto pelo ID, sem precisar do objeto temporário
            cursoRepository.deleteById(id);
        } catch (Exception e) {
            // Captura qualquer erro de banco e relança com uma mensagem amigável
            throw new Exception("Erro técnico ao remover: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        // Busca tudo usando o método nativo do Spring Data
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findByNomeOrDescricao(String termo) {
        // Busca personalizada repassada ao DAO
        return cursoRepository.findByNomeContainingIgnoreCaseOrDescricaoContainingIgnoreCase(termo, termo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Curso> findByText(String termo) throws Exception {
        // Regra de negócio: se o termo for muito curto, traz tudo para evitar buscas ineficientes
        if (termo.length() < 2) {
            return cursoRepository.findAll(); 
        }
        return cursoRepository.findByNomeContainingIgnoreCaseOrDescricaoContainingIgnoreCase(termo, termo);
    }
}