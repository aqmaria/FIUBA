## ABM de personas en C utilizando un archivo binario para persistencia y un archivo de control de posiciones de registros como estrategia para operar sobre los mismos

`` Se crea un registro de personas en archivo binario. Se agregan los registros de un archivo de texto o a partir de datos de consola.
NO SE VALIDAN LOS DATOS EXTRAIDOS DEL ARCHIVO!. Cuando se elimina un registro, éste se guarda en un archivo de respaldo. 
Existe un archivo de control de cantidad de registros, en éste se guarda la cantidad total de registros válidos, y 
las posiciones "libres" para volver a agregar registros. Cuando se agrega un registro, se chequea la ultima posición liberada, 
se escribe sobre ella,Y se la borra del archivo de control para poder truncarlo. `` 
