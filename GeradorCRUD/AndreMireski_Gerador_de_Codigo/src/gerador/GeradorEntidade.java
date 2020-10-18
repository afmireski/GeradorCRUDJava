/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerador;

import helpers.Capitalize;
import java.util.ArrayList;
import java.util.List;
import tools.ManipulaArquivo;

/**
 *
 * @author afmireski
 */
public class GeradorEntidade {

    private final Capitalize capitalize = new Capitalize();

    private final String nomeClasse;
    private final List<String> atributos;
    private final String destino;
    private final String autor;

    private final List<String> codigoGerado = new ArrayList();

    public GeradorEntidade(String nomeClasse, List<String> atributos,
            String destinyPackage, String autor) {
        this.nomeClasse = nomeClasse;
        this.atributos = atributos;
        this.destino = destinyPackage;
        this.autor = autor;
        this.gerar();
    }

    private void gerar() {
        codigoGerado.add("package " + destino + ";");
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
        String aux[];
        for (String atributo : atributos) {
            aux = atributo.split(";");
            codigoGerado.add("private " + aux[0] + " " + aux[1] + ";");
        }
    }

    private void gerarContrutores() {
        String aux[];
        String parametros = "";
        codigoGerado.add("\npublic " + nomeClasse + "() {\n}");
        for (String var : atributos) {
            aux = var.split(";");
            parametros += aux[0] + " " + aux[1];
            if (!var.equalsIgnoreCase(atributos.get(atributos.size() - 1))) {
                parametros += ", ";
            }
        }

        codigoGerado.add("public " + nomeClasse + "(" + parametros + ") {");
        for (String var : atributos) {
            aux = var.split(";");
            codigoGerado.add("this." + aux[1] + " = " + aux[1] + ";");
        }
        codigoGerado.add("}\n");
    }

    private void gerarGettersSetters() {
        String aux[];
        for (String var : atributos) {
            aux = var.split(";");
            codigoGerado.add("public " + aux[0] + " get" + capitalize.capitalizeTextUpper(aux[1]) + "() {\n"
                    + "        return " + aux[1] + ";\n"
                    + "    }\n"
                    + "public void set" + capitalize.capitalizeTextUpper(aux[1]) + "(" + aux[0] + " " + aux[1] + ") {\n"
                    + "        this." + aux[1] + " = " + aux[1] + ";\n"
                    + "    }\n");
        }
    }

    private void gerarToFk() {
        codigoGerado.add("public String toFk() {\n"
                + "        //GERA UMA STRING PARA SER USADA COMO FK\n");
        String fk[] = atributos.get(0).split(";"); //PEGA A PK DA ENTIDADE
        String atri[] = atributos.get(1).split(";"); //PEGA O PRIMEIRO ATRIBUTO DEPOIS DA PK       
        String retorno = fk[1] + "+ \" - \" +" + atri[1];
        codigoGerado.add("return " + retorno + ";\n}");
    }

    private void gerarToString() {
        String aux[];
        codigoGerado.add("@Override\n    public String toString() {");
        String retorno = "return ";
        for (int i = 0; i < atributos.size(); i++) {
            aux = atributos.get(i).split(";");
            retorno += aux[1];
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
        manipulaArquivo.salvarArquivo("src/" + destino + "/" + nomeClasse + ".java", codigoGerado);
    }

}
