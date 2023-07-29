Interview Test App
======================


## Prerequisites
In order to run this project you need the following:
- Android Studio 4.1.0 or better
- Gradle 6.5 or better
- JDK 1.8
- [Android SDK](https://developer.android.com/studio/index.html)



# Architecture
The project implemented with
[MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)
and [The Clean Architecture](https://developer.foursquare.com/docs/places-api/getting-started/])


## Data
We have repository and data source implementations.
Creating different models for data layers cause lots of boilerplate but we can develop tools to
generate that automatically. If we don't have time we could use entity models. In my experience
importing some android related SDKs to domain layer cause other careless import and make testing hard.
We could also control it with lints and merge request templates for review.


### Refactor Strategy:
We can set some guidelines in the beginning.
So later if we develop a tool for that we would migrate easily and without change to domain layer entities



## Ui Modules
With this strategy we reduce build time in long term and in CI/CD.
We avoid unwanted access to other features class and we can review more effectively. and later if we want have different apps.


# Technologies And Decisions
In this section I'll try to explain reason behind some of my decisions

## Hilt
We Use hilt because of less boilerplate codes. Also, generally I think it's easier for integration tests as well.

**Create Hilt modules in the related gradle modules:**
This scenario could help us, if we want to reuse our gradle modules in different apps or
if we don't want to mock them in some integration tests.

**Create Hilt modules in the app module:**
This scenario is easier to test for component testing but it needs more boilerplate codes.


## Coroutine
For the threading and observing I could use technologies like Livedata with Thread pools, Rx and Coroutine.

It's hard to handle things like back pressure
or debounce with Livedata and ThreadPools.

On the other hand, Coroutine is lighter than Rx. It's native and somehow
it's easier to test because Google has created some libraries for that.


## Test
I wrote tests for the useCases, repository, dataSource and viewModels in different layer using Mock and JUnit.


## UX
I keep UI minimal and tried to show I know how to use things like constraint layout, styles, dimens drawables, etc, but I know from the user side it's not good at all.

I created one activity for the host and another screen implemented as a fragment
for navigating between screens I used the navigation library of android jetpack
