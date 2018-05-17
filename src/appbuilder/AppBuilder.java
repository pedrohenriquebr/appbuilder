/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder;

import java.io.IOException;
import java.util.*;

/**
 *
 * @author aluno
 */
public class AppBuilder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        Scanner scan = new Scanner(System.in);
        System.out.println("Nome da classe: ");
        String nome = scan.nextLine();
        System.out.println("Pacote: ");
        String pacote = scan.nextLine();
        System.out.println("Caminho do pacote: ");
        String caminho = scan.nextLine();

        Classe classe = new Classe(nome, pacote, caminho);
        Construtor mt = new Construtor("public", nome);
        mt.setCorpo("System.out.println(\"Olá mundo !\");");
        classe.addConstrutor(mt);
        Metódo m = new Metódo("public", "static", "void", "main");
        m.addParametro("String []", "args");
        m.setCorpo("new "+classe.getNome()+"();");
        classe.addMetódo(m);
        classe.addAtributo(new Atributo("int", "valor"));

        ClassBuilder builder = new ClassBuilder("/home/psilva/Documentos/");
        builder.build(classe);

    }

}
