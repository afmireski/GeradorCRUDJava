/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerador;

import enums.DialogConfirmType;
import enums.DialogMessageType;
import functions.FileManager;
import helpers.BuildConfirmDialog;
import helpers.BuildMessageDialog;
import helpers.Capitalize;
import helpers.ErrorTools;
import helpers.GenericComponents;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelsGerador.Atributo;
import modelsGerador.ModelGerador;
import tools.CaixaDeFerramentas;
import tools.ManipulaArquivo;
import tools.Tools;

/**
 *
 * @author afmireski
 */
public class ScreenGerador extends JFrame {

    //INSTANCIA DOS HELPERS
    GenericComponents components = new GenericComponents();
    BuildMessageDialog messageDialog;
    BuildConfirmDialog confirmDialog;
    ErrorTools errorTools = new ErrorTools();
    Capitalize capitalize = new Capitalize();

    //INSTANCIA DAS FUNCTIONS
    FileManager fileManager = new FileManager();

//INSTANCIA DAS TOOLS
    final private CaixaDeFerramentas cf = new CaixaDeFerramentas();
    ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
    Tools tools = new Tools();

//INSTANCIA DOS CONTAINERS
    Container cp;

//INSTANCIA DOS PANELS
    //PANELS BASE
    JPanel panNorth = new JPanel();
    JPanel panSouth = new JPanel();
    JPanel panEast = new JPanel();
    JPanel panWest = new JPanel();
    JPanel panCenter = new JPanel();
    JPanel panBody = new JPanel(new GridLayout(4, 2));
    JPanel panBodyTable = new JPanel(new GridLayout(1, 1));
    

//NORTH PANELS
    //SUB NORTH PANELS    
    JPanel panSubNorth1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel panSubNorth2 = new JPanel(new GridLayout(1, 3));

    JPanel panPath = new JPanel();
    JPanel panNorth1 = new JPanel();
    JPanel panNorth2 = new JPanel();
    JPanel panNorth3 = new JPanel();

//BODY PANELS
    JPanel panL1C1 = new JPanel(); //Painel referente a posição da grade: Linha 1 - Coluna 1
    JPanel panL1C2 = new JPanel(); //Painel referente a posição da grade: Linha 1 - Coluna 2
    JPanel panL2C1 = new JPanel(); //Painel referente a posição da grade: Linha 2 - Coluna 1
    JPanel panL2C2 = new JPanel(); //Painel referente a posição da grade: Linha 2 - Coluna 2
    JPanel panL3C1 = new JPanel(); //Painel referente a posição da grade: Linha 3 - Coluna 1
    JPanel panL3C2 = new JPanel(); //Painel referente a posição da grade: Linha 3 - Coluna 2
    JPanel panL4C1 = new JPanel(); //Painel referente a posição da grade: Linha 3 - Coluna 1
    JPanel panL4C2 = new JPanel(); //Painel referente a posição da grade: Linha 3 - Coluna 2

     //INSTANCIA DA TABELA
    ModelGerador modelGerador = new ModelGerador();
    JTable jtable = new JTable(modelGerador);
    private JScrollPane scroll = new JScrollPane();
    
    //INSTANCIA DOS BUTTONS
    JButton btnInit = new JButton("Init");
    JButton btnAdd = new JButton("Adicionar");
    JButton btnGerar = new JButton("Gerar Completo");
    JButton btnCancel = new JButton("Cancel");
    JButton btnPath = new JButton("Caminho");
    JButton btnGerarEstrutura = new JButton("Gerar Estrutura");
    JButton btnGerarEntidade = new JButton("Gerar Entidade");
    JButton btnGerarController = new JButton("Gerar Controller");
    JButton btnGerarMain = new JButton("Gerar Main");
    JButton btnGerarGUI = new JButton("Gerar Interface");
    JButton btnVisualizarVariaveis = new JButton("Visualizar Variáveis");
    JButton btnCarregarEntidade = new JButton("Abrir");

