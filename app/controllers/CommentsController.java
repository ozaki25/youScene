package controllers;

import java.util.List;
import java.util.ArrayList;

import play.*;
import play.mvc.*;
import play.data.Form;
import static play.data.Form.*;

import models.Comments;
import models.Blogs;
import models.Users;
import views.html.comments.*;

public class CommentsController extends YouScene {
  final static Form<Comments> commentForm = form(Comments.class);

  public static Result create(Long blogId) {
    Form<Comments> form = commentForm.bindFromRequest();
    if(form.hasErrors()) return badRequest();

    Comments comment = form.get();
    comment.blog = Blogs.find.byId(blogId);
    comment.user = currentUser();
    comment.save();

    return ok(show.render(comment));
  }

  public static Result delete(Long blogId, Long commentId) {
    Comments comment = Comments.find.byId(commentId);
    comment.delete();

    return ok();
  }
}
