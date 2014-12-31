package controllers;

import java.util.List;
import java.util.ArrayList;

import play.*;
import play.mvc.*;
import play.mvc.Security.Authenticated;
import play.data.Form;
import static play.data.Form.*;

import models.Blogs;
import models.Likes;
import models.Users;
import models.Tags;
import views.html.blogs.*;

public class BlogsController extends YouScene {
  final static Form<Blogs> blogForm = form(Blogs.class);
  final static Form<Tags> tagForm = form(Tags.class);

  @Authenticated(Authenticate.class)
  public static Result index(int page) {
    return ok(index.render("記事一覧", page));
  }

  @Authenticated(Authenticate.class)
  public static Result show(Long blogId) {
    Blogs blog = Blogs.find.byId(blogId);
    Users user = currentUser();
    if(!user.isAuthor(blog)) user.addAccess(blog);

    return ok(show.render(blog, user));
  }

  @Authenticated(Authenticate.class)
  public static Result newBlog() {
      return ok(newBlog.render("新規作成",blogForm,tagForm));
  }

  public static Result create() {
    Form<Blogs> form = blogForm.bindFromRequest();
    if(form.hasErrors()) return badRequest(newBlog.render("新規作成",form,tagForm));

    Blogs blog = form.get();
    blog.author = currentUser();
    blog.tags = Tags.findByTagNames(blog.tagNames);
    blog.save();
    blog.saveManyToManyAssociations("tags");

    return redirect(routes.BlogsController.show(blog.id));
  }

  @Authenticated(Authenticate.class)
  public static Result edit(Long blogId) {
    Blogs blog = Blogs.find.byId(blogId);
    if(!currentUser().isAuthor(blog)) return redirect(routes.BlogsController.index(1));

    return ok(edit.render("記事編集",blog,blogForm.fill(blog),tagForm));
  }

  public static Result update(Long blogId) {
    Form<Blogs> form = blogForm.bindFromRequest();
    Blogs blog = Blogs.find.byId(blogId);
    if(form.hasErrors()) return badRequest(edit.render("記事編集",blog,form,tagForm));

    blog = form.get();
    blog.tags = Tags.findByTagNames(blog.tagNames);
    blog.update(blogId);
    blog.saveManyToManyAssociations("tags");

    return redirect(routes.BlogsController.show(blog.id));
  }

  public static Result delete(Long blogId) {
    Blogs blog = Blogs.find.byId(blogId);
    if(!currentUser().isAuthor(blog)) return redirect(routes.BlogsController.index(1));

    blog.delete();
    blog.deleteManyToManyAssociations("tags");

    return redirect(routes.BlogsController.index(1));
  }

    public static Result indexTagging(Long tagId) {
	Tags tag = Tags.find.byId(tagId);
	return ok(indexTagging.render(tag));
    }
}
