package br.pizzaria.sistemas.dao;

import br.pizzaria.sistemas.model.pizzaria.Pedido;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO extends ConnectionDAO {

    public int inserirPedido(Pedido pedido, int idCliente) {
        int idPedido = -1;
        connectToDb();
        pedido.setId_cliente(idCliente);
        String sql = "INSERT INTO pedido(data_pedido, valor_total, status, id_cliente) VALUES (?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, pedido.getData_pedido());
            pst.setFloat(2, pedido.getValor_total());
            pst.setString(3, pedido.getStatus());
            pst.setInt(4, pedido.getId_cliente());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idPedido = generatedKeys.getInt(1);
                }
                generatedKeys.close();
            }

            return idPedido;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pedido: " + e.getMessage());
            return idPedido;
        } finally {
            try{
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    public List<Pedido> selectPedido(){
        connectToDb();

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT * FROM pedido";

        try{
            st = connection.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()){

                Pedido pedido = new Pedido(
                        rs.getString("data_pedido"),
                        rs.getFloat("valor_total"),
                        rs.getString("status"),
                        rs.getInt("id_cliente"),
                        rs.getInt("idPedido")
                );
                pedidos.add(pedido);
            }

        } catch (SQLException e){
            System.out.println("Erro ao buscar pedido: " + e.getMessage());
        } finally {
            try{
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return pedidos;
    }

    public void updatePedido(Pedido pedido){
        connectToDb();

        String sql = "UPDATE pedido SET valor_total=?, status=? WHERE idPedido=?";

        try{
            pst = connection.prepareStatement(sql);
            pst.setFloat(1, pedido.getValor_total());
            pst.setString(2, pedido.getStatus());
            pst.setInt(3, pedido.getIdPedido());
            pst.execute();
        } catch (SQLException e){
            System.out.println("Erro ao atualizar pedido: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }

    }

    public void detelePedido(Pedido pedido){
        connectToDb();
        String sql = "DELETE FROM pedido WHERE idPedido=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, pedido.getIdPedido());
            pst.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao deletar pedido: " + e.getMessage());
        } finally {
            try {
                if(pst != null) pst.close();
                if(connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar: " + e.getMessage());
            }
        }
    }

    public boolean deletePedidoByCliente(int id){
        this.connectToDb();
        String sql = "DELETE FROM pedido WHERE id_cliente = ?";
        boolean sucesso;

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setInt(1, id);
            this.pst.execute();
            return true;
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
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

    public void listarPedidosClientes(int idCliente) {
        connectToDb();
        String sql = "SELECT p.idPedido, c.nome, p.data_pedido, p.valor_total FROM pedido p JOIN cliente c ON p.id_cliente = c.idCliente WHERE p.id_cliente=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idCliente);
            rs = pst.executeQuery();

            while(rs.next()) {
                System.out.println("ID Pedido: " + rs.getInt("idPedido") +
                        " | Cliente: " + rs.getString("nome") +
                        " | Data: " + rs.getString("data_pedido") +
                        " | Total: R$" + rs.getFloat("valor_total"));
            }
        } catch (SQLException e){
            System.out.println("Erro ao listar pedido clientes: " + e.getMessage());
        } finally {
            try{
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    public void listarDetalhesDoPedido() {
        connectToDb();
        String sql = "SELECT p.idPedido, pi.quantidade, pz.nome, p.valor_total FROM pedido p JOIN item_pedido pi ON p.idPedido = pi.id_pedido JOIN pizza pz ON pi.id_pizza = pz.idPizza";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()) {
                System.out.println("Pedido ID: " + rs.getInt("idPedido") +
                        " | Qtd: " + rs.getInt("quantidade") +
                        " | Pizza: " + rs.getString("nome") +
                        " | Total: R$" + rs.getFloat("valor_total"));
            }
        } catch (SQLException e){
            System.out.println("Erro ao listar detalhes pedido: " + e.getMessage());
        } finally {
            try{
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

}