    //INSTANCIA DOS LABELS
    JLabel lblEnt = new JLabel("ENTIDADE");
    JLabel lblAutor = new JLabel("AUTOR");
    JLabel lblTipo = new JLabel("TIPO");
    JLabel lblVar = new JLabel("VARIÁVEL");
    JLabel lblSize = new JLabel("TAMANHO DO CAMPO");
    JLabel lblChoose = new JLabel("CAMINHO DE DESTINO: ");

    //INSTANCIA DOS TEXTFIELD
    JTextField txtEnt = new JTextField(10);
    JTextField txtAutor = new JTextField(10);
    JTextField txtVar = new JTextField(10);
    JTextField txtPath = new JTextField(30);

    //INSTANCIA DOS SPINNERS
    JSpinner spnFieldSize;

    //INSTANCIA DOS COMBO BOX;
    JComboBox combTypeSelect;

    //INSTANCIA DOS TIPOS
    String types[] = {"byte", "short", "int", "long", "float", "double", "String", "boolean", "Date"};
    List<String> typeList = Arrays.asList(types);

    //INSTANCIA DO LIST ATRIBUTOS;
    List<String> variaveis = new ArrayList<>();
    List<Atributo> atributos = new ArrayList<>();

    //INSTANCIA DOS CAMINHOS PADRÃO
    String caminhos[] = {"\\src\\models", "\\src\\screens", "\\src\\controllers",
        "\\src\\enums", "\\src\\functions", "\\src\\helpers", "\\src\\tools", "\\src\\icons",
        "\\src\\main"};
    List<String> caminhosDefault = Arrays.asList(caminhos);

    //INSTACIA DOS FILE CHOOSER
    private JFileChooser fileChooser = components.createFileChooser(
            new FileNameExtensionFilter("DIRETÓRIO", "..", ".."),
            JFileChooser.DIRECTORIES_ONLY);
    private String destinyPath;
    private final String defaultPath = "C:\\..\\..\\..\\Documents\\NetBeansProjects";

    List<String> lastPath;

    ///INSTANCIA DA VAR TABLE;
    VarTableScreen varTableScreen;

