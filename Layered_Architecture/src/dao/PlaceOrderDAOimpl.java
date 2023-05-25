package dao;

import db.DBConnection;
import model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderDAOimpl implements CrudDAO<PlaceOrderDTO , String> {
    Connection connection = DBConnection.getDbConnection().getConnection();

    CrudDAO<CustomerDTO , String> crudDAOCustomer = new CustomerDAOimpl();
    CrudDAO<ItemDTO , String> getCrudDAOItem = new ItemDAOimpl();
    CrudDAO<OrderDetailDTO , String> crudDAOOrderDetail = new OrderDetailsDAOimpl();
    CrudDAO<OrderDTO , String> orderDAOOrder = new OrderDAOimpl();

    public PlaceOrderDAOimpl() throws SQLException, ClassNotFoundException {

    }

    @Override
    public List<PlaceOrderDTO> loadAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(PlaceOrderDTO placeOrderDTO) {
        try {
            boolean isOrderIdExist = orderDAOOrder.exist(placeOrderDTO.getOrderId());
            if (isOrderIdExist){
                return false;
            }


            connection.setAutoCommit(false);

            boolean isOrderAded = orderDAOOrder.add(new OrderDTO(placeOrderDTO.getOrderId(), placeOrderDTO.getOrderDate(), placeOrderDTO.getCustomerId() , null , null));

            if (!isOrderAded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO detail : placeOrderDTO.getOrderDetails()) {
                boolean isOrderDetailsPlaced = crudDAOOrderDetail.add(detail);

                if (!isOrderDetailsPlaced) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

//                //Search & Update Item

                ItemDTO item = getCrudDAOItem.search(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

                boolean isItemUpdated = getCrudDAOItem.update(item);

                if (!isItemUpdated) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;


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
    public boolean update(PlaceOrderDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public PlaceOrderDTO search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

}
