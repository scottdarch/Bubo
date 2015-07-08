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

/**
 * Helper interface for Observable objects. Use this interface when defining observable interfaces or abstract
 * types that support only 1 observer type.
 * @param <T>   The listener type supported by this object.
 */
public interface Observable<T> {

    /**
     * Add an external object to observe this one. See {@link com.fiftythree.bubo.annotations.Observable} for
     * detailed documentation on the Observer contract. Also see any other Bubo annotations on the class
     * implementing this interface for modifications and qualifications to the core Observable contract.
     *
     * @param listener The listener/observer to register with this object. References held will be strong unless
     *                 the object is also annotated with {@link com.fiftythree.bubo.annotations.WeakRegistrar}.
     */
    void addListener(T listener);

    /**
     * Remove an object previously added via {@link #addListener(Object)}. See {@link com.fiftythree.bubo.annotations.Observable}
     * for detailed documentation on the Observer contract.
     *
     * @param listener The listener/observer to deregister from this object. Note that the Observable contract
     *                 does allow for this listener to receive 1 more callback if this method is called while
     *                 a notification is in progress.
     */
    void removeListener(T listener);

}
