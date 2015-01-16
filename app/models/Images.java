package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

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
    @CreatedTimestamp
    public Date createdDate;

    public static Finder<Long, Images> find = new Finder(Long.class, Images.class);

    public Images(String imageName, Users user) {
	this.imageName = imageName;
	this.user = user;
    }

    public boolean save(File imageFile) {
	if(!this.user.isExistUserDir()) if(!this.user.mkUserDir()) return false;

	String uploadPath = this.user.userPath() + "/" + this.id + this.extension();
	if(!imageFile.renameTo(new File(uploadPath))) return false;

	return true;
    }

    public String extension() {
	return this.imageName.substring(this.imageName.lastIndexOf("."));
    }

    public String relativePath() {
	return "uploads/" + this.user.id + "/" +  this.id + this.extension();
    }
}
