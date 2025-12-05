### Google Play Subscription

```kotlin

/**
 * Initialize your subscription list in the Application class.
 */
AppBilling.init(context = this, billingItems = yourSubscriptionList, BuildConfig.DEBUG)

/**
 *Check your premium status
 * - true: If you have purchased any IAP item
 * - We have handled turn off ads if true
 */
AppBilling.isPurchasedFlow

/**
 * Get the full price of your IAP item
 */
AppBilling.getFullPriceText(subscription)

/**
 * Check if your IAP item is available for purchase on Google Play
 */
AppBilling.isBillingItemAvailable(subscription)

/**
 * Get information about the IAP item that the user has successfully purchased via Google Play
 */
AppBilling.purchasedItemMapFlow.map { map ->
    map.values.mapNotNull { billingItem ->
        val purchasedItem = billingItem.billingItem
        if (purchasedItem is SubscriptionBillingItem) {
            val billingList = listOf(
                //your billing items list
            )
            billingList.find { it.productId == purchasedItem.productId && it.offerId == purchasedItem.offerId }
        } else {
            null
        }
    }
}

/**
 * Handles the purchase of your IAP item
 * */
AppBilling.purchase(
    activity = activity,
    billingItem = billingItem,
    onFailure = onFailure,
    onSuccess = { billingItem, purchase ->
        onSuccess(billingItemInfo)
    }
)

```