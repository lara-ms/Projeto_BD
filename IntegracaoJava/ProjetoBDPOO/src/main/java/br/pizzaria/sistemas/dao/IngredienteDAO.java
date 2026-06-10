package br.pizzaria.sistemas.dao;

import br.pizzaria.sistemas.model.pizzaria.Ingrediente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredienteDAO extends ConnectionDAO{
        public boolean inserirIngrediente(Ingrediente ingrediente){
            connectToDb(); // Abre conexao
            String sql = "INSERT INTO ingrediente(nome, quantidade_estoque, unidade) VALUES (?, ?, ?)";

            try {
                pst = connection.prepareStatement(sql);
                pst.setString(1, ingrediente.getNome());
                pst.setInt(2, ingrediente.getQuantidade_estoque());
                pst.setString(3, ingrediente.getUnidade());
                pst.execute();
                return true;
            } catch (SQLException e) {
                System.out.println("Erro ao inserir cliente: " + e.getMessage());
            }finally {
                try{
                    connection.close();
                }catch (SQLException e){
                    System.out.println("Erro ao fechar conexão: "+ e.getMessage());
                }
            }

            return false;
        }

        public List<Ingrediente> selectIngrediente(){
            List<Ingrediente> ingredientes = new ArrayList<>();
            connectToDb();
            String sql = "SELECT * FROM ingrediente";

            try{
                st = connection.createStatement();
                rs = st.executeQuery(sql);
                while(rs.next()){
                    Ingrediente ingrediente = new Ingrediente(
                            rs.getInt("idIngrediente"),
                            rs.getString("nome"),
                            rs.getInt("quantidade_estoque"),
                            rs.getString("unidade")

                    );
                    ingredientes.add(ingrediente);
                }
            } catch (SQLException e){
                System.out.println("Erro ao buscar Ingredientes: " + e.getMessage());
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

            return ingredientes;
        }

        public boolean updateIngrediente(Ingrediente ingrediente){
            connectToDb();
            String sql = "UPDATE ingrediente SET quantidade_estoque=? WHERE idIngrediente=?";

            try{
                pst = connection.prepareStatement(sql);
                pst.setInt(1, ingrediente.getQuantidade_estoque());
                pst.setInt(2, ingrediente.getIdIngrediente());
                pst.execute();
                return true;
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar Ingrediente: " + e.getMessage());
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

        public boolean deleteIngrediente(int id){
            connectToDb();
            String sql = "DELETE FROM ingrediente WHERE idIngrediente = ?";

            try {
                this.pst = this.connection.prepareStatement(sql);
                this.pst.setInt(1, id);
                this.pst.execute();
                return true;
            } catch (SQLException exc) {
                System.out.println("Erro ao deletar de ingrediente: " + exc.getMessage());
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

}
