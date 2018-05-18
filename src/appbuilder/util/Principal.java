package appbuilder.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.lang.reflect.*;

/**
 *
 * @author psilva
 */
public class Principal {

    private int privado;

    public static void main(String[] args) {
        System.out.println("Olá mundo !");
    }

    public String toString() {
        String codigo = "";

        Class classe = Principal.class;
        

        
        for (Method method : classe.getDeclaredMethods()) {

            int modifiers = method.getModifiers();
            codigo += Modifier.toString(modifiers) + " ";
            codigo += method.getReturnType()+ " ";
            codigo += method.getName() + " ";
            codigo += "(";
            
          

            if (method.getParameterCount() > 0) {
                int contador = 0;

                for (Parameter param : method.getParameters()) {
                    contador++;
                    
                    if (contador % 2 == 0) {
                        codigo += ", ";
                    }
                    
                    String name = param.getName();
                    String type = param.getType().getTypeName();
                    codigo += type + " " + name;

                }

            }

            codigo += "){\n\n";
            codigo += "\n}\n\n";
        }

        return codigo;
    }

}
