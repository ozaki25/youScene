package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Users extends Model {
  @Id
  public Long id;
  public String name;
  public String sectionName;

  public static Finder<Long, Users> find = new Finder(Long.class, Users.class);

  public static void setUser() {
    Users user = new Users();
    user.name = "尾崎 勇一";
    user.sectionName = "ITプロデュース部";
    user.save();
  }
}
