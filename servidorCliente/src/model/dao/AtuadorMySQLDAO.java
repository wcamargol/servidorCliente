package model.dao;

import java.util.List;
import model.beans.AtuadorBean;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AtuadorMySQLDAO{
    private Session session;
    
    public AtuadorBean getAtuadorBean(String codigoAtuador){
        
        AtuadorBean atuador = null;
        if(codigoAtuador != null){
            session = FabricaSessoes.getSession();
            try{
                Query consulta = session.createQuery("select a from AtuadorBean a where "
                    + "a.codigoAtuador = '" + codigoAtuador + "'");
                List l = consulta.list();
                if (!l.isEmpty()){
                   atuador = (AtuadorBean)l.get(0);
                }
            }catch (HibernateException ex){
                ex.printStackTrace();
            }
        }
        return atuador;
    }
    
    public List listAtuadorBean(){
        session = FabricaSessoes.getSession();
        List listaAtuadoresBean = null;
        Query consulta = null;
        try{            
            consulta = session.createQuery("from AtuadorBean");
            listaAtuadoresBean = consulta.list();
        }catch (HibernateException ex){
                ex.printStackTrace();            
        }finally{
            session.close();
        }
        return listaAtuadoresBean;
    }
    
    public boolean updateAtuadorBean(AtuadorBean atuador){
        boolean sucesso = false;
        if(atuador != null){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.update(atuador);
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
    public boolean saveAtuadorBean(AtuadorBean atuador){
        boolean sucesso = false;
        if(atuador != null){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(atuador);
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
    public boolean deleteAtuadorBean(AtuadorBean atuador){
        boolean sucesso = false;
        if(atuador != null){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.delete(atuador);
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