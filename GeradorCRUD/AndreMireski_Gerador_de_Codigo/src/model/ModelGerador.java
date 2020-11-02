package model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelGerador extends AbstractTableModel {

    private List<Atributos> lista;
    private String[] colunas = new String[]{"Nome", "Tipo","Tamanho"};

    public ModelGerador() {
        this.lista = new ArrayList<Atributos>();
    }

    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int colunmIndex) {
        Atributos AtributoSelecionado = lista.get(rowIndex);
        String a = null;
        switch (colunmIndex) {
            case 0:
                a = AtributoSelecionado.getName();
                break;
            case 1:
                a = AtributoSelecionado.getType();
                break;
            case 2:
                a = AtributoSelecionado.getSize();
                break;
            default:
                System.err.println("Índice inválido");
        }

        return a;
    }

    public void clear() {
        lista.clear();
    }
    
    public void addModel(String name, String type, String size){
        Atributos atributo = new Atributos();
        atributo.setName(name);
        atributo.setType(type);
        atributo.setSize(size);
        lista.add(atributo);
        fireTableDataChanged();
        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public List<Atributos> getLista() {
        return new ArrayList<Atributos>(lista);
    }

}

class Atributos {
    private String name;
    private String type;
    private String size;

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
    
    
    
}
