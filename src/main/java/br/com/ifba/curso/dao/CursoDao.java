package br.com.ifba.curso.dao;

import br.com.ifba.curso.entity.Curso;
import br.com.ifba.infrastruture.dao.GenericDao;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// Avisa ao mecanismo do Spring Boot que esta classe é um componente gerenciado (Bean) da camada de dados
@Repository
public class CursoDao extends GenericDao<Curso> implements CursoIDao {

    // Método customizado para procurar o curso pelo nome ou pela descrição ignorando maiúsculas/minúsculas
    @Override
    @Transactional(readOnly = true) // Define como transação de leitura para melhorar o desempenho no Postgres
    public List<Curso> findByNomeOrDescricao(String termo) {
        
        // Query utilizando JPQL para buscar de forma parcial (LIKE) em ambos os campos
        String jpql = "SELECT c FROM Curso c WHERE lower(c.nome) LIKE lower(:termo) " +
                      "OR lower(c.descricao) LIKE lower(:termo)";

        // Executa a busca injetando o termo com os caracteres curinga (%) do SQL
        // Nota: O Spring gerencia a abertura e fechamento desta sessão sozinho!
        return entityManager.createQuery(jpql, Curso.class)
                            .setParameter("termo", "%" + termo + "%")
                            .getResultList();
    }
}