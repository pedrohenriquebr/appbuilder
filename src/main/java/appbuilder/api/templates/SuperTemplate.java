/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.templates;

/**
 *
 * @author psilva
 */
public class SuperTemplate {

    public int doIt(String task) {
        if (task.equals("run")) {
            return 1;
        } else {
            return 0;
        }
    }
}
