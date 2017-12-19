package com.bridgelabz.Service;

import java.util.Set;

import com.bridgelabz.Model.Images;

public interface ImageService {
	public int addImage(Images images);
	public boolean deleteImages(Images image);

	public Set<Images> getAllImages(int userId);
}
