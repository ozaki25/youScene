package controllers;

import play.*;
import models.Contents;
import models.Users;
import play.mvc.*;
import views.html.likes.*;

public class LikesController extends Controller {
  public static Result likes(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    if(!YouScene.loginUser().isAuthor(content)) return redirect(routes.Blog.index(1));

    return ok(likes.render(content));
  }

  public static Result like(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    Users user = YouScene.loginUser();
    user.addLike(content);

    return redirect(routes.Blog.show(contentId));
  }
}
