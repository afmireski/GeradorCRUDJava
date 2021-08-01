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
import modelsGerador.Crud;
import tools.ManipulaArquivo;

/**
 *
 * @author AFMireski
 */
public class GeradorGUI_Imagem {

    private final Capitalize capitalize = new Capitalize();

    private final String nomeClasse;
    private final String destinyPackage;
    private final String destinyPath;
    private final Crud crud;

    public GeradorGUI_Imagem(String nomeClasse, String destinyPackage, String destinyPath, Crud crud) {
        this.nomeClasse = nomeClasse + "Screen";
        this.destinyPackage = destinyPackage;
        this.destinyPath = destinyPath;
        this.crud = crud;

        gerar();
    }

    private Atributo pk;
    private final List<String> codigoGerado = new ArrayList();

    private void gerar() {
        codigoGerado.add("package " + destinyPackage + ";");
        gerarImports();
        codigoGerado.add("/**\n"
                + " *\n"
                + " * @author " + crud.getAutor() + "\n"
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
        gerarPaths();
        gerarImageConfigurations();
        gerarConstrutor();
        gerarButtonsInitialConfigurationMethod();
        gerarTxtInitialConfigurationsMethod();
        gerarClearAllFields();
        gerarMetodosAuxiliaresImagem();
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
                + "import javax.swing.ImageIcon;\n"
                + "import java.io.File;\n"
                + "import javax.swing.JFileChooser;"
                + "import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;\n"
                + "import tools.CaixaDeFerramentas;\n"
                + "import tools.Tools;\n"
                + "import tools.CopiarArquivos;\n"
                + "import tools.DiretorioDaAplicacao;\n"
                + "import tools.ImagemAjustada;\n"
                + "import enums.DialogMessageType;\n"
                + "import enums.DialogConfirmType;\n"
                + "import controllers." + crud.getEntidade() + "Controller;\n"
                + "import models." + crud.getEntidade() + ";\n");
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
                + "    final private ImagemAjustada imagemAjustada = new ImagemAjustada();"
                + "    final private DiretorioDaAplicacao dda = new DiretorioDaAplicacao();"
                + "    final private CopiarArquivos copiarArquivos = new CopiarArquivos();"
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
                + "    JPanel panEast = new JPanel(new BorderLayout());\n"
                + "    JPanel panWest = new JPanel();\n"
                + "    JPanel panBody = new JPanel();\n");

        codigoGerado.add("//EAST SUB PANELS\n"
                + "    JPanel panSubEast1 = new JPanel(new FlowLayout(FlowLayout.CENTER));\n"
                + "    JPanel panSubEast2 = new JPanel(new GridLayout(1, 1));");

