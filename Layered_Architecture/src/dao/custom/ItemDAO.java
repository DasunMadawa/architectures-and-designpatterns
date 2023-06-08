package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import dto.ItemDTO;
import entity.Item;

public interface ItemDAO extends CrudDAO<Item, String>  , SuperDAO {

}
