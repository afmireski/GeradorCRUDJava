package modelsGerador;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelGerador extends AbstractTableModel {

    private List<Atributo> lista;
    private String[] colunas = new String[]{"Nome", "Tipo","Tamanho"};

    public ModelGerador() {
        this.lista = new ArrayList<Atributo>();
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
        Atributo AtributoSelecionado = lista.get(rowIndex);
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
        int ultimoIndice = getRowCount();
        fireTableRowsDeleted(0, ultimoIndice);
    }
    
    public void removeModel(int index) {
        lista.remove(index);
        int ultimoIndice = getRowCount();
        fireTableRowsDeleted(index, index);
    }
    
    public void addModel(String name, String type, String size){
        Atributo atributo = new Atributo(name, type, size);
        lista.add(atributo);
        fireTableDataChanged();
        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public List<Atributo> getLista() {
        return new ArrayList<Atributo>(lista);
    }

}

