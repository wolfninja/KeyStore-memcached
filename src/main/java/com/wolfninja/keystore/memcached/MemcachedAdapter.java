package com.wolfninja.keystore.memcached;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.wolfninja.keystore.api.KeyValueStoreAdapter;
import com.wolfninja.keystore.api.Keyspace;

import net.spy.memcached.MemcachedClient;

/**
 * @since 0.1
 */
public class MemcachedAdapter implements KeyValueStoreAdapter {

	/**
	 * Create a new adapter instance, backed by the given client
	 * 
	 * @param client
	 *            {@link MemcachedClient} instance, not null
	 * @return new instance of {@link MemcachedAdapter}, not null
	 * @since 0.1
	 */
	@Nonnull
	public static MemcachedAdapter create(@Nonnull final MemcachedClient client) {
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
