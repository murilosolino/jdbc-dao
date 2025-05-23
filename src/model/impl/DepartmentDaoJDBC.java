package model.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("INSERT INTO department (Name) " +
                    "VALUES (?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0){
                rs = st.getGeneratedKeys();
                if (rs.next()){
                    int Id = rs.getInt(1);
                    obj.setId(Id);
                    System.out.println("Registro inserido com sucesso. Linhas afetadas: " + rowsAffected);
                }

            } else {
                throw new DbException("Falha ao inserir registro: Nenhum registro inserido");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");

            st.setString(1,obj.getName());
            st.setInt(2, obj.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0){
                throw new DbException("ERRO ao atualizar registro: NENHUM REGISTRO ATUALIZADO");
            }else {
                System.out.println("Registro atualizado com sucesso. Linhas afetadas: " + rowsAffected);
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0){
                throw new DbException("ERRO ao atualizar registro: NENHUM REGISTRO ATUALIZADO");
            }else {
                System.out.println("Registro excluido com sucesso. Linhas afetadas: " + rowsAffected);
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
            st.setInt(1,id);

            rs = st.executeQuery();

            if (rs.next()){
                Department dep = new Department(rs.getInt("Id"), rs.getString("Name"));
                return dep;
            } else {
                throw new DbException("NENHUM REGISTRO ENCONTRADO PARA O ID: " + id);
            }
        }catch (SQLException  e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM department");
            rs = st.executeQuery();
            List<Department> list = new ArrayList<>();

            while (rs.next()){
                Department dep = new Department(rs.getInt("Id"), rs.getString("Name"));
                list.add(dep);
            }

            if (list.isEmpty()){
                throw new DbException("NENHUM REGISTRO ENCONTRADO");
            }

            return list;

        }catch (SQLException  e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}

