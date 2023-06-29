# RickAndMorty

Disponible en Google play: https://play.google.com/store/apps/details?id=com.characters.rickandmorty

# Descripción general: 
Al abrir la aplicación podrá iniciar sesión con su cuenta de Gmail, cabe mencionar que estos datos solo se almacenan localmente en el dispositivo, seguidamente en el home recibirá una notificación de bienvenida por parte de FCM que esta integrado a 000webhost, también se muestra una lista paginada de personajes de la serie de Rick and Morty. Al hacer click en algún ítem de la lista se navegará hacia el detalle mostrando: el estado, especie, género, tipo y más. 

Tendrá la opción de guardar el personaje localmente presionando el icono de pulgar arriba, de manera que podrá visualizarlos de modo offline cuando quiera.

Se muestra las listas paginadas de las ubicaciones y episodios de la serie con su respectiva información.

En el apartado saved podrá ver los personajes que ha guardado anteriormente, puede utilizar el buscador para agilizar la búsqueda. Puede eliminar los personajes guardados presionando el icono de contenedor rojo.

En la parte superior derecha se muestra un icono de usuario de color blanco, si lo presiona navegara al perfil mostrando la información del usuario y la cuenta de Gmail con la que ha iniciado, puede cerrar la sesión presionando el texto de color rojo “Sign out”.

Para abordar este proyecto primeramente se revisó la documentación de la API (The Rick and Morty API) y con la herramienta de Postman se hicieron las peticiones de prueba para visualizar la respuesta en formato Json. Seguidamente se analizó el diseño y las funciones en general que tendría la app, cabe mencionar que se tomaron en cuenta algunos ejemplos y practicas recomendadas para poder desarrollarla.

Se registro y configuro el proyecto en la consola de firebase, ya que de esta manera podemos habilitar las notificaciones push y obtener las credenciales necesarias para este servicio, luego con 000webhost se creó un endpoint con php y este es el que recibe el token del dispositivo, crea y envía la notificación utilizando las credenciales de firebase.

Por ultimo se hizo las configuraciones necesarias para firmar y generar el archivo reléase del proyecto, se procedió a realizar el registro en la tienda de Google play y subir los archivos necesarios para mandar a revisión el proyecto. Al pasar unos días la app paso a estar disponible en la tienda.

# Tecnologias y herramientas

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

# Capturas:

<img width="172" alt="Imagen1" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/39d2cfd1-b4d9-499e-bebf-c8db59a77bff">

<img width="172" alt="Imagen2" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/e7d86818-22f7-43a2-8a99-e2ad49fec25b">

<img width="172" alt="Imagen3" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/54eb94ba-e450-4aec-8bc7-361c0e1ddcbb">

<img width="172" alt="Imagen4" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/eb9cdf18-a0b9-4496-a5a7-84132c0fe922">

<img width="172" alt="Imagen8" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/67574109-bb33-471f-835e-9b0380cf917e">

<img width="172" alt="Imagen5" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/a56b710c-0b78-4443-ad2c-39db88a49cbc">

<img width="172" alt="Imagen6" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/7bfcbfc9-ba7f-4c3b-ab08-fc7eb21421b9">

<img width="172" alt="Imagen7" src="https://github.com/carlosLoeza12/RickAndMorty/assets/68243731/9cad273b-17c1-4c4a-9409-a49dadcea73e">





