package br.com.ifba.infrastruture.dao;

import br.com.ifba.infrastruture.entity.PersistenceEntity;
import java.util.List;

/**
 * Interface genérica para o padrão DAO (Data Access Object).
 * Define as operações contratuais de um CRUD para qualquer entidade do sistema.
 * * @author mealf
 * @param <Entity> Qualquer classe que estenda PersistenceEntity
 */
public interface GenericIDao<Entity extends PersistenceEntity> {
    
    // Salva uma nova instância da entidade no banco de dados
    public abstract Entity save(Entity objeto);
    
    // Altera os dados de uma instância existente no banco de dados
    public abstract Entity update(Entity objeto);
    
    // Deleta uma instância existente do banco de dados
    public abstract void delete(Entity objeto);
    
    // Lista todos os registros daquela entidade salvos no banco
    public abstract List<Entity> findAll();
    
    // Encontra uma entidade específica baseada na sua Chave Primária (ID)
    public abstract Entity findById(Long id);
}