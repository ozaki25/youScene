package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Users extends Model {
  @Id
  Long id;
  String name;
  String sectionName;
}
