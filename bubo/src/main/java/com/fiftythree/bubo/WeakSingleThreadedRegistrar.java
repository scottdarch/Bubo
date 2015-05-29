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
import com.fiftythree.bubo.annotations.WeakRegistrar;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Registrar that holds only weak references to observers.
 */
@CopyOnWrite
@WeakRegistrar
public class WeakSingleThreadedRegistrar<LISTENER_TYPE> implements Registrar<LISTENER_TYPE> {

    private ArrayList<WeakReference<LISTENER_TYPE>> mRegistrar;
    private ReferenceQueue<LISTENER_TYPE> mListenerReferenceQueue;

    public WeakSingleThreadedRegistrar() {
        mRegistrar = new ArrayList<WeakReference<LISTENER_TYPE>>();
        mListenerReferenceQueue = new ReferenceQueue<LISTENER_TYPE>();
    }

    // +----------------------------------------------------------------------+
    // | Registrar
    // +----------------------------------------------------------------------+
    @Override
    public void addListener(LISTENER_TYPE listener) {
        if (null == listener) {
            throw new IllegalArgumentException("listener cannot be null.");
        }
        for (WeakReference<LISTENER_TYPE> reference : mRegistrar) {
            LISTENER_TYPE strongRef = reference.get();
            if (listener == strongRef) {
                return;
            } else if (strongRef == null) {
                reference.enqueue();
            }
        }
        mRegistrar = new ArrayList<WeakReference<LISTENER_TYPE>>(mRegistrar);
        mRegistrar.add(new WeakReference<LISTENER_TYPE>(listener, mListenerReferenceQueue));
        processQueue();
    }

    @Override
    public void removeListener(LISTENER_TYPE listener) {
        if (null == listener) {
            throw new IllegalArgumentException("listener cannot be null.");
        }
        LISTENER_TYPE theStrongRef = null;
        WeakReference<LISTENER_TYPE> theWeakRef = null;
        for (WeakReference<LISTENER_TYPE> reference : mRegistrar) {
            LISTENER_TYPE aStrongRef = reference.get();
            if (listener == aStrongRef) {
                theStrongRef = aStrongRef;
                theWeakRef = reference;
                break;
            } else if (aStrongRef == null) {
                reference.enqueue();
            }
        }
        if (null != theStrongRef) {
            mRegistrar = new ArrayList<WeakReference<LISTENER_TYPE>>(mRegistrar);
            mRegistrar.remove(theWeakRef);
        }
        processQueue();
    }

    @Override
    public void clear() {
        mRegistrar = new ArrayList<WeakReference<LISTENER_TYPE>>();
        mListenerReferenceQueue = new ReferenceQueue<LISTENER_TYPE>();
    }

    // +----------------------------------------------------------------------+
    // | Iterable
    // +----------------------------------------------------------------------+
    @Override
    public Iterator<LISTENER_TYPE> iterator() {
        return new WeakIterator<LISTENER_TYPE>(mRegistrar);
    }

    // +----------------------------------------------------------------------+
    // | PRIVATE
    // +----------------------------------------------------------------------+
    private static final class WeakIterator<T> implements Iterator<T> {

        private final List<WeakReference<T>> mWeakCollection;
        private int mIndex;
        private final int mCollectionLen;
        // Strong reference to the "next" item.
        private T mNextRef;

        WeakIterator(List<WeakReference<T> > weakCollection) {
            mWeakCollection = weakCollection;
            mCollectionLen = weakCollection.size();
            mIndex = 0;
            ensureStrongRef();
        }

        @Override
        public boolean hasNext() {
            return ensureStrongRef();
        }

        @Override
        public T next() {
            T strongRef = mNextRef;
            if (null == strongRef) {
                throw new NoSuchElementException();
            }
            mNextRef = null;
            ++mIndex;
            ensureStrongRef();
            return strongRef;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private boolean ensureStrongRef() {
            while(mIndex < mCollectionLen && null == mNextRef) {
                WeakReference<T> weakReference = mWeakCollection.get(mIndex);
                mNextRef = weakReference.get();
                if (null == mNextRef) {
                    weakReference.enqueue();
                    ++mIndex;
                }
            }
            return (mNextRef != null);
        }
    }

    private void processQueue() {
        Reference<? extends LISTENER_TYPE> expiredObserver;
        ArrayList<WeakReference<LISTENER_TYPE>> newList = null;
        while(null != (expiredObserver = mListenerReferenceQueue.poll())) {
            if (null == newList) {
                newList = new ArrayList<WeakReference<LISTENER_TYPE>>(mRegistrar);
                mRegistrar = newList;
            }
            newList.remove(expiredObserver);
        }
    }
}
