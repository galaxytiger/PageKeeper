/**
 * @author Anthony Torres
 * A class for representing User records such as username, password, and role
 * created: 4/13/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

public class User {
  private int userId;
  private String role;
  private String username;
  private String password;

  //constructor for existing record
  public User(int userId, String role, String username, String password) {
    this.userId = userId;
    this.role = role;
    this.username = username;
    this.password = password;
  }

  //constructor for new record
  public User(String role, String username, String password) {
    this.role = role;
    this.username = username;
    this.password = password;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
