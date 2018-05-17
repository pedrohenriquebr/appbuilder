/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder;

import java.io.*;

/**
 *
 * @author psilva
 */
public class ClassBuilder {
    
    private String caminho ;
    public ClassBuilder(String caminho){
        this.caminho = caminho;
    }
    
    public void build(Classe classe ) throws FileNotFoundException, IOException{
        FileWriter fw  = new FileWriter(new File(this.caminho + classe.getNome()+".java"));
        fw.write(classe.toString());
        fw.close();
    }
    
}