    public ScreenGerador() {
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("GERADOR DE CRUD");

        //DESTINY PATH CONFIG;
        verifyPath();

        //SPINNER CONFIG
        spnFieldSize = components.createIntSpinner(10, 1, 100, 1);

        //COMBO BOX CONFIG
        combTypeSelect = components.createComboBox(typeList);

        //BUTTONS INITIAL CONFIGURATIONS
        buttonInitialConfigurations();

        //TXT INITIAL CONFIGURATIONS
        txtPath.setEditable(false);

        //CONTAINER CONFIGURATIONS
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(panNorth, BorderLayout.NORTH);
        cp.add(panSouth, BorderLayout.SOUTH);
        cp.add(panEast, BorderLayout.EAST);
        cp.add(panWest, BorderLayout.WEST);
        cp.add(panCenter, BorderLayout.CENTER);

        //PAN NORTH CONFIGURATIONS
        panNorth.setLayout(new GridLayout(2, 1));
        panNorth.add(panSubNorth1);
        panNorth.add(panSubNorth2);

        //PAN SUB NOTH CONFIGURATIONS
        panSubNorth1.add(panPath);

        panSubNorth2.add(panNorth1);
        panSubNorth2.add(panNorth2);
        panSubNorth2.add(panNorth3);

        //CONFIGURATION PAN PATH
        panPath.add(lblChoose);
        panPath.add(txtPath);
        panPath.add(btnPath);

        //CONFIGURATION NORTH1
        panNorth1.add(lblEnt);
        panNorth1.add(txtEnt);
        panNorth1.add(btnCarregarEntidade);

        //CONFIGURATION NORTH2
        panNorth2.add(lblAutor);
        panNorth2.add(txtAutor);
        //CONFIGURATION NORTH3
        panNorth3.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panNorth3.add(btnInit);
        panNorth3.add(btnCancel);

        //PAN EAST CONFIGURATIONS
        //PAN WEST CONFIGURATIONS
        //PAN SOUTH CONFIGURATIONS
        panSouth.add(btnGerarEstrutura);
        panSouth.add(btnGerar);
        panSouth.add(btnGerarEntidade);
        panSouth.add(btnGerarController);
        panSouth.add(btnGerarMain);
        panSouth.add(btnGerarGUI);

        //PAN CENTER CONFIGURETIONS
        panCenter.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        panCenter.setLayout(new GridLayout(2, 1));
        panCenter.add(panBody);
        panCenter.add(panBodyTable);
        
        //PAN BODY CONFIGURATIONS

        //Prenchimento por Linha
        panBody.add(panL1C1);
        panBody.add(panL1C2);
        panBody.add(panL2C1);
        panBody.add(panL2C2);
        panBody.add(panL3C1);
        panBody.add(panL3C2);
        panBody.add(panL4C1);
        panBody.add(panL4C2);

        //Configuração/Prenchimento da tabela
        jtable.getColumnModel().getColumn(0).setPreferredWidth(70);
        jtable.getColumnModel().getColumn(1).setPreferredWidth(20);
        jtable.getColumnModel().getColumn(2).setPreferredWidth(10);
        jtable.setRowHeight(20);
        scroll.setPreferredSize(jtable.getPreferredSize());
        scroll.setViewportView(jtable);
        LineBorder lineBorder = new LineBorder(Color.BLACK);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, "Atributos", TitledBorder.CENTER, TitledBorder.DEFAULT_JUSTIFICATION);
        scroll.setBorder(titledBorder);
        panBodyTable.add(scroll);
        
        //Prenchimento Linha 1
        panL1C1.add(lblTipo);
        panL1C2.add(combTypeSelect);

        //Prenchimento Linha 2
        panL2C1.add(lblVar);
        panL2C2.add(txtVar);

        //Prenchimento Linha 3
        panL3C1.add(lblSize);
        panL3C2.add(spnFieldSize);

        //Prenchimento Linha 4        
        panL4C1.add(btnVisualizarVariaveis);
        panL4C2.add(btnAdd);

