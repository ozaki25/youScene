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
  public Contents content; 
  @ManyToOne
  public Users user;
  @CreatedTimestamp
  Date createdDate;

  public Accesses(Contents content, Users user) {
    this.content = content;
    this.user = user;
    this.createdDate = new Date();
  }
}
