/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelsGerador;

import java.util.List;

/**
 *
 * @author AFMireski
 */
public class Crud {
    
    private String entidade;
    private String autor;
    private boolean imageCrud = false;
    private List<Atributo> atributos;

    public Crud() {
    }

    public Crud(String entidade, String autor, boolean isImageCrud, List<Atributo> atributos) {
        this.entidade = entidade;
        this.autor = autor;
        this.imageCrud = isImageCrud;
        this.atributos = atributos;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isImageCrud() {
        return imageCrud;
    }

    public void setImageCrud(boolean imageCrud) {
        this.imageCrud = imageCrud;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }

    
    
    
    
    
    
    
}
