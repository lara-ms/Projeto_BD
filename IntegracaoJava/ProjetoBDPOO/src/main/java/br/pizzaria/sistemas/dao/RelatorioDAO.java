package br.pizzaria.sistemas.dao;

import java.sql.SQLException;

public class RelatorioDAO extends ConnectionDAO{
    
    public void listarPedidosComClientes() {
        connectToDb();
        String sql = "SELECT p.idPedido, c.nome, p.data_pedido, p.valor_total FROM pedido p JOIN cliente c ON p.id_cliente = c.idCliente";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()) {
                System.out.println("ID: " + rs.getInt("idPedido") + " | Cliente: " + rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
