package br.com.ifba.curso.entity;

import br.com.ifba.infrastruture.entity.PersistenceEntity;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cursos")
@NoArgsConstructor // Gera o construtor vazio exigido pelo JPA automaticamente
public class Curso extends PersistenceEntity implements Serializable {
    
    @Getter @Setter
    private String nome;
    
    @Getter @Setter
    private int quantidade;
    
    @Getter @Setter
    private String descricao;
    
    @Getter @Setter
    private String fornecedor;
}