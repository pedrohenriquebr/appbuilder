package appbuilder.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import appbuilder.models.Manifesto;
import java.io.*;
import java.util.*;

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

    public List build(List<appbuilder.api.classes.ClassBuilder> aClasses) throws IOException {
        List<File> files = new ArrayList<>();
        for (appbuilder.api.classes.ClassBuilder classBuilder : aClasses) {
            files.add(build(classBuilder));
        }

        return files;
    }

    public File build(appbuilder.api.classes.ClassBuilder classBuilder) throws FileNotFoundException, IOException {
        String path = "";
        String pathPrincipal = classBuilder.getPacote().getCaminho().replace(".", "/");
        if (caminho.endsWith("/")) {
            path = this.caminho + pathPrincipal;
        } else {
            path = this.caminho + "/" + pathPrincipal;
        }

        File arquivo = new File(path + "/" + classBuilder.getName() + ".java");
        diretórioArvore = new File(caminho);
        new File(path).mkdirs();
        FileWriter fw = new FileWriter(arquivo);
        fw.write(classBuilder.toString());

        fw.close();

        return arquivo;
    }

    public List<File> compile(List<File> files) throws IOException {
        List<File> compilados = new ArrayList<>();
        for (File file : files) {
            compilados.add(compile(file));
        }

        return compilados;
    }

    public File getDiretório() {

        return this.diretórioArvore;
    }

    public File compile(File arquivo) throws IOException {
        File diretorio = new File(getDiretório() + "/classes");
        diretorio.mkdir();

        System.out.println("Executando : " + "javac -cp " + getDiretório() + " "
                + arquivo.getAbsolutePath() + " -d " + diretorio.getAbsolutePath());
        Process process = Runtime.getRuntime().exec("javac -cp " + getDiretório() + " "
                + arquivo.getAbsolutePath() + " -d " + diretorio.getAbsolutePath());
        Scanner scan = new Scanner(process.getInputStream());

        while (scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }

        scan = new Scanner(process.getErrorStream());

        while (scan.hasNextLine()) {
            System.err.println(scan.nextLine());
        }

        scan.close();

        return diretorio;
    }

    public File packJar(String arquivoSaida, Manifesto manifesto) throws IOException {
        File verificar = new File(this.caminho + "/classes");
        if (!verificar.isDirectory() && verificar.exists()) {
            return null;
        }
        
        File libs = new File(this.caminho+"/libs");
        if(!libs.isDirectory() && libs.exists()){
            return null;
        }
        
        libs.mkdir();

        File executavel = new File(this.caminho + "/" + arquivoSaida + ".jar");
        String codigo = "jar cfm " + executavel.getAbsolutePath() + " "
                + manifesto.getCaminho() + " -C " + verificar.getAbsolutePath() + " . ";
        System.out.println("Executando: " + codigo);

        Process process = Runtime.getRuntime().exec(codigo);

        Scanner scan = new Scanner(process.getInputStream());

        while (scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }

        scan = new Scanner(process.getErrorStream());

        while (scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }

        scan.close();

        return executavel;
    }

    public void execute(appbuilder.api.classes.ClassBuilder classBuilder) throws IOException {
        System.out.println("Executando: " + "java -cp "
                + this.caminho+"/classes" + " " + classBuilder.getNomeCompleto());
        Process process = Runtime.getRuntime().exec("java -cp "
                + this.caminho+"/classes" + " " + classBuilder.getNomeCompleto());

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

    public void executeJar(File file) throws IOException {
        System.out.println("Executando: java -jar " + file.getAbsolutePath());
        Process process = Runtime.getRuntime().exec("java -jar " + file.getAbsolutePath());

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
