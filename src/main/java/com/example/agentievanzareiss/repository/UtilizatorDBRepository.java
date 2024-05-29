package com.example.agentievanzareiss.repository;

import com.example.agentievanzareiss.model.Utilizator;
import com.example.agentievanzareiss.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UtilizatorDBRepository implements UtilizatorRepository{

    private JdbcUtils dbUtils;

    public UtilizatorDBRepository(Properties props){

        this.dbUtils = new JdbcUtils(props);
    }
    @Override
    public int add(Utilizator elem) {
        return 0;

    }

    @Override
    public void delete(Utilizator elem) {

    }

    @Override
    public void update(Utilizator elem) {

    }

    @Override
    public Utilizator findById(Integer id) {
        return null;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        return null;
    }

    @Override
    public Utilizator findByUsername(String username) {

        Connection con = dbUtils.getConnection();
        Utilizator utilizator = null;
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from utilizatori where username=?")){
            preparedStatement.setString(1,username);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username1 = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    utilizator = new Utilizator(username1,password);
                    utilizator.setID(id);
                }
            }

        }
        catch (SQLException e){
            System.err.println("Error DB"+e);
        }

        return utilizator;
    }
}
