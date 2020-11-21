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
public class GeradorMain {

    private final Capitalize capitalize = new Capitalize();

    private final String nomeClasse;
    private final List<Atributo> atributos;
    private final String destinyPackage;
    private final String destinyPath;
    private final String autor;
    private final String entidade;

    private final List<String> codigoGerado = new ArrayList();

    public GeradorMain(String nomeClasse, List<Atributo> atributos, String destinyPackage, String destinyPath, String autor, String entidade) {
        this.nomeClasse = nomeClasse;
        this.atributos = atributos;
        this.destinyPackage = destinyPackage;
        this.destinyPath = destinyPath == null ? "" : destinyPath;
        this.autor = autor;
        this.entidade = entidade;

        this.gerar();
    }

    private void gerar() {
        codigoGerado.add("package " + destinyPackage + ";");
        gerarImports();
        codigoGerado.add("/**\n"
                + " *\n"
                + " * @author " + autor + "\n"
                + " */");
        codigoGerado.add("public class Main {\n");
        codigoGerado.add("/**\n"
                + "     * @param args the command line arguments\n"
                + "     */");
        gerarPSVM();
        codigoGerado.add("}");
        criarArquivo();
    }
    
    private void gerarImports() {
        codigoGerado.add("import screens."+capitalize.capitalizeClass(entidade)+"Screen;");
    }
    
    private void gerarPSVM() {
        codigoGerado.add("public static void main(String[] args) {");
        
        String newoEntidade = capitalize.capitalizeClass(entidade) + "Screen " 
                +capitalize.capitalizeVarLower(entidade) + "Screen" 
                + " = new " + capitalize.capitalizeClass(entidade) + "Screen();";
        codigoGerado.add(newoEntidade);
        codigoGerado.add("}");
    }

    private void criarArquivo() {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(destinyPath + "/src/" + destinyPackage + "/Main.java", codigoGerado);
    }

}
