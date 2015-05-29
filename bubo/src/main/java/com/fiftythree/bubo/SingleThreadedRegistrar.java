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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A registrar that can only be created and used from a single thread but which optimizes for fast
 * iteration.
 */
@CopyOnWrite
public class SingleThreadedRegistrar<LISTENER_TYPE> implements Registrar<LISTENER_TYPE> {

    private ArrayList<LISTENER_TYPE> mRegistrar;

    public SingleThreadedRegistrar() {
        mRegistrar = new ArrayList<LISTENER_TYPE>();
    }

    // +----------------------------------------------------------------------+
    // | Registrar
    // +----------------------------------------------------------------------+
    @Override
    public void addListener(LISTENER_TYPE listener) {
        if (null == listener) {
            throw new IllegalArgumentException("listener cannot be null.");
        }
        if (!mRegistrar.contains(listener)) {
            mRegistrar = new ArrayList<LISTENER_TYPE>(mRegistrar);
            mRegistrar.add(listener);
        }
    }

    @Override
    public void removeListener(LISTENER_TYPE listener) {
        if (null == listener) {
            throw new IllegalArgumentException("listener cannot be null.");
        }
        if (mRegistrar.contains(listener)) {
            mRegistrar = new ArrayList<LISTENER_TYPE>(mRegistrar);
            mRegistrar.remove(listener);
        }
    }

    @Override
    public void clear() {
        mRegistrar = new ArrayList<LISTENER_TYPE>();
    }

    // +----------------------------------------------------------------------+
    // | Iterable
    // +----------------------------------------------------------------------+
    @Override
    public Iterator<LISTENER_TYPE> iterator() {
        return mRegistrar.iterator();
    }

}
