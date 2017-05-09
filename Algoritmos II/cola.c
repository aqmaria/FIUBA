
#include "cola.h"
#include <stdlib.h>

typedef struct nodo 
{
	void* dato;
	struct nodo* siguiente;
} nodo_t;

struct cola 
{
	nodo_t* primero;
	nodo_t* ultimo;
};

cola_t* cola_crear( void ) 
{
	cola_t* cola = malloc( sizeof( cola_t ) );
	if( ! cola )
		return NULL;
	cola-> primero = NULL;
	cola-> ultimo = NULL;
	return cola;
}

bool cola_esta_vacia( const cola_t *cola )
{
	return( !( cola-> primero ) );
}

bool cola_encolar( cola_t *cola, void* valor )
{
	nodo_t* nuevo = malloc( sizeof( nodo_t ) );
	if( !nuevo )
		return false;
	if( cola_esta_vacia( cola ) )
		  cola-> primero = nuevo;
	else
		cola-> ultimo ->siguiente = nuevo ;
	cola-> ultimo = nuevo;
	nuevo->dato = valor;
	nuevo->siguiente = NULL;
	return true;
}

void* cola_ver_primero( const cola_t *cola )
{
	if( cola_esta_vacia( cola ) )
		return NULL;
	return( cola->primero->dato );
}

void* cola_desencolar( cola_t *cola )
{
	if( cola_esta_vacia( cola ) )
		return NULL;
	void* elemento = cola_ver_primero( cola );
	nodo_t* nodo_aux = cola-> primero;
	cola-> primero = nodo_aux-> siguiente;
	free( nodo_aux );
	return elemento;
}

void cola_destruir( cola_t *cola, void destruir_dato( void* ) )
{
	void* dato;
	while( !(cola_esta_vacia( cola ) ) )
	{
		dato = cola_desencolar( cola );
		if( destruir_dato )
			destruir_dato( dato );
	}
	free( cola );
}
