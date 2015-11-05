package model.dao;

import java.util.List;
import model.beans.SensorBean;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SensorMySQLDAO{
    private Session session;
    
    public SensorBean getSensorBean(String codigoSensor){
        
        SensorBean sensor = null;
        if(codigoSensor != null){
            session = FabricaSessoes.getSession();
            try{
                Query consulta = session.createQuery("select a from SensorBean a where "
                    + "a.codigoSensor = '" + codigoSensor + "'");
                List l = consulta.list();
                if (!l.isEmpty()){
                   sensor = (SensorBean)l.get(0);
                }
            }catch (HibernateException ex){
                ex.printStackTrace();
            }finally{
                session.close();
            }
        }
        return sensor;
    }
    
    public List listSensorBean(){
        session = FabricaSessoes.getSession();
        List listaSensoresBean = null;
        Query consulta = null;
        try{            
            consulta = session.createQuery("from SensorBean");
            listaSensoresBean = consulta.list();
        }catch (HibernateException ex){
                ex.printStackTrace();            
        }finally{
            session.close();
        }
        return listaSensoresBean;
    }
    
    public boolean updateSensorBean(SensorBean sensor){
        boolean sucesso = false;
        if(sensor != null){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.update(sensor);
                tx.commit();
                sucesso = true;                
            }catch(HibernateException ex){
                ex.printStackTrace();
                tx.rollback();
            }finally{
                session.close();
            }
        }
        return sucesso;    
    }
    public boolean saveSensorBean(SensorBean sensor){
        boolean sucesso = false;
        if(sensor != null){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(sensor);
                tx.commit();
                sucesso = true;                
            }catch(HibernateException ex){
                ex.printStackTrace();
                tx.rollback();
            }finally{
                session.close();
            }
        }
        return sucesso;            
    }
    public boolean deleteSensorBean(SensorBean sensor){
        boolean sucesso = false;
        if(sensor != null){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.delete(sensor);
                tx.commit();
                sucesso = true;                
            }catch(HibernateException ex){
                ex.printStackTrace();
                tx.rollback();
            }finally{
                session.close();
            }
        }
        return sucesso;
    }    
}