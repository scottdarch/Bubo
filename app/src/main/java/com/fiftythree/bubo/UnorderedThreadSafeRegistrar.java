/*
 *                                                           ^ ^
 *                                                           O O
 *                                                         /    )
 *                                                        /  ,,
 *                                                       /
 *
 * Bubo Observer Library for Android
 *
 * Copyright 2015 FiftyThree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fiftythree.bubo;

import com.fiftythree.bubo.annotations.ThreadSafe;
import com.fiftythree.bubo.annotations.Unordered;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A registrar that is safe to use across threads both for (de)registration and observer iteration. This
 * variant of a thread-safe registrar does not provide any iteration ordering guarantee. By discarding this
 * requirement the registrar can use a lock-free collection eliminating the overhead of synchronization. Prefer
 * this implementation on multi-core devices (and what isn't these days) when you don't need a consistent ordering
 * of observers while iterating on the registrar.
 */
@ThreadSafe
@Unordered
public class UnorderedThreadSafeRegistrar<LISTENER_TYPE> implements Registrar<LISTENER_TYPE> {

    private final ConcurrentLinkedQueue<LISTENER_TYPE> mRegistrar;

    public UnorderedThreadSafeRegistrar() {
        mRegistrar = new ConcurrentLinkedQueue<>();
    }

    // +----------------------------------------------------------------------+
    // | Registrar
    // +----------------------------------------------------------------------+
    @Override
    public void addListener(LISTENER_TYPE listener) {
        if (!mRegistrar.contains(listener)) {
            mRegistrar.add(listener);
        }
    }

    @Override
    public void removeListener(LISTENER_TYPE listener) {
        mRegistrar.remove(listener);
    }

    @Override
    public void clear() {
        mRegistrar.clear();
    }

    // +----------------------------------------------------------------------+
    // | Iterable
    // +----------------------------------------------------------------------+
    @Override
    public Iterator<LISTENER_TYPE> iterator() {
        return mRegistrar.iterator();
    }

}
