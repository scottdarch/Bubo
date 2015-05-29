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

import com.fiftythree.bubo.annotations.CopyOnWrite;
import com.fiftythree.bubo.annotations.Locking;
import com.fiftythree.bubo.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A registrar that is safe to use across threads both for (de)registration and observer iteration.
 */
@Locking
@CopyOnWrite
@ThreadSafe
public class ThreadSafeRegistrar<LISTENER_TYPE> implements Registrar<LISTENER_TYPE> {

    private final CopyOnWriteArrayList<LISTENER_TYPE> mRegistrar;

    public ThreadSafeRegistrar() {
        mRegistrar = new CopyOnWriteArrayList<LISTENER_TYPE>();
    }

    // +----------------------------------------------------------------------+
    // | Registrar
    // +----------------------------------------------------------------------+
    @Override
    public void addListener(LISTENER_TYPE listener) {
        if (null == listener) {
            throw new IllegalArgumentException("listener cannot be null.");
        }
        mRegistrar.addIfAbsent(listener);
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
