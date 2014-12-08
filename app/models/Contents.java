package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Contents extends Model {
  @Id
  public Long id;
  @Required
  public String title;
  @Required
  public String article;
  @ManyToOne
  public Users author;
  @CreatedTimestamp
  public Date createdDate;
  @UpdatedTimestamp
  public Date updatedDate;
  @OneToMany(cascade=CascadeType.ALL, mappedBy="content")
  public List<Accesses> accesses;
  @OneToMany(cascade=CascadeType.ALL, mappedBy="content")
  public List<Likes> likes;

  public static Finder<Long, Contents> find = new Finder(Long.class, Contents.class);

  public void addAccess(Users user) {
    Accesses access =new Accesses(this,user);
    access.save();
  }

  public void addLike(Users user) {
    Likes like = new Likes(this,user);
    like.save();
  }

  public boolean liked(Users user) {
    int count = Likes.find.where().eq("content", this).eq("user", user).findRowCount();
    return count >= 1;
  }

  public boolean author(Users user) {
    return this.author == user;
  }

  public static List<Contents> findLatestContents() {
    return find.orderBy().desc("createdDate").setMaxRows(5).findList();
  }
}
