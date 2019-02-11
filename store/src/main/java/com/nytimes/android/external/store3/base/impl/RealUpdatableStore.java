package com.nytimes.android.external.store3.base.impl;

import com.nytimes.android.external.store.util.Result;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.Single;

public class RealUpdatableStore<Parsed, Key> implements UpdatableStore<Parsed, Key> {

	private InternalUpdatableStore<Parsed, Key> internalStore;

	public RealUpdatableStore(InternalUpdatableStore<Parsed, Key> internalStore) {
		this.internalStore = internalStore;
	}

	@Nonnull
	@Override
	public Single<Boolean> update(@Nonnull Key key, @Nonnull Parsed value) {
		return this.internalStore.update(key, value);
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
}
