/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.views;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author psilva
 */
public class FolderFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory();
    }

    @Override
    public String getDescription() {
        return "Somente Diretórios";
    }

}
