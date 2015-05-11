package models;

import java.util.List;
import java.io.File;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import play.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Users extends Model {
  @Id
  public Long id;
  @Required
  public String userId;
  @Required
  public String name;
  @Required
  public String sectionName;
  @OneToMany(cascade=CascadeType.ALL, mappedBy="user")
  @OrderBy("createdDate Desc")
  public List<Images> images;

  public static Finder<Long, Users> find = new Finder(Long.class, Users.class);

  public static Users findByUserId(String userId) {
    return find.where().eq("userId", userId).findUnique();
  }

  public boolean isAuthor(Blogs blog) {
    return this.equals(blog.author);
  }

  public boolean liked(Blogs blog) {
    return Likes.find.where().eq("blog", blog).eq("user", this).findRowCount() >= 1;
  }

  public void addAccess(Blogs blog) {
    Accesses access =new Accesses(blog, this);
    access.save();
  }

  public void addLike(Blogs blog) {
    Likes like = new Likes(blog, this);
    like.save();
  }

    public String userPath() {
	return Play.application().path().getPath() + "/public/uploads/" + this.id;
    }

    public boolean isExistUserDir() {
	File userDir = new File(this.userPath());
	return userDir.isDirectory();
    }

    public boolean mkUserDir() {	
	File userDir = new File(this.userPath());
	return userDir.mkdir();
    }
}
