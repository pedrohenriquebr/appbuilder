package appbuilder.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author psilva
 */
public class ClassBuilder {

    private String caminho;
    private File arquivo;
    private File diretórioArvore;//o diretórioArvore que contém toda a árvore de diretórios da classe 
    private Classe classe;

    public ClassBuilder(String caminho) {
        this.caminho = caminho;
    }

    public void build(Classe classe) throws FileNotFoundException, IOException {
        String path = "";
        this.classe = classe;
        String pathPrincipal = classe.getPacote().getCaminho().replace(".", "/");
        if (caminho.endsWith("/")) {
            path = this.caminho + pathPrincipal;
        } else {
            path = this.caminho + "/" + pathPrincipal;
        }

        arquivo = new File(path + "/" + classe.getNome() + ".java");
        diretórioArvore = new File(caminho);
        new File(path).mkdirs();
        FileWriter fw = new FileWriter(arquivo);
        fw.write(classe.toString());

        fw.close();
    }

    public File getArquivo() {
        return this.arquivo;
    }

    public File getDiretório() {

        return this.diretórioArvore;
    }

    public void compile() throws IOException {
        System.out.println("Executando : " + "javac " + this.arquivo.getAbsolutePath());
        Process process = Runtime.getRuntime().exec("javac " + this.arquivo.getAbsolutePath());
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

    public void execute() throws IOException {
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
