

package com.example.khaidemsandipsingha.bakingappudacity.IdleResource;

import android.support.annotation.Nullable;


import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import android.support.test.espresso.IdlingResource;

/**
 * A very simple implementation of {@link IdlingResource}.
 * <p>
 * Consider using CountingIdlingResource from espresso-contrib package if you use this class from
 * multiple threads or need to keep a count of pending operations.
 */

public class SimpleIdleResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback resourceCallback;

    // Idleness is controlled with this boolean.
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    /**
     * Sets the new idle state, if isIdleNow is true, it pings the {@link ResourceCallback}.
     * @param isIdleNow false if there are pending operations, true if idle.
     */
    public void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && resourceCallback != null) {
            Objects.requireNonNull(resourceCallback).onTransitionToIdle();
        }
    }
}