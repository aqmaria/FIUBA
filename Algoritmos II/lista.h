
#ifndef LISTA_H
#define LISTA_H

#include <stdbool.h>
#include <stdlib.h>

/* ***DEFINICION DE LOS TIPOS DE DATOS*** */

typedef struct lista lista_t;
typedef struct lista_iter lista_iter_t;

/* ***PRIMITIVAS DE LA LISTA*** */

// Crea una lista.
// Post: devuelve una nueva lista vacía. 
lista_t *lista_crear( void );

// Devuelve true o false, según si la lista tiene o no elementos.
// Pre: la lista fue creada.
bool lista_esta_vacia( const lista_t *lista );

// Agrega un nuevo elemento al principio de la lista.Devuelve 
// false en caso de error.
// Pre: la lista fue creada.
// Post: se agregó un nuevo elemento al principio de la lista.
bool lista_insertar_primero( lista_t *lista, void *dato );

// Agrega un nuevo elemento al final de la lista.Devuelve 
// false en caso de error.
// Pre: la lista fue creada.
// Post: se agregó un nuevo elemento al final de la lista.
bool lista_insertar_ultimo( lista_t *lista, void *dato );

// Borra el primer elemento de la lis ta.Si la lista tiene elementos
// se quita el primero de la misma y se devuelve su valor.Si está vacía
// devuelve NULL.
// Pre: la lista fue creada.
// Post: se devolvió el elemento borrado de la lista, la lista contiene
// un elemento menos si no estaba vacía.
void *lista_borrar_primero( lista_t *lista );

// Obtiene el valor del primer elemento de la lista.Si la lista
// tiene elementos, se devuelve el valor del primero, si está vacía
// devuelve NULL.
// Pre: la lista fue creada.
// Post: se devolvió el primer elemento de la lista , cuando no está 
// vacía.
void *lista_ver_primero(const lista_t *lista );

// Obtiene el valor del ultimo elemento de la lista. Si la lista
// tiene elementos, se devuelve el valor del ultimo, si está vacía
// devuelve NULL.
// Pre : la lista fue creada.
// Post: se devolvió el ultimo elemento de la lista , cuando no está
// vacía.
void *lista_ver_ultimo( const lista_t *lista );


// Obtiene la longitud de la lista.
// Pre: lista fue creada.
size_t lista_largo( const lista_t *lista );

// Destruye la lista. Si se recibe la funcion destruir_dato por 
// parámetro, para cada uno de los elementos de la lista llama 
// a destruir_dato.
// Pre : la lista fue creada
// Post: se  eliminaron todos los elementos de la lista.
void lista_destruir( lista_t *lista, void (*destruir_dato)( void*) );

/* ***PRIMITIVAS DEL ITERADOR*** */

// Crea un iterador de la lista.
// Pre : la lista fue creada.
// Post: devuelve un nuevo iterador. 
lista_iter_t *lista_iter_crear( lista_t *lista );

// Avanza una posición sobre la lista.
// Pre: la lista fue creada y el iterador fueron creados.
// Post: devuelve true si no se llegó al final, en caso contrario
// se devuelve false.
bool lista_iter_avanzar( lista_iter_t *iter );

// Obtiene el valor del elemento en la posición actual en la lista.
// Pre: la lista y el iterador fueron creados
// Post: se devolvió el elemento en la posición actual si no se llegó
// al final de la lista . 
void* lista_iter_ver_actual( const lista_iter_t *iter );

// Devuelve true si se llegó al final de la lista,en caso contrario
// false.
// Pre: la lista y el iterador fueron creados
bool lista_iter_al_final( const lista_iter_t *iter );

// Destruye el iterador.
// Pre: el iterador fue creado.
// Post: se eliminaron todos los elementos del iterador.
void lista_iter_destruir( lista_iter_t *iter );

// Se agrega un elemento a la lista en la posición actual.
// Devuelve false en caso de error.
// Pre: la lista y el iterador fueron creados.
// Post: Se agregó un nuevo elemento a la lista.
bool lista_iter_insertar( lista_iter_t *iter, void *dato );

// Se elimina un elemento de la lista en la posición actual.
// Pre: la lista y el iterador fueron creados.
// Post: Se devuelve el elemento eliminado.
void* lista_iter_borrar( lista_iter_t *iter );

// Recorre la lista aplicando la accion definida en la funcion visitar
// pasada por parametro.Segun la implementacion del TAD, el
// parametro extra pasado por referencia si no es NULL puede ser 
// modificado de acuerdo a la funcion visitar.
// Pre : la lista y el iterador fueron creados.
void lista_iterar( lista_t *lista, bool ( *visitar ) ( void* dato , void* extra ),void* extra );

/* pruebas unitarias alumno*/
void pruebas_lista_alumno( void );

#endif
