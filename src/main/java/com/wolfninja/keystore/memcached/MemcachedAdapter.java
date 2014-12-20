package com.wolfninja.keystore.memcached;

import net.spy.memcached.MemcachedClient;

import com.google.common.base.Preconditions;
import com.wolfninja.keystore.api.KeyValueStoreAdapter;
import com.wolfninja.keystore.api.Keyspace;

public class MemcachedAdapter implements KeyValueStoreAdapter {

	private final MemcachedClient memcachedClient;

	protected MemcachedAdapter(final MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Override
	public Keyspace getKeyspace(final String keyspaceName) {
		Preconditions.checkNotNull(keyspaceName, "KeyspaceName must not be null");
		return new MemcachedKeyspace(keyspaceName, memcachedClient);
	}

}
