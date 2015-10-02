# Bubo ![Bubo Scandiacus (Snowy Owl: Photo, Attribution CC 2.0, http://en.wikipedia.org/wiki/Snowy_owl)](Bubo-eyes.png)
A small library implementing best practices when using the Observer Pattern with plain ol' Java objects (POJOs).

[ ![Download](https://api.bintray.com/packages/fiftythree/FiftyThreeCenter/bubo/images/download.svg) ](https://bintray.com/fiftythree/FiftyThreeCenter/bubo/_latestVersion)

## Background

[The Observer Pattern](https://en.wikipedia.org/wiki/Observer_pattern) is ubiquitous in Java (e.g. [ActionListener](http://docs.oracle.com/javase/7/docs/api/java/awt/event/ActionListener.html) in AWT or Android's [View.OnClickListener](http://developer.android.com/reference/android/view/View.OnClickListener.html)) and is often implemented by Java developers in their own classes without much thought. Typically a listener pattern is established using just a simple ArrayList.

```
public class MySubject {

    public interface MySubjectListener {
        void onSomeEvent();
    }

    private final List<MySubjectListener> mListeners = new ArrayList<>();

    public void addListener(MySubjectListener listener) {
        mListener.add(listener);
    }

    public void removeListener(MySubjectListener listener) {
        mListener.remove(listener);
    }

}
```

For the most part this simple approach is adequate but there are subtle issues that can emerge as a program grows in complexity. These complexities often involve the otherwise encapsulated implementation of the add/remove listener methods and can lead to observers coding to these internals without any way to track the stability of the implementation.

> Note that within Bubo the terms Listener and Observer are synonymous. In practice the former is a more specialized version of the latter but since Bubo only deals with the Observable's notification dispatch behaviour it has no need to distinguish the two concepts.

Bubo provides a set of annotations, interfaces, and common implementations that help define and communicate observer patterns for your objects. For example, the previous example might look like this using Bubo:

```

@Observable(MySubjectListener.class)
public class MySubject {

    public interface MySubjectListener {
        void onSomeEvent();
    }

    private final Registrar<MySubjectListener> mListeners = new SingleThreadedRegistrar<>();

    public void addListener(MySubjectListener listener) {
        mListener.add(listener);
    }

    public void removeListener(MySubjectListener listener) {
        mListener.remove(listener);
    }

}
```

These two subtle changes provide the following benefits:

1. The documentation  of the [Observable](https://github.com/FiftyThree/Bubo/blob/master/bubo/src/main/java/com/fiftythree/bubo/annotations/Observable.java) annotation provides a clear specification of the behaviours this object will adhere to.
2. Adherence to said specification is provided as part of the SingleThreadedRegistrar.
3. Declaration of the listener type in the `Observable` indicates what subjects are available at the class definition (It is even conceivable that registration could be performed using reflection and proxy listeners).

To see what registrar implementations are available take a look at the [com.fiftythree.bubo package on Github](https://github.com/FiftyThree/Bubo/tree/master/bubo/src/main/java/com/fiftythree/bubo). These provide an iterable object that implement the contract detailed by the [Observable](https://github.com/FiftyThree/Bubo/blob/master/bubo/src/main/java/com/fiftythree/bubo/annotations/Observable.java) annotation for various scenarios (e.g. Thread safe, weak listener references, etc).

## License

Bubo is provided under the [Apache 2 Licence](http://www.apache.org/licenses/). It has no other software dependencies.

## Contributing

Please see the [CONTRIBUTING.md](https://github.com/FiftyThree/Bubo/blob/master/CONTRIBUTING.md) document for information on contributing fixes or additions to the Bubo project.

## Support

Bubo is provided without warranty or support. That said, feel free to open [GitHub issues](https://github.com/FiftyThree/Bubo/issues) and we'll try to address the problems as we have time.


![FiftyThree Logo](53-circular.png)
