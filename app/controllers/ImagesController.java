package controllers;

import java.util.List;
import java.io.File;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import play.*;
import play.mvc.*;
import play.mvc.Http.MultipartFormData.FilePart;

import models.Images;

public class ImagesController extends YouScene {
    public static Result upload() throws Exception {
	FilePart file = request().body().asMultipartFormData().getFile("image");
	String imageName = file.getFilename();
	File imageFile = file.getFile();
	System.out.println("imageFile");
	System.out.println(imageFile);

	Images image = new Images(imageName, currentUser());
	if(!image.save(imageFile)) return badRequest();

	return ok(image.relativePath());
    }

    public static Result renderImage(Long id) {
	System.out.println("renderImage");
	try {
	    Images image = Images.find.byId(id);
	    System.out.println("image.name : "+image.imageName);
	    System.out.println("image.file : "+image.file);
	    InputStream is = new ByteArrayInputStream(image.file);
	System.out.println("11111111111");
	System.out.println(is);
	    BufferedImage bi = ImageIO.read(is);
	System.out.println(bi);
	System.out.println("22222222222");
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	System.out.println("333333333333");
	System.out.println("image.extension() : "+image.extension());
	//ImageIO.write(bi, image.extension(), baos);
	ImageIO.write(bi, "jpeg", baos);
	baos.flush();
	System.out.println("444444444444");
	    return ok(baos.toByteArray()).as("image/jpg");
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return ok();
    }
}
