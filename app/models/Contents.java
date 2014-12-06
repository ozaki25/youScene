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
  Long id;
  @Required
  String title;
  @Required
  String article;
  @ManyToOne
  Users user;
  @CreatedTimestamp
  Date createdDate;
  @UpdatedTimestamp
  Date updatedDate;
}