        btnInit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtEnt.getText().isEmpty() || txtAutor.getText().isEmpty()) {
                        throw new Exception("Defina uma Entidade e um Autor para continuar....");
                    } else {
                        btnInit.setEnabled(false);
                        btnAdd.setEnabled(true);
                        btnCancel.setEnabled(true);
                        
                        btnCarregarEntidade.setEnabled(false);
                        txtEnt.setEnabled(false);
                        txtAutor.setEnabled(false);
                        if (atributos.isEmpty()) {
                            btnGerar.setEnabled(false);
                            btnGerarEntidade.setEnabled(false);
                            btnGerarController.setEnabled(false);
                            btnGerarMain.setEnabled(false);
                            btnGerarGUI.setEnabled(false);
                        } else {
                            btnGerar.setEnabled(true);
                            btnGerarEntidade.setEnabled(true);
                            btnGerarController.setEnabled(true);
                            btnGerarMain.setEnabled(true);
                            btnGerarGUI.setEnabled(true);
                        }
                    }
                } catch (Exception excep) {
                    errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            excep.getMessage(),
                            "CAMPOS VAZIOS",
                            cp);
                }

            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //BUTTONS INITIAL CONFIGURATIONS
                buttonInitialConfigurations();
                

                txtEnt.setEnabled(true);
                txtAutor.setEnabled(true);
                txtEnt.setText("");
                txtAutor.setText("");

                combTypeSelect.setSelectedIndex(0);
                txtVar.setText("");
                spnFieldSize.setValue(10);

                txtEnt.requestFocus();
                atributos.clear();
                modelGerador.clear();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtVar.getText().isEmpty()) {
                        throw new Exception("Você deve escolher um nome para sua variável...");
                    } else {
                        String type = combTypeSelect.getSelectedItem().toString();
                        String var = capitalize.capitalizeVarLower(capitalize.removerAcentos(txtVar.getText()));
                        String size = spnFieldSize.getValue().toString();
                        
                        Atributo atributo = new Atributo(var, type, size);
                        
                        System.out.println("Atributo -> " + atributo.toString());
                        
                        atributos.add(atributo);
                        
                        modelGerador.addModel(var, type, size);
                        txtVar.setText("");
                        spnFieldSize.setValue(10);
                        combTypeSelect.requestFocus();
                        if (!atributos.isEmpty()) {
                            btnVisualizarVariaveis.setEnabled(true);
                        }
                        if (!atributos.isEmpty() && atributos.size() > 1) {
                            btnGerar.setEnabled(true);
                            btnGerarEntidade.setEnabled(true);
                            btnGerarController.setEnabled(true);
                            btnGerarGUI.setEnabled(true);
                            btnGerarMain.setEnabled(true);
                        }
                    }
                } catch (Exception excep) {
                    errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            excep.getMessage(),
                            "VARIÁVEL SEM NOME",
                            cp);
                }
            }
        });

        btnPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File(destinyPath);
                if (file.exists()) {
                    fileChooser.setCurrentDirectory(file);
                } else {
                    fileChooser.setCurrentDirectory(null);
                }
                if (fileChooser.showOpenDialog(cp) == JFileChooser.APPROVE_OPTION) {
                    destinyPath = fileChooser.getSelectedFile().getAbsolutePath();
                    txtPath.setText(destinyPath);
                    lastPath.clear();
                    lastPath.add(destinyPath);
                    manipulaArquivo.salvarArquivo("LastPath.txt", lastPath);
                }
            }
        });

        btnGerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtEnt.getText().isEmpty() || txtAutor.getText().isEmpty()) {
                        throw new Exception("Defina uma Entidade e um Autor para continuar....");
                    } else if (atributos.isEmpty()) {
                        throw new Exception("Adicione pelo menos 1 variável para prosseguir....");
                    } else {
                        String entidade = capitalize.capitalizeVarUpper(capitalize.removerAcentos(txtEnt.getText()));
                        String autor = txtAutor.getText();
                        System.out.println("ENTIDADE -> " + entidade);
                        System.out.println("AUTOR -> " + autor);
                        confirmDialog = new BuildConfirmDialog(
                                DialogConfirmType.YES_NO,
                                "Deseja realmente gerar uma GUI - CRUD?",
                                "CONFIRMAÇÃO");
                        if (confirmDialog.getResponse() == JOptionPane.YES_OPTION) {
                            GeradorEntidade geradorEntidade = new GeradorEntidade(
                                    entidade,
                                    atributos,
                                    "models",
                                    txtPath.getText(),
                                    autor);
                            GeradorController geradorController = new GeradorController(
                                    entidade,
                                    atributos,
                                    "controllers",
                                    txtPath.getText(),
                                    autor,
                                    entidade);
                            GeradorGUI geradorGUI = new GeradorGUI(
                                    entidade,
                                    atributos,
                                    "screens",
                                    txtPath.getText(),
                                    autor,
                                    entidade);
                            GeradorMain geradorMain = new GeradorMain(entidade,
                                    atributos,
                                    "main",
                                    txtPath.getText(),
                                    autor,
                                    entidade);  
                            
                            salvarEntidadeGerada();
                            messageDialog = new BuildMessageDialog(
                                    DialogMessageType.INFO,
                                    "Sua CRUD foi gerada com sucesso",
                                    "GERAÇÃO CONCLUÍDA",
                                    cp);
                            atributos.clear();
                            modelGerador.clear();
                            txtEnt.setEnabled(true);
                            txtAutor.setEnabled(true);
                            txtEnt.setText("");
                            txtAutor.setText("");
                            txtVar.setText("");
                            combTypeSelect.setSelectedIndex(0);
                            spnFieldSize.setValue(10);

                            btnInit.setEnabled(true);
                            btnCancel.setEnabled(false);
                            btnAdd.setEnabled(false);
                            btnGerar.setEnabled(false);
                        }
                    }

                } catch (Exception excep) {
                    errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            excep.getMessage(),
                            "FALHA AO GERAR",
                            cp);
                }
            }
        });

        btnGerarEstrutura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fileManager.createDirectorys(destinyPath, caminhosDefault);

                    fileManager.copyFiles(destinyPath, caminhosDefault.get(3).substring(1));
                    fileManager.copyFiles(destinyPath, caminhosDefault.get(4).substring(1));
                    fileManager.copyFiles(destinyPath, caminhosDefault.get(5).substring(1));
                    fileManager.copyFiles(destinyPath, caminhosDefault.get(6).substring(1));
                    fileManager.copyFiles(destinyPath, caminhosDefault.get(7).substring(1));

                } catch (Exception excep) {
                    errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            "Erro ao gerrar a estrutura!",
                            "FALHA AO GERAR",
                            cp);
                }
            }
        });
        
        btnGerarEntidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtEnt.getText().isEmpty() || txtAutor.getText().isEmpty()) {
                        throw new Exception("Defina uma Entidade e um Autor para continuar....");
                    } else if (atributos.isEmpty()) {
                        throw new Exception("Adicione pelo menos 1 variável para prosseguir....");
                    } else {
                        String entidade = capitalize.capitalizeVarUpper(capitalize.removerAcentos(txtEnt.getText()));
                        String autor = txtAutor.getText();  
                        System.out.println("ENTIDADE -> " + entidade);
                        System.out.println("AUTOR -> " + autor);
                        confirmDialog = new BuildConfirmDialog(
                                DialogConfirmType.YES_NO,
                                "Deseja realmente gerar uma Entidade?",
                                "CONFIRMAÇÃO");
                        if (confirmDialog.getResponse() == JOptionPane.YES_OPTION) {
                            GeradorEntidade geradorEntidade = new GeradorEntidade(
                                    entidade,
                                    atributos,                                    
                                    "models",
                                    txtPath.getText(),
                                    autor);                         
                            
                            salvarEntidadeGerada();
                            messageDialog = new BuildMessageDialog(
                                    DialogMessageType.INFO,
                                    "Sua Entidade foi gerada com sucesso",
                                    "GERAÇÃO CONCLUÍDA",
                                    cp);
                            atributos.clear();
                            modelGerador.clear();
                            txtEnt.setEnabled(true);
                            txtAutor.setEnabled(true);
                            txtEnt.setText("");
                            txtAutor.setText("");
                            txtVar.setText("");
                            combTypeSelect.setSelectedIndex(0);
                            spnFieldSize.setValue(10);

                            buttonInitialConfigurations();
                        }
                    }
                } catch (Exception excep) {
                    errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            excep.getMessage(),
                            "FALHA AO GERAR",
                            cp);
                }
            }
        });
        
        btnGerarController.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtEnt.getText().isEmpty() || txtAutor.getText().isEmpty()) {
                        throw new Exception("Defina uma Entidade e um Autor para continuar....");
                    } else if (atributos.isEmpty()) {
                        throw new Exception("Adicione pelo menos 1 variável para prosseguir....");
                    } else {
                        String entidade = capitalize.capitalizeVarUpper(capitalize.removerAcentos(txtEnt.getText()));
                        String autor = txtAutor.getText();  
                        System.out.println("ENTIDADE -> " + entidade);
                        System.out.println("AUTOR -> " + autor);
                        confirmDialog = new BuildConfirmDialog(
                                DialogConfirmType.YES_NO,
                                "Deseja realmente gerar um Controller?",
                                "CONFIRMAÇÃO");
                        if (confirmDialog.getResponse() == JOptionPane.YES_OPTION) {                              
                            GeradorController geradorController = new GeradorController(
                                    entidade,
                                    atributos,
                                    "controllers",
                                    txtPath.getText(),
                                    autor,
                                    entidade);  
                            
                            salvarEntidadeGerada();
                            messageDialog = new BuildMessageDialog(
                                    DialogMessageType.INFO,
                                    "Seu Controller foi gerada com sucesso",
                                    "GERAÇÃO CONCLUÍDA",
                                    cp);
                            atributos.clear();
                            modelGerador.clear();
                            txtEnt.setEnabled(true);
                            txtAutor.setEnabled(true);
                            txtEnt.setText("");
                            txtAutor.setText("");
                            txtVar.setText("");
                            combTypeSelect.setSelectedIndex(0);
                            spnFieldSize.setValue(10);

                            buttonInitialConfigurations();
                        }
                    }
                } catch (Exception excep) {
                    errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            excep.getMessage(),
                            "FALHA AO GERAR",
                            cp);
                }
            }
        });
        
        btnGerarMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtEnt.getText().isEmpty() || txtAutor.getText().isEmpty()) {
                        throw new Exception("Defina uma Entidade e um Autor para continuar....");
                    } else if (atributos.isEmpty()) {
                        throw new Exception("Adicione pelo menos 1 variável para prosseguir....");
                    } else {
                        String entidade = capitalize.capitalizeVarUpper(capitalize.removerAcentos(txtEnt.getText()));
                        String autor = txtAutor.getText();  
                        System.out.println("ENTIDADE -> " + entidade);
                        System.out.println("AUTOR -> " + autor);
                        confirmDialog = new BuildConfirmDialog(
                                DialogConfirmType.YES_NO,
                                "Deseja realmente gerar uma Classe Main?",
                                "CONFIRMAÇÃO");
                        if (confirmDialog.getResponse() == JOptionPane.YES_OPTION) { 
                            GeradorMain geradorMain = new GeradorMain(entidade,
                                    atributos,
                                    "main",
                                    txtPath.getText(),
                                    autor,
                                    entidade);                            
                            
                            salvarEntidadeGerada();
                            messageDialog = new BuildMessageDialog(
                                    DialogMessageType.INFO,
                                    "Sua Classe Main foi gerada com sucesso!",
                                    "GERAÇÃO CONCLUÍDA",
                                    cp);
                            atributos.clear();
                            modelGerador.clear();
                            txtEnt.setEnabled(true);
                            txtAutor.setEnabled(true);
                            txtEnt.setText("");
                            txtAutor.setText("");
                            txtVar.setText("");
                            combTypeSelect.setSelectedIndex(0);
                            spnFieldSize.setValue(10);

                            buttonInitialConfigurations();
                        }
                    }
                } catch (Exception excep) {
                    errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            excep.getMessage(),
                            "FALHA AO GERAR",
                            cp);
                }
            }
        });
        
        btnGerarGUI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtEnt.getText().isEmpty() || txtAutor.getText().isEmpty()) {
                        throw new Exception("Defina uma Entidade e um Autor para continuar....");
                    } else if (atributos.isEmpty()) {
                        throw new Exception("Adicione pelo menos 1 variável para prosseguir....");
                    } else {
                        String entidade = capitalize.capitalizeVarUpper(capitalize.removerAcentos(txtEnt.getText()));
                        String autor = txtAutor.getText();  
                        System.out.println("ENTIDADE -> " + entidade);
                        System.out.println("AUTOR -> " + autor);
                        confirmDialog = new BuildConfirmDialog(
                                DialogConfirmType.YES_NO,
                                "Deseja realmente gerar uma Interface?",
                                "CONFIRMAÇÃO");
                        if (confirmDialog.getResponse() == JOptionPane.YES_OPTION) {                              
                            GeradorGUI geradorGUI = new GeradorGUI(
                                    entidade,
                                    atributos,
                                    "screens",
                                    txtPath.getText(),
                                    autor,
                                    entidade);
                            
                            salvarEntidadeGerada();
                            messageDialog = new BuildMessageDialog(
                                    DialogMessageType.INFO,
                                    "Sus Interface foi gerada com sucesso",
                                    "GERAÇÃO CONCLUÍDA",
                                    cp);
                            atributos.clear();
                            modelGerador.clear();
                            txtEnt.setEnabled(true);
                            txtAutor.setEnabled(true);
                            txtEnt.setText("");
                            txtAutor.setText("");
                            txtVar.setText("");
                            combTypeSelect.setSelectedIndex(0);
                            spnFieldSize.setValue(10);

                            buttonInitialConfigurations();
                        }
                    }
                } catch (Exception excep) {
                    errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            excep.getMessage(),
                            "FALHA AO GERAR",
                            cp);
                }
            }
        });

        btnCarregarEntidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtEnt.getText().trim().isEmpty()) {
                        throw new Exception("Defina a Entidade!");
                    } else {
                        String entidade = capitalize.capitalizeVarUpper(capitalize.removerAcentos(txtEnt.getText()));
                        String nomeArquivo = "src\\entidades_geradas\\" + entidade.trim();
                        atributos = new Atributo().forListStringToList(fileManager.buscarDadosEmArquivoTxt(nomeArquivo));
                        if (atributos.isEmpty()) {
                            atributos.clear();
                            btnVisualizarVariaveis.setEnabled(false);                            
                            throw new Exception("Entidade não encontrada!");
                        } else {
                            for (Atributo var : atributos) {
                                modelGerador.addModel(var.getType(), var.getName(), var.getSize());
                                
                            }
                            messageDialog = new BuildMessageDialog(
                                    DialogMessageType.SUCESS,
                                    "Entidade encontrada e carregada!",
                                    "BUSCA REALIZADA",
                                    cp);
                        }

                    }
                } catch (Exception excep) {
                    errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            excep.getMessage(),
                            "FALHA AO ABRIR",
                            cp);
                }

            }
        });
        

        pack();

        //Close Window Action
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

    }
    
    private void buttonInitialConfigurations() {
        btnInit.setEnabled(true);
        btnAdd.setEnabled(false);
        btnCancel.setEnabled(false);
        btnGerar.setEnabled(false);
        btnVisualizarVariaveis.setEnabled(false);
        btnGerarEntidade.setEnabled(false);
        btnGerarController.setEnabled(false);
        btnGerarMain.setEnabled(false);
        btnGerarGUI.setEnabled(false);
    }

    private void verifyPath() {
        if (!manipulaArquivo.existeOArquivo("LastPath.txt")) {
            manipulaArquivo.criarArquivoVazio("LastPath.txt");
        }

        lastPath = manipulaArquivo.abrirArquivo("LastPath.txt");
        if (!lastPath.isEmpty()) {
            destinyPath = lastPath.get(0);
        } else {
            destinyPath = defaultPath;
        }
        txtPath.setText(destinyPath);
    }
    
    private void salvarEntidadeGerada() {
        try {
            if (txtEnt.getText().trim().isEmpty() || atributos.isEmpty()) {
                throw new Exception("Campo Entidade ou variáveis não cadastradas!");
            } else {
                String entidade = capitalize.capitalizeVarUpper(capitalize.removerAcentos(txtEnt.getText()));
                String nomeArquivo = "src\\entidades_geradas\\" + entidade.trim();
                Atributo atr = new Atributo();
                fileManager.armazenarDadosEmArquivoTxt(atr.toListString(atributos), nomeArquivo);
            }
        } catch (Exception excep) {
            errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            excep.getMessage(),
                            "FALHA AO ABRIR",
                            cp);
        }
    }

}
