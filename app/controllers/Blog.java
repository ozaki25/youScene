package controllers;

import models.Contents;
import models.Users;
import play.*;
import static play.data.Form.*;
import play.data.Form;
import play.mvc.*;
import play.mvc.Security.Authenticated;
import views.html.*;

public class Blog extends Controller {
  final static Form<Contents> contentForm = form(Contents.class);

  @Authenticated(Authenticate.class)
  public static Result index() {
    return ok(index.render("記事一覧", Contents.find.all()));
  }

  @Authenticated(Authenticate.class)
  public static Result show(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    Users user = YouScene.loginUser();
    if(user != content.author) content.addAccess(user);

    return ok(show.render(content));
  }

  @Authenticated(Authenticate.class)
  public static Result create() {
    return ok(create.render("新規作成",contentForm));
  }

  public static Result accept() {
    Form<Contents> form = contentForm.bindFromRequest();
    if(form.hasErrors()) return badRequest(create.render("新規作成",form));

    Contents content = form.get();
    content.author = YouScene.loginUser();
    content.save();

    return redirect(routes.Blog.index());
  }

  @Authenticated(Authenticate.class)
  public static Result edit(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    return ok(edit.render("記事編集",contentForm.fill(content),content));
  }

  public static Result update(Long contentId) {
    Form<Contents> form = contentForm.bindFromRequest();
    if(form.hasErrors()) return badRequest(create.render("記事編集",form));

    Contents content = Contents.find.byId(contentId);
    content = form.get();
    content.update(contentId);

    return redirect(routes.Blog.index());
  }

  public static Result delete(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    content.delete();

    return redirect(routes.Blog.index());
  }
}
