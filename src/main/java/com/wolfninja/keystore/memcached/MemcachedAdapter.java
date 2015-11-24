package com.wolfninja.keystore.memcached;

import java.util.Objects;

import com.wolfninja.keystore.api.KeyValueStoreAdapter;
import com.wolfninja.keystore.api.Keyspace;

import net.spy.memcached.MemcachedClient;

public class MemcachedAdapter implements KeyValueStoreAdapter {

	public static MemcachedAdapter create(final MemcachedClient client) {
		return new MemcachedAdapter(client);
	}

	private final MemcachedClient memcachedClient;

	protected MemcachedAdapter(final MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Override
	public Keyspace getKeyspace(final String keyspaceName) {
		Objects.requireNonNull(keyspaceName, "KeyspaceName must not be null");
		return new MemcachedKeyspace(keyspaceName, memcachedClient);
	}

}
