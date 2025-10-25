package tut0301.group1.healthz.dataaccess.inmemory;
import tut0301.group1.healthz.dataaccess.ClockGateway; import java.time.Instant;
/** System clock adapter. */
public class SystemClockGateway implements ClockGateway { @Override public Instant now(){ return Instant.now(); } }
