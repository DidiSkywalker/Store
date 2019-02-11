package com.nytimes.android.external.store3.base.impl;

import com.nytimes.android.external.store.util.Result;
import com.nytimes.android.external.store3.base.Fetcher;
import com.nytimes.android.external.store3.base.Persister;
import com.nytimes.android.external.store3.util.KeyParser;

import javax.annotation.Nonnull;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class RealInternalUpdatableStore<Raw, Parsed, Key> implements InternalUpdatableStore<Parsed, Key> {

	private RealInternalStore<Raw, Parsed, Key> internalStore;
	private KeyParser<Key, Parsed, Raw> deparser;

	public RealInternalUpdatableStore(Fetcher<Raw, Key> fetcher,
									  Persister<Raw, Key> persister,
									  KeyParser<Key, Raw, Parsed> parser,
									  KeyParser<Key, Parsed, Raw> deparser,
									  StalePolicy stalePolicy) {
		this(fetcher, persister, parser, deparser, null, stalePolicy);
	}

	public RealInternalUpdatableStore(Fetcher<Raw, Key> fetcher,
									  Persister<Raw, Key> persister,
									  KeyParser<Key, Raw, Parsed> parser,
									  KeyParser<Key, Parsed, Raw> deparser,
									  MemoryPolicy memoryPolicy,
									  StalePolicy stalePolicy) {
		this.internalStore = new RealInternalStore<>(fetcher, persister, parser, memoryPolicy, stalePolicy);
		this.deparser = deparser;
	}


	@Nonnull
	@Override
	public Single<Boolean> update(@Nonnull Key key, @Nonnull Parsed value) {
		this.internalStore.memCache.put(key, Maybe.create(emitter -> emitter.onSuccess(value)));
		return this.internalStore.persister().write(key, deparser.apply(key, value));
	}

	@Nonnull
	@Override
	public Single<Parsed> get(@Nonnull Key key) {
		return this.internalStore.get(key);
	}

	@Nonnull
	@Override
	public Single<Result<Parsed>> getWithResult(@Nonnull Key key) {
		return this.internalStore.getWithResult(key);
	}

	@Override
	public Observable<Parsed> getRefreshing(@Nonnull Key key) {
		return this.internalStore.getRefreshing(key);
	}

	@Nonnull
	@Override
	public Single<Parsed> fetch(@Nonnull Key key) {
		return this.internalStore.fetch(key);
	}

	@Nonnull
	@Override
	public Single<Result<Parsed>> fetchWithResult(@Nonnull Key key) {
		return this.internalStore.fetchWithResult(key);
	}

	@Nonnull
	@Override
	public Observable<Parsed> stream() {
		return this.internalStore.stream();
	}

	@Nonnull
	@Override
	public Observable<Parsed> stream(Key key) {
		return this.internalStore.stream(key);
	}

	@Override
	public void clearMemory() {
		clear();
	}

	@Override
	public void clearMemory(@Nonnull Key key) {
		clear(key);
	}

	@Override
	public void clear() {
		this.internalStore.clear();
	}

	@Override
	public void clear(@Nonnull Key key) {
		this.internalStore.clear(key);
	}

	@Nonnull
	@Override
	public Maybe<Parsed> memory(@Nonnull Key key) {
		return this.internalStore.memory(key);
	}

	@Nonnull
	@Override
	public Maybe<Parsed> disk(@Nonnull Key key) {
		return this.internalStore.disk(key);
	}
}
