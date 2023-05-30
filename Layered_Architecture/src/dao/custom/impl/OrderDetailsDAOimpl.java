package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.OrderDetailsDAO;
import model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDAOimpl implements OrderDetailsDAO {

    @Override
    public List<OrderDetailDTO> loadAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)",
                orderDetailDTO.getOrderId() ,
                orderDetailDTO.getItemCode() ,
                orderDetailDTO.getUnitPrice() ,
                orderDetailDTO.getQty()
        );

    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetailDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDetailDTO search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}
