package tut0301.group1.healthz.dataaccess;
import java.time.Instant;
/** DataAccessInterface: time source. */
public interface ClockGateway { Instant now(); }
