package controllers;

import play.*;
import play.mvc.*;
import models.Tags;
import views.html.*;
import static play.data.Form.*;
import play.data.Form;

public class TagsController extends Controller {
    public static Result create() {
	String tagName = form().bindFromRequest().get("tagName");
	if(tagName.isEmpty()) return badRequest();

	Tags tag = new Tags(tagName);
	if(tag.isExist()) return ok();

	tag.save();
	return ok();
    }

    public static Result tagsContents(Long tagId) {
	Tags tag = Tags.find.byId(tagId);
	return ok(tagsContents.render(tag));
    }
}
