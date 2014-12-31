package controllers;

import play.*;
import play.mvc.*;

import models.Users;

public class YouScene extends Controller {
    public static Users currentUser() {
	return Users.findByUserId(session().get("username"));
    }
}
