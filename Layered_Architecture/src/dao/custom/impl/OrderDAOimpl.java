package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.OrderDAO;
import db.DBConnection;
import javafx.scene.control.Alert;
import model.OrderDTO;

import java.sql.*;
import java.util.List;

public class OrderDAOimpl implements OrderDAO {

    @Override
    public List<OrderDTO> loadAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)",
                orderDTO.getOrderId(),
                orderDTO.getOrderDate(),
                orderDTO.getCustomerId()
        );
    }

    @Override
    public boolean exist(String oId) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.execute("SELECT oid FROM `Orders` WHERE oid=?", oId);
        return rs.next();
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1");

        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";

    }

    @Override
    public OrderDTO search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}