        codigoGerado.add("//BODY PANELS");
        for (int i = 0; i < crud.getAtributos().size(); i++) {
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
                + "    JButton btnCreate = components.buttonWithIcon(\"Create\", \"/icons/create.png\");\n"
                + "    JButton btnRetrieve = components.buttonWithIcon(\"Retrieve\", \"/icons/retrieve.png\");\n"
                + "    JButton btnUpdate = components.buttonWithIcon(\"Update\", \"/icons/update.png\");\n"
                + "    JButton btnDelete = components.buttonWithIcon(\"Delete\", \"/icons/delete.png\");\n"
                + "    JButton btnAction = new JButton(\"Add to List\");\n"
                + "    JButton btnCancel = components.buttonWithIcon(\"Cancel\", \"/icons/cancel.png\");\n"
                + "    JButton btnList = components.buttonWithIcon(\"List\", \"/icons/list.png\");\n"
                + "    JButton btnSelectImage = new JButton(\"Select Image\");\n"
                + "    JButton btnRemoveImage = new JButton(\"Remove Image\");\n");
    }

    private void gerarControllers() {
        codigoGerado.add("//INSTANCIA DOS CONTROLLERS\n"
                + "    String actionController;\n"
                + "    boolean listController = false;\n"
                + "    boolean imageController = false;\n"
                + "    " + crud.getEntidade() + "Controller " + capitalize.capitalizeTextLower(crud.getEntidade())
                + "Controller = new " + crud.getEntidade() + "Controller();\n");
    }

    private void gerarLabels() {
        codigoGerado.add("\n//INSTANCIA DOS LABELS");
        String aux[];
        for (Atributo atributo : crud.getAtributos()) {
            if (!atributo.getType().equalsIgnoreCase("Image")) {
                codigoGerado.add("JLabel lbl" + capitalize.capitalizeTextUpper(atributo.getName()) + " = new JLabel(\"" + atributo.getName().toUpperCase() + "\");");
            }
        }
        codigoGerado.add("JLabel lblImage = new JLabel();");
    }

    private void gerarTextFields() {
        codigoGerado.add("\n//INSTANCIA DOS TEXTFIELD");
        pk = crud.getAtributos().get(0);
        for (Atributo atributo : crud.getAtributos()) {
            if (!atributo.getType().equalsIgnoreCase("Image")) {
                codigoGerado.add("JTextField txt" + capitalize.capitalizeTextUpper(atributo.getName()) + " = new JTextField(" + atributo.getSize() + ");");
            }
        }
    }

    private void gerarEntidades() {
        codigoGerado.add("\n//INSTANCIA DAS ENTIDADES");
        codigoGerado.add(crud.getEntidade() + " " + capitalize.capitalizeTextLower(crud.getEntidade()) + " = new " + crud.getEntidade() + "();");
    }

    private void gerarTableScreens() {
        codigoGerado.add("//INSTANCIA DAS TABLE SCREENS");
        codigoGerado.add(crud.getEntidade() + "TableScreen" + " " + capitalize.capitalizeTextLower(crud.getEntidade()) + "TableScreen" + ";");
        GeradorTableScreen geradorTableScreen = new GeradorTableScreen(crud.getEntidade(), crud.getAtributos(), destinyPackage, destinyPath, crud.getAutor(), crud.getEntidade());
    }

    private void gerarPaths() {
        codigoGerado.add("//PATHS\n"
                + "    String currentPath = dda.getDiretorioDaAplicacao();");
    }

    private void gerarImageConfigurations() {
        codigoGerado.add("//IMAGE CONFIGURATIONS\n"
                + "    int defaultHeight = 300;\n"
                + "    int defaultWidth = 300;\n"
                + "    String defaultImagePath = currentPath + \"/src/images/default.png\";\n"
                + "    String currentImage;");
    }

    private void gerarConstrutor() {
        codigoGerado.add("\n\npublic " + nomeClasse + "() {");
        gerarScreenDefaultConfigurations();
        gerarImageInitialConfigurations();
        gerarLoadData();
        gerarButtonsInitialConfiguration();
        gerarTxtInitialConfigurations();
        gerarContainerConfigurations();
        gerarPanNorthConfigurations();
        gerarPanEastConfigurations();
        gerarPanBodyConfigurations();
        gerarListenerRetrieve();
        gerarListenerCreate();
        gerarListenerUpdate();
        gerarListenerAction();
        gerarListenerDelete();
        gerarListenerList();
        gerarListenerCancel();
        gerarListenerSelectImage();
        gerarListenerRemoveImage();
        gerarDispose();
        gerarLastDefautlConfigurations();
        codigoGerado.add("}");
    }

    private void gerarScreenDefaultConfigurations() {
        codigoGerado.add("        setDefaultCloseOperation(DISPOSE_ON_CLOSE);\n"
                + "        setLocationRelativeTo(null);\n"
                + "        setTitle(\"CRUD - " + crud.getEntidade().toUpperCase() + "\");\n");
    }

    private void gerarImageInitialConfigurations() {
        codigoGerado.add("//IMAGE INITIAl CONFIGURATIONS\n"
                + "        setImage(defaultImagePath);\n"
                + "        currentImage = defaultImagePath;");
    }

    private void gerarLoadData() {
        codigoGerado.add("\t//LOAD DATA\n"
                + "        final String path = \"" + capitalize.capitalizeTextUpper(crud.getEntidade()) + ".csv\";\n"
                + "        " + capitalize.capitalizeTextLower(crud.getEntidade()) + "Controller.loadData(path);\n");
    }

    private void gerarButtonsInitialConfiguration() {
        codigoGerado.add("\t//BUTTONS INITIAL CONFIGURATIONS\n"
                + "        buttonsInitialConfiguration();\n");
    }

    private void gerarTxtInitialConfigurations() {
        codigoGerado.add("\t//BUTTONS INITIAL CONFIGURATIONS\n"
                + "        textFieldInitialConfiguration();\n");
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

    private void gerarPanEastConfigurations() {

        codigoGerado.add("\n\t//PAN EAST CONFIGURATIONS\n"
                + "        panEast.setBackground(Color.BLACK);\n"
                + "        panEast.add(panSubEast1, BorderLayout.NORTH);\n"
                + "        panEast.add(panSubEast2, BorderLayout.CENTER);\n"
                + "        \n"
                + "        panSubEast1.add(btnSelectImage);\n"
                + "        panSubEast1.add(btnRemoveImage);\n"
                + "        \n"
                + "        panSubEast2.add(lblImage);");
    }

    private void gerarPanBodyConfigurations() {
        codigoGerado.add("\t//PAN BODY CONFIGURATIONS\n"
                + "        panBody.setBorder(BorderFactory.createLineBorder(Color.black, 5));\n"
                + "        panBody.setLayout(new GridLayout(" + crud.getAtributos().size() + ", 2));\n");
        codigoGerado.add("\t//Prenchimento por Linha");
        for (int i = 0; i < crud.getAtributos().size(); i++) {
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
        for (int i = 1; i < crud.getAtributos().size(); i++) {
            int l = i;
            String atributoName = capitalize.capitalizeTextUpper(crud.getAtributos().get(i).getName());
            if (!crud.getAtributos().get(i).getType().equalsIgnoreCase("Image")) {
                codigoGerado.add("\n\t//Prenchimento Linha " + l);
                codigoGerado.add("\tpanL" + l + "C1.add(lbl" + atributoName + ");");
                codigoGerado.add("\tpanL" + l + "C2.add(txt" + atributoName + ");");
            }

        }
        int last = crud.getAtributos().size();
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
        codigoGerado.add(capitalize.capitalizeTextLower(crud.getEntidade()) + " = " + capitalize.capitalizeTextLower(crud.getEntidade()) + "Controller.retrieve("
                + gerarValueOf("txt" + capitalize.capitalizeTextUpper(pk.getName() + ".getText()")) + ");");
        codigoGerado.add("if (" + capitalize.capitalizeTextLower(crud.getEntidade()) + "!= null){btnCreate.setEnabled(false);\n"
                + "                            btnCreate.setVisible(true);\n"
                + "                            btnUpdate.setEnabled(true);\n"
                + "                            btnUpdate.setVisible(true);\n"
                + "                            btnDelete.setEnabled(true);\n");
        for (int i = 1; i < crud.getAtributos().size(); i++) {
            Atributo atributo = crud.getAtributos().get(i);
            if (!atributo.getType().equalsIgnoreCase("Image")) {
                codigoGerado.add("\ttxt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".setEditable(false);");
            }
        }
        codigoGerado.add("");
        gerarSetters();
        codigoGerado.add("String image;\n"
                + "                            if (" + capitalize.capitalizeTextLower(crud.getEntidade()) + ".get" + getImageVar() + "().equals(\"Sim\")) {"
                + "image = getImage(txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".getText());");
        gerarSetIcon("image");
        codigoGerado.add("} else {"
                + "image = defaultImagePath;");
        gerarSetIcon("image");
        codigoGerado.add("} "
                + "currentImage = image;");
        codigoGerado.add("} else {\n"
                + "                            btnCreate.setEnabled(true);\n"
                + "                            btnCreate.setVisible(true);\n"
                + "                            btnUpdate.setEnabled(false);\n"
                + "                            btnDelete.setEnabled(false);\n");
        gerarSetEditableTxt(true);
        gerarSetText("\"\"");
        gerarSetIcon("defaultImagePath");
        codigoGerado.add("currentImage = defaultImagePath;");
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
                + "                btnAction.setVisible(true);\n"
                + "                btnSelectImage.setVisible(true);\n" 
                + "                btnRemoveImage.setVisible(true);\n");
        codigoGerado.add("txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".setEditable(false);");
        for (int i = 1; i < crud.getAtributos().size(); i++) {
            Atributo atributo = crud.getAtributos().get(i);
            if (!atributo.getType().equalsIgnoreCase("Image")) {
                ///Requisita o focus do primeiro atributo após a PK e que não for uma Image
                codigoGerado.add("txt" + capitalize.capitalizeTextUpper(crud.getAtributos().get(i).getName()) + ".requestFocus();");
                break;
            }
        }
        
        codigoGerado.add("\n"
                + "                actionController = \"CREATE\";\n"
                + "imageController = false;"
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
                + "                btnAction.setVisible(true);\n"
                + "                btnSelectImage.setVisible(true);\n" 
                + "                btnRemoveImage.setVisible(true);\n");
        codigoGerado.add("txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".setEditable(false);");
        gerarSetEditableTxt(true);
        for (int i = 1; i < crud.getAtributos().size(); i++) {
            Atributo atributo = crud.getAtributos().get(i);
            if (!atributo.getType().equalsIgnoreCase("Image")) {
                ///Requisita o focus do primeiro atributo após a PK e que não for uma Image
                codigoGerado.add("txt" + capitalize.capitalizeTextUpper(crud.getAtributos().get(i).getName()) + ".requestFocus();");
                break;
            }
        }
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
                + "                        " + capitalize.capitalizeTextLower(crud.getEntidade()) + " = new " + crud.getEntidade() + "();\n"
                + "                    }");
        codigoGerado.add(crud.getEntidade() + " " + capitalize.capitalizeTextLower(crud.getEntidade()) + "Antigo = " + capitalize.capitalizeTextLower(crud.getEntidade()) + ";");
        gerarVerificarTxt();
        codigoGerado.add(" else {");
        gerarSetterEntidade();
        codigoGerado.add("if (imageController) {\n"
                + "copiaFoto(txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".getText());\n"
                + "}");
        codigoGerado.add("}");
        codigoGerado.add("if (actionController.equalsIgnoreCase(\"CREATE\")) {\n"
                + "                            System.out.println(" + capitalize.capitalizeTextLower(crud.getEntidade()) + ".toString());\n"
                + "                            " + capitalize.capitalizeTextLower(crud.getEntidade()) + "Controller.create(" + capitalize.capitalizeTextLower(crud.getEntidade()) + ");\n"
                + "                            System.out.println(\"" + crud.getEntidade().toUpperCase() + " ADICIONADO!\");\n"
                + "                        } else if (actionController.equalsIgnoreCase(\"UPDATE\")) {\n"
                + "                            System.out.println(\"" + crud.getEntidade().toUpperCase() + " => \" + " + capitalize.capitalizeTextLower(crud.getEntidade()) + ".toString());\n"
                + "                            System.out.println(\"" + crud.getEntidade().toUpperCase() + " ANTIGO => \" + " + capitalize.capitalizeTextLower(crud.getEntidade()) + "Antigo.toString());\n"
                + "                            " + capitalize.capitalizeTextLower(crud.getEntidade()) + "Controller.update(" + capitalize.capitalizeTextLower(crud.getEntidade()) + "Antigo, " + capitalize.capitalizeTextLower(crud.getEntidade()) + ");\n"
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
                + "                        btnSelectImage.setVisible(false);\n"
                + "                        btnRemoveImage.setVisible(false);\n"
                + ""
                + "                        textFieldInitialConfiguration();");

        codigoGerado.add("\n"
                + "                        txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".setEditable(true);\n"
                + "                        txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".requestFocus();\n"
                + "");        
        codigoGerado.add("clearAllFields();");
        codigoGerado.add("");
        codigoGerado.add("currentImage = defaultImagePath;\n"
                + "\tsetImage(defaultImagePath);");
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
                + "                    btnSelectImage.setVisible(false);\n"
                + "                    btnRemoveImage.setVisible(false);\n"
                + "\n"
                + "                    actionController = \"DELETE\";\n"
                + "                    btnAction.setVisible(false);\n"
                + "                    btnRetrieve.setEnabled(true);\n"
                + "");
        gerarTxtInitialConfigurations();
        codigoGerado.add("\n"
                + "                        txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".setEditable(true);\n"
                + "                        txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".requestFocus();\n"
                + "");
//        gerarSetEditableTxt(false);
        codigoGerado.add("clearAllFields();");
        codigoGerado.add("File image = new File(currentImage.trim());");
        codigoGerado.add("\nif (image.exists()) {\n"
                + "                        if (!currentImage.equals(defaultImagePath)) {\n"
                + "                            image.delete();                            \n"
                + "                        }\n"
                + "                        setImage(defaultImagePath);\n");
        codigoGerado.add(capitalize.capitalizeTextLower(crud.getEntidade()) + "Controller.delete(" + capitalize.capitalizeTextLower(crud.getEntidade()) + ");\n}");
        codigoGerado.add("messageDialog = new BuildMessageDialog(\n"
                + "                            DialogMessageType.SUCESS,\n"
                + "                            \"" + crud.getEntidade().toUpperCase() + " EXCLUÍDO COM SUCESSO\",\n"
                + "                            \"DELETE\",\n"
                + "                            container);\n"
                + "                    System.out.println(\"" + crud.getEntidade().toUpperCase() + " EXCLUÍDO\");");
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
                + "                    " + capitalize.capitalizeTextLower(crud.getEntidade()) + "TableScreen.dispose();\n"
                + "                    listController = false;\n"
                + "                }\n"
                + "                List<" + crud.getEntidade() + "> " + capitalize.capitalizeTextLower(crud.getEntidade()) + "s = "
                + capitalize.capitalizeTextLower(crud.getEntidade()) + "Controller.listar();\n"
                + "                " + capitalize.capitalizeTextLower(crud.getEntidade()) + "TableScreen = new "
                + capitalize.capitalizeTextUpper(crud.getEntidade()) + "TableScreen(" + capitalize.capitalizeTextLower(crud.getEntidade()) + "s);\n"
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
        gerarButtonsInitialConfiguration();
        gerarTxtInitialConfigurations();
        codigoGerado.add("\n"
                + "                        txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".setText(\"\");\n"
                + "                        txt" + capitalize.capitalizeTextUpper(pk.getName()) + ".requestFocus();\n"
                + "");        
        codigoGerado.add("clearAllFields();");
        codigoGerado.add("");
        codigoGerado.add("currentImage = defaultImagePath;\n"
                + "                setImage(defaultImagePath);");
        codigoGerado.add("}\n"
                + "        });\n");

    }

    private void gerarListenerSelectImage() {
        codigoGerado.add("btnSelectImage.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {\n"
                + "                JFileChooser fc = components.createFileChooser(\n"
                + "                        null, \n"
                + "                        JFileChooser.FILES_ONLY);\n"
                + "                if (fc.showOpenDialog(container) == JFileChooser.APPROVE_OPTION) {\n"
                + "                    File img = fc.getSelectedFile();\n"
                + "                    currentImage = img.getAbsolutePath();\n"
                + "                    \n"
                + "                    try {\n"
                + "                        setImage(currentImage);\n"
                + "                        imageController = true;\n"
                + "                    } catch (Exception excep) {\n"
                + "                        messageDialog = new BuildMessageDialog(\n"
                + "                                DialogMessageType.ERROR, \n"
                + "                                \"Falha a carregar imagem!\", \n"
                + "                                \"FALHA AO CARREGAR\", \n"
                + "                                container);\n"
                + "                    }\n"
                + "                }\n"
                + "            }\n"
                + "        });");
    }

    private void gerarListenerRemoveImage() {
        codigoGerado.add("btnRemoveImage.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {\n"
                + "                confirmDialog = new BuildConfirmDialog(\n"
                + "                        DialogConfirmType.YES_NO, \n"
                + "                        \"Deseja realmente remover a imagem?\", \n"
                + "                        \"CONFIRMAR REMOÇÃO\");\n"
                + "                \n"
                + "                int response = confirmDialog.getResponse();\n"
                + "                if (response == JOptionPane.YES_OPTION) {\n"
                + "                    File img = new File(currentImage);\n"
                + "                    if (img.exists()) {\n"
                + "                        if (!currentImage.equals(defaultImagePath)) {\n"
                + "                            img.delete();                            \n"
                + "                        }\n"
                + "                        currentImage = defaultImagePath;\n"
                + "                        setImage(defaultImagePath);\n"
                + "                        imageController = false;\n"
                + "                    }                    \n"
                + "                    \n"
                + "                }\n"
                + "            }\n"
                + "        });");
    }

    private void gerarDispose() {
        codigoGerado.add("//Close Window Action\n"
                + "        addWindowListener(new WindowAdapter() {\n"
                + "            @Override\n"
                + "            public void windowClosing(WindowEvent e) {\n"
                + "                if (listController) {\n"
                + "                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.\n"
                + "                    " + capitalize.capitalizeTextLower(crud.getEntidade()) + "TableScreen.dispose();\n"
                + "                    listController = false;\n"
                + "                }\n"
                + "                " + capitalize.capitalizeTextLower(crud.getEntidade()) + "Controller.saveData(path);\n"
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
        for (Atributo atributo : crud.getAtributos()) {
            if (!atributo.getType().equalsIgnoreCase("Image")) {
                codigoGerado.add("txt" + capitalize.capitalizeTextUpper(atributo.getName())
                        + ".setText(String.valueOf(" + capitalize.capitalizeTextLower(crud.getEntidade())
                        + ".get" + capitalize.capitalizeTextUpper(atributo.getName()) + "()));");
            }

        }
    }

    private String getImageVar() {
        ///Retorna a primeira variável do tipo Image contida em atributos;
        for (Atributo atributo : crud.getAtributos()) {
            if (atributo.getType().equalsIgnoreCase("Image")) {
                return capitalize.capitalizeTextUpper(atributo.getName());
            }
        }
        return "/*FIXME: adicione a variável que armazena a imagem*/";
    }

    private void gerarSetIcon(String image) {
        codigoGerado.add("ImageIcon icon = imagemAjustada.getImagemAjustada(\n"
                + "                                       " + image + ", \n"
                + "                                        defaultHeight, \n"
                + "                                        defaultWidth);\n"
                + "                                lblImage.setIcon(icon); ");
    }

    private void gerarSetterEntidade() {
        for (Atributo atributo : crud.getAtributos()) {
            if (atributo.getType().equalsIgnoreCase("Image")) {
                codigoGerado.add(capitalize.capitalizeTextLower(crud.getEntidade()) + ".set" + capitalize.capitalizeTextUpper(atributo.getName())
                        + "(imageController ? \"Sim\" : \"Não\");");
            } else {
                codigoGerado.add(capitalize.capitalizeTextLower(crud.getEntidade()) + ".set" + capitalize.capitalizeTextUpper(atributo.getName()) + "("
                        + gerarValueOf("txt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".getText()") + ");");
            }
        }
    }

    private void gerarSetEditableTxt(boolean isEditable) {
        for (int i = 1; i < crud.getAtributos().size(); i++) {
            Atributo atributo = crud.getAtributos().get(i);
            if (!atributo.getType().equalsIgnoreCase("Image")) {
                codigoGerado.add("\ttxt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".setEditable(" + isEditable + ");");
            }
        }
    }

    private void gerarSetText(String content) {
        for (int i = 1; i < crud.getAtributos().size(); i++) {
            Atributo atributo = crud.getAtributos().get(i);
            if (!atributo.getType().equalsIgnoreCase("Image")) {
                codigoGerado.add("\ttxt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".setText(" + content + ");");
            }

        }
    }

    private void gerarVerificarTxt() {
        String condicao = "";
        for (int i = 1; i < crud.getAtributos().size(); i++) {
            Atributo atributo = crud.getAtributos().get(i);
            if (!atributo.getType().equalsIgnoreCase("Image")) {
                condicao += "txt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".getText().trim().isEmpty()";

                if (i < crud.getAtributos().size() - 1) {
                    condicao += " || ";
                }
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
        for (Atributo atributo : crud.getAtributos()) {
            if (campo.toLowerCase().contains(atributo.getName().toLowerCase())) {
                switch (atributo.getType().trim()) {
                    case "byte":
                        return "Byte.valueOf(" + campo + ")";
                    case "short":
                        return "Short.valueOf(" + campo + ")";
                    case "int":
                        return "Integer.valueOf(" + campo + ")";
                    case "double":
                        return "Double.valueOf(" + campo + ")";
                    case "float":
                        return "Float.valueOf(" + campo + ")";
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

    private void gerarButtonsInitialConfigurationMethod() {
        codigoGerado.add("private void buttonsInitialConfiguration()  {\n"
                + "        btnRetrieve.setEnabled(true);\n"
                + "        btnCancel.setVisible(false);\n"
                + "        btnList.setVisible(true);\n"
                + "        btnCreate.setEnabled(false);\n"
                + "        btnAction.setVisible(false);\n"
                + "        btnUpdate.setEnabled(false);\n"
                + "        btnDelete.setEnabled(false);\n"
                + "        btnSelectImage.setVisible(false);\n"
                + "        btnRemoveImage.setVisible(false);\n"
                + "    }");
    }
    
    private void gerarTxtInitialConfigurationsMethod() {
        codigoGerado.add("private void textFieldInitialConfiguration() {"
                + "\t//TEXTFIELD INITIAL CONFIGURATIONS");
        String bool = "";
        for (Atributo atributo : crud.getAtributos()) {
            if (atributo.equals(crud.getAtributos().get(0))) {
                bool = "true";
            } else {
                bool = "false";
            }
            if (!atributo.getType().equalsIgnoreCase("Image")) {
                codigoGerado.add("\ttxt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".setEditable(" + bool + ");");                
            }
        }
        codigoGerado.add("}\n\n");
    }
    
    private void gerarClearAllFields() {
        codigoGerado.add("private void clearAllFields() {\n");
        for (Atributo atributo : crud.getAtributos()) {
            if (!atributo.getType().equalsIgnoreCase("Image")) {
              codigoGerado.add("\ttxt" + capitalize.capitalizeTextUpper(atributo.getName()) + ".setText(\"\");");
            }
        }
        codigoGerado.add("    }\n\n");
    }

    private void gerarMetodosAuxiliaresImagem() {
        codigoGerado.add("private void setImage(String imagePath) {\n"
                + "        lblImage.setIcon(imagemAjustada.getImagemAjustada(\n"
                + "                imagePath, defaultHeight, defaultWidth));\n"
                + "    }\n");
        codigoGerado.add("private String getImage(String pk) {\n"
                + "        return currentPath + \"/src/images/\" + pk.trim() + \".png\";\n"
                + "    }\n");
        codigoGerado.add("private void copiaFoto(String pk) {\n"
                + "        String destino = getImage(pk);\n"
                + "        \n"
                + "        System.out.println(currentImage);\n"
                + "        System.out.println(destino);\n"
                + "        \n"
                + "        copiarArquivos.copiar(currentImage, destino);\n"
                + "    }\n");
    }

    private void criarArquivo() {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(destinyPath + "/src/" + destinyPackage + "/" + nomeClasse + ".java", codigoGerado);
        System.out.println("GEROU!");
    }
}
