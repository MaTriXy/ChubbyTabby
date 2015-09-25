# ChubbyTabby <img src="web_hi_res_512.png" height="128" width="128" alt="ChubbyTabby"/> ChubbyTabby 

## Summary

This is a sample project using the Chrome custom tabs feature to show how we can use it and customize look and feel. 
It covers UI customization, callback setup, pre-warming and pre-fetching.


The application uses Helper Classes from the [example project by Google](https://github.com/GoogleChrome/custom-tabs-client).


## Introduction

Chrome Custom Tabs provides a way for an application to customize and interact with a Chrome tab on Android,
to make it a part of the application experience, while retaining the full functionality and performance of a complete
web browser.

### Overview

* UI customization:
  * Toolbar color
  * Action button
  * Custom menu items
  * Custom in/out animations
  * Pre-warming of the Browser in the background
  * Providing a pre-fetching URL in advance to the browser speeding up page load time.

These features are enabled through two mechanisms:

* Adding extras to the `ACTION_VIEW` intent sent to the browser.
* Connecting to a bound service in the target browser.


## UI Customization

UI customization is done through the methods exposed by `CustomTabsIntent.Builder`.

## Communication Customization
The communication between the custom tab activity and the application is done
via pending intents For each menu items and action button. 

## Extra power

* Warming up the browser
* Pre fetching a given URL

communication with the browser is done through a bound background service. 
This binding is done by `CustomTabClient.bindCustomTabsService()`.
 
After the service is connected, we get access to `CustomTabsClient` object, until disconnected. 

This client can be used in these two cases:
* **Warmup**: Warms up the browser to make navigation faster. Once started, Chrome will not use additional
  resources. call is done by `CustomTabsClient.warmup()`.
* **Pre fetching URL** Indicate that a possible URL might be called to load soon.
Chrome might try and pre fetch it or do some more work related to page loading.
First must call warmup `CustomTabsClient.warmup()`.
Call is done with `CustomTabsSession.mayLaunchUrl()`.

