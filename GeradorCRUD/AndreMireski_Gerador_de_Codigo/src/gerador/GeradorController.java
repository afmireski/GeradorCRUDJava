/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerador;

import helpers.Capitalize;
import java.util.ArrayList;
import java.util.List;
import modelsGerador.Atributo;
import tools.ManipulaArquivo;

/**
 *
 * @author afmireski
 */
public class GeradorController {

    private final Capitalize capitalize = new Capitalize();

    private final String nomeClasse;
    private final List<Atributo> atributos;
    private final String destinyPackage;
    private final String destinyPath;
    private final String autor;
    private final String entidade;

    private final List<String> codigoGerado = new ArrayList();

    public GeradorController(String nomeClasse, List<Atributo> atributos,
            String destinyPackage, String destinyPath, String autor, String entidade) {
        this.nomeClasse = nomeClasse + "Controller";
        this.atributos = atributos;
        this.destinyPackage = destinyPackage != null ? destinyPackage : "controller";
        this.destinyPath = destinyPath == null ? "" : destinyPath;
        this.autor = autor != null ? autor : "anônimo";
        this.entidade = entidade;
        gerar();
    }

    private void gerar() {
        codigoGerado.add("package " + destinyPackage + ";");
        gerarImports();
        codigoGerado.add("/**\n"
                + " *\n"
                + " * @author " + autor + "\n"
                + " */");
        codigoGerado.add("public class " + nomeClasse + " {\n");
        gerarListAndHelpers();
        gerarConstrutor();
        gerarCRUDActions();
        gerarGetFkList();
        gerarSaveData();
        gerarLoadData();
        codigoGerado.add("}");
        criarArquivo();
    }

    private void gerarImports() {
        codigoGerado.add("import functions.ConvertToEnum;\n"
                + "import java.util.ArrayList;\n"
                + "import java.util.List;\n"
                + "import tools.CaixaDeFerramentas;\n"
                + "import tools.ManipulaArquivo;"
                + "import models." + entidade + ";");
    }

    private void gerarListAndHelpers() {
        codigoGerado.add("private final ConvertToEnum convert = new ConvertToEnum();\n"
                + "    private final CaixaDeFerramentas cf = new CaixaDeFerramentas();\n"
                + "    \n"
                + "    private List<" + entidade + "> list = new ArrayList<>();");
    }

    private void gerarConstrutor() {
        codigoGerado.add("public " + nomeClasse + "() {\n"
                + "    }");
    }

    private void gerarCRUDActions() {
        codigoGerado.add("public void clearList() {\n"
                + "        //Limpa a lista;\n"
                + "        list.clear();\n"
                + "    }");
        codigoGerado.add("public void create(" + entidade + " " + capitalize.capitalizeTextLower(entidade) + ") {\n"
                + "        //Adiciona um Objeto " + entidade + " a lista;\n"
                + "        list.add(" + capitalize.capitalizeTextLower(entidade) + ");\n"
                + "    }");
        codigoGerado.add("public List<" + entidade + "> listar() {\n"
                + "        return list;\n"
                + "    }");
        Atributo pk = atributos.get(0);
        codigoGerado.add("public " + entidade + " retrieve(" + pk.getType() + " " + pk.getName() + ") {\n"
                + "        for (int i = 0; i < list.size(); i++) {\n"
                + "            if (list.get(i).get" + capitalize.capitalizeTextUpper(pk.getName()) + "()"
                + verificacaoRetrieve(pk.getType(), pk.getName()) + ") {\n"
                + "                return list.get(i);\n"
                + "            }\n"
                + "        }\n"
                + "        return null;\n"
                + "    }");
        codigoGerado.add("public void update(" + entidade + " old" + entidade + ", " + entidade + " new" + entidade + ") {\n"
                + "        list.set(list.indexOf(old" + entidade + "), new" + entidade + ");\n"
                + "    }\n"
                + "");
        codigoGerado.add("public void delete(" + entidade + " " + capitalize.capitalizeTextLower(entidade) + ") {\n"
                + "        list.remove(" + capitalize.capitalizeTextLower(entidade) + ");\n"
                + "    }");

    }

