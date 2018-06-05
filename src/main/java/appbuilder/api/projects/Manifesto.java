/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.projects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author psilva
 */
public class Manifesto {

    private float versão = 1.0F;
    private String classePrincipal = "";
    private String autor = "1.0 (JavaAppBuilder)";
    private String caminho = "";
    private List<String> classPath = new ArrayList<String>();

    public Manifesto() {
        classPath.add("libs/*.jar");
    }

    public void write(String caminho) throws IOException {
        if (caminho.endsWith("/")) {
            write(new File(caminho + "MANIFEST.MF"));
        } else {
            write(new File(caminho + "/MANIFEST.MF"));
        }
    }

    public void write(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter writer = new FileWriter(file);
        this.caminho = file.getAbsolutePath();
        writer.write("Manifest-Version: " + versão + "\n");
        writer.write("Created-By: " + autor + "\n");
        if (!classPath.isEmpty()) {
            writer.write("Class-Path: ");

            for (String path : this.classPath) {
                writer.write(path + " ");
            }
            writer.write("\n");
        }
        if (!classePrincipal.isEmpty()) {
            writer.write("Main-Class: " + classePrincipal + "\n");
        }
        writer.close();
    }

    public boolean addClassPath(String path) {
        return this.classPath.add(path);
    }

    public List<String> getClassPath() {
        return classPath;
    }

    public void setClassPath(List<String> classPath) {
        this.classPath = classPath;
    }

    public String getCaminho() {
        return this.caminho;
    }

    public float getVersion() {
        return versão;
    }

    public void setVersion(float version) {
        this.versão = version;
    }

    public String getClassePrincipal() {
        return classePrincipal;
    }

    public void setClassePrincipal(String classePrincipal) {
        this.classePrincipal = classePrincipal;
    }

    public String getAuthor() {
        return autor;
    }

    public void setAuthor(String author) {
        this.autor = author;
    }

}
