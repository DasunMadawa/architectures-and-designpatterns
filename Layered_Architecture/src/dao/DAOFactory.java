package dao;

import dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER , ITEM , ORDER , ORDER_DETAILS , QUERY_DAO
    }

//    public SuperDAO
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes) {
            case CUSTOMER :
                return new CustomerDAOimpl();
            case ITEM :
                return new ItemDAOimpl();
            case ORDER :
                return new OrderDAOimpl();
            case ORDER_DETAILS :
                return new OrderDetailsDAOimpl();
            case QUERY_DAO :
                return new QuaryDAOimpl();
            default :
                return null;

        }

    }

}
