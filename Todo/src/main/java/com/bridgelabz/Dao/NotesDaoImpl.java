package com.bridgelabz.Dao;

import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Model.Notes;
import com.bridgelabz.Model.UserDetails;

public class NotesDaoImpl implements NoteDao {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	UserDao userDao;

	@Override
	public int saveNotes(Notes note) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		int id = (Integer) session.save(note);
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
	public Set<Notes> getNotes(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		UserDetails user = session.get(UserDetails.class, id);
		if (user != null) {
			Set<Notes> notes = user.getNotes();
			notes.size();
			session.close();
			return notes;
		}
		return null;

	}

	@Override
	public Notes getNoteById(int noteId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Notes note = session.get(Notes.class, noteId);
		return note;
	}

	@Override
	public boolean deleteNote(int deleteNodeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		if (deleteNodeId > 0) {
			session.delete(getNoteById(deleteNodeId));
			try {
				transaction.commit();

			} catch (HibernateException e) {
				e.printStackTrace();
				transaction.rollback();
			} finally {
				session.close();
			}
		}

		return false;
	}

	@Override
	public int updateNotes(Notes note) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		//session.update(note);
		session.saveOrUpdate(note);
		try {			
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			transaction.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

}
