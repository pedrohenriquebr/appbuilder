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

public final class Log {

    public static PrintStream out = new PrintStream(System.out);

    public static void debug(String message) {
        out.println("[DEBUG]" + message);
    }

    public static void debug(String message, Object var) {
        debug(message + "(" + var + ")");
    }
    
    public static void debug(String tag, String message){
        debug("["+tag+"]"+message);
    }
    
    public static void debug(Class context, String message){
        debug(context.getSimpleName(),message);
    }
    
    public static void debug(Class context, String message, Object var){
        debug(context,message+"("+var+")");
    }
    
    
}
