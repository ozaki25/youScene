package controllers;

import play.*;
import play.mvc.*;

import models.Blogs;
import models.Users;
import views.html.likes.*;

public class LikesController extends Controller {
  public static Result index(Long blogId) {
    Blogs blog = Blogs.find.byId(blogId);
    if(!YouScene.loginUser().isAuthor(blog)) return redirect(routes.BlogsController.index(1));

    return ok(index.render(blog));
  }

  public static Result create(Long blogId) {
    Blogs blog = Blogs.find.byId(blogId);
    Users user = YouScene.loginUser();
    user.addLike(blog);

    return redirect(routes.BlogsController.show(blogId));
  }
}
