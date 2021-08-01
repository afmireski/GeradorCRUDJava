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
public class GeradorEntidade {

    private final Capitalize capitalize = new Capitalize();

    private final String nomeClasse;
    private final List<Atributo> atributos;
    private final String destinyPackage;
    private final String destinyPath;
    private final String autor;

    private final List<String> codigoGerado = new ArrayList();

    public GeradorEntidade(String nomeClasse, List<Atributo> atributos,
            String destinyPackage, String destinyPath, String autor) {
        this.nomeClasse = nomeClasse;
        this.atributos = atributos;
        this.destinyPackage = destinyPackage;
        this.destinyPath = destinyPath == null ? "" : destinyPath;
        this.autor = autor;
        this.gerar();
    }

    private void gerar() {
        codigoGerado.add("package " + destinyPackage + ";");
        codigoGerado.add("/**\n"
                + " *\n"
                + " * @author " + autor + "\n"
                + " */");
        codigoGerado.add("public class " + nomeClasse + " {\n");
        gerarAtributos();
        gerarContrutores();
        gerarGettersSetters();
        gerarToFk();
        gerarToString();
        codigoGerado.add("}");
        criarArquivo();
    }

    private void gerarAtributos() {
        for (Atributo atributo : atributos) {
            if (atributo.getType().equalsIgnoreCase("Image")) {                 
                codigoGerado.add("private String " + atributo.getName() + ";");
            } else {
                codigoGerado.add("private " + atributo.getType() + " " + atributo.getName() + ";");
            }
        }
    }

    private void gerarContrutores() {
        String aux[];
        String parametros = "";
        codigoGerado.add("\npublic " + nomeClasse + "() {\n}");
        for (Atributo atributo : atributos) {            
            if (atributo.getType().equalsIgnoreCase("Image")) {                 
                parametros += "String" + " " + atributo.getName();
            } else {
                parametros += atributo.getType() + " " + atributo.getName();
            }
            
            if (!atributo.equals(atributos.get(atributos.size() - 1))) {
                parametros += ", ";
            }
        }

        codigoGerado.add("public " + nomeClasse + "(" + parametros + ") {");
        for (Atributo atributo : atributos) {
            codigoGerado.add("this." + atributo.getName() + " = " + atributo.getName() + ";");
        }
        codigoGerado.add("}\n");
    }

    private void gerarGettersSetters() {
        String aux[];
        for (Atributo atributo : atributos) {
            if (atributo.getType().equalsIgnoreCase("Image")) {                 
               codigoGerado.add("public String get" + capitalize.capitalizeTextUpper(atributo.getName()) + "() {\n"
                    + "        return " + atributo.getName() + ";\n"
                    + "    }\n"
                    + "public void set" + capitalize.capitalizeTextUpper(atributo.getName()) + "(String " + atributo.getName() + ") {\n"
                    + "        this." + atributo.getName() + " = " + atributo.getName() + ";\n"
                    + "    }\n");
            } else {
                codigoGerado.add("public " + atributo.getType() + " get" + capitalize.capitalizeTextUpper(atributo.getName()) + "() {\n"
                    + "        return " + atributo.getName() + ";\n"
                    + "    }\n"
                    + "public void set" + capitalize.capitalizeTextUpper(atributo.getName()) + "(" + atributo.getType() + " " + atributo.getName() + ") {\n"
                    + "        this." + atributo.getName() + " = " + atributo.getName() + ";\n"
                    + "    }\n");
            }
            
        }
    }

    private void gerarToFk() {
        codigoGerado.add("public String toFk() {\n"
                + "        //GERA UMA STRING PARA SER USADA COMO FK\n");
        Atributo fk = atributos.get(0); //PEGA A PK DA ENTIDADE
        Atributo atri = atributos.get(1); //PEGA O PRIMEIRO ATRIBUTO DEPOIS DA PK       
        String retorno = fk.getName() + "+ \" - \" +" + atri.getName();
        codigoGerado.add("return " + retorno + ";\n}");
    }

    private void gerarToString() {
        codigoGerado.add("@Override\n    public String toString() {");
        String retorno = "return ";
        for (int i = 0; i < atributos.size(); i++) {
            Atributo atributo = atributos.get(i);
            retorno += atributo.getName();
            if (i < atributos.size() - 1) {
                retorno += " + \";\" + ";
            } else {
                retorno += ";\n}";
            }
        }
        codigoGerado.add(retorno);
    }

    private void criarArquivo() {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(destinyPath + "/src/" + destinyPackage + "/" + nomeClasse + ".java", codigoGerado);
    }

}
