package controllers;

import java.util.List;

import play.*;
import play.mvc.*;
import play.data.Form;
import static play.data.Form.*;
import play.libs.Json;

import models.Tags;
import views.html.tags.*;

public class TagsController extends YouScene {
    public static Result index() {
	List<Tags> tags = Tags.find.all();
	return ok(index.render("タグ一覧", tags));
    }

    public static Result create() {
	String tagName = form().bindFromRequest().get("tagName");
	if(tagName.isEmpty()) return badRequest();

	Tags tag = new Tags(tagName);
	if(tag.isExist()) return ok();

	tag.save();
	return ok();
    }

    public static Result list(String term) {
	List<Tags> tags = Tags.findByTagNameLike(term);
	return ok(Json.toJson(tags));
    }
}
