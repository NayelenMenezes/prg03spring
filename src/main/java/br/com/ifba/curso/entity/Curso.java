package br.com.ifba.curso.entity;

import br.com.ifba.infrastruture.entity.PersistenceEntity;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos")
public class Curso extends PersistenceEntity implements Serializable {
    
    private String nome;
    private int quantidade;
    private String descricao;
    private String fornecedor;

    public Curso() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
}