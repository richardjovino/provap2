/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import db.User;
import java.sql.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author palom
 */
public class DbListener implements ServletContextListener {
    public static final String CLASS_NAME = "org.sqlite.JDBC";
    public static final String URL = "jdbc:sqlite:p2.db";
    
    public static String step = null;
    public static Exception exception = null;
    
    public static Connection getConnection() throws Exception{
        return DriverManager.getConnection(URL);
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            step = "Conectando com a base";
            Class.forName(CLASS_NAME);
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            //stmt.execute("DROP TABLE users");
            step = "Criando tabela de usuários";
            String sql = "CREATE TABLE IF NOT EXISTS users("
                    + "name VARCHAR(200) NOT NULL,"
                    + "login VARCHAR(50) UNIQUE NOT NULL,"
                    + "password_hash LONG,"
                    + "role VARCHAR(20) NOT NULL"
                    + ")";
            stmt.execute(sql);
            if(User.getUsers().isEmpty()){
                step = "Inserindo usuário administrativo";
                sql = "INSERT INTO users(name, login, password_hash, role) "
                    + "VALUES('Administrador', 'admin', '"+("1234".hashCode())+"', 'ADMIN')";
                stmt.execute(sql);
                sql = "INSERT INTO users(name, login, password_hash, role) "
                    + "VALUES('Paloma Ierardi', 'paloma', '"+("1234".hashCode())+"', 'USER')";
                stmt.execute(sql); 
            }
            step = "Criando tabela de disciplinas ";
            sql = "CREATE TABLE IF NOT EXISTS disciplinas("
                    + "nome VARCHAR(50) PRIMARY KEY,"
                    + "diadasemana VARCHAR(200) NOT NULL,"
                    + "horario VARCHAR(200) NOT NULL,"
                    + "qtaulas VARCHAR(200) NOT NULL,"
                    + "notap1 double NOT NULL,"
                    + "notap2 double NOT NULL,"
                    + ")";
            stmt.execute(sql);
           
                
            
            //TODO: implementar código de criação das outras tabelas
            stmt.close();
            con.close();
        }catch(Exception ex){
            exception = ex;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}