/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helpers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author afmireski
 */
public class GenericComponents {
    
    ///SPINNERS
    public JSpinner createIntSpinner(int initialValue, int minValue, int maxValue, int passo) {
        SpinnerNumberModel model = new SpinnerNumberModel(
                initialValue, 
                minValue, 
                maxValue, 
                passo);
        return new JSpinner(model);
    }
    
    public JSpinner createDoubleSpinner(double initialValue, double minValue, double maxValue, double passo) {
        SpinnerNumberModel model = new SpinnerNumberModel(
                initialValue, 
                minValue, 
                maxValue, 
                passo);
        return new JSpinner(model);
    }
    
    public JSpinner createListSpinner(String list[], int width) {
        SpinnerListModel model = new SpinnerListModel(list);
        JSpinner jSpinner = new JSpinner(model);
        
        JComponent editor = jSpinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setColumns(width); //DEFINE A LARGURA DO SPINER
        return jSpinner;
    }
    
    ///PANELS
    public JPanel createChildPanel(Component child, FlowLayout layout) {
        JPanel jPanel = new JPanel(layout);
        jPanel.add(child);
        return jPanel;
    }

    ///COMBO BOX
    public JComboBox createComboBox(List<String> list) {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        JComboBox comboBox = new JComboBox(model);   
        if (list != null) {
            for (String str : list) {
                model.addElement(str);
            }
        }        
        return  comboBox;
    }
    
    
    
}
