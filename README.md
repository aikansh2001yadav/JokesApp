# JokesApp

## Description

JokesApp is an sample jokes application which follows MVC architecture. The sample application shows jokes from yoMama and Chuck Norris categories. The user can also add custom joke. 
The user can also add any joke to favorite category. It uses volley library to fetch jokes from local json file.

## Application Screenshots 

* Yo Mama Jokes Screen: It shows jokes from yo mama category.
   <p align="center">
   <img src = "images/img_yo_mama_jokes.png" width="200" height="400">
   </p>
   
* Chuck Norris Jokes Screen: It shows jokes from chuck norris category.
   <p align="center">
   <img src = "images/img_chuck_norris_jokes.png" width="200" height="400">
   </p>
   
* Custom Jokes Screen: It shows jokes which are added manually.
   <p align="center">
   <img src = "images/img_custom_joke.png" width="200" height="400">
   </p>
   
* Add Custom Joke Screen: It shows how to add custom joke.
   <p align="center">
   <img src = "images/img_add_custom_joke.png" width="200" height="400">
   </p>
   
* Favorite Jokes Screen: It shows jokes which are added to favorite category.
   <p align="center">
   <img src = "images/img_favorite_jokes.png" width="200" height="400">
   </p>
   
* Share Joke Screen: It shows sharing joke screen.
  -<p align="center">
   <img src = "images/img_share_joke.png" width="200" height="400">
   </p>

## Code Structure

As the name implies MVC pattern has three layers, which are:

* Model: Represents the business layer of the application. 
 <br> Our application consists of model classes such as CustomJokeManager, Joke and JokeManager.
* View: Defines the presentation of the application
 <br> MainActivity shows jokes from various category. The user can also add custom joke. The user can also share joke. FavoriteJokesActivity shows favorite jokes. 
* Controller: Manages the flow of the application
 <br> Our application consists of a lot of adapters which deal with list of data and update recycler views. Data is fetched via volley library from local json file.
 
 
 ## Package Structure
 
    com.example.jokesapp               # Root Package
      .
      ├── activities                   # Contains MainActivity to show jokes from various categories. Also contains FavoriteJokesActivity which shows favorite jokes.
      ├── controller                   # Contains adapters to manage the flow of the application.
      ├── fragments                    # Contains FavoriteJokesFragment which shows favorite jokes.
      ├── model                        # Contains various model classes to implement business layer of the application.
      └── utils                        # Contains JokesData and shake detector which helps to refresh data after shaking.
      
      
 ## Technologies and Libraries
- [Java](https://docs.oracle.com/en/java/javase/11/) - Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.
- [Async Library](https://github.com/jmartinesp/AsyncJobLibrary) - Android library to easily queue background and UI tasks.
- [Swipe Layout](https://github.com/zerobranch/SwipeLayout) - SwipeLayout is a project for the android platform, providing the opportunity to perform swipe for any layout, in the specified direction.
- [Card Stack View](https://github.com/yuyakaido/CardStackView) - Tinder like swipeable card view for android.

## Built With

* Android Studio

## Author
* <a href="https://github.com/aikansh2001yadav"> **Aikansh Yadav** </a>



