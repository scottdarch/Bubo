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
 * Common contract for all Bubo observable implementations.
 *
 * <p>See {@link com.fiftythree.bubo.annotations.Observable} for extensive details on how
 * Bubo Observables should behave. Registrar objects must also adhere to this contract.</p>
 */
public interface Registrar<LISTENER_TYPE> extends Iterable<LISTENER_TYPE> {

    /**
     * Add a listener/observer registration to this object. See the Registration Method Rule,
     * the Observer Registration Key Rule, and the Observer Registration Idempotentence Rule in
     * the {@link Observable} contract.
     *
     * @param  listener The listener object to register. Must not be null.
     * @throws IllegalArgumentException if listener is null.
     */
    void addListener(LISTENER_TYPE listener);

    /**
     * Remove a listener/observer registration from this object. See the De-Registration Method Rule,
     * the Observer Registration Key Rule, and the Observer De-Registration Infallibility Rule in
     * the {@link Observable} contract.
     *
     * @param  listener The listener object to de-register. Must not be null.
     * @throws IllegalArgumentException if listener is null.
     */
    void removeListener(LISTENER_TYPE listener);

    /**
     * Removes all listener/observer registration from this object. This method is provided to help
     * Observables adhere to the Observer Reachability Rule.
     */
    void clear();

}
