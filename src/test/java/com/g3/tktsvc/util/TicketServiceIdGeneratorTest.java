package com.g3.tktsvc.util;

import junit.framework.TestCase;

public class TicketServiceIdGeneratorTest extends TestCase {

	public TicketServiceIdGeneratorTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGenerateId() {
		int uniqueId = TicketServiceIdGenerator.generateId();
		assertTrue(uniqueId > 0);
	}

}
