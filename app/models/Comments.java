package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Comments extends Model {
    @Id
    public Long id;
    @Required
    @Column(columnDefinition="TEXT")
    public String article;
    @ManyToOne
    public Users author;
    @ManyToOne
    public Blogs blog;
    @CreatedTimestamp
    public Date createdDate;
    @UpdatedTimestamp
    public Date updatedDate;

    public static Finder<Long, Comments> find = new Finder(Long.class, Comments.class);

}
