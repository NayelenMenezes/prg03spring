package br.com.ifba.infrastruture.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class PersistenceEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Melhor estratégia para o PostgreSQL
    private Long id;
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
}