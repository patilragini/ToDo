//tue night
package com.bridgelabz.Controller;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Model.Notes;
import com.bridgelabz.Model.UserDetails;
import com.bridgelabz.Service.NotesService;

@RestController
public class NoteController {
	@Autowired
	NotesService notesService;

	@RequestMapping(value = "/saveNote", method = RequestMethod.POST)
	public ResponseEntity<String> saveNote(HttpSession session, @RequestBody Notes note) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		System.out.println("here:" + user);
		if (user != null) {
			int isActive = user.getActivated();
			if (isActive > 0) {
				note.setUserDetails(user);
				notesService.saveNotes(note);
				return new ResponseEntity<String>("Note Updated " + user, HttpStatus.OK);
			}
			return new ResponseEntity<String>("User not Activated", HttpStatus.OK);

		} else
			return new ResponseEntity<String>("User not login", HttpStatus.OK);
	}

	@RequestMapping(value = "/showNotes", method = RequestMethod.POST)
	public ResponseEntity<String> showNotes(HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		System.out.println("in Show notes USER @#$:" + user);
		if (user != null) {
			Set<Notes> notes = notesService.getNotes(user.getActivated());
			System.out.println("1232:" + notes);
			if (notes != null) {
				System.out.println(notes);
				return new ResponseEntity<String>("Notes found :" + notes, HttpStatus.OK);
			}

			else {
				return new ResponseEntity<String>("Note not added", HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>("User not logged in!!!", HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteNotes/{deleteId}", method = RequestMethod.POST)
	public ResponseEntity<String> deleteNotes(HttpSession session, @PathVariable("deleteId") int deleteId) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		if (user != null) {
			System.out.println("USER" + user + " id:" + user.getId());
			Notes check = notesService.getNoteById(deleteId);
			System.out.println("!@#" + check);
			if (check.getUserDetails().getId() == user.getId()) {
				System.out.println("NOTE :" + check.getId());
				System.out.println("ID to be deleted" + check);
				System.out.println("del!!!:" + check.getId() + " " + check.getUserDetails());
				Set<Notes> notes = notesService.getNotes(user.getActivated());
				if (notes != null) {
					notesService.deleteNote(deleteId);
					System.out.println(notes);
					return new ResponseEntity<String>("Notes Deleted :", HttpStatus.OK);
				}
				return new ResponseEntity<String>("Notes Not exists :", HttpStatus.OK);
			}
			return new ResponseEntity<String>("Notes Not Created ", HttpStatus.OK);

		}
		return new ResponseEntity<String>("Login To Delete note!!!", HttpStatus.OK);
	}

	@RequestMapping(value = "/updateNote/{updateId}", method = RequestMethod.POST)
	public ResponseEntity<String> updateNote(HttpSession session, @RequestBody Notes note,
			@PathVariable("updateId") int updateId) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		System.out.println("here:user" + user);
		System.out.println("here:" + note);
		if (user != null) {			
			String description = note.getDescription();
			String title = note.getTitle();			
			System.out.println("New Description n title" + description + title);
			notesService.updateNotes(note);
			int updateStatus = notesService.updateNotes(note);
			System.out.println("status update :"+updateStatus);
			if (updateStatus == 1)
				return new ResponseEntity<String>("Note updated", HttpStatus.OK);
			else
				return new ResponseEntity<String>("Note not updated", HttpStatus.OK);

		} else
			return new ResponseEntity<String>("User not login", HttpStatus.OK);
	}

}
