/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.templates;

import appbuilder.api.classes.Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author psilva
 */
public class Dao {
    
    private Connection con;
    
    public Dao(){
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/db","user","password");
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public boolean adicionou(Modelo m) throws SQLException{
        PreparedStatement stmt = this.con.prepareCall("INSER INTO ");
        stmt.setDate(0, new Date((Calendar.getInstance().getTimeInMillis())));
        
        return stmt.executeUpdate()>0;
    }
    
    
    public Modelo pesquisarPorNome(String nome) throws SQLException{
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM tabela WHERE nome=?");
        stmt.setString(1,nome);
        Calendar c = Calendar.getInstance();
       
        ResultSet rs  = stmt.executeQuery();
        ArrayList<Modelo> m = new ArrayList<>();
        while(rs.next()){
            String g = rs.getString("");
            
            
        }
        
        return null;
    }
}
