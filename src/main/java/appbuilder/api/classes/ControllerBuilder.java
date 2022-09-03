/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.classes;

import appbuilder.api.vars.AttributeBuilder;

/**
 *
 * @author psilva
 */
public class ControllerBuilder extends ClassBuilder {

    private AttributeBuilder daoVar;
    
    public ControllerBuilder(DaoBuilder dao, ModelBuilder modelBuilder) {
        super(modelBuilder.getName() + "Controller");

        addImport(dao);

        this.daoVar = new AttributeBuilder("private", dao.getName(), "dao");
        addAttribute(this.daoVar);
    }

}
