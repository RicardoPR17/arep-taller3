# **Taller 3 - Microframework web**
### *Hecho por Ricardo Pulido Renteria*

En este taller, se extiende servidor web creado en el taller anterior utilizando Java y archivos almacenados en el disco.
Ahora, se convierte en un servidor de aplicaciones con el cual las personas podrían crear un servicio sencillo, tomando de guía el framework de Spark, brindando al usuario la posibilidad de tratar peticiones GET y POST.

## **Descarga y ejecución**

Para poder ejecutar este proyecto, el cual se ejecutará en tu ambiente local por fines de desarrollo y pruebas, debes contar con algunos elementos que serán indicados a continuación.


## **Prerequisitos**

La ejecución de este proyecto requiere de:
- `Java (versión 8 o superior)`
- `Maven (3.8.1 o superior)`
- `Conexión a internet`


## **Instalación**

Para poder trabajar con el proyecto, es necesario descargarlo desde GitHub. Para esto puede clonar el repositorio en su máquina o puede descargarlo en formato zip. Luego, una vez acceda al directorio del proyecto, debe ejecutar el comando `mvn install` para descargar las dependencias del proyecto, ya sea desde la terminal de comandos o desde la terminal que le brinde el intérprete de código de su preferencia (VS Code, IntelliJ, NetBeans, etc).

Para ejecutarlo, podrá hacerlo desde la terminal de comandos como se explica a continuación. O desde el intérprete de código de su elección, haciendo `run` o ejecutando el código de la clase _MyWebServices_.


- **_Ejecución usando terminal de comandos_**
  
  En caso de realizar la ejecución desde la terminal de comandos, se debe realizar lo siguiente:
  1. Acceder al directorio del proyecto usando el comando `cd arep-taller3`.
  2. Una vez dentro del directorio del proyecto, se ejecuta el comando `mvn package` para generar la carpeta _target_.
  3. Desde la terminal, ejecutamos el comando `java -cp .\target\classes com.example.taller3.myspark.MyWebServices`.
  4. Listo, el servidor web estará corriendo y verás un mensaje diciendo que está listo para recibir peticiones.


## **Uso**

Primero, vamos a acceder a través de un navegador web a nuestro servidor creado. Veremos los recursos que se encontraban ya guardados en el disco de forma base en el servidor. Esto, por medio de la ruta http://localhost:17000/ que por si sola nos mostrará una página de error. Pero, si añadimos elementos al path obtendremos diferentes recursos, estos son:

+ Página web con formularios: http://localhost:17000/public.html
+ Código JavaScript de los formularios: http://localhost:17000/public.js
+ Página web API películas: http://localhost:17000/movie.html
+ Código JavaScript API películas: http://localhost:17000/movieRequest.js
+ Hoja de estilos página web API películas: http://localhost:17000/movie.css
+ Imagen camara de cine:  http://localhost:17000/camera.png
+ Imagen cubo de rubik: http://localhost:17000/cube.jpg
+ GIF cubo de rubik: http://localhost:17000/scramble.gif

Ahora, si queremos ver el servicio de ejemplo que podría crear un usuario, accedemos por la ruta http://localhost:17000/action/ seguido del archivo que queramos consultar o la ruta que nuestro usuario haya especificado. Estos son:

+ Imagen camara fotográfica: http://localhost:17000/action/camera.png
+ Página web API nueva: http://localhost:17000/action/films.html
+ Código JavaScript API películas: http://localhost:17000/action/movieRequest.js
+ Hoja de estilos página web API películas: http://localhost:17000/action/movie.css
+ Imagen pruebas unitarias: http://localhost:17000/action/testing.png
+ Página simple AREP, puede enviar parámetros: http://localhost:17000/action/arep?param=a | http://localhost:17000/action/arep
+ Página simple ARSW: http://localhost:17000/action/arsw
+ Página simple IETI: http://localhost:17000/action/ieti
+ Página simple Queries, puede enviar parámetros: http://localhost:17000/action/queries?test=taller3

En este caso, nos trajimos el servicio de la API web de películas para mostrar que seguía funcionando incluso si el usuario quería hacer su versión de la página. Además, para mostrar que no está siendo tomada del directorio que maneja el servidor sino por la creada por el usuario, vemos que maneja una imagen de cámara diferente y un CSS distinto.

El usuario, puede crear peticiones GET que respondan de la forma que desee tal como se observó con las últimas rutas.

## **Diseño**

Para este proyecto se manejan una clase que es _HttpServer_, siendo la clase del taller 2 que vamos a extender con el fin de actuar como el servicio que actúa por detrás para que el usuario pueda crear sus aplicaciones. Aquí, añadimos el manejo de rutas especiales que indique el usuario junto a las acciones que deben ejecutarse.

Para esto, se crea un mapa que permita relacionar la ruta (ejemplo: /arep) con la acción que desea el usuario. Esta acción, se define como una función lambda la cual cumple con una interfaz funcional, la interfaz _WebServiceInter_, que le permite manejar parámetros recibidos por la URL al acceder a la ruta que haya seleccionado. Las acciones que puede hacer el usuario son peticiones GET y POST.

