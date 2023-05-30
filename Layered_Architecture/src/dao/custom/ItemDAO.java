package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import model.ItemDTO;

public interface ItemDAO extends CrudDAO<ItemDTO , String>  , SuperDAO {

}
