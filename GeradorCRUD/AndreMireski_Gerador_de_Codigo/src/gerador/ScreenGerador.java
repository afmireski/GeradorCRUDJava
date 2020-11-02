package gerador;

import enums.DialogConfirmType;
import enums.DialogMessageType;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import model.ModelGerador;
import tools.CaixaDeFerramentas;
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

//INSTANCIA DAS TOOLS
    final private CaixaDeFerramentas cf = new CaixaDeFerramentas();
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
    JPanel panNort1 = new JPanel();
    JPanel panNort2 = new JPanel();
    JPanel panNort3 = new JPanel();

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
    JButton btnGerar = new JButton("Gerar");
    JButton btnCancel = new JButton("Cancel");

    //INSTANCIA DOS LABELS
    JLabel lblEnt = new JLabel("ENTIDADE");
    JLabel lblAutor = new JLabel("AUTOR");
    JLabel lblTipo = new JLabel("TIPO");
    JLabel lblVar = new JLabel("VARIÁVEL");
    JLabel lblSize = new JLabel("TAMANHO DO CAMPO");

    //INSTANCIA DOS TEXTFIELD
    JTextField txtEnt = new JTextField(10);
    JTextField txtAutor = new JTextField(10);
    JTextField txtVar = new JTextField(10);

    //INSTANCIA DOS SPINNERS
    JSpinner spnFieldSize;

    //INSTANCIA DOS COMBO BOX;
    JComboBox combTypeSelect;

    //INSTANCIA DOS TIPOS
    String types[] = {"byte", "short", "int", "long", "float", "double", "String", "boolean", "Date"};
    List<String> typeList = Arrays.asList(types);

    //INSTANCIA DO LIST ATRIBUTOS;
    List<String> atributos = new ArrayList<>();

    public ScreenGerador() {
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("GERADOR DE CRUD");

        //SPINNER CONFIG
        spnFieldSize = components.createIntSpinner(10, 1, 100, 1);

        //COMBO BOX CONFIG
        combTypeSelect = components.createComboBox(typeList);

        //BUTTONS INITIAL CONFIGURATIONS
        btnInit.setEnabled(true);
        btnAdd.setEnabled(false);
        btnCancel.setEnabled(false);
        btnGerar.setEnabled(false);

        //CONTAINER CONFIGURATIONS
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(panNorth, BorderLayout.NORTH);
        cp.add(panSouth, BorderLayout.SOUTH);
        cp.add(panEast, BorderLayout.EAST);
        cp.add(panWest, BorderLayout.WEST);
        cp.add(panCenter, BorderLayout.CENTER);

        //PAN NORTH CONFIGURATIONS
        panNorth.setLayout(new FlowLayout(FlowLayout.LEFT));
        panNorth.add(panNort1);
        panNorth.add(panNort2);
        panNorth.add(panNort3);

        //CONFIGURATION NORT1
        panNort1.add(lblEnt);
        panNort1.add(txtEnt);

        //CONFIGURATION NORT2
        panNort2.add(lblAutor);
        panNort2.add(txtAutor);
        //CONFIGURATION NORT3
        panNort3.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panNort3.add(btnInit);
        panNort3.add(btnCancel);

        //PAN EAST CONFIGURATIONS
        //PAN WEST CONFIGURATIONS
        //PAN SOUTH CONFIGURATIONS
        panSouth.add(btnGerar);

        //PAN CENTER CONFIGURATIONS
        panCenter.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        panCenter.setLayout(new GridLayout(2, 1));
        panCenter.add(panBody);
        panCenter.add(panBodyTable);

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

                        txtEnt.setEnabled(false);
                        txtAutor.setEnabled(false);
                        if (atributos.isEmpty()) {
                            btnGerar.setEnabled(false);
                        } else {
                            btnGerar.setEnabled(true);
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
                btnInit.setEnabled(true);
                btnAdd.setEnabled(false);
                btnCancel.setEnabled(false);
                btnGerar.setEnabled(false);

                txtEnt.setEnabled(true);
                txtAutor.setEnabled(true);
                txtEnt.setText("");
                txtAutor.setText("");

                combTypeSelect.setSelectedIndex(0);
                txtVar.setText("");
                spnFieldSize.setValue(10);

                txtEnt.requestFocus();
                atributos.clear();
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
                        String atributo = type + ";" + var + ";" + size;
                        System.out.println("Atributo -> " + atributo);
                        atributos.add(atributo);
                        modelGerador.addModel(var, type, size);
                        txtVar.setText("");
                        spnFieldSize.setValue(10);
                        combTypeSelect.requestFocus();
                        if (!atributos.isEmpty() && atributos.size() > 1) {
                            btnGerar.setEnabled(true);
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
                                    autor);
                            GeradorController geradorController = new GeradorController(
                                    entidade,
                                    atributos,
                                    "controllers",
                                    autor,
                                    entidade);
                            GeradorGUI geradorGUI = new GeradorGUI(
                                    entidade,
                                    atributos,
                                    "screens",
                                    autor,
                                    entidade);
                            messageDialog = new BuildMessageDialog(
                                    DialogMessageType.INFO,
                                    "Sua CRUD foi gerada com sucesso",
                                    "GERAÇÃO CONCLUÍDA",
                                    cp);
                            atributos.clear();
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

        pack();

        //Close Window Action
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

    }

}
