# Hackthon- "[Globhack 2020](https://www.linkedin.com/events/6661728790860881920/)"
A virtual hackathon to be *kind*.

# Idea

AbrazAr propone desarrollar una aplicación para teléfonos móviles que colabore con la disminución del riesgo de contagio de "coronavirus" COVID-19 mediante la construcción de una red vecinal de ayuda solidaria. Así, evitamos que las personas vulnerables (adultos mayores, embarazadas, inmunodeprimidos, etc.) se expongan al salir de sus hogares para realizar tareas cotidianas.

Algunos de los objetivos de esta idea son:

* Conectar a gente solidaria con gente vulnerable.
* Proponer un sistema que estimula la colaboracion mediante *gamification*. 
* Reducir al minimo el nivel de exposicion de los "peticionantes" al momento de solicitar la ayuda de los colaboradores solidarios.

El *problema principal* que se busca atacar, tiene que ver con la cantidad incipiente de casos de CODVID-19 que ponen en riesgo a la poblacion mas fragil. No podemos estar sordos a esto, y existen los medios tecnologicos para proveerles a las personas las herramientas necesarias para organizarse. Para sertirse bien ayudando y para premiarse mutuamente como mejor lo crean posible!.

*AbrazAr* es un sistema que lograra reunir a dos personas que necesitan, en realidad, la una de la otra. Un mecanismo unico, pero necesario para acercar soluciones a personas necesitadas.

## Idealmente...

Idealmente, hablamos de una aplicacion tipo *Ingress* o *Pokemon GO*, que provea de tareas y premios a potenciales adolescentes y adultos jovenes. De tal forma que se vean incentivados a participar de actividades que, en estos momentos, podrian resultar de primordial interes para la comunidad; como es el caso de maximizar el nivel de aislamiento de los adultos mayores, embarazadas o diabeticos, por ejemplo.

# Workflow

A continuacion se describiran el tipo de situaciones que intentara favorecer la aplicacion y el workflow ideal de la misma.

## Una historia ilustrativa

Una persona necesita salir de compras, proveerse de medicacion. Siendo un adulto vulnerable al CODVID-19, salir de su casa lo expone a un peligro mortal. Inicia la aplicacion Abrazar en su celular de bajo presupuesto. Se loguea, y crea una nueva peticion de ayuda; ingresa:
"Necesito comprar dos kilos de tomates, zapallitos y cuatro kilos de naranjas. Ademas necesito alcohol en gel.". Pone fecha y hora limite para cumplir con el pedido: "Hoy, a las 18hs". Como es poco el tiempo para cumplirlo, la aplicacion le sugiere aplicar algun tipo de recompensa. Ingresa "Dos muffins de chocolate", como premio y presiona el boton "Ayuda!".

A pocas cuadras de alli, la aplicacion repiquetea en el bolsillo de un joven de veinte años, harto de estar encerrado en su casa. Le dice que a pocas cuadras de alli, se ha encendido una nueva "quest" con "recompensa". Es fan del chocolate, y ademas, si completa esta busqueda recibira varios puntos de experiencia que le harian pasar de nivel "Iniciado" a "Comprometido". Eso le habriria la puerta a nuevas "quest", mas dificiles y mas largas. Y tambien podra ver las quest con premios mas jugosos *antes* que el resto de los usuarios *colaboradores*. Decide realizar las compras para Elena, quien parece vivir en el quinto piso de corrientes al 9000. La señora dejo instrucciones de contacto y despacho. Asi que realiza las compras, recibe los dos muffins y la experiencia tan pronto como Elena le da "Ok" al pedido recibido.

Esta historia es ilustrativa de una situacion *ideal* de utilizacion de la aplicacion. Por supuesto que habra contratiempos, recompensas y premios que no seran suficientes. Pero el generar una interaccion como esta, deberia ser el ideal ultimo al que *AbrazAr* intentara llegar.

