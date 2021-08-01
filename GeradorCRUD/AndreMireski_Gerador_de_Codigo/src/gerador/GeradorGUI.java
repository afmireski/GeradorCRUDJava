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
public class GeradorGUI {

    private final Capitalize capitalize = new Capitalize();

    private final String nomeClasse;
    private final List<Atributo> atributos;
    private final String destinyPackage;
    private final String destinyPath;
    private final String autor;
    private final String entidade;

    public GeradorGUI(String nomeClasse, List<Atributo> atributos, String destinyPackage, String destinyPath, String autor, String entidade) {
        this.nomeClasse = nomeClasse + "Screen";
        this.atributos = atributos;
        this.destinyPackage = destinyPackage;
        this.destinyPath = destinyPath == null ? "" : destinyPath;
        this.autor = autor;
        this.entidade = entidade;
        gerar();
    }

    private Atributo pk;
    private final List<String> codigoGerado = new ArrayList();

    private void gerar() {
        codigoGerado.add("package " + destinyPackage + ";");
        gerarImports();
        codigoGerado.add("/**\n"
                + " *\n"
                + " * @author " + autor + "\n"
                + " */");
        codigoGerado.add("public class " + nomeClasse + " extends JDialog{\n");
        gerarHelpers();
        gerarFunctions();
        gerarTools();
        gerarContainers();
        gerarPanels();
        gerarButtons();
        gerarControllers();
        gerarLabels();
        gerarTextFields();
        gerarEntidades();
        gerarTableScreens();
        gerarConstrutor();
        gerarButtonsInitialConfiguration();
        gerarTxtInitialConfigurations();
        gerarClearAllFields();
        codigoGerado.add("}");
        criarArquivo();        
    }

    private void gerarImports() {
        codigoGerado.add("import functions.ConvertToEnum;\n"
                + "import functions.ConvertFromEnum;\n"
                + "import java.util.ArrayList;\n"
                + "import java.util.List;\n"
                + "import tools.CaixaDeFerramentas;\n"
                + "import tools.ManipulaArquivo;\n"
                + "import functions.VerifyPK;\n"
                + "import helpers.BuildConfirmDialog;\n"
                + "import helpers.BuildMessageDialog;\n"
                + "import helpers.ErrorTools;\n"
                + "import helpers.GenericComponents;\n"
                + "import java.awt.BorderLayout;\n"
                + "import java.awt.Color;\n"
                + "import java.awt.Container;\n"
                + "import java.awt.Desktop;\n"
                + "import java.awt.FlowLayout;\n"
                + "import java.awt.GridLayout;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import java.awt.event.ActionListener;\n"
                + "import java.awt.event.WindowAdapter;\n"
                + "import java.awt.event.WindowEvent;"
                + "import java.util.Date;\n"
                + "import java.util.List;\n"
                + "import javax.swing.BorderFactory;\n"
                + "import javax.swing.JButton;\n"
                + "import javax.swing.JFrame;\n"
                + "import javax.swing.JDialog;\n"
                + "import javax.swing.JLabel;\n"
                + "import javax.swing.JOptionPane;\n"
                + "import javax.swing.JPanel;\n"
                + "import javax.swing.JSpinner;\n"
                + "import javax.swing.JTextField;\n"
                + "import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;\n"
                + "import tools.CaixaDeFerramentas;\n"
                + "import tools.Tools;\n"
                + "import enums.DialogMessageType;\n"
                + "import enums.DialogConfirmType;\n"
                + "import controllers." + entidade + "Controller;\n"
                + "import models." + entidade + ";\n");
    }

    private void gerarHelpers() {
        codigoGerado.add("//INSTANCIA DOS HELPERS\n"
                + "    GenericComponents components = new GenericComponents();\n"
                + "    BuildMessageDialog messageDialog;\n"
                + "    BuildConfirmDialog confirmDialog;    \n"
                + "    ErrorTools errorTools = new ErrorTools();\n");
    }

    private void gerarFunctions() {
        codigoGerado.add("//INSTANCIA DOS FUNCTIONS\n"
                + "    ConvertFromEnum convertFromEnum = new ConvertFromEnum();\n"
                + "    ConvertToEnum convertToEnum = new ConvertToEnum();\n"
                + "    VerifyPK verifyPK = new VerifyPK();\n");
    }

