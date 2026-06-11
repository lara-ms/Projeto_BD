package br.pizzaria.sistemas.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ConnectionDAO {
    protected Connection connection;
    protected PreparedStatement pst;
    protected Statement st;
    protected ResultSet rs;
    protected String database = "pizzaria";
    protected String user = "root";
    protected String password = "root";
    protected String url;

    public ConnectionDAO() {
        this.url = "jdbc:mysql://localhost:3306/" + this.database;
    }

    public void connectToDb() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }

    }
}
