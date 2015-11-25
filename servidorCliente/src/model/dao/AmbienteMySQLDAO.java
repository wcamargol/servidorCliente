package model.dao;

import java.util.List;
import model.beans.AmbienteBean;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AmbienteMySQLDAO {
     private Session session;
    
    public AmbienteBean getAmbienteBean(String codigo){
        AmbienteBean ambiente = null;
        if(codigo != null){
            session = FabricaSessoes.getSession();
            try{                
                Query consulta = session.createQuery("select a from AmbienteBean a where "
                    + "a.codigoAmbiente = '"+codigo+"'");
                List l = consulta.list();
                if (!l.isEmpty()){
                   ambiente = (AmbienteBean)l.get(0);
                }
            }catch (HibernateException ex){
                ex.printStackTrace();
            }finally{
                session.close();
            }
        }
        return ambiente;
    }
    
    public List listAmbienteBean(){
        session = FabricaSessoes.getSession();
        List listaAmbientesBean = null;
        Query consulta = null;
        try{            
            consulta = session.createQuery("from AmbienteBean");
            listaAmbientesBean = consulta.list();
        }catch (HibernateException ex){
                ex.printStackTrace();            
        }finally{
            session.close();
        }
        return listaAmbientesBean;
    }
    
    public boolean updateAmbienteBean(AmbienteBean ambiente){
        boolean sucesso = false;
        if(ambiente != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.update(ambiente);
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
    public boolean saveAmbienteBean(AmbienteBean ambiente){
        boolean sucesso = false;
        if(ambiente != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(ambiente);
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
    public boolean deleteAmbienteBean(AmbienteBean ambiente){
        boolean sucesso = false;
        if(ambiente != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.delete(ambiente);
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
