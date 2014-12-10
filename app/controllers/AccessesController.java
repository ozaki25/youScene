package controllers;

import play.*;
import models.Contents;
import play.mvc.*;
import views.html.*;

public class AccessesController extends Controller {
  public static Result accesses(Long id) {
    Contents content = Contents.find.byId(id);
    if(!YouScene.loginUser().isAuthor(content)) return redirect(routes.Blog.index(1));

    return ok(accesses.render(content));
  }
}
