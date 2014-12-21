package controllers;

import java.util.List;
import java.util.ArrayList;

import models.Contents;
import models.Likes;
import models.Users;
import models.Tags;
import play.*;
import static play.data.Form.*;
import play.data.Form;
import play.mvc.*;
import play.mvc.Security.Authenticated;
import views.html.*;

public class Blog extends Controller {
  final static Form<Contents> contentForm = form(Contents.class);
  final static Form<Tags> tagForm = form(Tags.class);

  @Authenticated(Authenticate.class)
  public static Result index(int page) {
    return ok(index.render("記事一覧", page));
  }

  @Authenticated(Authenticate.class)
  public static Result show(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    Users user = YouScene.loginUser();
    if(!user.isAuthor(content)) user.addAccess(content);

    return ok(show.render(content));
  }

  @Authenticated(Authenticate.class)
  public static Result create() {
      return ok(create.render("新規作成",contentForm,tagForm));
  }

  public static Result accept() {
    Form<Contents> form = contentForm.bindFromRequest();
    if(form.hasErrors()) return badRequest(create.render("新規作成",form,tagForm));

    Contents content = form.get();
    content.author = YouScene.loginUser();
    content.tags = Tags.findByTagNames(content.tagNames);
    content.save();
    content.saveManyToManyAssociations("tags");

    return redirect(routes.Blog.show(content.id));
  }

  @Authenticated(Authenticate.class)
  public static Result edit(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    if(!YouScene.loginUser().isAuthor(content)) return redirect(routes.Blog.index(1));

    return ok(edit.render("記事編集",content,contentForm.fill(content),tagForm));
  }

  public static Result update(Long contentId) {
    Form<Contents> form = contentForm.bindFromRequest();
    Contents content = Contents.find.byId(contentId);
    if(form.hasErrors()) return badRequest(edit.render("記事編集",content,form,tagForm));

    content = form.get();
    content.update(contentId);

    return redirect(routes.Blog.show(content.id));
  }

  public static Result delete(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    if(!YouScene.loginUser().isAuthor(content)) return redirect(routes.Blog.index(1));

    content.delete();

    return redirect(routes.Blog.index(1));
  }
}