    private void gerarTools() {
        codigoGerado.add("//INSTANCIA DAS TOOLS\n"
                + "    final private CaixaDeFerramentas cf = new CaixaDeFerramentas();\n"
                + "    Tools tools = new Tools();\n");
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

        codigoGerado.add("//BODY PANELS");
        for (int i = 0; i < atributos.size(); i++) {
            //GERA UM PAR DE PAINÉIS PARA CADA LINHA E COLUNA DO GRID DO PAN BODY
            for (int j = 0; j < 2; j++) {
                int l = i + 1;
                int c = j + 1;
                codigoGerado.add("JPanel panL" + l + "C" + c + " = new JPanel(); //Painel referente a posição da grade: Linha " + l + " - Coluna " + c);
            }

        }
        codigoGerado.add("\n");
    }

    private void gerarButtons() {
        codigoGerado.add("//INSTANCIA DOS BUTTONS\n"
                + "    JButton btnCreate = new JButton(\"Create\");\n"
                + "    JButton btnRetrieve = new JButton(\"Retrieve\");\n"
                + "    JButton btnUpdate = new JButton(\"Update\");\n"
                + "    JButton btnDelete = new JButton(\"Delete\");\n"
                + "    JButton btnAction = new JButton(\"Add to List\");\n"
                + "    JButton btnRemove = new JButton(\"Remove\");\n"
                + "    JButton btnCancel = new JButton(\"Cancel\");\n"
                + "    JButton btnList = new JButton(\"List\");\n");
    }

    private void gerarControllers() {
        codigoGerado.add("//INSTANCIA DOS CONTROLLERS\n"
                + "    String actionController;\n"
                + "    boolean listController = false;\n"
                + "    " + entidade + "Controller " + capitalize.capitalizeTextLower(entidade)
                + "Controller = new " + entidade + "Controller();\n");
    }

    private void gerarLabels() {
        codigoGerado.add("\n//INSTANCIA DOS LABELS");
        String aux[];
        for (Atributo atributo : atributos) {
            codigoGerado.add("JLabel lbl" + capitalize.capitalizeTextUpper(atributo.getName()) + " = new JLabel(\"" + atributo.getName().toUpperCase() + "\");");
        }
    }

    private void gerarTextFields() {
        codigoGerado.add("\n//INSTANCIA DOS TEXTFIELD");
        pk = atributos.get(0);
        for (Atributo atributo : atributos) {
            codigoGerado.add("JTextField txt" + capitalize.capitalizeTextUpper(atributo.getName()) + " = new JTextField(" + atributo.getSize() + ");");
        }
    }

    private void gerarEntidades() {
        codigoGerado.add("\n//INSTANCIA DAS ENTIDADES");
        codigoGerado.add(entidade + " " + capitalize.capitalizeTextLower(entidade) + " = new " + entidade + "();");
    }

    private void gerarTableScreens() {
        codigoGerado.add("//INSTANCIA DAS TABLE SCREENS");
        codigoGerado.add(entidade + "TableScreen" + " " + capitalize.capitalizeTextLower(entidade) + "TableScreen" + ";");
        GeradorTableScreen geradorTableScreen = new GeradorTableScreen(entidade, atributos, destinyPackage, destinyPath, autor, entidade);
    }

    private void gerarConstrutor() {
        codigoGerado.add("\n\npublic " + nomeClasse + "() {");
        gerarScreenDefaultConfigurations();
        gerarLoadData();
        codigoGerado.add("buttonsInitialConfiguration();");
        codigoGerado.add("textFieldInitialConfiguration();");
        gerarContainerConfigurations();
        gerarPanNorthConfigurations();
        gerarPanBodyConfigurations();
        gerarListenerRetrieve();
        gerarListenerCreate();
        gerarListenerUpdate();
        gerarListenerAction();
        gerarListenerDelete();
        gerarListenerList();
        gerarListenerCancel();
        gerarDispose();
        gerarLastDefautlConfigurations();
        codigoGerado.add("}\n\n");
    }

    private void gerarScreenDefaultConfigurations() {
        codigoGerado.add("        setDefaultCloseOperation(DISPOSE_ON_CLOSE);\n"
                + "        setLocationRelativeTo(null);\n"
                + "        setTitle(\"CRUD - " + entidade.toUpperCase() + "\");\n");
    }

    private void gerarLoadData() {
        codigoGerado.add("\t//LOAD DATA\n"
                + "        final String path = \"" + capitalize.capitalizeTextUpper(entidade) + ".csv\";\n"
                + "        " + capitalize.capitalizeTextLower(entidade) + "Controller.loadData(path);\n");
    }

