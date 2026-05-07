# Google Play Subscription

## Initialize Billing

Initialize your subscription list in the `Application` class.

```kotlin
AppBilling.init(
    context = this,
    billingItems = yourSubscriptionList,
    isDebug = BuildConfig.DEBUG,
)
```

## Check Premium Status

`AppBilling.isPurchasedFlow` returns `true` when the user has purchased any IAP item. Ads are
handled as off when this value is `true`.

```kotlin
AppBilling.isPurchasedFlow
```

## Read Billing Item Information

```kotlin
// Get the full price text of an IAP item.
AppBilling.getFullPriceText(subscription)

// Check if an IAP item is available for purchase on Google Play.
AppBilling.isBillingItemAvailable(subscription)
```

## Read Purchased Items

```kotlin
AppBilling.purchasedItemMapFlow.map { map ->
    map.values.mapNotNull { billingItem ->
        val purchasedItem = billingItem.billingItem
        if (purchasedItem is SubscriptionBillingItem) {
            val billingList = listOf(
                // Your billing items list.
            )
            billingList.find {
                it.productId == purchasedItem.productId &&
                    it.offerId == purchasedItem.offerId
            }
        } else {
            null
        }
    }
}
```

## Purchase an Item

```kotlin
AppBilling.purchase(
    activity = activity,
    billingItem = billingItem,
    onFailure = onFailure,
    onSuccess = { billingItem, purchase ->
        onSuccess(billingItemInfo)
    },
)
```
