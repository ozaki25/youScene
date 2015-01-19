package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Blogs extends Model {
    @Id
    public Long id;
    @Required
    public String title;
    @Required
    @Column(columnDefinition="TEXT")
    public String article;
    @ManyToOne
    public Users author;
    @CreatedTimestamp
    public Date createdDate;
    @UpdatedTimestamp
    public Date updatedDate;
    @OneToMany(cascade=CascadeType.ALL, mappedBy="blog")
    public List<Accesses> accesses;
    @OneToMany(cascade=CascadeType.ALL, mappedBy="blog")
    public List<Likes> likes;
    @ManyToMany(cascade=CascadeType.REMOVE, mappedBy="blogs")
    public List<Tags> tags = new ArrayList<Tags>();
    public List<String> tagNames = new ArrayList<String>();

    public static Finder<Long, Blogs> find = new Finder(Long.class, Blogs.class);
    final static int perPageBlogs = 5;

    public static List<Blogs> findLatestBlogs() {
	return find.orderBy().desc("updatedDate").setMaxRows(perPageBlogs).findList();
    }

    public static List<Blogs> findPagingList(int page) {
	return find.orderBy().desc("updatedDate").findPagingList(perPageBlogs).getPage(page-1).getList();
    }

    public static boolean isLastPage(int page) {
	int totalPageCount = find.findPagingList(perPageBlogs).getTotalPageCount();
	int lastPage = totalPageCount == 0 ? 1 : totalPageCount;

	return page == lastPage;
    }
}
