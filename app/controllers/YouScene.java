package controllers;

import static play.data.Form.form;
import models.Login;
import models.Users;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class YouScene extends Controller {
  final static Form<Users> signupForm = form(Users.class);
  final static Form<Login> loginForm = form(Login.class);

  public static Result entry() {
    return ok(signup.render("新規登録",signupForm));
  }

  public static Result signup() {
    Form<Users> form = signupForm.bindFromRequest();
    if(form.hasErrors()) return badRequest(signup.render("新規登録",form));

    Users user = form.get();
    user.save();

    return redirect(routes.Blog.index());
  }

  public static Result login() {
    return ok(login.render("ログイン",loginForm));
  }

  public static Result authenticate() {
    Form<Login> form = loginForm.bindFromRequest();
    if(form.hasErrors()) return badRequest(login.render("ログイン",form));

    Users user = Users.findByUserId(form.get().userId);
    if(user == null) {
      form.reject("userId", "Incorrect username or password.");
      return badRequest(login.render("ログイン",form));
    }

    session().put("username",user.userId);
    session().put("name",user.name);
    session().put("sectionName",user.sectionName);

    return redirect(routes.Blog.index());
  }

  public static Result logout() {
    session().clear();
    return redirect(routes.YouScene.login());
  }

  public static Users loginUser() {
    return Users.findByUserId(session().get("username"));
  }
}
