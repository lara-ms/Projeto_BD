package br.pizzaria.sistemas.dao;

import br.pizzaria.sistemas.model.pizzaria.Cliente;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends ConnectionDAO{
    public int inserirCliente(Cliente cliente){
        int idCliente = -1;
        connectToDb(); // Abre conexao
        String sql = "INSERT INTO cliente(nome, email, telefone, endereco) VALUES (?, ?, ?, ?)";

        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getEmail());
            pst.setString(3, cliente.getTelefone());
            pst.setString(4, cliente.getEndereco());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idCliente = generatedKeys.getInt(1);
                }
                generatedKeys.close();
            }

            return idCliente;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println("Erro ao fechar conexão: "+ e.getMessage());
            }
        }

        return idCliente;
    }

    public List<Cliente> selectCliente() {
        List<Cliente> clientes = new ArrayList<>();
        connectToDb();
        String sql = "SELECT * FROM cliente";

        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("endereco")

                );

                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Clientes: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return clientes;
    }

    public boolean updateCliente(Cliente cliente) {
        connectToDb();
        String sql = "UPDATE cliente SET telefone=?, endereco=? WHERE idCliente=?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, cliente.getTelefone());
            pst.setString(2, cliente.getEndereco());
            pst.setInt(3, cliente.getIdCliente());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Cliente: " + e.getMessage());
            return false;
        } finally {
            try{
                if(pst != null){
                    pst.close();
                }
                if(connection != null){
                    connection.close();
                }
            }catch (SQLException e){
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    public boolean deleteCliente(Cliente cliente){
        connectToDb();
        String sql = "DELETE FROM cliente WHERE idCliente=?";

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setInt(1, cliente.getIdCliente());
            this.pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        } finally {
            try {
                this.connection.close();
                this.pst.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }
    }

    public Cliente buscarClientePorNome(String nome){
        connectToDb();
        String sql= "SELECT * FROM cliente WHERE nome=?";
        Cliente cliente = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("endereco")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Clientes: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return cliente;
    }

    public Cliente buscarClientePorId(int id) {
        connectToDb();
        String sql = "SELECT * FROM cliente WHERE idCliente = ?";
        Cliente cliente = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("endereco")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Cliente pelo ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
        return cliente;
    }


}
