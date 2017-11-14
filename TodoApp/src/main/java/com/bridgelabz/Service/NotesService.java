package com.bridgelabz.Service;

import java.util.Set;

import com.bridgelabz.Model.Notes;

public interface NotesService {
	public int saveNotes(Notes note);

	public Notes getNoteById(int noteId);

	public Set<Notes> getNotes(int id);

	public boolean deleteNote(int deleteNodeId);

	public int updateNotes(Notes note);

}
