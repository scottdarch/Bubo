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
package com.fiftythree.bubo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can be used by objects to mark themselves as adhering to the Bubo Observable
 * contract. In general any object that delegates all observer/listener registration
 * and notification to a {@link com.fiftythree.bubo.Registrar} automatically adheres
 * to this contract.
 *
 * <h2>The Bubo Observable Contract</h2>
 * <p>Bubo compliant observables can publish update notifications of any kind. This contract
 * governs only the behaviour of the observable when broadcasting updates to a list of
 * observers. This process involves two types of operations:
 * <ol>
 *     <li>Observer/Listener registration</li>
 *     <li>Observable notifications</li>
 * </ol>
 * <blockquote>Note that within Bubo the terms Listener and Observer are synonymous. In practice the former
 * is a more specialized version of the latter but since Bubo only deals with the Observable's notification
 * dispatch behaviour it has no need to distinguish two concepts.</blockquote>
 * </p>
 * <h3>Observer/Listener Registration</h3>
 * <p>The following registration behaviours must be observed by Bubo compliant Observables:
 * <ol>
 *     <li><strong>Observer Registration Key Rule – </strong>
 *     Observer registration is keyed by the observer's object reference. Direct object
 *     comparison is used to identify registered observers.</li>
 *     <li><strong>Observer Registration Idempotentence Rule – </strong>
 *     Observer registration is idempotent. If an observer registers 1 + n times it will
 *     be registered 1 time with the observable.</li>
 *     <li><strong>Observer De-Registration Infallibility Rule – </strong>
 *     Observer de-registration never fails (see atomic dispatch rule below for caveat).
 *     If an observer was not registered de-registration has no effect. There is no valid condition where
 *     de-registering an observer would fail to to remove it from the observable's registrar. This includes
 *     de-registration from within an observable notification callstack.</li>
 *     <li><strong>Observer Reachability Rule – </strong>An Observable <em>must not</em> extend
 *     observer reachability beyond its own rechability graph as the result of observer/listener
 *     registration. For example: if an observer provided only 1 strong reference to another object, the
 *     observer via its addListener method, and the Observable became unreachable then the observer must also
 *     become unreachable.</li>
 * </ol>
 * </p>
 * <h3>Observable Registration Protocol</h3>
 * <p>Bubo does not specify the object interface for an Observable but all valid Observables must
 * support the following:
 * <ol>
 *     <li><strong>Registration Method Rule– </strong>1 or more methods that register an observer/listener.</li>
 *     <li><strong>De-Registration Method Rule - </strong>1 or more methods that de-register an observer/listener.</li>
 * </ol>
 * </p>
 * <h3>Observable Notifications</h3>
 * <p>The following notification/event-dispatch behaviours must be observed by Bubo compliant Observables:
 * <ol>
 *     <li><strong>Atomic Dispatch Rule – </strong>Once an observable has initiated a notification dispatch
 *     all observers registered at the start of this dispatch will be notified regardless of changes which
 *     occur to the observer either concurrently or from within the notification callstack. This includes
 *     observer de-registration.
 *     <blockquote>For example; if objects x and y are registered with an observer and object
 *     x deregisters object y while processing the callback <em>object y will still be notified.</em>
 *     For subsequent notifications object y will no longer be called. Note that this rule implies
 *     that object references may live longer then their registrations but does not constitute an exception
 *     to the Observer Reachability Rule.</blockquote></li>
 *     <li><strong>Ordered By Default Rule – </strong>Unless an Observable is annotated with {@link Unordered}
 *     observers/listeners will be notified in the order in which they registered.</li>
 * </ol>
 * </p>
 * <h3>Observable Concurrency</h3>
 * <p>The following concurrency characteristics must be implemented for Bubo compliant Observables:
 * <ol>
 *     <li><strong>Default Thread Safety Rule – </strong> Unless annotated with {@link ThreadSafe} an
 *     Observable should not be considered thread-safe.</li>
 *     <li><strong>Apartment Threaded Dispatch Rule – </strong> Observables will only dispatch a notification
 *     on one thread at a time. There is not guarantee for observers that the thread stack will be retained
 *     between notifications.</li>
 *     <li><strong>Concurrent Notification Rule – </strong> At this time Bubo does not guarantee any ordering,
 *     parallelism, or serialization between notifications from an observer. Future revisions may add
 *     contractual annotations for this behaviour.</li>
 * </ol>
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Observable {
}
