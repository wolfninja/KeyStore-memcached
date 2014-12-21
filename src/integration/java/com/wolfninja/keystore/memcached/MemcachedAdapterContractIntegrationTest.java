package com.wolfninja.keystore.memcached;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

import org.testng.annotations.Test;

import com.wolfninja.keystore.api.BaseKeyValueStoreAdapterTest;

@Test
public class MemcachedAdapterContractIntegrationTest extends BaseKeyValueStoreAdapterTest {

	public MemcachedAdapterContractIntegrationTest() throws IOException {
		super(new MemcachedAdapter(new MemcachedClient(new BinaryConnectionFactory(),
				AddrUtil.getAddresses("localhost:11211"))));
	}
}
