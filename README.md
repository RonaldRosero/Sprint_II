# # Bootcamp Backend Java Wave 21 Sprint N°2 - Spring




## Realizado por

- 👨🏻‍💻 [@RonaldRosero](https://github.com/RonaldRosero)




## Objetivo

El objetivo de este desafío fue aplicar los contenidos del BOOTCAMP (Git, Java, Spring y Testing) y fortalecer por medio de la práctica,
haciendo énfasis en las validaciones y tipos de testing que pueden ser utilizados a partir de un enunciado propuesto,
una especificación de requerimientos y documentación técnica.

Para realizar el Sprint II recibí el proyecto del Sprint I realizado por el grupo 4  
[Proyecto Sprint I Grupo 4](https://github.com/extjotabell/wave21-practicas/tree/laura_sanabria/4.%20sprint%20I/Grupal)


## Pautas para la actividad
El desafío que se propone a continuación consta de 2 partes:
#### A. Implementar validaciones y tests unitarios :
A partir de un primer incremento de un escenario conocido (Sprint Nº 1), se deberán establecer distintos procesos de validación de datos y de test unitarios necesarios.

#### B. Implementar test de integración:
Luego de lograr implementar tests unitarios, implemantar test de integración.

#### C. Bonus:
En caso de que hayas logrado resolver tanto el apartado A de manera grupal como todo el apartado B de forma individual y aún cuentes con tiempo, te invitamos a que propongas otros tests de integración (diferentes al que hayas implementado en el punto B).  No te olvides que este apartado es 100% OPCIONAL y NO OBLIGATORIO.

## Tecnologias Utilizadas

Git, Java, Spring y Testing

## Decisiones del grupo 4

- Decisión 1: Un usuario puede seguir a otro usuario, sin importar si tiene post (Usuario).
- Decisión 2: Se decide crear los atributos has_promo y discount para identificar cuando un producto está en descuento.

## Decisiones tomadas por el desarrollador del sprint II
- Decisión 1: Implementar las decisiones futuras del grupo 4, Validar cuando un usuario no existe en las historias US 0005 y US 0006.
  Implementar DTO para el mensaje de respuesta de la historia US 0005.
- Decisión 2: No implementar mejora de restricciones en los posts (en especial US 0005) propuesta del grupo 4, por el contrario se quitaron las restriciones y se implentó otra validación.
- Decisión 3: Refactorizar los métodos según las especificaciones de los requerimientos de los test, agregar validaciones y excepciones. 

##  Requerimientos técnicos funcionales (Punto A)


#### User Stories
SocialMeli contaba anteriormente con las siguientes User Stories y requerimientos técnicos:



**US-0001:** Poder realizar la acción de “Follow” (seguir) a un determinado vendedor.

**US-0002::** Obtener el resultado de la cantidad de usuarios que siguen a un determinado vendedor.

**US-0003:** Obtener un listado de todos los usuarios que siguen a un determinado vendedor (¿Quién me sigue?).

**US-0004:** Obtener un listado de todos los vendedores a los cuales sigue un determinado usuario (¿A quién sigo?).

**US-0005:** Dar de alta una nueva publicación.

**US-0006:** Obtener un listado de las publicaciones realizadas por los vendedores que un usuario sigue en las últimas dos semanas (para esto tener en cuenta ordenamiento por fecha, publicaciones más recientes primero).

**US-0007:** Poder realizar la acción de “Unfollow” (dejar de seguir) a un determinado.

**US-0008:** Ordenamiento alfabético ascendente y descendente.

**US-0009:** Ordenamiento por fecha ascendente y descendente.

##  Resumen de Datos de entrada (todas las US):


| Datos/Parámetros      | Tipo  | Longitud  | Descripción |
|:-----------------|:------|:-----------------|:--------------------------|
| `userId`         | `Integer` |   |Número que identifica al usuario actual |
| `user_id_to_follow`| `Integer` |   |Número que identifica al usuario a seguir |
| `user_name`         | `String` |  15 |Nombre de usuario asociado a la user_id |
| `followers_count`| `Integer` |   |Cantidad de seguidores|
| `id_post`         | `Integer` |   |Número identificatorio de cada una de las publicaciones|
| `date`         | `LocalDate` |   |Fecha de la publicación en formato dd-MM-yyyy|
| `product_id`  | `Integer` |   |Número identificatorio de cada uno de los productos asociados a una publicación|
| `product_name`| `String` | 40  |Cadena de caracteres que representa el nombre de un producto|
| `type`         | `String` |  15 |Cadena de caracteres que representa el tipo de un producto|
| `brand`| `String` |  25 |Cadena de caracteres que representa la marca de un producto|
| `color` | `String` |  15 |Cadena de caracteres que representa el color de un producto|
| `notes`   | `String` |  80 |Cadena de caracteres para colocar notas u observaciones de un producto|
| `category`| `Integer` |   |Identificador que sirve para conocer la categoría a la que pertenece un producto. Por ejemplo: 100: Sillas, 58: Teclados|
| `price` | `Double` | 10.000.000 (Max) |Precio del producto|
| `user_id_to_unfollow`| `Integer` |   |Número que identifica al usuario a dejar de seguir|
| `order` | `String` |  15 |Cadena de caracteres que representa el color de un producto|
| `notes`   | `String` |  80 |Establece el ordenamiento. Puede poseer los valores: name_asc, name_desc, date_asc, date_desc|

#### Validaciones en campos (Todas las US):
| Dato/Parámetro    | ¿Obligatorio?| Validación | Mensaje de error |
|:-----------------|:------|:----------------------|----------|
| `userId` | `Si` | - Que el campo no esté vacío - Mayor 0 |-El  id no puede estar vacío.  - El id debe ser mayor a cero|
| `id_post`| `SI` |  - Que el campo no esté vacío - Mayor 0 |- El id_post no puede estar vacío. - El id_post debe ser mayor a cero.
| `date`         | `SI` |  Que el campo no esté vacío. |- La fecha no puede estar vacía.|
| `product_id`| `SI` | - Que el campo no esté vacío - Mayor 0   |- La id no puede estar vacía. -El id debe ser mayor a cero|
| `product_name`| `SI` | - Que el campo no esté vacío. -Longitud máxima de 40 caracteres. - Que no posea caracteres especiales (%, &, $, etc) |- El campo no puede estar vacío. - La longitud no puede superar los 40 caracteres. - El campo no puede poseer caracteres especiales.|
| `type`         | `SI` | - Que el campo no esté vacío. -Longitud máxima de 15 caracteres. - Que no posea caracteres especiales (%, &, $, etc) |- El campo no puede estar vacío. - La longitud no puede superar los 15 caracteres. - El campo no puede poseer caracteres especiales.|
| `brand`  | `SI` | - Que el campo no esté vacío. -Longitud máxima de 25 caracteres. - Que no posea caracteres especiales (%, &, $, etc) |-El campo no puede estar vacío. - La longitud no puede superar los 25 caracteres. - El campo no puede poseer caracteres especiales.|
| `color`| `SI` | - Que el campo no esté vacío.  - Que no posea caracteres especiales (%, &, $, etc), permite espacios. |- El campo no puede estar vacío. - La longitud no puede superar los 15 caracteres. - El campo no puede poseer caracteres especiales.|
| `notes` | `No` | - Longitud máxima de 80 caracteres especiales -Que no posea caracteres especiales (%, &, $, etc), permite espacios. |- La longitud no puede superar los 80 caracteres. - El campo no puede poseer caracteres especiales.|
| `category`| `SI` | - Que el campo no esté vacío. |- El campo no puede estar vacío.|
| `price` | `SI` |  - Que el campo no esté vacío. -El precio máximo puede ser 10.000.000. |- El campo no puede estar vacío. - El precio máximo por producto es de 10.000.000|

#### *Nota: Tener en cuenta que para la devolución de los mensajes de error es recomendable utilizar los status code correspondientes.



#### Tests Unitarios::


|      | Situaciones de entrada | Comportamiento Esperado   | 
|:-----------------|:------|:------------------------------------------|
| `T-0001`| `Verificar que el usuario a seguir exista. (US-0001)` | `Se cumple:` Permite continuar con normalidad. `No se cumple:` Notifica la no existencia mediante una excepción.| 
| `T-0002` | `Verificar que el usuario a dejar de seguir exista. (US-0007)` |`Se cumple:` Permite continuar con normalidad. `No se cumple:` Notifica la no existencia mediante una excepción. |
| `T-0003` | `Verificar que el tipo de ordenamiento alfabético exista (US-0008)` | `Se cumple:` Permite continuar con normalidad. `No se cumple:` Notifica la no existencia mediante una excepción. |
| `T-0004` | `Verificar el correcto ordenamiento ascendentey descendente por nombre. (US-0008)` | Devuelve la lista ordenada según el criterio solicitado|
| `T-0005` | `Verificar que el tipo de ordenamiento por fecha exista (US-0009)` |`Se cumple:` Permite continuar con normalidad. `No se cumple:` Notifica la no existencia mediante una excepción.|
| `T-0006` | `Verificar el correcto ordenamiento ascendente y descendente por fecha. (US-0009)` | Verificar el correcto ordenamiento ascendente y descendente por fecha. (US-0009)|
| `T-0007` | `Verificar que la cantidad de seguidores de un determinado usuario sea correcta. (US-0002)` | Devuelve el cálculo correcto del total de la cantidad de seguidores que posee un usuario. |
| `T-0008` | `Verificar que la consulta de publicaciones realizadas en las últimas dos semanas de un determinado vendedor sean efectivamente de las últimas dos semanas. (US-0006)` | Devuelve únicamente los datos de las publicaciones que tengan fecha de publicación dentro de las últimas dos semanas a partir del día de la fecha.|

##   

#### Agradecimientos: A Johanna Tabella por guiarme en la realización de este proyecto y por su gran apoyo, tambien agradezco a los instructores Martin y Marco por compartir su conocimiento!! 

