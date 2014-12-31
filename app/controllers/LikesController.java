package controllers;

import play.*;
import play.mvc.*;

import models.Blogs;
import models.Users;
import views.html.likes.*;

public class LikesController extends YouScene {
  public static Result index(Long blogId) {
    Blogs blog = Blogs.find.byId(blogId);
    if(!currentUser().isAuthor(blog)) return redirect(routes.BlogsController.index(1));

    return ok(index.render(blog));
  }

  public static Result create(Long blogId) {
    Blogs blog = Blogs.find.byId(blogId);
    Users user = currentUser();
    user.addLike(blog);

    return redirect(routes.BlogsController.show(blogId));
  }
}
