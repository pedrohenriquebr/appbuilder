package appbuilder.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import appbuilder.util.classes.Classe;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author psilva
 */
public class ClassBuilder {

    private String caminho;
    private File diretórioArvore;

    public ClassBuilder(String caminho) {
        this.caminho = caminho;
    }

    public File build(Classe classe) throws FileNotFoundException, IOException {
        String path = "";
        String pathPrincipal = classe.getPacote().getCaminho().replace(".", "/");
        if (caminho.endsWith("/")) {
            path = this.caminho + pathPrincipal;
        } else {
            path = this.caminho + "/" + pathPrincipal;
        }

        File arquivo = new File(path + "/" + classe.getNome() + ".java");
        diretórioArvore = new File(caminho);
        new File(path).mkdirs();
        FileWriter fw = new FileWriter(arquivo);
        fw.write(classe.toString());

        fw.close();

        return arquivo;
    }

    public File getDiretório() {

        return this.diretórioArvore;
    }

    public void compile(File arquivo) throws IOException {
        System.out.println("Executando : " + "javac " + arquivo.getAbsolutePath());
        Process process = Runtime.getRuntime().exec("javac " + arquivo.getAbsolutePath());
        Scanner scan = new Scanner(process.getInputStream());

        while (scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }

        scan = new Scanner(process.getErrorStream());

        while (scan.hasNextLine()) {
            System.err.println(scan.nextLine());
        }

        scan.close();
    }

    public void execute(Classe classe) throws IOException {
        System.out.println("Executando: " + "java -cp " + diretórioArvore.getAbsolutePath() + " " + classe.getNomeCompleto());
        Process process = Runtime.getRuntime().exec("java -cp " + diretórioArvore.getAbsolutePath() + " " + classe.getNomeCompleto());

        Scanner scan = new Scanner(process.getInputStream());

        while (scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }

        scan = new Scanner(process.getErrorStream());

        while (scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }

        scan.close();
    }
}
