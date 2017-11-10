package com.bridgelabz.Dao;

import java.util.Set;

import com.bridgelabz.Model.Notes;

/**
 * 
 * @author Ragini interface
 *       
 */
public interface NoteDao {
	public int saveNotes(Notes note);
	public Set<Notes> getNotes(int id);
	public Notes getNoteById(int id);
	public boolean deleteNote(int deleteNodeId);
	public int updateNotes(Notes note);

	
}
