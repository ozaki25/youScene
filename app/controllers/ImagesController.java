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

	Images image = new Images(imageName,currentUser());
	image.save();

	String userPath = Play.application().path().getPath() + "/public/uploads/" + currentUser().id;
	File userDir = new File(userPath);
	if(!userDir.isDirectory()) if(!userDir.mkdir()) return badRequest();
	String uploadPath = userPath + "/" + image.id + image.extension();
	if(!imageFile.renameTo(new File(uploadPath))) return badRequest();
	System.out.println("return ok");
	return ok();

    }
}
