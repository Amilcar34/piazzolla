# piazzolla
Gimnasio de Boxeo Piazzolla

### Prerrequisitos

1. JDK 11+ instalado con JAVA_HOME configurado apropiadamente
2. Apache Maven 3.9.3

### Proyecto :rocket:

API desarrollada con Quarkus para la gestión de Gimnasio de boxeo

### Requerimientos

Se desea ingresar en el sistema a los aspirantes a Boxeador que llegan en un día. El gimnasio cuenta con 4 entrenadores, cada uno abarca 2 categorías:

Mosca-Gallo

Pluma-Ligero

Welter-Mediano

Mediopesado-Pesado

Los aspirantes a boxeadores serán fichados y asignados al entrenador correspondiente. A Cada entrenador se le puede asignar 5 boxeadores como máximo, una vez cubierto el cupo del entrenador se rechazaron los siguientes, que corresponden a dicho entrenador. Las categorías serán las 8 típicas del Boxeo:

| Categoría   | Peso Mínimo | Peso Máximo |
|:------------|:------------|:------------|
| Mosca       | 48.988      | 50.802      |
| Gallo       | 52.163      | 53.525      |
| Pluma       | 55.338      | 57.152      |
| Ligero      | 58.967      | 61.237      |
| Welter      | 63.503      | 66.678      |
| Mediano     | 69.853      | 72.562      |
| Mediopesado | 76.205      | 79.378      |
| Pesado      | 91          | Sin Límite  |


Los Boxeadores que caen dentro del límite de una categoría, son asignados a ella. Los que caen fuera de los límites serán asignados a la categoría inmediatamente inferior, se espera que el Boxeador pierda peso en el entrenamiento. Sí su peso es menor que la menor categoría, será asignado a la menor categoría, y se le dará una dieta de engorde.

Al finalizar el día se debe imprimir la cantidad de Boxeadores Totales que ingresaron en el día y un detalle de la cantidad por Entrenador.

### Ejecución :checkered_flag:
Desde una terminal con el directorio donde se encuentra la aplicación ejecute:
quarkus dev

### Documentación :page_facing_up:
Para acceder a documentación de los endpoint en Swagger acceder a http://localhost:8080/q/swagger-ui/




