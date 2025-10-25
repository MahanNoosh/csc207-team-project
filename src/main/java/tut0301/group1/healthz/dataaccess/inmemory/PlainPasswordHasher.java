package tut0301.group1.healthz.dataaccess.inmemory;
import tut0301.group1.healthz.dataaccess.PasswordHasher;
/** Demo-only hasher: NOT SECURE. Replace with BCrypt adapter in real app. */
public class PlainPasswordHasher implements PasswordHasher {
  @Override public String hash(String raw){ return "HASH:" + raw; }
  @Override public boolean matches(String raw, String hash){ return hash.equals(hash(raw)); }
}
