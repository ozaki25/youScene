package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.annotation.CreatedTimestamp;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Likes extends Model {
  @Id
  public Long id;
  @ManyToOne
  public Blogs blog;
  @ManyToOne
  public Users user;
  @CreatedTimestamp
  public Date createdDate;

  public static Finder<Long, Likes> find = new Finder(Long.class, Likes.class);

  public Likes(Blogs blog, Users user) {
    this.blog = blog;
    this.user = user;
    this.createdDate = new Date();
  }
}
