package bo.custom.impl;

import bo.custom.PurchaseOrderBO;
import dao.DAOFactory;
import dao.SQLUtil;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import db.DBConnection;
import dto.*;
import entity.Customer;
import entity.Item;
import entity.OrderDetails;
import entity.Orders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        Customer customer = customerDAO.search(id);
        return new CustomerDTO(customer.getId() , customer.getName() , customer.getAddress() );
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(code);
        return new ItemDTO(item.getCode() , item.getDescription() , item.getUnitPrice() , item.getQtyOnHand() );
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
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer customer : customerDAO.loadAll()) {
            customerDTOList.add(new CustomerDTO(customer.getId() , customer.getName() , customer.getAddress() ));
        }

        return customerDTOList;
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item item: itemDAO.loadAll() ) {
            itemDTOList.add(new ItemDTO(item.getCode() , item.getDescription() , item.getUnitPrice() , item.getQtyOnHand()));
        }

        return itemDTOList;
    }

    @Override
    public boolean purchaseOrder(PlaceOrderDTO placeOrderDTO) {

        try {
            boolean isOrderIdExist = orderDAO.exist(placeOrderDTO.getOrderId());
            if (isOrderIdExist){
                return false;
            }


            connection.setAutoCommit(false);

            boolean isOrderAded = orderDAO.add(new Orders(placeOrderDTO.getOrderId(), placeOrderDTO.getOrderDate(), placeOrderDTO.getCustomerId() ));

            if (!isOrderAded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO detail : placeOrderDTO.getOrderDetails()) {
                boolean isOrderDetailsPlaced = orderDetailsDAO.add(new OrderDetails(detail.getOrderId() , detail.getItemCode() , detail.getQty() , detail.getUnitPrice() ));

                if (!isOrderDetailsPlaced) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

//                //Search & Update Item

                Item item = itemDAO.search(detail.getItemCode());
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
