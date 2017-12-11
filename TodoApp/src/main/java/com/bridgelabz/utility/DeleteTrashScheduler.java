package com.bridgelabz.utility;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.bridgelabz.Model.Notes;
import com.bridgelabz.Service.NotesService;

@EnableScheduling
public class DeleteTrashScheduler {
	
	@Autowired
	NotesService noteService;
	
	@Scheduled(fixedDelay=60000)
	public void deleteTrashNote() {
		List<Notes> notes=noteService.getNotesInTrash();
		int size=notes.size();
//		System.out.println("size"+size);
		long day=System.currentTimeMillis();
//		System.out.println("current mili sec"+day);
		day=day-(7*24*60*60*1000);
		Date dayMOdified=new Date(day);
//		System.out.println("dayMOdified"+dayMOdified);
		for(int i=0;i<size;i++) {
//			System.out.println("in for");
			if(notes.get(i).getLastUpdated().before(dayMOdified)) {
//				System.out.println("in if:::::::");
//				System.out.println("note time::"+notes.get(i).getLastUpdated());
				noteService.deleteNote(notes.get(i).getId());
			}
		}
		
	}

}
