package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
  @OneToMany(cascade=CascadeType.ALL, mappedBy="content")
  public List<Accesses> accesses;
  @OneToMany(cascade=CascadeType.ALL, mappedBy="content")
  public List<Likes> likes;

  public static Finder<Long, Contents> find = new Finder(Long.class, Contents.class);

  public static List<Contents> findLatestContents() {
    return find.orderBy().desc("updatedDate").setMaxRows(5).findList();
  }

  public static List<Contents> findPagingList(int page) {
    return find.orderBy().desc("updatedDate").findPagingList(5).getPage(page-1).getList();
  }

  public static boolean isLastPage(int page) {
    int totalPageCount = find.findPagingList(5).getTotalPageCount();
    int lastPage = totalPageCount == 0 ? 1 : totalPageCount;
    return page == lastPage;
  }
}
