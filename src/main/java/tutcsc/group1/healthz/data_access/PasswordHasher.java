package tutcsc.group1.healthz.data_access;
/** DataAccessInterface: crypto boundary. */
public interface PasswordHasher {
  String hash(String raw);
  boolean matches(String raw, String hash);
}