    private void gerarButtonsInitialConfiguration() {
        codigoGerado.add("private void buttonsInitialConfiguration() {"
                + "\t//BUTTONS INITIAL CONFIGURATIONS\n"                
                + "        btnRetrieve.setEnabled(true);\n"
                + "        btnCancel.setVisible(false);\n"
                + "        btnList.setVisible(true);\n"
                + "        btnCreate.setEnabled(false);\n"
                + "        btnAction.setVisible(false);\n"
                + "        btnUpdate.setEnabled(false);\n"
                + "        btnDelete.setEnabled(false);\n"
                + "}\n\n");
    }

    private void gerarTxtInitialConfigurations() {
        codigoGerado.add("private void textFieldInitialConfiguration() {"
                + "\t//TEXTFIELD INITIAL CONFIGURATIONS");
        String bool = "";
        for (Atributo atributo : atributos) {
            if (atributo.equals(atributos.get(0))) {
                bool = "true";
            } else {
                bool = "false";
            }
            codigoGerado.add("\ttxt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".setEditable(" + bool + ");");
        }
        codigoGerado.add("}\n\n");
    }
    
    private void gerarClearAllFields() {
        codigoGerado.add("private void clearAllFields() {\n");
        for (Atributo atributo : atributos) {
            codigoGerado.add("\ttxt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".setText(\"\");");
        }
        codigoGerado.add("    }\n\n");
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

    private void gerarPanNorthConfigurations() {
        codigoGerado.add("\n\t//PAN NORTH CONFIGURATIONS\n"
                + "        panNorth.setLayout(new FlowLayout(FlowLayout.LEFT));\n"
                + "        panNorth.add(lbl" + capitalize.capitalizeTextUpper(pk.getName()) + ");\n"
                + "        panNorth.add(txt" + capitalize.capitalizeTextUpper(pk.getName()) + ");\n"
                + "\n"
                + "        panNorth.add(btnRetrieve);\n"
                + "        panNorth.add(btnCreate);\n"
                + "        panNorth.add(btnUpdate);\n"
                + "        panNorth.add(btnDelete);\n"
                + "        panNorth.add(btnList);\n"
                + "        panNorth.add(btnCancel);\n"
                + "        //PAN EAST CONFIGURATIONS\n"
                + "        //PAN WEST CONFIGURATIONS\n");
    }

    private void gerarPanBodyConfigurations() {
        codigoGerado.add("\t//PAN BODY CONFIGURATIONS\n"
                + "        panBody.setBorder(BorderFactory.createLineBorder(Color.black, 5));\n"
                + "        panBody.setLayout(new GridLayout(" + atributos.size() + ", 2));\n");
        codigoGerado.add("\t//Prenchimento por Linha");
        for (int i = 0; i < atributos.size(); i++) {
            //GERA UM PAR DE PAINÉIS PARA CADA LINHA E COLUNA DO GRID DO PAN BODY
            for (int j = 0; j < 2; j++) {
                int l = i + 1;
                int c = j + 1;
                codigoGerado.add("\tpanBody.add(panL" + l + "C" + c + ");");
            }
        }
        gerarConfiguracaoLinhaX();

    }

    private void gerarConfiguracaoLinhaX() {
        for (int i = 1; i < atributos.size(); i++) {
            int l = i;
            String atributoName = capitalize.capitalizeTextUpper(atributos.get(i).getName());
            codigoGerado.add("\n\t//Prenchimento Linha " + l);
            codigoGerado.add("\tpanL" + l + "C1.add(lbl" + atributoName + ");");
            codigoGerado.add("\tpanL" + l + "C2.add(txt" + atributoName + ");");
        }
        int last = atributos.size();
        codigoGerado.add("\n\t//Prenchimento Linha " + last);
        codigoGerado.add("\tpanL" + last + "C2.add(btnAction);");
    }

    private void gerarListenerRetrieve() {
        codigoGerado.add(" //BTN RETRIEVE ACTION LISTENER");
        codigoGerado.add("btnRetrieve.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {");
        codigoGerado.add("try {\n"
                + "                    if (!txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".getText().trim().isEmpty()) {");
        codigoGerado.add(capitalize.capitalizeTextLower(entidade) + " = " + capitalize.capitalizeTextLower(entidade) + "Controller.retrieve("
                + gerarValueOf("txt" + capitalize.capitalizeTextUpper(pk.getName() + ".getText()")) + ");");
        codigoGerado.add("if (" + capitalize.capitalizeTextLower(entidade) + "!= null){btnCreate.setEnabled(false);\n"
                + "                            btnCreate.setVisible(true);\n"
                + "                            btnUpdate.setEnabled(true);\n"
                + "                            btnUpdate.setVisible(true);\n"
                + "                            btnDelete.setEnabled(true);\n");        
        for (int i = 1; i < atributos.size(); i++) {
            Atributo atributo = atributos.get(i);
            codigoGerado.add("\ttxt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".setEditable(false);");
        }
        codigoGerado.add("");
        gerarSetters();
        codigoGerado.add("} else {\n"
                + "                            btnCreate.setEnabled(true);\n"
                + "                            btnCreate.setVisible(true);\n"
                + "                            btnUpdate.setEnabled(false);\n"
                + "                            btnDelete.setEnabled(false);\n");
        gerarSetEditableTxt(true);
        gerarSetText("\"\"");
        codigoGerado.add("}\n}");
        gerarCatch("Data");

        codigoGerado.add("}\n"
                + "        });\n");
    }

    private void gerarListenerCreate() {
        codigoGerado.add(" //BTN CREATE ACTION LISTENER");
        codigoGerado.add("btnCreate.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {");
        codigoGerado.add("btnRetrieve.setEnabled(false);\n"
                + "                btnUpdate.setEnabled(false);\n"
                + "                btnDelete.setEnabled(false);\n"
                + "                btnCreate.setVisible(false);\n"
                + "                btnCancel.setVisible(true);\n"
                + "                btnAction.setVisible(true);\n");
        codigoGerado.add("txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".setEditable(false);");
        codigoGerado.add("txt" + capitalize.capitalizeTextUpper(atributos.get(1).getName()) + ".requestFocus();");
        codigoGerado.add("\n"
                + "                actionController = \"CREATE\";\n"
                + "\n"
                + "                btnAction.setText(\"Adicionar à Lista\");");
        codigoGerado.add("}\n"
                + "        });\n");
    }

    private void gerarListenerUpdate() {
        codigoGerado.add(" //BTN UPDATE ACTION LISTENER");
        codigoGerado.add("btnUpdate.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {");
        codigoGerado.add("btnRetrieve.setEnabled(false);\n"
                + "                btnUpdate.setEnabled(false);\n"
                + "                btnDelete.setEnabled(false);\n"
                + "                btnCreate.setVisible(false);\n"
                + "                btnCancel.setVisible(true);\n"
                + "                btnAction.setVisible(true);\n");
        codigoGerado.add("txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".setEditable(false);");
        gerarSetEditableTxt(true);
        codigoGerado.add("txt" + capitalize.capitalizeTextUpper(atributos.get(1).getName()) + ".requestFocus();");
        codigoGerado.add("\n"
                + "                actionController = \"UPDATE\";\n"
                + "\n"
                + "                btnAction.setText(\"Atualizar na Lista\");");
        codigoGerado.add("}\n"
                + "        });\n");
    }

    private void gerarListenerAction() {
        codigoGerado.add(" //BTN ACTION ACTION LISTENER");
        codigoGerado.add("btnAction.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {");
        codigoGerado.add("try {");
        codigoGerado.add("if (actionController.equalsIgnoreCase(\"CREATE\")) {\n"
                + "                        " + capitalize.capitalizeTextLower(entidade) + " = new " + entidade + "();\n"
                + "                    }");
        codigoGerado.add(entidade + " " + capitalize.capitalizeTextLower(entidade) + "Antigo = " + capitalize.capitalizeTextLower(entidade) + ";");
        gerarVerificarTxt();
        codigoGerado.add(" else {");
        gerarSetterEntidade();
        codigoGerado.add("}");
        codigoGerado.add("if (actionController.equalsIgnoreCase(\"CREATE\")) {\n"
                + "                            System.out.println(" + capitalize.capitalizeTextLower(entidade) + ".toString());\n"
                + "                            " + capitalize.capitalizeTextLower(entidade) + "Controller.create(" + capitalize.capitalizeTextLower(entidade) + ");\n"
                + "                            System.out.println(\"" + entidade.toUpperCase() + " ADICIONADO!\");\n"
                + "                        } else if (actionController.equalsIgnoreCase(\"UPDATE\")) {\n"
                + "                            System.out.println(\"" + entidade.toUpperCase() + " => \" + " + capitalize.capitalizeTextLower(entidade) + ".toString());\n"
                + "                            System.out.println(\"" + entidade.toUpperCase() + " ANTIGO => \" + " + capitalize.capitalizeTextLower(entidade) + "Antigo.toString());\n"
                + "                            " + capitalize.capitalizeTextLower(entidade) + "Controller.update(" + capitalize.capitalizeTextLower(entidade) + "Antigo, " + capitalize.capitalizeTextLower(entidade) + ");\n"
                + "                            System.out.println(\"LISTA ATUALIZADA!\");\n"
                + "                        } else {\n");
        gerarException("Falha ao executar a ação na lista");
        codigoGerado.add("\n                        }");
        codigoGerado.add("\n"
                + "                        btnAction.setVisible(false);\n"
                + "                        btnRetrieve.setEnabled(true);\n"
                + "                        btnUpdate.setVisible(true);\n"
                + "                        btnUpdate.setEnabled(false);\n"
                + "                        btnCreate.setVisible(true);\n"
                + "                        btnCreate.setEnabled(false);\n"
                + "                        btnCancel.setVisible(false);\n"
                + "                        textFieldInitialConfiguration();"
                + "");

        codigoGerado.add("\n"
                + "                        txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".setEditable(true);\n"
                + "                        txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".requestFocus();\n"
                + "");
        codigoGerado.add("clearAllFields();");
        codigoGerado.add("");
        gerarCatch("ACTION");
        codigoGerado.add("}\n"
                + "        });\n");
    }

    private void gerarListenerDelete() {
        codigoGerado.add(" //BTN DELETE ACTION LISTENER");
        codigoGerado.add("btnDelete.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {");
        codigoGerado.add("confirmDialog = new BuildConfirmDialog(\n"
                + "                        DialogConfirmType.YES_NO,\n"
                + "                        \"Deseja realmente excluir estes dados da lista?\",\n"
                + "                        \"Confirmar Exclusão\");\n"
                + "\n"
                + "                int response = confirmDialog.getResponse();\n"
                + "\n"
                + "                if (response == JOptionPane.YES_OPTION) {");
        codigoGerado.add("btnRetrieve.setEnabled(false);\n"
                + "                    btnUpdate.setEnabled(false);\n"
                + "                    btnDelete.setEnabled(false);\n"
                + "                    btnCreate.setEnabled(false);\n"
                + "\n"
                + "                    actionController = \"DELETE\";\n"
                + "                    btnAction.setVisible(false);\n"
                + "                    btnRetrieve.setEnabled(true);\n"
                + "");
        codigoGerado.add("\n"
                + "                        txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".setEditable(true);\n"
                + "                        textFieldInitialConfiguration();\n"
                + "                        txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".requestFocus();\n"
                + "");
        gerarSetEditableTxt(false);
        codigoGerado.add("clearAllFields();");
        codigoGerado.add(capitalize.capitalizeTextLower(entidade) + "Controller.delete(" + capitalize.capitalizeTextLower(entidade) + ");\n");
        codigoGerado.add("messageDialog = new BuildMessageDialog(\n"
                + "                            DialogMessageType.SUCESS,\n"
                + "                            \""+entidade.toUpperCase()+" EXCLUÍDO COM SUCESSO\",\n"
                + "                            \"DELETE\",\n"
                + "                            container);\n"
                + "                    System.out.println(\""+entidade.toUpperCase()+" EXCLUÍDO\");");
        codigoGerado.add("}");
        codigoGerado.add("}\n"
                + "        });\n");
    }

    private void gerarListenerList() {
        codigoGerado.add(" //BTN LIST ACTION LISTENER");
        codigoGerado.add("btnList.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {\n"
                + "                // A JANELA TABELA SÓ PODE SER ABERTA SE TABLESCREEN NÃO ESTIVER ATIVA.\n"
                + "                if (listController) {\n"
                + "                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.\n"
                + "                    " + capitalize.capitalizeTextLower(entidade) + "TableScreen.dispose();\n"
                + "                    listController = false;\n"
                + "                }\n"
                + "                List<" + entidade + "> " + capitalize.capitalizeTextLower(entidade) + "s = "
                + capitalize.capitalizeTextLower(entidade) + "Controller.listar();\n"
                + "                " + capitalize.capitalizeTextLower(entidade) + "TableScreen = new "
                + capitalize.capitalizeTextUpper(entidade) + "TableScreen(" + capitalize.capitalizeTextLower(entidade) + "s);\n"
                + "                listController = true;\n"
                + "            }\n"
                + "        });");
    }

    private void gerarListenerCancel() {
        codigoGerado.add(" //BTN CANCEL ACTION LISTENER");
        codigoGerado.add("btnCancel.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {\n"
                + "                //RETORNA A TELA AO ESTADO INICIAL");
        codigoGerado.add("buttonsInitialConfiguration();");
        codigoGerado.add("textFieldInitialConfiguration();");        
        codigoGerado.add("clearAllFields();");                
        codigoGerado.add("\n"
                + "                        txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".requestFocus();\n"
                + "");
        codigoGerado.add("");
        codigoGerado.add("}\n"
                + "        });\n");

    }

    private void gerarDispose() {
        codigoGerado.add("//Close Window Action\n"
                + "        addWindowListener(new WindowAdapter() {\n"
                + "            @Override\n"
                + "            public void windowClosing(WindowEvent e) {\n"
                + "                if (listController) {\n"
                + "                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.\n"
                + "                    " + capitalize.capitalizeTextLower(entidade) + "TableScreen.dispose();\n"
                + "                    listController = false;\n"
                + "                }\n"
                + "                "+ capitalize.capitalizeTextLower(entidade) + "Controller.saveData(path);\n"
                + "                dispose();\n"
                + "            }\n"
                + "        });");
    }
    
    private void gerarLastDefautlConfigurations() {
        codigoGerado.add("\n\tpack();"
                + "\n\tsetModal(true);"
                + "\n\tsetVisible(true);");
    }

    private void gerarSetters() {
        for (Atributo atributo : atributos) {
            codigoGerado.add("txt" + capitalize.capitalizeTextUpper(atributo.getName())
                    + ".setText(String.valueOf(" + capitalize.capitalizeTextLower(entidade)
                    + ".get" + capitalize.capitalizeTextUpper(atributo.getName()) + "()));");
        }
    }

    private void gerarSetterEntidade() {
        for (Atributo atributo : atributos) {
            codigoGerado.add(capitalize.capitalizeTextLower(entidade) + ".set" + capitalize.capitalizeTextUpper(atributo.getName()) + "("
                    + gerarValueOf("txt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".getText()") + ");");
        }
    }

    private void gerarSetEditableTxt(boolean isEditable) {
        for (int i = 1; i < atributos.size(); i++) {
            Atributo atributo = atributos.get(i);
            codigoGerado.add("\ttxt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".setEditable(" + isEditable + ");");
        }
    }

    private void gerarSetText(String content) {
        for (int i = 1; i < atributos.size(); i++) {
            Atributo atributo = atributos.get(i);
            codigoGerado.add("\ttxt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".setText(" + content + ");");
        }
    }

    private void gerarVerificarTxt() {
        String condicao = "";
        for (int i = 1; i < atributos.size(); i++) {
            Atributo atributo = atributos.get(i);
            condicao += "txt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".getText().trim().isEmpty()";
            if (i < atributos.size() - 1) {
                condicao += " || ";
            }
        }
        codigoGerado.add("if (" + condicao + "){");
        gerarException("Verifique se os seus campos estão preenchidos!");

        codigoGerado.add("}");
    }

    private void gerarException(String message) {
        codigoGerado.add("throw new Exception(\"" + message + "\");");
    }

    private void gerarCatch(String errorTitle) {
        codigoGerado.add("} catch (Exception excep) {\n"
                + "                    errorTools.showExceptionStackTrace(excep);\n"
                + "                    messageDialog = new BuildMessageDialog(\n"
                + "                            DialogMessageType.ERROR,\n"
                + "                            excep.getMessage(),\n"
                + "                            \"" + errorTitle + " Error\",\n"
                + "                             container);\n"
                + "                }");
    }

    private String gerarValueOf(String campo) {
        for (Atributo atributo : atributos) {
            if (campo.toLowerCase().contains(atributo.getName().toLowerCase())) {
                switch (atributo.getType().trim()) {
                    case "byte":
                        return "Byte.valueOf("+ campo +")";
                    case "short":
                        return "Short.valueOf("+ campo +")";
                    case "int":
                        return "Integer.valueOf(" + campo + ")";
                    case "double":
                        return "Double.valueOf(" + campo + ")";
                    case "float":
                        return "Float.valueOf("+ campo +")";
                    case "long":
                        return "Long.valueOf(" + campo + ")";
                    case "Date":
                        return "cf.converteDeStringParaDate(" + campo + ")";      
                    case "String":
                    default:
                        return campo;
                }
            }
        }
        return campo;
    }

    private void criarArquivo() {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(destinyPath + "/src/" + destinyPackage + "/" + nomeClasse + ".java", codigoGerado);
        System.out.println("GEROU!");
    }

}
