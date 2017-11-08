//tueday
package com.bridgelabz.Controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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
import com.bridgelabz.Service.UserService;
import com.bridgelabz.utility.Token;

@RestController
public class NoteController {
	@Autowired
	NotesService notesService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/saveNote", method = RequestMethod.POST)
	public ResponseEntity<String> saveNote(HttpServletRequest request, HttpSession session, @RequestBody Notes note) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		System.out.println("here:" + user);
		String token = request.getHeader("Authorization");
		System.out.println(token);
		UserDetails userToken = userService.getUserById(Token.verifyToken(token));
		if (userToken != null) {
			if (user != null) {
				int isActive = user.getActivated();
				if (isActive > 0) {
					note.setUserDetails(user);
					notesService.saveNotes(note);
					return new ResponseEntity<String>("Note Updated " + user, HttpStatus.OK);
				}
				return new ResponseEntity<String>("User not Activated", HttpStatus.CONFLICT);

			} else
				return new ResponseEntity<String>("User not login", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Login to continue", HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/showNotes", method = RequestMethod.POST)
	public ResponseEntity<String> showNotes(HttpServletRequest request, HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		if (user != null) {
			String token = request.getHeader("Authorization");
			System.out.println(token);
			UserDetails userToken = userService.getUserById(Token.verifyToken(token));
			if (userToken != null) {
				Set<Notes> notes = notesService.getNotes(user.getActivated());
				if (notes != null) {
					System.out.println(notes);
					return new ResponseEntity<String>("Notes found :" + notes, HttpStatus.OK);
				}

				else {
					return new ResponseEntity<String>("Note not added", HttpStatus.OK);
				}
			} else
				return new ResponseEntity<String>("User not login", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Login to continue", HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/deleteNotes/{deleteId}", method = RequestMethod.POST)
	public ResponseEntity<String> deleteNotes(HttpServletRequest request, HttpSession session,
			@PathVariable("deleteId") int deleteId) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		if (user != null) {
			String token = request.getHeader("Authorization");
			System.out.println(token);
			UserDetails userToken = userService.getUserById(Token.verifyToken(token));
			if (userToken != null) {
				Notes check = notesService.getNoteById(deleteId);
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

			} else
				return new ResponseEntity<String>("User not login", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Login to continue", HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/updateNote/{updateId}", method = RequestMethod.POST)
	public ResponseEntity<String> updateNote(HttpServletRequest request, HttpSession session, @RequestBody Notes note,
			@PathVariable("updateId") int updateId) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		if (user != null) {
			String token = request.getHeader("Authorization");
			System.out.println(token);
			UserDetails userToken = userService.getUserById(Token.verifyToken(token));
			if (userToken != null) {
				note.setDescription(note.getDescription());
				note.setTitle(note.getTitle());
				note.setColor(note.getColor());
				note.setUserDetails(user);
				notesService.updateNotes(note);
				int updateStatus = notesService.updateNotes(note);
				System.out.println("status update :" + updateStatus);
				if (updateStatus == 1)
					return new ResponseEntity<String>("Note updated", HttpStatus.OK);
				else
					return new ResponseEntity<String>("Note not updated", HttpStatus.OK);

			} else
				return new ResponseEntity<String>("User not login", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Login to continue", HttpStatus.SERVICE_UNAVAILABLE);
	}
}
