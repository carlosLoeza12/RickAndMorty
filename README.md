# RickAndMorty

Available in Google play: https://play.google.com/store/apps/details?id=com.characters.rickandmorty

# Overview: 
When opening the application, the user can sign in with their Gmail account. It is important to mention that this data is stored only locally on the device.
After signing in, the home screen will display a welcome notification sent through FCM, which is integrated with 000webhost. Additionally, a paginated list of characters from the Rick and Morty series will be displayed.
By selecting any character from the list, the user will navigate to a detail screen showing more complete information such as status, species, gender, type, and other additional data. 

You will have the option to save the character locally by tapping the thumbs-up icon, allowing you to view them offline whenever you want.

Paginated lists of the series’ locations and episodes are displayed, along with their corresponding information.

In the Saved section, you can view the characters you have previously stored, and you can use the search bar to quickly find them. You can delete saved characters by tapping the red trash can icon.

In the upper right corner, a white user icon is displayed. If you tap it, you will navigate to the profile screen, where you can view the user’s information and the Gmail account used to sign in. You can sign out by tapping the red “Sign out” text.

To begin this project, the documentation of The Rick and Morty API was reviewed, and test requests were made using Postman to visualize the responses in JSON format. Afterwards, the app’s overall design and functionality were analyzed. It is worth mentioning that some examples and recommended best practices were taken into consideration during development.

The project was registered and configured in the Firebase console, allowing push notifications to be enabled and the necessary credentials for this service to be obtained. Then, using 000webhost, a PHP endpoint was created, which receives the device token and generates and sends the notification using Firebase credentials.

Lastly, the necessary configurations were made to sign and generate the project’s release file. The registration on the Google Play Store was then completed, and the required files were uploaded to submit the project for review. After a few days, the app became available in the store.

# Technologies and tools

#Android Studio
#Kotlin
#Mvvm
#Navigation component
#Retrofit and interceptors
#Dependency injection
#Coil
#Room
#Paging
#Coroutines
#Push Notifications
#Login Gmail

# Links:

Rick and Morty: https://rickandmortyapi.com/

Google cloud platform: https://cloud.google.com/?hl=es

Google play console: https://play.google.com/console/about/

000Webhost: https://mex.000webhost.com/

Firebase Console: https://console.firebase.google.com/

# screenshots:

<img width="172" alt="Imagen1" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/39d2cfd1-b4d9-499e-bebf-c8db59a77bff">

<img width="172" alt="Imagen2" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/e7d86818-22f7-43a2-8a99-e2ad49fec25b">

<img width="172" alt="Imagen3" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/54eb94ba-e450-4aec-8bc7-361c0e1ddcbb">

<img width="172" alt="Imagen4" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/eb9cdf18-a0b9-4496-a5a7-84132c0fe922">

<img width="172" alt="Imagen8" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/67574109-bb33-471f-835e-9b0380cf917e">

<img width="172" alt="Imagen5" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/a56b710c-0b78-4443-ad2c-39db88a49cbc">

<img width="172" alt="Imagen6" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/7bfcbfc9-ba7f-4c3b-ab08-fc7eb21421b9">

<img width="172" alt="Imagen7" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/9cad273b-17c1-4c4a-9409-a49dadcea73e">





