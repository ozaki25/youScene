package controllers;

import models.Contents;
import models.Users;
import play.*;
import static play.data.Form.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Blog extends Controller {
  final static Form<Contents> contentForm = form(Contents.class);
  public static Result index() {
    return ok(index.render("記事一覧", Contents.find.all()));
  }

  public static Result create() {
    //Users.setUser();
    return ok(create.render("新規作成",contentForm));
  }

  public static Result accept() {
    Form<Contents> form = contentForm.bindFromRequest();
    if(form.hasErrors()) return badRequest(create.render("新規作成",form));

    Contents content = new Contents();
    content = form.get();
    content.user = loginUser();
    content.save();

    return redirect("/");
  }

  public static Users loginUser() {
    return Users.find.all().get(0);
  }
}
