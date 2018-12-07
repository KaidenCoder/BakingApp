# BakingApp
Baking App is an app which displays a list of items which teaches you to cook these items through video lectures and you can bookmark you favorite item which will be displayed on a widget./

## Features
* App is written solely in the Java Programming Language
* Uses stable release versions of all libraries, Gradle, and Android Studio. Debug/beta/canary versions are not acceptable.
* App utilizes stable release versions of all libraries, Gradle, and Android Studio.

### General App Usage
* App should display recipes from provided network resource.
* App should allow navigation between individual recipes and recipe steps.
* App uses RecyclerView and can handle recipe steps that include videos or images.

### Components and Libraries
* Application uses Master Detail Flow to display recipe steps and navigation between them.
* Application uses Exoplayer to display videos.
* Application properly initializes and releases video assets when appropriate.
* Application should properly retrieve media assets from the provided network links. It should properly handle network requests.
* Application makes use of Espresso to test aspects of the UI.
* Application sensibly utilizes a third-party library to enhance the app's features. That could be helper library to interface with ContentProviders if you choose to store the recipes, a UI binding library to avoid writing findViewById a bunch of times, or something similar.

### Homescreen Widget
* Application has a companion homescreen widget.
* Widget displays ingredient list for desired recipe.
