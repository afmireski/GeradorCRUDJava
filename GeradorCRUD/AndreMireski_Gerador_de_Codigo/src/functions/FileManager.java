/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package functions;

import java.io.File;
import java.util.List;
import tools.CopiarArquivos;

/**
 *
 * @author afmireski
 */
public class FileManager {
    
    public void createDirectorys(String path, List<String> pastas) {
        ///MÉTODO PARA CRIAR PASTAS
        for (String dir : pastas) {
            String packagePath = path + dir;
            File pack = new File(packagePath);
            if (!pack.exists()) {
                new File(packagePath).mkdir(); //CRIA A PASTA
            }
        }
    }
    
    public void copyFiles(String destinyPath, String filesPath) {
        ///MÉTODO PARA COPIAR ARQUIVOS
        File dir = new File(filesPath);
        
        if (dir.exists()) {
            File[] dirFiles = dir.listFiles(); //LISTA TODOS OS ARQUIVOS
            CopiarArquivos copiarArquivos = new CopiarArquivos();
            
            for (File file : dirFiles) {
                String finalPath = destinyPath + "\\"+filesPath +"\\"+ file.getName();
                copiarArquivos.copiar(file.getAbsolutePath(), finalPath);
            }
        }
            
        
    }
    
}
