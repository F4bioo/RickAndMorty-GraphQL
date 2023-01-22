# RickAndMorty-GraphQL
![Badge IN PROGRESS](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

This project was built with Android Studio Dolphin, with clean architecture in a multi-modules structure. The core module is the center and other modules branch off from it. The data module wraps remote, hubSrc, and local modules. Each module has its own interface and hubSrc module implements these interfaces. There's also a common and a domain module inside the core. The features module outside of the core uses the domain module and its usecases.

<img src="images/graph.svg"/>
<img src="images/core.png" width="50%"/>

## This project involves the use of:

* [Koin dependency injection](https://insert-koin.io/)

* [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)

* [MVVM - presentation pattern](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)

* [Clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

* [Builder pattern](https://refactoring.guru/design-patterns/builder)

* [StateFlow for View states](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#stateflow)

* [SharedFlow for View actions](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#sharedflow)

* [ListAdapter - Generic adapter](https://medium.com/@costa.fbo/reduce-boilerplate-code-with-a-single-listadapter-for-multiple-recyclerviews-c7d657917c66)

* [DiffUtil](https://developer.android.com/reference/androidx/recyclerview/widget/DiffUtil)

* [ViewBinding](https://developer.android.com/topic/libraries/view-binding)

* [Lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)

* [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences)

* [Apollo cliet](https://www.apollographql.com/docs/kotlin/v2/)


## Unit tests, integration tests and instrumentation tests with

* [Espresso](https://developer.android.com/training/testing/espresso)

* [Mockk](https://mockk.io/ANDROID.html)

* [Robot Pattern](https://jakewharton.com/testing-robots/)

## Quality

* [Detekt](https://detekt.dev/)

* [JaCoCo](https://en.wikipedia.org/wiki/Java_code_coverage_tools)

## Module dependencies

* [Modularization](https://developer.android.com/topic/modularization)

## Screens
<img src="images/1.jpg" width="30%"/> <img src="images/2.jpg" width="30%"/> <img src="images/3.jpg" width="30%"/>
<img src="images/4.jpg" width="30%"/> <img src="images/5.jpg" width="30%"/> <img src="images/6.jpg" width="30%"/>

## GIFS
<img src="images/1.gif" width="30%"/> <img src="images/2.gif" width="30%"/> <img src="images/3.gif" width="30%"/>
