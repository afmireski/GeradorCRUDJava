package gerador;

import helpers.Capitalize;
import java.util.ArrayList;
import java.util.List;
import tools.ManipulaArquivo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author afmireski
 */
public class GeradorTableScreen {

    private final Capitalize capitalize = new Capitalize();

    private final String nomeClasse;
    private final List<String> atributos;
    private final String destinyPackage;
    private final String destinyPath;
    private final String autor;
    private final String entidade;

    public GeradorTableScreen(String nomeClasse, List<String> atributos, String destinyPackage, String destinyPath, String autor, String entidade) {
        this.nomeClasse = nomeClasse + "TableScreen";
        this.atributos = atributos;
        this.destinyPackage = destinyPackage;
        this.destinyPath = destinyPath == null ? "" : destinyPath;
        this.autor = autor;
        this.entidade = entidade;
        this.gerar();
    }

    private final List<String> codigoGerado = new ArrayList();

    private void gerar() {
        codigoGerado.add("package " + destinyPackage + ";");
        gerarImports();
        codigoGerado.add("/**\n"
                + " *\n"
                + " * @author " + autor + "\n"
                + " */");
        codigoGerado.add("public class " + nomeClasse + " extends JDialog{\n");
        gerarContainers();
        gerarPanels();
        gerarColunasLinhas(false);
        gerarTableModel();
        gerarListaDados();
        gerarConstrutor();
        codigoGerado.add("}");
        criarArquivo();
    }

    private void gerarImports() {
        codigoGerado.add("import java.awt.BorderLayout;\n"
                + "import java.awt.Container;\n"
                + "import java.awt.GridLayout;\n"
                + "import java.util.List;\n"
                + "import javax.swing.JDialog;\n"
                + "import javax.swing.JFrame;\n"
                + "import javax.swing.JPanel;\n"
                + "import javax.swing.JScrollPane;\n"
                + "import javax.swing.JTable;\n"
                + "import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;\n"
                + "import javax.swing.table.DefaultTableModel;\n"
                + "import models." + entidade + ";\n");
    }

    private void gerarContainers() {
        codigoGerado.add("//INSTANCIA DOS CONTAINERS\n"
                + "    Container container;\n");
    }

    private void gerarPanels() {
        codigoGerado.add("//INSTANCIA DOS PANELS\n"
                + "    //PANELS BASE\n"
                + "    JPanel panNorth = new JPanel();\n"
                + "    JPanel panSouth = new JPanel();\n"
                + "    JPanel panEast = new JPanel();\n"
                + "    JPanel panWest = new JPanel();\n"
                + "    JPanel panBody = new JPanel();\n");
    }

    private void gerarColunasLinhas(boolean inConstrutor) {
        codigoGerado.add("//TABELA");
        String colunas = "";
        String aux[];
        for (String atributo : atributos) {
            aux = atributo.split(";");
            colunas += "\"" + aux[1].toUpperCase() + "\", ";
        }
        colunas = colunas.substring(0, colunas.length() - 2);
        codigoGerado.add("String colunas[] = new String[]{" + colunas + "};");
        if (inConstrutor) {
            codigoGerado.add("Object dados[][] = new Object[this." + capitalize.capitalizeTextLower(entidade) + "s.size()][colunas.length];\n");
        } else {
            codigoGerado.add("String linhas[][] = new String[0][colunas.length];\n");
        }
    }

    private void gerarTableModel() {
        codigoGerado.add("DefaultTableModel tableModel = new DefaultTableModel(linhas, colunas);\n"
                + "    JTable listTable = new JTable(tableModel);\n\n"
                + "    private JScrollPane scrollTable = new JScrollPane();\n");
    }

    private void gerarListaDados() {
        codigoGerado.add("//DADOS\n    List<" + entidade + "> " + capitalize.capitalizeTextLower(entidade) + "s;");
    }

    private void gerarConstrutor() {
        codigoGerado.add("\n\npublic " + nomeClasse + "(List<" + entidade + "> " + capitalize.capitalizeTextLower(entidade) + "s) {");
        codigoGerado.add("this." + capitalize.capitalizeTextLower(entidade) + "s = " + capitalize.capitalizeTextLower(entidade) + "s;\n");
        gerarScreenDefaultConfigurations();
        gerarContainerConfigurations();
        codigoGerado.add("\npanBody.setLayout(new GridLayout(1, 1));");
        gerarColunasLinhas(true);
        codigoGerado.add("String aux[];\n"
                + "\n"
                + "        for (int i = 0; i < this." + capitalize.capitalizeTextLower(entidade) + "s.size(); i++) {\n"
                + "            aux = this." + capitalize.capitalizeTextLower(entidade) + "s.get(i).toString().split(\";\");\n"
                + "            for (int j = 0; j < colunas.length; j++) {\n"
                + "                dados[i][j] = aux[j];\n"
                + "            }\n"
                + "        }\n"
                + "        panBody.add(scrollTable);\n"
                + "        scrollTable.setViewportView(listTable);\n"
                + "        tableModel.setDataVector(dados, colunas);");
        gerarLastDefaultConfigurations();
        codigoGerado.add("}");
    }

    private void gerarScreenDefaultConfigurations() {
        codigoGerado.add("        setDefaultCloseOperation(DISPOSE_ON_CLOSE);\n"
                + "        setLocationRelativeTo(null);\n"
                + "        setTitle(\"CRUD - " + entidade.toUpperCase() + "\");\n");
    }

    private void gerarContainerConfigurations() {
        codigoGerado.add("\n\t//CONTAINER CONFIGURATIONS\n"
                + "        container = getContentPane();\n"
                + "        container.setLayout(new BorderLayout());\n"
                + "        container.add(panNorth, BorderLayout.NORTH);\n"
                + "        container.add(panSouth, BorderLayout.SOUTH);\n"
                + "        container.add(panEast, BorderLayout.EAST);\n"
                + "        container.add(panWest, BorderLayout.WEST);\n"
                + "        container.add(panBody, BorderLayout.CENTER);\n");
    }
    
    private void gerarLastDefaultConfigurations() {
        codigoGerado.add("\n\tsetModal(true);"
                + "\tpack();" 
                + "\tsetVisible(true);\n");
    }

    private void criarArquivo() {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(destinyPath+"/src/" + destinyPackage + "/" + nomeClasse + ".java", codigoGerado);
    }

}