    private void gerarGetFkList() {        
        codigoGerado.add("public List<String> getFkList() {\n"
                + "//GERA UMA LISTA DE STRINGS EM FORMA DE CHAVE ESTRAGEIRA\n"
                + "        List<String> fks = new ArrayList<>();\n"
                + "        for (" + entidade + " " + capitalize.capitalizeTextLower(entidade) + " : list) {\n"
                + "            String fk = " + capitalize.capitalizeTextLower(entidade) + ".toFk();\n"
                + "            fks.add(fk);\n"
                + "        }\n"
                + "        return fks;\n}");
    }

    private String verificacaoRetrieve(String type, String var) {
        switch (type) {
            case "String":
                return ".equals(" + var + ")";
            default:
                return " == " + var;
        }
    }

    private void gerarSaveData() {
        codigoGerado.add("\n\n");
        codigoGerado.add("public void saveData(String path) {\n"
                + "        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();\n"
                + "        List<String> stringList = new ArrayList<>();\n"
                + "        for (" + entidade + " " + capitalize.capitalizeTextLower(entidade) + " : list) {\n"
                + "            stringList.add(" + capitalize.capitalizeTextLower(entidade) + ".toString());\n"
                + "        }\n"
                + "        manipulaArquivo.salvarArquivo(path, stringList);\n"
                + "    }\n");
    }

    private void gerarLoadData() {
        codigoGerado.add("public void loadData(String path) {\n"
                + "        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();\n"
                + "        if (!manipulaArquivo.existeOArquivo(path)) {\n"
                + "            manipulaArquivo.criarArquivoVazio(path);\n"
                + "        }\n"
                + "\n"
                + "        List<String> stringList = manipulaArquivo.abrirArquivo(path);\n"
                + "        //converter de CSV para " + entidade + "\n"
                + "        " + entidade + " " + capitalize.capitalizeTextLower(entidade) + ";\n"
                + "        for (String string : stringList) {\n"
                + "            String aux[] = string.split(\";\");\n"
                + "            " + capitalize.capitalizeTextLower(entidade) + " = new " + entidade + "(\n"
                + gerarNew()
                + "            );\n"
                + "            list.add(" + capitalize.capitalizeTextLower(entidade) + ");\n"
                + "        }\n"
                + "    }");
    }
    
    private String gerarNew() {
        String s = "";
        for (int i = 0; i < atributos.size(); i++) {
            Atributo atributo = atributos.get(i);
            System.out.println("Atributo.type -> " + atributo.getType());
                switch (atributo.getType()) {
                    case "byte":
                        s += "Byte.valueOf(aux[" + i + "] )";
                        break;
                    case "short":
                        s +=  "Short.valueOf(aux[" + i + "] )";
                        break;
                    case "int":
                        s += "Integer.valueOf(aux[" + i + "] )";
                        break;
                    case "double":
                        s += "Double.valueOf(aux[" + i + "] )";
                        break;
                    case "float":
                        s +=  "Float.valueOf(aux[" + i + "])";
                        break;
                    case "long":
                        s += "Long.valueOf(aux[" + i + "] )";
                        break;
                    case "Date":
                        s += "cf.converteDeStringParaDate(aux[" + i + "] )";                    
                        break;
                    case "boolean":
                        s += "aux[" + i + "].equals(\"Sim\")";
                        break;
                    case "String":
                    default:
                        s += "aux[" + i + "]";
            }        
            if (i < atributos.size() - 1) {
                s += ",";
            }
            s += " //" + atributo.getName() + "\n";
        }
        return s;
    }

    private void criarArquivo() {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(destinyPath + "/src/" + destinyPackage + "/" + nomeClasse + ".java", codigoGerado);
    }

}
