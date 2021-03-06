
package com.bridgelabz.Controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Model.Collaborator;
import com.bridgelabz.Model.Label;
import com.bridgelabz.Model.Notes;
import com.bridgelabz.Model.UrlData;
import com.bridgelabz.Model.UserDetails;
import com.bridgelabz.Service.NotesService;
import com.bridgelabz.Service.UserService;
import com.bridgelabz.utility.CustomResponse;
import com.bridgelabz.utility.LinkScrapper;
import com.bridgelabz.utility.Token;

@RestController
public class NoteController {
	@Autowired
	NotesService notesService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/saveNote", method = RequestMethod.POST)
	public ResponseEntity<Set<Notes>> saveNote(HttpSession session, @RequestBody Notes note, HttpServletRequest request,
			HttpServletResponse response) {
		String token = request.getHeader("login");
		int id = Token.verify(token);
		UserDetails user = userService.getUserById(id);
		Date date = new Date();
		if (user != null) {
			if (id > 0) {
				int isActive = user.getActivated();
				if (isActive > 0 && (note.getTitle().length() > 0 || note.getDescription().length() > 0)) {
					note.setUserDetails(user);
					note.setCreateDate(date);
					note.setLastUpdated(date);
					notesService.saveNotes(note);
					System.out.println(note);
					return new ResponseEntity<Set<Notes>>(HttpStatus.OK);
					// return new ResponseEntity<String>("Note Updated !!! " +
					// user, HttpStatus.OK);
				}
				return new ResponseEntity<Set<Notes>>(HttpStatus.CONFLICT);
				// return new ResponseEntity<String>("User not Activated !!!",
				// HttpStatus.OK);
			} else
				return new ResponseEntity<Set<Notes>>(HttpStatus.CONFLICT);
			// return new ResponseEntity<String>("Token issue !!!",
			// HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Set<Notes>>(HttpStatus.CONFLICT);
		// return new ResponseEntity<String>("Invalid!!!\n Login To Continue",
		// HttpStatus.OK);
	}

	@RequestMapping(value = "/showNotes", method = RequestMethod.POST)
	public ResponseEntity<Set<Notes>> showNotes(HttpServletRequest request) {
		String token = request.getHeader("login");
		int id = Token.verify(token);
		UserDetails user = userService.getUserById(id);
		if (user != null) {
			if (id > 0) {
				Set<Notes> notes = notesService.getNotes(user.getId());
				Set<Notes> collborated =notesService.getCollboratedNotes(user.getId());
				notes.addAll(collborated);
					if (!notes.isEmpty()) {
					return ResponseEntity.ok(notes);
				} else {
					return new ResponseEntity<Set<Notes>>(HttpStatus.OK);
				}
			} else
				return new ResponseEntity<Set<Notes>>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Set<Notes>>(HttpStatus.OK);
	}
		
	
	
	
	
	@RequestMapping(value = "/deleteForeverNote/{noteId}", method = RequestMethod.POST)
	public ResponseEntity<Set<Notes>> deleteForeverNote(@PathVariable("noteId") int noteId,HttpServletRequest request, @RequestBody Notes note) {
		String token = request.getHeader("login");
		int id = Token.verify(token);
		UserDetails user = userService.getUserById(id);
		Notes notedelete = notesService.getNoteById(noteId);
		System.out.println("1");
		System.out.println(noteId);
		
		if (user != null) {
			System.out.println("token id:"+id);			
			System.out.println("note user"+notedelete.getUserDetails());			
			System.out.println("note id"+notedelete.getUserDetails().getId());
				if (notedelete.getUserDetails().getId() == id) {							
				System.out.println("note user login user are same::");
					notesService.deleteNote(noteId);
				return new ResponseEntity<Set<Notes>>(HttpStatus.OK);				
			}			
			
			return new ResponseEntity<Set<Notes>>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Set<Notes>>(HttpStatus.CONFLICT);
	}
	
	
	@RequestMapping(value = "/updateNote/{updateId}", method = RequestMethod.POST)
	public ResponseEntity<String> updateNote(HttpSession session, HttpServletRequest request, @RequestBody Notes note,
			@PathVariable("updateId") int updateId) {
		Date date = new Date();
		System.out.println("in update note::");
		System.out.println();
		String token = request.getHeader("login");
		int id = Token.verify(token);
		UserDetails user = userService.getUserById(id);
		Notes noteUpdate = notesService.getNoteById(updateId);
		if (user != null && noteUpdate != null) {
			if (id > 0) {
				System.out.println("in updation");
				//check token user id and note user
				if (noteUpdate.getUserDetails().getId() == id) {
					note.setUserDetails(user);					
					note.setLastUpdated(date);	
					int updateStatus = notesService.updateNotes(note);
					if (updateStatus == 1)
						return new ResponseEntity<String>("Note updated", HttpStatus.OK);
					else{
						System.out.println("error in updation1");
						return new ResponseEntity<String>("Note not updated", HttpStatus.OK);
					}
				}
				else{
				System.out.println("error in updation2");
				List<UserDetails> users=notesService.getListOfUser(note.getId());
				int i=0;
				int flag=0;
				while(users.size()>i) {
					System.out.println("updation2");
					if(users.get(i).getId()==user.getId()) {
						flag=1;
						System.out.println("2up");
					}
					i++;
				}if(flag==1){
					System.out.println("2up");
					note.setUserDetails(noteUpdate.getUserDetails());
					notesService.updateNotes(note);
				}
				return new ResponseEntity<String>("Note do not exist", HttpStatus.OK);
				}
			} else{
				System.out.println("error in updation3");

				return new ResponseEntity<String>("Token issue", HttpStatus.CONFLICT);
			}
			} else
			return new ResponseEntity<String>("Invalid!!!", HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/collaborate", method = RequestMethod.POST)
	public ResponseEntity<List<UserDetails>> getNotes(@RequestBody Collaborator collborator, HttpServletRequest request){
		List<UserDetails> users=new ArrayList<UserDetails>();
		Collaborator collaborate =new Collaborator();
//		System.out.println(collaborate);
		Notes note= (Notes) collborator.getNote();
//		System.out.println(note);
		UserDetails shareWith= (UserDetails) collborator.getShareWithId();
//		System.out.println(shareWith);
		shareWith=userService.getUserByEmail(shareWith.getEmail());
		UserDetails owner= (UserDetails) collborator.getOwnerId();
//		System.out.println("owner  backend "+owner);
		String token=request.getHeader("login");
//		System.out.println("token ::"+token);
		
		users=	notesService.getListOfUser(note.getId());
		UserDetails user=userService.getUserById(Token.verify(token));
		if(user!=null) {
				if(shareWith!=null && shareWith.getId()!=owner.getId()) {
					int i=0;
					int flag=0;
					while(users.size()>i) {
						if(users.get(i).getId()==shareWith.getId()) {
							flag=1;
						}
						i++;
					}
					if(flag==0) {
						collaborate.setNote(note);
						collaborate.setOwnerId(owner);
						collaborate.setShareWithId(shareWith);
						if(notesService.saveCollborator(collaborate)>0) {
						  	users.add(shareWith);
						}else {
							 ResponseEntity.ok(users);
						}
					}
		}
		}
		return ResponseEntity.ok(users);
	}
	
	
	@RequestMapping(value = "/getOwner", method = RequestMethod.POST)
	public ResponseEntity<UserDetails> getOwner(@RequestBody Notes note, HttpServletRequest request){
		String token=request.getHeader("login");
//		System.out.println("in get owner !!!!!!!!!!!!!!!!!!1");
		UserDetails user=userService.getUserById(Token.verify(token));
		if(user!=null) {
			Notes noteComplete=notesService.getNoteById(note.getId());
		UserDetails owner=noteComplete.getUserDetails();
		return ResponseEntity.ok(owner);
		}
		else{
			return ResponseEntity.ok(null);
		}
	}

	@RequestMapping(value = "/removeCollborator", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> removeCollborator(@RequestBody Collaborator collborator, HttpServletRequest request){
		CustomResponse response=new CustomResponse();
		int shareWith=collborator.getShareWithId().getId();
		int noteId=collborator.getNote().getId();
		Notes note=notesService.getNoteById(noteId);
		UserDetails owner=note.getUserDetails();
		String token=request.getHeader("login");
		UserDetails user=userService.getUserById(Token.verify(token));
		if(user!=null) {
				if(owner.getId()!=shareWith){
					if(notesService.removeCollborator(shareWith, noteId)>0){
						response.setMessage("Collborator removed");
						return ResponseEntity.ok(response);
					}else{
						response.setMessage("database problem");
						return ResponseEntity.ok(response);
					}
				}else{
					response.setMessage("Can't remove owner");
					return ResponseEntity.ok(response);
				}
		
	    }else{
	    	response.setMessage("Token expired");
			return ResponseEntity.ok(response);
	    }
	}
	
@RequestMapping(value = "/addLabel", method = RequestMethod.POST)
public ResponseEntity<CustomResponse> addLabel(@RequestBody Label label,HttpServletRequest request){
	CustomResponse response=new CustomResponse();
	String token =request.getHeader("login");

	System.out.println("in add label"+token);
	UserDetails user=userService.getUserById(Token.verify(token));
	if(user!=null){
		
		Set<Label> labels=userService.getAllLabels(user.getId());
		if(labels!=null){
	    	Iterator<Label> itr = labels.iterator();
		    while(itr.hasNext())
		     {
		    	System.out.println("inside");
		      	Label oldLabel=(Label) itr.next();
	         	if(oldLabel.getLabelName().equals(label.getLabelName())){
	     	    	response.setMessage("label already exist");
				   return ResponseEntity.ok(response); 
	         	}
		     }
			}
		
		label.setUser(user);
		int id=userService.addLabel(label);
		if(id>0){
			response.setMessage("Label added");
			return ResponseEntity.ok(response);
		}else{
			 response.setMessage("Problem occured");
			 return ResponseEntity.ok(response);
		}
	}else{
		response.setMessage("Token expired");
		 return ResponseEntity.ok(response);
	  
	}
}
//deleteLable

@RequestMapping(value = "/deletelabel", method = RequestMethod.POST)
public ResponseEntity<CustomResponse> 	deleteLabel(@RequestBody Label label,HttpServletRequest request){
	CustomResponse response=new CustomResponse();
	String token =request.getHeader("login");
System.out.println("in delete label"+label);
	UserDetails user=userService.getUserById(Token.verify(token));
	if(user!=null){			
		Set<Label> labels=userService.getAllLabels(user.getId());
		if(labels!=null){
		
			boolean id=userService.deleteLable(label);
			
			if(id){
				response.setMessage("Label removed !!!");
				return ResponseEntity.ok(response);
			}else{
				 response.setMessage("ERROR");
				 return ResponseEntity.ok(response);
			}
			}
	}else{
		response.setMessage("Token expired !!!");
		 return ResponseEntity.ok(response);
	  
	}
	response.setMessage("Token Issue !!!");
	 return ResponseEntity.ok(response);		
}
@RequestMapping(value = "/updateLabel", method = RequestMethod.POST)
public ResponseEntity<CustomResponse> 	updateLabel(@RequestBody Label label,HttpServletRequest request){
	CustomResponse response=new CustomResponse();
	String token =request.getHeader("login");
System.out.println("in delete label"+label);
	UserDetails user=userService.getUserById(Token.verify(token));
	if(user!=null){			
		Set<Label> labels=userService.getAllLabels(user.getId());
		if(labels!=null){
		
			boolean id=userService.updateLable(label);
			
			if(id){
				response.setMessage("Label updated !!!");
				return ResponseEntity.ok(response);
			}else{
				 response.setMessage("ERROR in label updation ");
				 return ResponseEntity.ok(response);
			}
			}
	}else{
		response.setMessage("Token expired !!!");
		 return ResponseEntity.ok(response);
	  
	}
	response.setMessage("Token Issue !!!");
	 return ResponseEntity.ok(response);		
}
	
@RequestMapping(value = "/getEmailUserlist", method = RequestMethod.GET)
public ResponseEntity<List<UserDetails>> getUserList(HttpServletRequest request){
	String token =request.getHeader("login");
	UserDetails user=userService.getUserById(Token.verify(token));
	if(user!=null){
		List<UserDetails> emailUserlist=userService.getUserList();
		System.out.println("in get user list "+emailUserlist);
		return ResponseEntity.ok(emailUserlist);
	}else{
		return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	}
}
	
@RequestMapping(value = "/getUrlData", method = RequestMethod.POST)
public ResponseEntity<?> getUrlData(HttpServletRequest request){
	String url=request.getHeader("url");
	LinkScrapper link=new LinkScrapper();
	UrlData data=null;
	try {
		data = link.getUrlMetaData(url);
	} catch (IOException e) {
		e.printStackTrace();
	}
	return ResponseEntity.ok(data);
}
	
	
}