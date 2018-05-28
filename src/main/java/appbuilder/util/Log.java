/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.util;

/**
 *
 * @author pedrohenrique
 */
import java.io.*;
import java.util.*;

public final class Log {

    private static boolean ativado = true;
    private static String filter = "";

    public static void setEstado(boolean estado) {
        ativado = estado;
    }

    public static boolean getEstado() {
        return ativado;
    }

    public static PrintStream out = new PrintStream(System.out);

    /**
     * Todos os outros métodos de depuração, usam esse método para exibir.
     *
     * @param message
     */
    public static void debug(String message) {
        //Verifica se o estado está ativado
        if (ativado) {
            out.println("[DEBUG]" + message);
        }
    }

    public static void debug(String message, Object var) {
        debug(message + "(" + var + ")");
    }

    public static void debug(String tag, String message) {
        if (!filter.isEmpty()) {
            if (tag.equals(filter)) {
                debug("[" + tag + "]", message);
            }
        } else {
            debug("[" + tag + "]" + message);
        }
    }

    public static void filterTag(String tag) {
        filter = tag;
    }

    public static void debug(Class context, String message) {
        debug(context.getSimpleName(), message);
    }

    public static void debug(Class context, String message, Object var) {
        debug(context, message + "(" + var + ")");
    }

}
