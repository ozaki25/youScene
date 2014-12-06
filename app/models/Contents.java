package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
  public Users user;
  @CreatedTimestamp
  public Date createdDate;
  @UpdatedTimestamp
  public Date updatedDate;

  public static Finder<Long, Contents> find = new Finder(Long.class, Contents.class);
}
