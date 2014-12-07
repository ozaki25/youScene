package models;

import play.data.validation.Constraints.Required;

public class Login {
  @Required
  public String userId;
  public String password;
}
