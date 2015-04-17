package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;

import javax.persistence.Lob;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.annotation.CreatedTimestamp;

import play.data.validation.Constraints.Required;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Images extends Model {
    @Id
    public Long id;
    public String imageName;
    @ManyToOne
    public Users user;
    @Lob
    public byte[] file;
    @CreatedTimestamp
    public Date createdDate;

    public static Finder<Long, Images> find = new Finder(Long.class, Images.class);

    public Images(String imageName, Users user) {
	this.imageName = imageName;
	this.user = user;
    }

    public boolean save(File imageFile) throws Exception {
	if(!this.user.isExistUserDir()) if(!this.user.mkUserDir()) return false;

	this.save();

	String uploadPath = this.user.userPath() + "/" + this.id + this.extension();
	if(!imageFile.renameTo(new File(uploadPath))) return false;
	System.out.println(new File(uploadPath));
	InputStream imageInputStream = new FileInputStream(new File(uploadPath));
	this.file = getByteArray(imageInputStream);
	this.save();

	return true;
    }

    public String extension() {
	return this.imageName.substring(this.imageName.lastIndexOf("."));
    }

    public String relativePath() {
	return "uploads/" + this.user.id + "/" +  this.id + this.extension();
    }

    public static byte[] getByteArray(InputStream is) throws Exception {
	ByteArrayOutputStream b = new ByteArrayOutputStream();
	BufferedOutputStream os = new BufferedOutputStream(b);
	os.write(0);
	os.flush();
	os.close();
	return b.toByteArray();
    }
}
