package br.pizzaria.sistemas.dao;

import br.pizzaria.sistemas.model.pizzaria.Pizza;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PizzaDAO extends ConnectionDAO{
    public int  inserirPizza(Pizza pizza){
        int idPizza = -1;
        connectToDb(); // Abre conexao
        String sql = "INSERT INTO pizza(nome, descricao, preco, tamanho) VALUES (?, ?, ?, ?)";

        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, pizza.getNome());
            pst.setString(2, pizza.getDescricao());
            pst.setFloat(3, pizza.getPreco());
            pst.setString(4, pizza.getTamanho());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idPizza = generatedKeys.getInt(1);
                }
                generatedKeys.close();
            }
            return idPizza;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pizza: " + e.getMessage());
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println("Erro ao fechar conexão: "+ e.getMessage());
            }
        }

        return idPizza;
    }

    public boolean vincularIngrediente(int idPizza, int idIngrediente) {
        connectToDb();
        String sql = "INSERT INTO pizza_ingrediente (idPizza, idIngrediente) VALUES (?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idPizza);
            pst.setInt(2, idIngrediente);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao vincular: " + e.getMessage());
            return false;
        } finally {
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public List<Pizza> selectPizza(){
        List<Pizza> pizzas = new ArrayList<>();
        connectToDb();
        String sql = "SELECT * FROM pizza";

        try{
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                Pizza pizza = new Pizza(
                        rs.getInt("idPizza"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getFloat("preco"),
                        rs.getString("tamanho")

                );
                pizzas.add(pizza);
            }
        } catch (SQLException e){
            System.out.println("Erro ao buscar Pizza: " + e.getMessage());
        } finally {
            try{
                if(rs != null) {
                    rs.close();
                }
                if(st != null){
                    st.close();
                }
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e){
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return pizzas;
    }

    public void updatePizza(Pizza pizza, int id){
        connectToDb();
        String sql = "UPDATE pizza SET nome = ?, descricao=?, preco = ?, tamanho = ? WHERE idPizza = ?";

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setString(1, pizza.getNome());
            this.pst.setString(2, pizza.getDescricao());
            this.pst.setFloat(3, pizza.getPreco());
            this.pst.setString(4, pizza.getTamanho());
            this.pst.setInt(5, id);
            this.pst.execute();
        } catch (SQLException exc) {
            System.out.println("Erro ao atualizar pizza: " + exc.getMessage());
        } finally {
            try {
                this.connection.close();
                this.pst.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }
    }

    public boolean deletePizza(int id){
        connectToDb();
        String sql = "DELETE FROM pizza WHERE idPizza = ?";

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setInt(1, id);
            this.pst.execute();
            return true;
        } catch (SQLException exc) {
            System.out.println("Erro ao deletar pizza: " + exc.getMessage());
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

    public Pizza buscarPizzaPorNome(String nome) {
        connectToDb();
        String sql = "SELECT * FROM pizza WHERE nome = ?";
        Pizza pizza = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            if (rs.next()) {
                pizza = new Pizza(
                        rs.getInt("idPizza"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getFloat("preco"),
                        rs.getString("tamanho")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pizza por nome: " + e.getMessage());
        } finally {
            try {
                if(rs!=null) rs.close();
                if(pst!=null) pst.close();
                if(connection!=null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar: " + e.getMessage());
            }
        }
        return pizza;
    }

    public Pizza buscarPizzaPorId(int idPizza) {
        connectToDb();
        String sql = "SELECT * FROM pizza WHERE idPizza = ?";
        Pizza pizza = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idPizza);
            rs = pst.executeQuery();

            if (rs.next()) {
                pizza = new Pizza(
                        rs.getInt("idPizza"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getFloat("preco"),
                        rs.getString("tamanho")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Pizza pelo ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
        return pizza;
    }

    public void listarIngredientesDaPizza(int idPizza) {
        connectToDb();
        String sql = "SELECT i.nome FROM ingrediente i JOIN pizza_ingrediente pi ON i.idIngrediente = pi.idIngrediente WHERE pi.idPizza = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idPizza);
            rs = pst.executeQuery();
            System.out.println("Ingredientes da pizza:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("nome"));
            }
        } catch (SQLException e){
            System.out.println("Erro ao buscar Pizza: " + e.getMessage());
        } finally {
            try{
                if(rs != null) {
                    rs.close();
                }
                if(st != null){
                    st.close();
                }
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e){
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

}
