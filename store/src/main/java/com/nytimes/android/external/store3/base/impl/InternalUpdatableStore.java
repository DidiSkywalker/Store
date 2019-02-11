package com.nytimes.android.external.store3.base.impl;

import javax.annotation.Nonnull;

import io.reactivex.Maybe;

public interface InternalUpdatableStore<Parsed, Key> extends UpdatableStore<Parsed, Key> {

	@Nonnull
	Maybe<Parsed> memory(@Nonnull final Key key);

	@Nonnull
	Maybe<Parsed> disk(@Nonnull final Key key);

}
