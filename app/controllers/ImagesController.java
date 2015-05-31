package controllers;

import java.io.File;
import java.io.ByteArrayInputStream;

import play.*;
import play.mvc.*;
import play.mvc.Http.MultipartFormData.FilePart;

import models.Images;
import views.html.images.*;

public class ImagesController extends YouScene {
    public static Result upload() throws Exception {
	FilePart filePart = request().body().asMultipartFormData().getFile("image");
	String fileName = filePart.getFilename();
	File file = filePart.getFile();

	Images image = new Images(file, fileName, currentUser());
	image.save();

	return ok(show.render(image));
    }

    public static Result show(Long id) {
	Images image = Images.find.byId(id);
	ByteArrayInputStream is = new ByteArrayInputStream(image.file);

	return ok(is).as("image/png");
    }

  public static Result delete(Long id) {
    Images image = Images.find.byId(id);
    image.delete();

    return ok();
  }
}
