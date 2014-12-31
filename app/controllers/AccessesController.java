package controllers;

import play.*;
import play.mvc.*;

import models.Blogs;
import views.html.accesses.*;

public class AccessesController extends Controller {
  public static Result index(Long blogId) {
    Blogs blog = Blogs.find.byId(blogId);
    if(!YouScene.loginUser().isAuthor(blog)) return redirect(routes.BlogsController.index(1));

    return ok(index.render(blog));
  }
}
