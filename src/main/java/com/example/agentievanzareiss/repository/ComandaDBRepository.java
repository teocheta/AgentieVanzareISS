package com.example.agentievanzareiss.repository;
import com.example.agentievanzareiss.model.Comanda;
import com.example.agentievanzareiss.model.Produs;
import com.example.agentievanzareiss.model.Utilizator;
import com.example.agentievanzareiss.utils.JdbcUtils;

import java.sql.*;
import java.util.Properties;


public class ComandaDBRepository implements ComandaRepository {

    private JdbcUtils dbUtils;

    public ComandaDBRepository(Properties props) {

        this.dbUtils = new JdbcUtils(props);

    }

    @Override
    public int add(Comanda elem) {

        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into comenzi(id_agent, detalii_livrare)  values (?,?)")){
            preparedStatement.setInt(1, elem.getAgent().getID());
            preparedStatement.setString(2, elem.getDetaliiLivrare());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                try (Statement stmt = con.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        elem.setID(id);
                        return id;
                    }
                }
            }

        }
        catch (SQLException ex){
            System.err.println("Error DB" + ex);
        }
        return 0;

    }

    @Override
    public void delete(Comanda elem) {

    }

    @Override
    public void update(Comanda elem) {

    }

    @Override
    public Comanda findById(Integer id) {
        Connection con = dbUtils.getConnection();
        Comanda comanda = null;
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from comenzi where id = ?")){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int idC = resultSet.getInt("id");
                    int idAgent = resultSet.getInt("id_agent");
                    Utilizator agent = null;
                    try(PreparedStatement preparedStatement1 = con.prepareStatement("select * from utilizatori where id = ?")){
                        preparedStatement1.setInt(1, idAgent);
                        try(ResultSet resultSet1 = preparedStatement1.executeQuery()) {

                            while (resultSet1.next()) {
                                int idA = resultSet1.getInt("id");
                                String username = resultSet1.getString("username");
                                String password = resultSet1.getString("password");
                                agent = new Utilizator(username, password);
                                agent.setID(idA);
                            }
                        }

                    }
                    catch (SQLException e){
                        System.err.println("Error DB"+e);
                    }
                    String detaliiLivrare = resultSet.getString("detalii_livrare");
                    comanda = new Comanda(agent, detaliiLivrare);
                    comanda.setID(idC);
                }
            }

        }
        catch (SQLException e){
            System.err.println("Error DB"+e);
        }

        return comanda;
    }

    @Override
    public Iterable<Comanda> findAll() {
        return null;
    }
}
