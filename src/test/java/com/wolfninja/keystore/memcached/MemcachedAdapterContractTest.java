package com.wolfninja.keystore.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.testng.annotations.Test;

import net.spy.memcached.MemcachedClient;

import com.wolfninja.keystore.api.BaseKeyValueStoreAdapterTest;

@Test
public class MemcachedAdapterContractTest extends BaseKeyValueStoreAdapterTest {

	public MemcachedAdapterContractTest() throws IOException {
		super(new MemcachedAdapter(new MemcachedClient(new InetSocketAddress("localhost", 11211))));
	}
}
