package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.Usuario;
import JPAUtil.JPAUtil;


public class UsuarioDao {
	public static void save(Usuario user) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void update(Usuario user) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		Usuario userToUpdate = em.find(Usuario.class, user.getId());
		
		userToUpdate.setNome(user.getNome());
		userToUpdate.setDataNascimento(user.getDataNascimento());
		userToUpdate.setSexo(user.getSexo());
		
		em.merge(userToUpdate);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void delete(Usuario user) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		user = em.find(Usuario.class, user.getId());
		em.remove(user);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<Usuario> listAll(){
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select user from Usuario user");
		List<Usuario> users = q.getResultList();
		em.close();
		
		return users;
	}
	
	
	public static Long countedUsers() {
		
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select count(user) from Usuario user");
		Long countedUsers = (Long) q.getSingleResult();
		em.close();
		
		return countedUsers;
	}
	
	public static boolean validate(String nome, Date dataNascimento, Integer id) {
	    EntityManager em = JPAUtil.criarEntityManager();
	 
	    String sqlQuery = "SELECT COUNT(u) FROM Usuario u WHERE u.nome = :nome AND u.dataNascimento = :dataNascimento";
	    
	    if (id != null) {
	        sqlQuery += " AND u.id = :id"; 
	    }
	   
	    Query query = em.createQuery(sqlQuery);
	    query.setParameter("nome", nome);
	    query.setParameter("dataNascimento", dataNascimento);
	    
	    if (id != null) {
	        query.setParameter("id", id);
	    }
	    
	    Long count = (Long) query.getSingleResult();
	    em.close();
	    
	    return count > 0;
	}
}
