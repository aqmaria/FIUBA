#include "lista.h"
#include <stdlib.h>
#include <stdbool.h>
typedef struct nodo{
	void* dato;
	struct nodo* siguiente;
}nodo_t;

struct lista{
	nodo_t* primero;
	nodo_t* ultimo;
	size_t largo;
};

struct lista_iter{
	lista_t* lista;
	nodo_t* actual;
	nodo_t* anterior;
};

lista_t* lista_crear( void ) 
{
	lista_t* lista = malloc( sizeof( lista_t ) );
	if( ! lista )
		return NULL;
	lista-> primero = NULL;
	lista-> ultimo = NULL;
	lista-> largo = 0;
	return lista;
}

bool lista_esta_vacia( const lista_t* lista )
{
	return( !( lista-> primero ) );
}

bool lista_insertar_primero( lista_t *lista, void* valor )
{
	nodo_t* nuevo = malloc( sizeof( nodo_t ) );
	if( !nuevo )
		return false;
	if( lista_esta_vacia( lista ) ){
		  lista-> primero = nuevo;
		  lista-> ultimo = nuevo;
		  nuevo->siguiente = NULL;
	}
	else{
		nodo_t* actual = lista->primero;
		lista-> primero = nuevo ;
		nuevo->siguiente = actual;
	}
	nuevo->dato = valor;
	++ ( lista->largo ); 
	return true;
}

bool lista_insertar_ultimo( lista_t *lista, void* valor )
{
	nodo_t* nuevo = malloc( sizeof( nodo_t ) );
	if( !nuevo )
		return false;
	if( lista_esta_vacia( lista ) ){
		  lista-> primero = nuevo;
		  lista-> ultimo = nuevo;
		  nuevo->siguiente = NULL;
	}
	else{
		nodo_t* actual = lista->ultimo;
		actual->siguiente = nuevo;
		lista-> ultimo = nuevo;
		nuevo->siguiente = NULL;
	}
	nuevo->dato = valor;
	++ ( lista->largo );
	return true;
}

void* lista_ver_primero(const lista_t* lista )
{
	if( lista_esta_vacia( lista ) )
		return NULL;
	return( lista->primero->dato );
}

void* lista_ver_ultimo( const lista_t* lista )
{
	if( lista_esta_vacia( lista ) )
		return NULL;
	return( lista->ultimo->dato );
}

size_t lista_largo( const lista_t *lista ){
	return lista->largo;
}


void* lista_borrar_primero( lista_t* lista )
{
	if( lista_esta_vacia( lista ) )
		return NULL;
	-- ( lista->largo );
	void* elemento = lista_ver_primero( lista );
	nodo_t* actual = lista-> primero;
	lista-> primero = actual-> siguiente;
	if( lista->largo < 2 )
		lista-> ultimo = lista->primero;
	free( actual );
	return elemento;
}

void lista_destruir( lista_t *lista, void destruir_dato( void* ) )
{
	void* dato;
	while( !(lista_esta_vacia( lista ) ) )
	{
		dato = lista_borrar_primero( lista );
		if( destruir_dato )
			destruir_dato( dato );
	}
	free( lista );
}


lista_iter_t *lista_iter_crear( lista_t *lista ){
	lista_iter_t* iter = malloc( sizeof( lista_iter_t ) );
	if( ! iter )
		return NULL;
	iter->lista = lista;
	if( !lista_esta_vacia( lista ) )
		iter-> actual = lista->primero;
	else
		iter->actual = NULL;
	iter->anterior = NULL;
	return iter;
}

bool lista_iter_al_final( const lista_iter_t *iter ){
	return !( iter->actual );
}


bool lista_iter_avanzar( lista_iter_t *iter ){
	if( lista_iter_al_final( iter ) )
		return false;
	iter->anterior = iter->actual;
	iter->actual = iter->actual->siguiente;
	return true; 
}	

void* lista_iter_ver_actual( const lista_iter_t *iter ){
	if( lista_iter_al_final( iter ) )
		return NULL;
	return( iter->actual->dato );
}	

void lista_iter_destruir( lista_iter_t *iter ){
	free( iter );
}

bool lista_iter_insertar( lista_iter_t *iter, void *dato ){
	///insertar al principio de la lista
	if( iter->lista->primero == iter->actual ){
		if( !lista_insertar_primero( iter->lista, dato ) )
			return false;
		iter->actual = iter->lista->primero;
		return true;
	}
	///insertar al final de lista
	if( !iter->actual ){
		if ( !lista_insertar_ultimo( iter->lista,dato ) )
			return false;
		iter->actual= iter->lista->ultimo;
		return true;
	}
	///insertar despues del principio y antes del final
	nodo_t* nuevo = malloc( sizeof( nodo_t ) );
	if( !nuevo )
		return false;
	nuevo->dato = dato;
	nuevo->siguiente = iter->actual;
	iter->anterior->siguiente = nuevo;
	iter->actual = nuevo;
	++ ( iter->lista->largo ); 
	return true;
}

void* lista_iter_borrar( lista_iter_t *iter ){
	if( lista_iter_al_final(iter ) )
		return NULL;
	void* dato;
	///borrar el primer elemento 
	if( iter->actual == iter->lista->primero ){
		dato = lista_borrar_primero( iter->lista );
		iter->actual= iter->lista->primero;
		return dato;
	}
	nodo_t* actual = iter->actual;
	dato = actual->dato;
	iter->anterior->siguiente=actual->siguiente;
	iter->actual = iter->actual->siguiente;
	///borrar el ultimo elemento en lista de elementos >1
	if( lista_largo( iter->lista ) == 2 )
		iter->lista->ultimo = iter->lista->primero;	
	free( actual );
	--( iter->lista->largo ); 
	return dato;
}

void lista_iterar( lista_t *lista, bool ( *visitar ) ( void* dato , void* extra ),void* extra ){
	lista_iter_t *iter = lista_iter_crear(lista);
	bool continuar = true;
    while (!lista_iter_al_final(iter) && continuar )
    {
        void *elemento = lista_iter_ver_actual(iter);
        continuar = visitar( elemento, extra );
        lista_iter_avanzar(iter);
    }
    lista_iter_destruir(iter);
}
