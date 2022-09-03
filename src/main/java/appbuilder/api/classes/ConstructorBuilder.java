package appbuilder.api.classes;

import appbuilder.api.methods.MethodBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pedro Henrique Braga da Silva
 */
public class ConstructorBuilder extends MethodBuilder {

    public ConstructorBuilder(String accessMod, String name) {
        super(accessMod, "", "", name);
    }

}
