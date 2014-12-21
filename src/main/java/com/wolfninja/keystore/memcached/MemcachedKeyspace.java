package com.wolfninja.keystore.memcached;

import java.util.concurrent.ExecutionException;

import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.wolfninja.keystore.api.KeyValue;
import com.wolfninja.keystore.api.Keyspace;

public class MemcachedKeyspace implements Keyspace {

	private String keyspaceName;
	private MemcachedClient memcachedClient;

	public MemcachedKeyspace(final String keyspaceName, final MemcachedClient memcachedClient) {
		Preconditions.checkNotNull(keyspaceName, "KeyspaceName must not be null");
		Preconditions.checkNotNull(memcachedClient, "MemcachedClient must not be null");

		this.keyspaceName = keyspaceName;
		this.memcachedClient = memcachedClient;
	}

	private String key(final String key) {
		return keyspaceName + ":" + key;
	}

	@Override
	public boolean add(final String key, final String value) {
		Preconditions.checkNotNull(key, "Key should be provided");
		Preconditions.checkNotNull(value, "Value should be provided");

		try {
			return memcachedClient.add(key(key), 0, value).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean checkAndSet(final String key, final String value, long version) {
		Preconditions.checkNotNull(key, "Key should be provided");
		Preconditions.checkNotNull(value, "Value should be provided");
		final CASResponse response = memcachedClient.cas(key(key), version, value);
		return response.equals(CASResponse.OK);
	}

	@Override
	public boolean delete(final String key) {
		Preconditions.checkNotNull(key, "Key should be provided");
		try {
			return memcachedClient.delete(key(key)).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean deletes(final String key, final long version) {
		Preconditions.checkNotNull(key, "Key should be provided");
		try {
			// XXX this requires the binary protocol to be used, ascii throws exception
			return memcachedClient.delete(key(key), version).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<String> get(final String key) {
		Preconditions.checkNotNull(key, "Key should be provided");

		final String value = (String) memcachedClient.get(key(key));
		return Optional.fromNullable(value);
	}

	@Override
	public Optional<KeyValue> gets(final String key) {
		Preconditions.checkNotNull(key, "Key should be provided");

		final String builtKey = key(key);
		final CASValue<Object> value = memcachedClient.gets(builtKey);
		final KeyValue keyValue = (value == null) ? null : KeyValue.create(key, value.getValue().toString(),
				value.getCas());
		return Optional.fromNullable(keyValue);
	}

	@Override
	public boolean exists(final String key) {
		Preconditions.checkNotNull(key, "Key should be provided");
		return memcachedClient.get(key(key)) != null;
	}

	@Override
	public boolean replace(final String key, final String value) {
		Preconditions.checkNotNull(key, "Key should be provided");
		Preconditions.checkNotNull(value, "Value should be provided");

		try {
			final CASValue<Object> existing = memcachedClient.gets(key(key));
			return memcachedClient.replace(key(key), 0, value).get() && !value.equals(existing.getValue());
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean set(final String key, final String value) {
		Preconditions.checkNotNull(key, "Key should be provided");
		Preconditions.checkNotNull(value, "Value should be provided");

		try {
			return memcachedClient.set(key(key), 0, value).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

}
