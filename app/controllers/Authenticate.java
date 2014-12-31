package controllers;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

import views.html.*;

public class Authenticate extends Authenticator {
  @Override
  public Result onUnauthorized(Context ctx) {
    return redirect(routes.AuthController.login());
  }
}
