package br.com.ifba.curso.service;

import br.com.ifba.curso.dao.CursoIDao;
import br.com.ifba.curso.entity.Curso;
import br.com.ifba.infrastruture.util.StringUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementação da camada de serviço para gerenciamento de Cursos.
 * Esta classe é um Bean gerenciado pelo Spring Boot onde ficam as regras de negócio.
 * @author mealf
 */
@Service
public class CursoService implements CursoIService {

    // O Spring se encarrega de injetar a instância correta do DAO aqui
    private final CursoIDao cursoDao;

    // A anotação @Autowired no construtor faz a Injeção de Dependência automática do Bean de DAO
    @Autowired
    public CursoService(CursoIDao cursoDao) {
        this.cursoDao = cursoDao;
    }
    
    @Override
    public void save(Curso curso) {
        // Regra de negócio: impede que um curso seja salvo sem nome
        if (StringUtil.isEmpty(curso.getNome())){
            throw new RuntimeException("O nome do curso não pode estar vazio!");
        }
        cursoDao.save(curso);
    }

    @Override
    public void update(Curso curso) {
        // Atualiza os dados utilizando o ecossistema do Spring
        cursoDao.update(curso);
    }

    @Override
    public void delete(Long id) throws Exception {
        try {
            // Cria uma instância temporária apenas com o ID para enviar ao DAO de deleção
            Curso curso = new Curso();
            curso.setId(id);
            
            cursoDao.delete(curso);
        } catch (Exception e) {
            // Captura qualquer erro de banco e relança com uma mensagem amigável
            throw new Exception("Erro técnico ao remover: " + e.getMessage());
        }
    }

    @Override
    public List<Curso> findAll() {
        // Repassa a busca de listagem completa para o DAO
        return cursoDao.findAll();
    }

    @Override
    public List<Curso> findByNomeOrDescricao(String termo) {
        // Busca personalizada repassada ao DAO
        return cursoDao.findByNomeOrDescricao(termo);
    }
    
    @Override
    public List<Curso> findByText(String termo) throws Exception {
        // Regra de negócio: se o termo for muito curto, traz tudo para evitar buscas ineficientes
        if (termo.length() < 2) {
            return cursoDao.findAll(); 
        }
        return cursoDao.findByNomeOrDescricao(termo);
    }
}