package com.g3.tktsvc.util;

public class TicketServiceIdGenerator {
	  static private long id = System.currentTimeMillis();
	  
	  private TicketServiceIdGenerator() {}
	  
	  static public synchronized int generateId() {
		  
		  return (int) (id++ % Integer.MAX_VALUE);
	  }
}
