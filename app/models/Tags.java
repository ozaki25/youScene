package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.avaje.ebean.annotation.CreatedTimestamp;

import org.codehaus.jackson.annotate.JsonBackReference;

import play.data.validation.Constraints.Required;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Tags extends Model {
    @Id
    public Long id;
    public String tagName;
    @ManyToMany
    @JsonBackReference
    public List<Blogs> blogs;
    @CreatedTimestamp
    public Date createdDate;

    public static Finder<Long, Tags> find = new Finder(Long.class, Tags.class);

    public Tags(String tagName) {
	this.tagName = tagName;
    }

    public boolean isExist() {
	return find.where().eq("tagName",this.tagName).findRowCount() > 0;
    }

    public static List<Tags> list() {
	 return Tags.find.orderBy("tagName").findList();
    }

    public static Tags findByTagName(String tagName) {
	return find.where().eq("tagName",tagName).findUnique();
    }

    public static List<Tags> findByTagNames(List<String> tagNames) {
	return find.where().in("tagName",tagNames).findList();
    }

    public static List<Tags> findByTagNameLike(String term) {
	return find.where().ilike("tagName","%" + term + "%").orderBy("tagName").findList();
    }

    public static List<Tags> popularTags() {
	return find.setMaxRows(5).findList();
    }
}
