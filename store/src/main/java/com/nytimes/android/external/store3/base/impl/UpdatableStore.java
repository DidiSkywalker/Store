package com.nytimes.android.external.store3.base.impl;

import com.nytimes.android.external.store3.base.impl.Store;

import javax.annotation.Nonnull;

import io.reactivex.Single;

public interface UpdatableStore<Parsed, Key> extends Store<Parsed, Key> {

	@Nonnull
	Single<Boolean> update(@Nonnull Key key, @Nonnull Parsed value);

}
