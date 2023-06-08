package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import dto.OrderDetailDTO;
import entity.OrderDetails;

public interface OrderDetailsDAO extends CrudDAO<OrderDetails, String> , SuperDAO {

}
