package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import dto.OrderDTO;
import entity.Orders;

public interface OrderDAO extends CrudDAO<Orders, String> , SuperDAO {

}
