package com.example.agentievanzareiss.repository;

import com.example.agentievanzareiss.model.ProdusComanda;
import com.example.agentievanzareiss.utils.JdbcUtils;

import java.sql.*;
import java.util.Properties;

public class ProdusComandaDBRepository implements ProdusComandaRepository{

    private JdbcUtils dbUtils;

    public ProdusComandaDBRepository(Properties props) {

        this.dbUtils = new JdbcUtils(props);

    }

    @Override
    public int add(ProdusComanda elem) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into produse_comenzi(id_produs,id_comanda, cantitate)  values (?,?,?)")){
            preparedStatement.setInt(1, elem.getProdus().getID());
            preparedStatement.setInt(2, elem.getComanda().getID());
            preparedStatement.setInt(3, elem.getCantitate());
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
    public void delete(ProdusComanda elem) {

    }

    @Override
    public void update(ProdusComanda elem) {

    }

    @Override
    public ProdusComanda findById(Integer id) {
        return null;
    }

    @Override
    public Iterable<ProdusComanda> findAll() {
        return null;
    }
}
