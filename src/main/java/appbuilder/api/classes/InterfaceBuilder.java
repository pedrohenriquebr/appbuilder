/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.methods.MethodBuilder;
import appbuilder.api.packages.PackageBuilder;
import appbuilder.api.vars.AttributeBuilder;

/**
 *
 * @author psilva
 */
public class InterfaceBuilder extends ClassBuilder {

    public InterfaceBuilder(String nome) {
        super(nome);
        setInterface(true);
    }
    
    public InterfaceBuilder(String nome, String pacote) {
        this(nome);
        this.packageBuilder = new PackageBuilder(pacote);
    }

    public InterfaceBuilder(String name, String _package, String path) {
        this(name);
        this.packageBuilder = new PackageBuilder(_package, path);
    }
    
    
    @Override
    public boolean addAttribute(AttributeBuilder atr) {
        return false;
    }

    @Override
    public boolean addMethod(MethodBuilder method) {
        method.setBelongsInterface(true);
        return super.addMethod(method);
    }

}
