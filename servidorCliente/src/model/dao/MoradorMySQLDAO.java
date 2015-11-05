package model.dao;

import java.util.List;
import model.beans.MoradorBean;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MoradorMySQLDAO{
    private Session session;
    
    public MoradorBean getMoradorBean(String login){
        MoradorBean morador = null;
        if (login != null){
            session = FabricaSessoes.getSession();
            try{
                Query consulta = session.createQuery("select m from MoradorBean "
                    + "m where m.login = '"+login+"'");
                List l = consulta.list();
                if (!l.isEmpty()){
                   morador = (MoradorBean)l.get(0);
                }
            }catch (HibernateException ex){
                ex.printStackTrace();
            }finally{
                session.close();
            }
        }
        return morador;
    }
    
    public List listMoradorBean(){
        session = FabricaSessoes.getSession();
        List listaMoradoresBean = null;
        Query consulta = null;
        try{            
            consulta = session.createQuery("from MoradorBean");
            listaMoradoresBean = consulta.list();
        }catch (HibernateException ex){
                ex.printStackTrace();            
        }finally{
            session.close();
        }
        return listaMoradoresBean;
    }
    
    public boolean updateMoradorBean(MoradorBean morador){
        boolean sucesso = false;
        if(morador != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.update(morador);
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
    public boolean saveMoradorBean(MoradorBean morador){
        boolean sucesso = false;
        if(morador != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(morador);
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
    public boolean deleteMoradorBean(MoradorBean morador){
        boolean sucesso = false;
        if(morador != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.delete(morador);
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