## Paso a paso

El regristro del usuario en la aplicacion. Debera definir si es un "ayudado"/"solicitante" o un "colaborador"/"ayudanrte". La eleccion de esto, al momento del registro, llevara a una experiencia de usuario completamente diferente.

### El workflow de Colaborador

Al loguearse, la persona vera un billboard de "quests" o "tareas" para realizar. Algunas con recompensas o premios y otras sin otra recompensa que no sea "dar las gracias". En ningun caso el intercambio podra ser economico o fundamentado en el uso de modenas. Tampoco en el intercambio o "trueque". Lo que se busca es que se intercambien "servicios", por "nada" o "algo", si es que la persona que lo publico asi lo desea.

Desde este billboard, podra seleccionar cual desea completar. Podra cambiarle el estado a iniciada y empezara a correr un timer. La persona que inicio la busqueda sera notificada de que persona comenzo con esta tarea/mandado. De esta forma sabra que el pedido esta en curso. 

Al finalizarse la tarea, el "colaborador" que la inicio, podra cambiarla de estado. Esto notificara al "peticionante" / "ayudado" quien sera quien podra dar por completada la misma.

### El workflow del peticionador / ayudado

Cualquier persona podra poner un pedido en las inmediaciones de su barrio, geolocalizandose. Desde alli, se permitira que un pedido este asociado a una direccion especifica y este restringido a una cantidad de horas o dias especificos. El pedido en cuestion, sera una lista de tareas que deberan cumplirse una a una. Luego de esto, se podra, opcionalmente proveer de una "recompensa" o "premio" por completarlo. Con lo cual, sera el peticionador quien podra tener la ultima palabra sobre cuando "la tarea esta completada".

## El MVP

El producto minimo que presentaremos no es la aplicacion completa, con quests, premios, geolocalizacion y gamificacion integrada. Tampoco tendra en cuenta temas de seguridad que atañen a la aplicacion en si misma, asegurar el buen gusto de los que "se pide" o asegurar que la interaccion entre ambas personas "sea segura". Lo que pretendemos armar, el *core* de la funcionalidad que pretendemos testear en el *campo* / *mercado* son las siguientes:

1. Establecer si las personas estarian interesadas en participar.
2. Establecer si la interfaz (UX) es adecuada para todos los grupos etarios del lado peticionante.
3. Establecer si el problema de "resolver las comprar sin exponernos" sirve.

Consideremos que en esencia, se trata de una app que buscara establecer "mandados" entre desconocidos. Esto es lo primero y primordial que queremos estimar. No buscamos establecer si "existe un mercado" para esto. Pero si, determinar si las personas, al enterarse que hay alguien en su barrio que necesita ayuda, estarian dispuesto a ayudarlo, o si lo ignoraran.

Esto, por ende, constituye el primer paso de nuestro experimento: establecer si hay masa critica para una aplicacion mas ambiciosa.

### Funcionalidades implementadas.

Se implementaran:

* El registro de usuarios sin la utilizacion de OAuth. Contemplando "ayudado" y "colaborador" como los dos unicos perfiles.
* La creacion de un pedido en un barrio ficticio, sin geolocalizacion.
* La visualizacion de la lista de "quests" o "trabajitos" que coinciden con tu barrio.
* El tomar la tarea y cambiarla de estado a "en progreso". Luego, a "completada".
* Cambiar el estado de la tarea, por parte del "ayudado", a terminada. 

Luego de esto, se pondra la aplicacion piloto en las manos de los ciudadanos y se monitoreara su uso y como se comportan en relacion a las tareas, los barrios y las situaciones de cada uno. La aplicacion ira creciendo desde alli, en funcionalidad (general o geolocalizada) y en su facilidad de uso.

# Integrantes
[Antonio](https://github.com/stranger01)
[Lucas Camino](https://github.com/lucascan95)
[Ezequiel H. Martinez](https://github.com/exemartinez)










