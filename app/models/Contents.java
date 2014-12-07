package models;

import java.util.Date;
import java.util.List;

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
  @OneToMany(mappedBy="content")
  public List<Accesses> accesses;

  public static Finder<Long, Contents> find = new Finder(Long.class, Contents.class);

  public void addAccess(Users user) {
    Accesses access =new Accesses(this,user);
    access.save();
  }
}