![P es nuestro parámetro en cuestion](<Imágenes README/image.png>)

Adicionalmente, se implementa un método para asignar cómo queremos que sea enviada la respuesta de la API de películas. Ya sea en formato JSON o como texto plano.

![Selección de formato](<Imágenes README/image2.png>)

Esto, puede ser asignado por el usuario con el simple llamado a la función y enviando el valor booleano que decida.

![Tipo de respuesta y asignar carpeta](<Imágenes README/image3.png>)

Además, en el directorio _resources/public_ encontramos todos los recursos que el servidor ofrece y envía cuando son solicitados. Pero, si el usuario quiere manejar otro directorio para sus archivos, puede crearlo manualmente dentro de _resources_ con el nombre que quiera y tal como se ve en la última línea de la imagen anterior, pasando el nombre del directorio, quedará asignado como espacio de búsqueda para los archivos solicitados para su implementación. Es decir, las rutas que inicien con el path */action*

Gracias a esta distribución de archivos en carpetas, es posible diferenciar los recursos manejados por el servidor de forma nativa y los recursos que el usuario quiera añadir y trabajar para su implementación particular usando nuestro servidor de aplicaciones.

## **Pruebas**

Para estas pruebas, vamos a acceder a cada uno de los recursos añadidos por el usuario principalmente. Para eso, se usará el navegador de Firefox y el apartado de red de su inspección de recursos.

+ Imagen camara fotográfica: http://localhost:17000/action/camera.png

![Imágen cámara usuario](<Imágenes README/image-camera.png>)

+ Página web API nueva: http://localhost:17000/action/films.html

![API usuario](<Imágenes README/image-1.png>)

Esta, es la versión del usuario de la API de pelícilas, por ello se cuenta con un diseño diferente pero el nombre de los archivos semejante. Y si hacemos una petición, se resolverá correctamente y vemos esto en el último registro de las peticiones realizadas con respuesta JSON.

![Petición](<Imágenes README/image-2.png>)

+ Código JavaScript API películas: http://localhost:17000/action/movieRequest.js

![JS usuario](<Imágenes README/image-3.png>)

+ Hoja de estilos página web API películas: http://localhost:17000/action/movie.css

![CSS usuario](<Imágenes README/image-4.png>)

+ Imagen pruebas unitarias: http://localhost:17000/action/testing.png

![Testing](<Imágenes README/image-5.png>)

+ Página simple AREP, puede enviar parámetros: http://localhost:17000/action/arep?param=a | http://localhost:17000/action/arep

  + Sin parámetros

  ![AREP sin parámetros](<Imágenes README/image-6.png>)

  + Con parámetros

  ![AREP con parámetros](<Imágenes README/image-7.png>)

+ Página simple ARSW: http://localhost:17000/action/arsw

![ARSW X.X](<Imágenes README/image-8.png>)

+ Página simple IETI: http://localhost:17000/action/ieti

![IETI :D](<Imágenes README/image-9.png>)

+ Página simple Queries, puede enviar parámetros: http://localhost:17000/action/queries?test=taller3

![Queries](<Imágenes README/image-10.png>)

+ Prueba del método post, puede enviar parámetros: http://localhost:17000/action/post_test | http://localhost:17000/action/post_test?name=Paco

  + Sin parámetros

  ![Post sin parámetros](<Imágenes README/post1.png>)

  + Con parámetros

  ![Post con parámetros](<Imágenes README/post2.png>)



También, verificamos que los recursos nativos sigan funcionando de forma adecuada.

+ Página web API películas: http://localhost:17000/movie.html

![API](<Imágenes README/image-11.png>)

Aquí, vemos que trae los recursos necesarios para cargar la página. Y también, la petición satisfactoria de la información de una película.

+ GIF cubo de rubik: http://localhost:17000/scramble.gif

![BOOM](<Imágenes README/image-12.png>)

### _Linux_

Para mostrar el funcionamiento del servidor y accesibilidad desde otros sistemas operativos, por medio de una máquina virtual vamos a acceder a algunos de los servicios creados por el usuario y algunos nativos del servidor. Para esto, se revisó la IP de la máquina donde corría el servidor y con ella acceder al servicio.

+ Imagen cámara fotográfica: http://`<IP servidor>`:17000/action/camera.png

![Linux cámara usuario](<Imágenes README/linux cam.png>)

+ Página web API nueva: http://`<IP servidor>`:17000/action/films.html

![Linux API películas](<Imágenes README/linux api.png>)

+ Imagen cubo de rubik: http://`<IP servidor>`:17000/cube.jpg

![Linux cubo](<Imágenes README/linux cube.png>)

+ Página de formularios: http://`<IP servidor>`:17000/public.html

![Linux formularios](<Imágenes README/linux form.png>)

Como vemos, la respuesta del servidor fue aceptada y presentada de forma correcta en la máquina virtual con sistema operativo Linux, en este caso Kali Linux. Con esto, vemos que nuestro servidor de aplicaciones funciona de la forma esperada y es interpretada por 2 de los sistemas operativos más utilizados.

## **Construido con**
  - [Git](https://git-scm.com) - Control de versiones
  - [Maven](https://maven.apache.org) - Administrador de dependencias
