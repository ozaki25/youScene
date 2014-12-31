package models;

import javax.persistence.Entity;
import javax.persistence.Id;

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
}
