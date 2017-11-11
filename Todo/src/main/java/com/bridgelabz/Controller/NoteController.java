//Friday
package com.bridgelabz.Controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.bridgelabz.utility.Token;

@RestController
public class NoteController {
	@Autowired
	NotesService notesService;

	@RequestMapping(value = "/saveNote", method = RequestMethod.POST)
	public ResponseEntity<String> saveNote(HttpSession session, @RequestBody Notes note, HttpServletRequest request,
			HttpServletResponse response) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		System.out.println("here:" + user);
		if (user != null) {
			String token = request.getHeader("login");
			int id = Token.verify(token);
			System.out.println("TOKEN ID::" + id);
			if (id > 0) {
				int isActive = user.getActivated();
				if (isActive > 0) {
					note.setUserDetails(user);
					notesService.saveNotes(note);
					return new ResponseEntity<String>("Note Updated " + user, HttpStatus.OK);
				}
				return new ResponseEntity<String>("User not Activated", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("Token issue", HttpStatus.CONFLICT);

		}

		return new ResponseEntity<String>("User not login", HttpStatus.OK);
	}

	@RequestMapping(value = "/showNotes", method = RequestMethod.POST)
	public ResponseEntity<String> showNotes(HttpSession session, HttpServletRequest request) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		System.out.println("in Show notes USER @#$:" + user);
		if (user != null) {
			String token = request.getHeader("login");
			int id = Token.verify(token);
			System.out.println("TOKEN ID::" + id);
			if (id > 0) {
				Set<Notes> notes = notesService.getNotes(user.getActivated());
				System.out.println("1232:" + notes);
				if (notes != null) {
					System.out.println(notes);
					return new ResponseEntity<String>("Notes found :" + notes, HttpStatus.OK);
				}

				else {
					return new ResponseEntity<String>("Note not added", HttpStatus.OK);
				}
			} else
				return new ResponseEntity<String>("Token issue", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("User not logged in!!!", HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteNotes/{deleteId}", method = RequestMethod.POST)
	public ResponseEntity<String> deleteNotes(HttpSession session, HttpServletRequest request,
			@PathVariable("deleteId") int deleteId) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		if (user != null) {
			String token = request.getHeader("login");
			int id = Token.verify(token);
			System.out.println("TOKEN ID::" + id);
			if (id > 0) {
				Notes check = notesService.getNoteById(deleteId);
				if (check.getUserDetails().getId() == user.getId()) {
					Set<Notes> notes = notesService.getNotes(user.getActivated());
					if (notes != null) {
						notesService.deleteNote(deleteId);
						return new ResponseEntity<String>("Notes Deleted ", HttpStatus.OK);
					}
					return new ResponseEntity<String>("Notes Not exists ", HttpStatus.OK);
				}
				return new ResponseEntity<String>("Notes Not Created ", HttpStatus.OK);

			} else
				return new ResponseEntity<String>("Token issue", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("Login To Delete note!!!", HttpStatus.OK);
	}

	@RequestMapping(value = "/updateNote/{updateId}", method = RequestMethod.POST)
	public ResponseEntity<String> updateNote(HttpSession session, HttpServletRequest request, @RequestBody Notes note,
			@PathVariable("updateId") int updateId) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		if (user != null) {
			String token = request.getHeader("login");
			int id = Token.verify(token);
			System.out.println("TOKEN ID::" + id);
			if (id > 0) {
				Notes noteUpdate = notesService.getNoteById(updateId);
				noteUpdate.setUserDetails(user);
				noteUpdate.setTitle(note.getTitle());
				noteUpdate.setDescription(note.getDescription());
				noteUpdate.setColor(note.getColor());
				int updateStatus = notesService.updateNotes(noteUpdate);
				if (updateStatus == 1)
					return new ResponseEntity<String>("Note updated", HttpStatus.OK);
				else
					return new ResponseEntity<String>("Note not updated", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("Token issue", HttpStatus.CONFLICT);
		} else
			return new ResponseEntity<String>("Invalid", HttpStatus.OK);
	}
}
