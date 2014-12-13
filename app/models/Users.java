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

  public boolean isAuthor(Contents content) {
    return this.equals(content.author);
  }

  public boolean liked(Contents content) {
    int count = Likes.find.where().eq("content", content).eq("user", this).findRowCount();
    return count >= 1;
  }

  public void addAccess(Contents content) {
    Accesses access =new Accesses(content, this);
    access.save();
  }

  public void addLike(Contents content) {
    Likes like = new Likes(content, this);
    like.save();
  }
}
