package controllers;

import play.*;
import models.Contents;
import play.mvc.*;
import views.html.accesses.*;

public class AccessesController extends Controller {
  public static Result accesses(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    if(!YouScene.loginUser().isAuthor(content)) return redirect(routes.Blog.index(1));

    return ok(accesses.render(content));
  }
}
