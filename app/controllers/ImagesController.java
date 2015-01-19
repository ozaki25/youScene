package controllers;

import java.util.List;
import java.io.File;

import play.*;
import play.mvc.*;
import play.mvc.Http.MultipartFormData.FilePart;

import models.Images;

public class ImagesController extends YouScene {
    public static Result upload() {
	FilePart file = request().body().asMultipartFormData().getFile("image");
	String imageName = file.getFilename();
	File imageFile = file.getFile();

	Images image = new Images(imageName, currentUser());
	if(!image.save(imageFile)) return badRequest();

	return ok(image.relativePath());
    }
}
