package com.example.agentievanzareiss.repository;

import com.example.agentievanzareiss.model.Produs;
import com.example.agentievanzareiss.utils.JdbcUtils;

import java.sql.*;
import java.util.*;

public class ProdusDBRepository implements ProdusRepository {
    private JdbcUtils dbUtils;

    public ProdusDBRepository(Properties props) {

        this.dbUtils = new JdbcUtils(props);

    }

    @Override
    public int add(Produs elem) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into produse(denumire, pret, stoc)  values (?,?,?)")){
            preparedStatement.setString(1, elem.getDenumire());
            preparedStatement.setFloat(2, elem.getPret());
            preparedStatement.setInt(3, elem.getStoc());
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
    public void delete(Produs elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM produse WHERE id = ?")) {
            statement.setInt(1, elem.getID());
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void update(Produs elem) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("update produse set pret = ?, stoc = ? where id = ?")){
            preparedStatement.setFloat(1, elem.getPret());
            preparedStatement.setInt(2, elem.getStoc());
            preparedStatement.setInt(3,elem.getID());
            preparedStatement.executeUpdate();

        }
        catch (SQLException ex){

            System.err.println("Error DB" + ex);
        }


    }

    @Override
    public Produs findById(Integer id) {
        Connection con = dbUtils.getConnection();
        Produs produs = null;
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from produse where id = ?")){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int idP = resultSet.getInt("id");
                    String denumire = resultSet.getString("denumire");
                    float pret = resultSet.getFloat("pret");
                    int stoc = resultSet.getInt("stoc");
                    produs = new Produs(denumire, pret, stoc);
                    produs.setID(idP);
                }
            }

        }
        catch (SQLException e){
            System.err.println("Error DB"+e);
        }

        return produs;
    }

    @Override
    public Iterable<Produs> findAll() {
        Connection con = dbUtils.getConnection();
        List<Produs> produse = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from produse")){
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String denumire = resultSet.getString("denumire");
                    float pret = resultSet.getFloat("pret");
                    int stoc = resultSet.getInt("stoc");
                    Produs produs = new Produs(denumire, pret, stoc);
                    produs.setID(id);
                    produse.add(produs);
                }
            }

        }
        catch (SQLException e){
            System.err.println("Error DB"+e);
        }

        return produse;
    }


    @Override
    public Produs findByDenumire(String denumire) {
        Connection con = dbUtils.getConnection();
        Produs produs = null;
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from produse where denumire = ?")){
            preparedStatement.setString(1, denumire);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String denumireP = resultSet.getString("denumire");
                    float pret = resultSet.getFloat("pret");
                    int stoc = resultSet.getInt("stoc");
                    produs = new Produs(denumireP, pret, stoc);
                    produs.setID(id);

                }
            }

        }
        catch (SQLException e){
            System.err.println("Error DB"+e);
        }

        return produs;
    }
}
