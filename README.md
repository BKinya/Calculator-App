## Calculator-App
Calculator app to attempt different ways to handle SingleLiveEvents in Android

# About
The app takes two numbers and calculates the sum. When the sum is greater than 100, the app shows a SnackBar.
Images

## Single Live events
Side effects / ViewModel events(according to Google) etc.

### Requirements
- New events cannot overwrite unobserved events.
- If there's no observer, events must buffer until an observer starts to consume them.
- The view may have an important lifecycle state during which it can observe the events safely. Therefore, the observer may not always be active or consuming the flow at a given moment in time.
- Events must be observed once and only once

### Approaches
#### Using Kotlin Channel
Kotlin channel is received as a flow to emit events that an observer may receive during appropriate lifecycle states.
I prefer this option. I implemented it in [this branch](https://github.com/BKinya/Calculator-App/tree/Implement-single-live-event-with-channels).

The approach has drawbacks. There is no guarantee on delivery and processing of events. See this [issue here](https://github.com/Kotlin/kotlinx.coroutines/issues/2886).
To ensure delivery and processing use `Dispatchers.Main.immediate` as described in this [comment](https://github.com/Kotlin/kotlinx.coroutines/issues/2886#issuecomment-901188295).

One thing to note: One may forget to send events from `Dispatchers.Main.immediate` making this approach error prone. To mitigate, enforce the rule in lint scripts. And you'll be good to go!
#### Sending ViewModel events to UI - Google recommendation
According to Google [guidelines here](https://developer.android.com/topic/architecture/ui-layer/events#handle-viewmodel-events)
- Events originating from the ViewModel, ViewModel event, should always result to UI state update
- Consuming the events can trigger UI state updates.
- The View should notifying the ViewModel that the event has been processed.

Each event requires an unique ID for tracking. You can find more  in [this article](https://www.droidcon.com/2022/01/14/sending-viewmodel-events-to-the-ui-in-android/). Precise and detailed.


### Resources
- [Android SingleLiveEvent Redux with Kotlin Flow](https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055)
- [ViewModel Events: Antipatterns](https://medium.com/androiddevelopers/viewmodel-one-off-event-antipatterns-16a1da869b95)
- [Sending ViewModel Events to the UI in Android](https://www.droidcon.com/2022/01/14/sending-viewmodel-events-to-the-ui-in-android/)
- [Handle ViewModel Events: Docs](https://developer.android.com/topic/architecture/ui-layer/events#handle-viewmodel-events)

