package tut0301.group1.healthz.dataaccess;
/** DataAccessInterface: crypto boundary. */
public interface PasswordHasher {
  String hash(String raw);
  boolean matches(String raw, String hash);
}
