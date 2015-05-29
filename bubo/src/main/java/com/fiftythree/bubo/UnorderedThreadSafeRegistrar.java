/*
 *
 *                                ^ ^
 *                                O O
 *                              /    )
 *                             /  ,,
 * Bubo – Observable Contracts and Specialized Implementations.
 *                              |/
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

import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

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

    private final ConcurrentSkipListSet<LISTENER_TYPE> mRegistrar;

    public UnorderedThreadSafeRegistrar() {
        mRegistrar = new ConcurrentSkipListSet<LISTENER_TYPE>(new Comparator<LISTENER_TYPE>() {
            @Override
            public int compare(LISTENER_TYPE o1, LISTENER_TYPE o2) {
                // We don't actually care about the order. To obey the Observer Registration Key Rule
                // we must use object identity for equity. For ordering between instances we can use the
                // hashcode to obtain a stable if arbitrary ordering.
                if (o1 == o2) {
                    return 0;
                } else if (o1.hashCode() > o2.hashCode()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    // +----------------------------------------------------------------------+
    // | Registrar
    // +----------------------------------------------------------------------+
    @Override
    public void addListener(LISTENER_TYPE listener) {
        if (null == listener) {
            throw new IllegalArgumentException("listener cannot be null.");
        }
        mRegistrar.add(listener);
    }

    @Override
    public void removeListener(LISTENER_TYPE listener) {
        if (null == listener) {
            throw new IllegalArgumentException("listener cannot be null.");
        }
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
