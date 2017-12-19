package com.bridgelabz.Service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Dao.ImageDao;
import com.bridgelabz.Model.Images;

public class ImagesImpl implements ImageService {
	@Autowired
	ImageDao imageDao;

	@Override
	public int addImage(Images images) {
		// TODO Auto-generated method stub
		int id=imageDao.addImage(images);
		return id;
	}

	@Override
	public boolean deleteImages(Images image) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Images> getAllImages(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
