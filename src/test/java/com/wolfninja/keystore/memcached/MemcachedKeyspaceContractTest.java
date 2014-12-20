package com.wolfninja.keystore.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

import org.testng.annotations.Test;

import com.wolfninja.keystore.api.BaseKeyspaceTest;

@Test
public class MemcachedKeyspaceContractTest extends BaseKeyspaceTest {

	public MemcachedKeyspaceContractTest() throws IOException {
		super(new MemcachedAdapter(new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211)))
				.getKeyspace("testkeyspace"));
	}
}
