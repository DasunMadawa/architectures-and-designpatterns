package bo.custom.impl;

import bo.custom.PurchaseOrderBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import dao.custom.impl.CustomerDAOimpl;
import dao.custom.impl.ItemDAOimpl;
import dao.custom.impl.OrderDAOimpl;
import dao.custom.impl.OrderDetailsDAOimpl;
import db.DBConnection;
import model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    Connection connection = DBConnection.getDbConnection().getConnection();
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);


    public PurchaseOrderBOImpl() throws SQLException, ClassNotFoundException {

    }



    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.search(id);
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.search(code);
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        return customerDAO.loadAll();
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        return itemDAO.loadAll();
    }

    @Override
    public boolean purchaseOrder(PlaceOrderDTO placeOrderDTO) {

        try {
            boolean isOrderIdExist = orderDAO.exist(placeOrderDTO.getOrderId());
            if (isOrderIdExist){
                return false;
            }


            connection.setAutoCommit(false);

            boolean isOrderAded = orderDAO.add(new OrderDTO(placeOrderDTO.getOrderId(), placeOrderDTO.getOrderDate(), placeOrderDTO.getCustomerId() , null , null));

            if (!isOrderAded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO detail : placeOrderDTO.getOrderDetails()) {
                boolean isOrderDetailsPlaced = orderDetailsDAO.add(detail);

                if (!isOrderDetailsPlaced) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

//                //Search & Update Item

                ItemDTO item = itemDAO.search(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

                boolean isItemUpdated = itemDAO.update(item);

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
    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException {
        return searchItem(code);
    }
}
