//21 nov
package com.bridgelabz.Controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

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
import com.bridgelabz.Service.UserService;
import com.bridgelabz.utility.Token;

@RestController
public class NoteController {
	@Autowired
	NotesService notesService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/saveNote", method = RequestMethod.POST)
	public ResponseEntity<String> saveNote(HttpSession session, @RequestBody Notes note, HttpServletRequest request,
			HttpServletResponse response) {
		String token = request.getHeader("login");
		int id = Token.verify(token);
		UserDetails user = userService.getUserById(id);
//		System.out.println("here:" + user);
		Date date = new Date();
		if (user != null) {
			System.out.println("TOKEN ID::" + id+"\n TOKEN ::"+token);
			if (id > 0) {
				int isActive = user.getActivated();
				if (isActive > 0) {
					note.setUserDetails(user);
					note.setCreateDate(date);
					note.setLastUpdated(date);
					notesService.saveNotes(note);
					return new ResponseEntity<String>("Note Updated !!! " + user, HttpStatus.OK);
				}
				return new ResponseEntity<String>("User not Activated !!!", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("Token issue !!!", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("Invalid!!!\n Login To Continue", HttpStatus.OK);
	}

	@RequestMapping(value = "/showNotes", method = RequestMethod.POST)
	public ResponseEntity<String> showNotes(HttpSession session, HttpServletRequest request) {
		String token = request.getHeader("login");
		int id = Token.verify(token);
		UserDetails user = userService.getUserById(id);
//		System.out.println("here:" + user);
		if (user != null) {
			System.out.println("TOKEN ID::" + id+"\n TOKEN ::"+token);
			if (id > 0) {
				Set<Notes> notes = notesService.getNotes(user.getId());
				System.out.println(notes.isEmpty());
				if (!notes.isEmpty()) {
					System.out.println(notes);
					return new ResponseEntity<String>("Notes found :" + notes, HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("Note not added !!!", HttpStatus.OK);
				}
			} else
				return new ResponseEntity<String>("Token issue !!!", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("Invalid!!!\n Login To Continue", HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteNotes/{deleteId}", method = RequestMethod.POST)
	public ResponseEntity<String> deleteNotes(HttpSession session, HttpServletRequest request,
			@PathVariable("deleteId") int deleteId) {
		String token = request.getHeader("login");
		int id = Token.verify(token);
		UserDetails user = userService.getUserById(id);
		//System.out.println("here:" + user);
		Notes noteDelete = notesService.getNoteById(deleteId);

		if (user != null && noteDelete!=null) {
			//System.out.println("TOKEN ID::" + id);
			if (id > 0) {
				//System.out.println("HERE::" + noteUpdate.getUserDetails().getId());
				if (noteDelete.getUserDetails().getId() == id) {
						Set<Notes> notes = notesService.getNotes(user.getId());
						if (notes != null) {
							notesService.deleteNote(deleteId);
							return new ResponseEntity<String>("Notes Deleted ", HttpStatus.OK);
						}
						return new ResponseEntity<String>("Notes Not exists ", HttpStatus.OK);
					}
					return new ResponseEntity<String>("Notes Not Created ", HttpStatus.OK);
				}
				return new ResponseEntity<String>("Notes Not Created ", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("Invalid!!!\n Login To Continue", HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/updateNote/{updateId}", method = RequestMethod.POST)
	public ResponseEntity<String> updateNote(HttpSession session, HttpServletRequest request, @RequestBody Notes note,
			@PathVariable("updateId") int updateId) {
		Date date = new Date();
		String token = request.getHeader("login");
		int id = Token.verify(token);
		UserDetails user = userService.getUserById(id);
		//System.out.println("here:" + user);
		Notes noteUpdate = notesService.getNoteById(updateId);
System.out.println(noteUpdate);
		if (user != null && noteUpdate!=null) {
			//System.out.println("TOKEN ID::" + id);
			if (id > 0) {
				//System.out.println("HERE::" + noteUpdate.getUserDetails().getId());
				if (noteUpdate.getUserDetails().getId() == id) {
					// check id of token is = user id for authentication extra
					noteUpdate.setUserDetails(user);
					noteUpdate.setTitle(note.getTitle());
					noteUpdate.setDescription(note.getDescription());
					noteUpdate.setColor(note.getColor());
					noteUpdate.setLastUpdated(date);
					int updateStatus = notesService.updateNotes(noteUpdate);
					if (updateStatus == 1)
						return new ResponseEntity<String>("Note updated", HttpStatus.OK);
					else
						return new ResponseEntity<String>("Note not updated", HttpStatus.OK);

				}
				return new ResponseEntity<String>("Note do not exist", HttpStatus.OK);

			} else
				return new ResponseEntity<String>("Token issue", HttpStatus.CONFLICT);
		} else
			return new ResponseEntity<String>("Invalid!!!", HttpStatus.OK);
	}
}