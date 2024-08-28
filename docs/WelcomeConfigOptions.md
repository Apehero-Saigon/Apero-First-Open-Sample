Welcome Screen config full options
==================

Only use one of the setXxxContent, using both will result in undefined behaviour

```kotlin
val welcomeConfig = AperoWelcomeUiConfig.Builder()
    // Set color of some elements on screen such as ads button
    .setPrimaryColor(yourPrimaryColor)

    // Set welcome screen layout with View
    .setViewContentProvider{ /* View here */ }

    // Set welcome screen layout with Composable
    .setComposeContent(/* Composable here */)

    .build()
```