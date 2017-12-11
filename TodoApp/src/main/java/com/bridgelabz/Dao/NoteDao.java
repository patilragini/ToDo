package com.bridgelabz.Dao;

import java.util.List;
import java.util.Set;

import com.bridgelabz.Model.Collaborator;
import com.bridgelabz.Model.Notes;
import com.bridgelabz.Model.UserDetails;

/**
 * 
 * @author Ragini interface
 *       
 */
public interface NoteDao {
	public int saveNotes(Notes note);
	
	public Set<Notes> getNotes(int id);
	
/*	public Set<Notes> getNotesTrash(int id,boolean inTrash);
*/
	public List<Notes> getNotesInTrash();

	public Notes getNoteById(int id);
	
	public boolean deleteNote(int deleteNodeId);
	
	public int updateNotes(Notes note);
	 
	public int saveCollborator(Collaborator collborate);
	
	
	public Set<Notes> getCollboratedNotes(int userId);
	
	public int removeCollborator(int shareWith,int noteId );
	
	public List<UserDetails> getListOfUser(int noteId);

	
}
