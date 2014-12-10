package controllers;

import java.util.List;

import models.Contents;
import models.Likes;
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
  public static Result index(int page) {
    List<Contents> contents = Contents.findPagingList(page);
    return ok(index.render("記事一覧", contents, page));
  }

  @Authenticated(Authenticate.class)
  public static Result show(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    Users user = YouScene.loginUser();
    if(!user.isAuthor(content)) content.addAccess(user);

    List<Contents> latestContents = Contents.findLatestContents(); 
    return ok(show.render(content,latestContents));
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

    return redirect(routes.Blog.index(1));
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

    return redirect(routes.Blog.index(1));
  }

  public static Result delete(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    content.delete();

    return redirect(routes.Blog.index(1));
  }

  public static Result like(Long contentId) {
    Contents content = Contents.find.byId(contentId);
    Users user = YouScene.loginUser();
    content.addLike(user);

    return redirect(routes.Blog.show(contentId));
  }
}
