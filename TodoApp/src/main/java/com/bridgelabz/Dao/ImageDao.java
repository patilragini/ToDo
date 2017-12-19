package com.bridgelabz.Dao;

import java.util.Set;

import com.bridgelabz.Model.Images;

public interface ImageDao {
	public int addImage(Images images);
	public boolean deleteImages(Images image);
	public Set<Images> getAllImages(int noteid);
}
