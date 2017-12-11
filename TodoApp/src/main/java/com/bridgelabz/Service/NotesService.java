package com.bridgelabz.Service;

import java.util.List;
import java.util.Set;

import com.bridgelabz.Model.Collaborator;
import com.bridgelabz.Model.Notes;
import com.bridgelabz.Model.UserDetails;

public interface NotesService {
	public int saveNotes(Notes note);

	public Notes getNoteById(int noteId);

	public Set<Notes> getNotes(int id);
	
/*	public Set<Notes> getNotesTrash(int id,int inTrash);
*/

	public List<Notes> getNotesInTrash();

	public boolean deleteNote(int deleteNodeId);

	public int updateNotes(Notes note);
	
	
	public List<UserDetails> getListOfUser(int noteId);
	
	public Set<Notes> getCollboratedNotes(int userId);
	
	public int removeCollborator(int shareWith,int noteId);

	public int saveCollborator(Collaborator collaborate);

}
