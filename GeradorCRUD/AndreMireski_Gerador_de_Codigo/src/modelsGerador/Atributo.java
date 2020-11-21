/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelsGerador;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author afmireski
 */
public class Atributo {
    private String name;
    private String type;
    private String size;

    public Atributo() {
    }
    
    public Atributo(String name, String type, String size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    
    public List<String> toListString(List<Atributo> atributos) {
        List<String> stringList = new ArrayList<>();
        for (Atributo atributo : atributos) {
            stringList.add(atributo.getType() + ";" + atributo.getName() + ";" + atributo.getSize());
        }
        return stringList;        
    }
    
    public List<Atributo> forListStringToList(List<String> atributos) {
        List<Atributo> atributosList = new ArrayList<>();
        for (String atributo : atributos) {
            String aux[] = atributo.split(";");
            Atributo var = new Atributo(aux[0], aux[1], aux[2]);
            atributosList.add(var);
        }
        return atributosList;        
    }

    @Override
    public String toString() {
        return "Atributo{" + "name=" + name + ", type=" + type + ", size=" + size + '}';
    }
    
    
    
}
