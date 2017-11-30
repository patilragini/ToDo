package com.bridgelabz.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Dao.NoteDao;
import com.bridgelabz.Model.Collaborator;
import com.bridgelabz.Model.Notes;
import com.bridgelabz.Model.UserDetails;

/**
 * 
 * @author Ragini
 *         
 */
public class NotesSeviceImpl implements NotesService{

	@Autowired
	NoteDao noteDao;

	public int saveNotes(Notes note) {
		// TODO Auto-generated method stub
		int id=noteDao.saveNotes(note);
		return id;
	}
	public Set<Notes> getNotes(int id) {
		// TODO Auto-generated method stub
		return noteDao.getNotes(id);
	}

	public Notes getNoteById(int noteId) {
		// TODO Auto-generated method stub

		return noteDao.getNoteById(noteId);
		
	}
	@Override
	public boolean deleteNote(int deleteNodeId) {
		// TODO Auto-generated method stub
		return noteDao.deleteNote(deleteNodeId);
	}
	@Override
	public int updateNotes(Notes note) {
		// TODO Auto-generated method stub
		int id=noteDao.updateNotes(note);
		return id;
		
	}
/*	@Override
	public Set<Notes> getNotesTrash(int id, boolean inTrash) {
		// TODO Auto-generated method stub
		return noteDao.getNotesTrash(id,inTrash);
	}*/
	public int saveCollborator(Collaborator collborate) {
		// TODO Auto-generated method stub
		return noteDao.saveCollborator(collborate);
	}

	public List<UserDetails> getListOfUser(int noteId) {
		return noteDao.getListOfUser(noteId);
	}

	public Set<Notes> getCollboratedNotes(int userId) {
		// TODO Auto-generated method stub
		return noteDao.getCollboratedNotes(userId);	
	}

	public int removeCollborator(int shareWith,int noteId) {
		// TODO Auto-generated method stub
		return noteDao.removeCollborator(shareWith, noteId);
	}
	
	

}
