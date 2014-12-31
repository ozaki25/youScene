package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.annotation.CreatedTimestamp;

import play.db.ebean.Model;

@Entity
public class Accesses extends Model {
  @Id
  public Long id;
  @ManyToOne
  public Blogs blog;
  @ManyToOne
  public Users user;
  @CreatedTimestamp
  public Date createdDate;

  public Accesses(Blogs blog, Users user) {
    this.blog = blog;
    this.user = user;
    this.createdDate = new Date();
  }
}
