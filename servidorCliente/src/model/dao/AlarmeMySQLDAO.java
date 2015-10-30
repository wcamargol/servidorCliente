package model.dao;

import java.util.List;
import model.beans.AlarmeBean;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AlarmeMySQLDAO{
    private Session session;
    
    public AlarmeBean getAlarmeBean(String codigo){        
        AlarmeBean alarme = null;
        if (codigo != null){
            session = FabricaSessoes.getSession();
            try{
            Query consulta = session.createQuery("select a from AlarmeBean a where "
                + "a.codigoAlarme = '"+codigo+"'");
            List l = consulta.list();
            if (!l.isEmpty()){
               alarme = (AlarmeBean)l.get(0);
            }
            }catch (HibernateException ex){
                ex.printStackTrace();
            }
        }
        return alarme;
    }
    
    public List listAlarmeBean(){
        session = FabricaSessoes.getSession();
        List listaAlarmesBean = null;
        Query consulta = null;
        try{            
            consulta = session.createQuery("from AlarmeBean");
            listaAlarmesBean = consulta.list();
        }catch (HibernateException ex){
                ex.printStackTrace();            
        }finally{
            session.close();
        }
        return listaAlarmesBean;
    }
    
    public boolean updateAlarmeBean(AlarmeBean alarme){
        boolean sucesso = false;
        if(alarme != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.update(alarme);
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
    public boolean saveAlarmeBean(AlarmeBean alarme){
        boolean sucesso = false;
        if(alarme != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(alarme);
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
    public boolean deleteAlarmeBean(AlarmeBean alarme){
        boolean sucesso = false;
        if(alarme != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.delete(alarme);
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