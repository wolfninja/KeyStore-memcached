package com.wolfninja.keystore.memcached;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

import org.testng.annotations.Test;

import com.wolfninja.keystore.api.BaseKeyspaceTest;

@Test
public class MemcachedKeyspaceContractIntegrationTest extends BaseKeyspaceTest {

	public MemcachedKeyspaceContractIntegrationTest() throws IOException {
		super(new MemcachedAdapter(new MemcachedClient(new BinaryConnectionFactory(),
				AddrUtil.getAddresses("localhost:11211"))).getKeyspace("testkeyspace"));
	}
}
