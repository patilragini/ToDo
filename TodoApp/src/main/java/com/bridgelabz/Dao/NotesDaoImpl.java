package com.bridgelabz.Dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Model.Collaborator;
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
		UserDetails UserDetails = session.get(UserDetails.class, id);
		if (UserDetails != null) {
			Set<Notes> notes = UserDetails.getNotes();
			notes.size();
			session.close();
			return notes;
		}
		return null;

	}

	/*@Override
	public Set<Notes> getNotesTrash(int tokenId, boolean inTrash) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		UserDetails UserDetails = session.get(UserDetails.class, tokenId);
		System.out.println("here::" + UserDetails);
		if (UserDetails != null) {
			Set<Notes> trashNotes1 = (Set<Notes>) session.get(Notes.class, UserDetails.getId());
			System.out.println("here::" + trashNotes1);
			System.out.println("here::" + trashNotes1.size());
			TypedQuery<Notes> query = session.createQuery("from Notes note where user_id=:id AND inTrash=:inTrash");
			query.setParameter("user_id", tokenId);
			query.setParameter("inTrash", inTrash);
			Set<Notes> list = (Set<Notes>) query.getResultList();

			if (list != null) {
				System.out.println("here::" + list);
				System.out.println("here::" + list.size());
				return list;
			}
			return null;

		}
		return null;
	}*/
	public List<Notes> getNotesInTrash() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("From Notes where inTrash=true");
		
		List<Notes> notes = query.list();		
		System.out.println(notes);
		session.close();
		return notes;
	}
	@Override
	public Notes getNoteById(int noteId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Notes note = session.get(Notes.class, noteId);
		session.close();
		return note;
	}

	
	@Override
	public boolean deleteNote(int deleteNodeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		if (deleteNodeId > 0) {
			System.out.println("note del be::");
			System.out.println(":::::"+getNoteById(deleteNodeId));
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
		// session.update(note);
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

	public int saveCollborator(Collaborator collborate) {
		int collboratorId=0;
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		collboratorId=(Integer) session.save(collborate);
		transaction.commit();
		}catch(HibernateException e){
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
		return collboratorId;
	}

	public List<UserDetails> getListOfUser(int noteId) {
		Session session = sessionFactory.openSession();
		Query querycollab = session.createQuery("select c.shareWithId from Collaborator c where c.note= " + noteId);
		List<UserDetails> listOfSharedCollaborators = querycollab.list();
		System.out.println("listOfSharedCollaborators "+listOfSharedCollaborators);
		session.close();
		return listOfSharedCollaborators;	
	}

	public Set<Notes> getCollboratedNotes(int userId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select c.note from Collaborator c where c.shareWithId= " + userId);
		List<Notes> colllboratedNotes = query.list();
		Set<Notes> notes=new HashSet<Notes>(colllboratedNotes);
		
		session.close();
		return notes;
	}

	public int removeCollborator(int shareWith,int noteId) {
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Query query = session.createQuery("delete  Collaborator c where c.shareWithId= "+shareWith+" and c.note="+noteId );
		/*query.setParameter("noteId", noteId);
		query.setParameter("shareWith", shareWith);*/
		int status=query.executeUpdate();
		session.close();
		return status;
	}


}
