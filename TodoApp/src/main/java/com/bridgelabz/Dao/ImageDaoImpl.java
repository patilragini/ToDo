package com.bridgelabz.Dao;

import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Model.Images;
import com.bridgelabz.Service.NotesService;

public class ImageDaoImpl implements ImageDao{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public int addImage(Images image) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		int id = (Integer) session.save(image);
		try {
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		return id;

	}
	
	
	
	

	@Override
	public boolean deleteImages(Images image) {
		
		
		// TODO Auto-generated method stub
		
		
		
		return false;
	}

	@Override
	public Set<Images> getAllImages(int noteid) {
		
		
		// TODO Auto-generated method stub
		
		
		return null;
	}

}
