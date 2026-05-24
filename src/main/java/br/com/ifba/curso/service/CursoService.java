package br.com.ifba.curso.service;

import br.com.ifba.curso.entity.Curso;
import br.com.ifba.curso.repository.CursoRepository;
import br.com.ifba.infrastruture.util.StringUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação da camada de serviço para gerenciamento de Cursos.
 * Esta classe é um Bean gerenciado pelo Spring Boot onde ficam as regras de negócio.
 * @author mealf
 */
@Slf4j // Cria o objeto 'log' automaticamente para a classe
@Service
@RequiredArgsConstructor// Injeta o CursoRepository via construtor automaticamente para o atributo final
public class CursoService implements CursoIService {

    // Substituímos o antigo CursoIDao pelo CursoRepository
    private final CursoRepository cursoRepository;

    @Override
    @Transactional
    public void save(Curso curso) {
        log.info("Iniciando processo para salvar o curso: {}", curso.getNome());
        
        if (StringUtil.isEmpty(curso.getNome())){
            log.warn("Tentativa de salvar um curso com o nome vazio abortada.");
            throw new RuntimeException("O nome do curso não pode estar vazio!");
        }
        
        cursoRepository.save(curso);
        log.info("Curso '{}' salvo com sucesso no banco de dados.", curso.getNome());
    }

    @Override
    @Transactional
    public void update(Curso curso) {
        log.info("Atualizando dados do curso ID: {}", curso.getId());
        cursoRepository.save(curso);
        log.info("Curso ID {} atualizado com sucesso.", curso.getId());
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        log.info("Tentativa de remoção do curso com ID: {}", id);
        try {
            cursoRepository.deleteById(id);
            log.info("Curso com ID {} removido com sucesso.", id);
        } catch (Exception e) {
            log.error("Falha ao tentar remover o curso com ID: {}. Motivo: {}", id, e.getMessage());
            throw new Exception("Erro técnico ao remover: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        log.info("Buscando a listagem completa de todos os cursos.");
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findByNomeOrDescricao(String termo) {
        log.info("Executando busca genérica pelo termo: '{}'", termo);
        return cursoRepository.findByNomeContainingIgnoreCaseOrDescricaoContainingIgnoreCase(termo, termo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Curso> findByText(String termo) throws Exception {
        log.info("Filtrando busca textual pelo termo: '{}'", termo);
        if (termo.length() < 2) {
            log.debug("Termo muito curto ('{}'). Retornando listagem completa para otimização.", termo);
            return cursoRepository.findAll(); 
        }
        return cursoRepository.findByNomeContainingIgnoreCaseOrDescricaoContainingIgnoreCase(termo, termo);
    }
}