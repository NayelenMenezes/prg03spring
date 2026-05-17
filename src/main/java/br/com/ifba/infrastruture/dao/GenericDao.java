package br.com.ifba.infrastruture.dao;

import br.com.ifba.infrastruture.entity.PersistenceEntity;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

// Suprime os avisos de conversão de tipo (cast) que o Java reclama por causa do Generics
@SuppressWarnings("unchecked")
@Transactional // Garante que as operações de escrita aconteçam dentro de uma transação automática
public abstract class GenericDao<Entity extends PersistenceEntity> implements GenericIDao<Entity> {
    
    // O Spring gerencia as conexões e injeta o EntityManager correto aqui de forma segura
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Entity save(Entity entity) {
        // Se o ID for nulo, o Spring JPA insere (persist), se já tiver ID ele atualiza (merge)
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public Entity update(Entity entity) {
        // Sincroniza o estado do objeto vindo da View com o banco de dados
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Entity entity) {
        // Reanexa o objeto ao contexto atual do Hibernate do Spring e realiza a remoção
        Entity mergedEntity = entityManager.merge(entity); 
        entityManager.remove(mergedEntity);
    }

    @Override
    @Transactional(readOnly = true) // Otimiza a busca avisando que é uma operação apenas de leitura
    public List<Entity> findAll() {
        // Pegamos a classe correta através do método corrigido
        Class<Entity> typeClass = getTypeClass();
        
        // Buscamos no banco fazendo a consulta HQL/JPQL dinâmica
        // Nota: O cast para List<Entity> resolve o problema de tipos incompatíveis da IDE
        return (List<Entity>) entityManager.createQuery("from " + typeClass.getName(), typeClass).getResultList();
    }

    @Override
    @Transactional(readOnly = true) // Otimiza a busca por ID apenas para leitura
    public Entity findById(Long id) {
        // Busca direta no banco utilizando a chave primária e a classe correta
        return entityManager.find(getTypeClass(), id);
    }

    /**
     * Técnica de Reflection corrigida para descobrir dinamicamente qual entidade está usando a classe filha.
     * Alterado o retorno para Class<Entity> para evitar erros de tipos incompatíveis no JPA.
     */
    protected Class<Entity> getTypeClass() {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<Entity>) type;
    }
}