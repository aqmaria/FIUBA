#include <stdlib.h>
#include <stdbool.h>
#include "pila.h"
#include <stdio.h>
/* Algoritmos 2 Wachenchauzer, Maria Aquino 99871 */

/* Definición del struct pila proporcionado por la cátedra.
 */
 
struct pila {
    void** datos;
    size_t cantidad;  // Cantidad de elementos almacenados.
    size_t capacidad;  // Capacidad del arreglo 'datos'.
};


/* ***************************************************************
*                     FUNCIONES AUXILIARES                       *
* ***************************************************************/
#define TAM_INICIAL 3 

/* Devuelve true si se pudo redimensionar la pila 
 * Pre : la pila fue creada y no es vacía 
 * Post : la capacidad de la pila fue modificada de acuerdo al parametro nueva_capacidad*/
bool pila_redimensionar(pila_t* pila, size_t nueva_capacidad )
{
	void** datos_nuevo =realloc( pila->datos, nueva_capacidad * sizeof( void* ) );
	if( datos_nuevo == NULL )
		return false;
	pila->datos = datos_nuevo;
	pila->capacidad = nueva_capacidad;
	return true;
}

/* *****************************************************************
 *                    PRIMITIVAS DE LA PILA
 * *****************************************************************/


pila_t* pila_crear( void ) 
{
	pila_t* pila = malloc( sizeof( pila_t ) );
	if( pila == NULL )
		return false;
    pila->datos = (void**)malloc( TAM_INICIAL * sizeof( void* ) );
    if (pila->datos== NULL) 
		return false;
    pila->cantidad = 0;
    pila->capacidad = TAM_INICIAL;
    return pila;
} 

void pila_destruir( pila_t* pila )
{  
	if( ! ptr_es_NULL( pila ) ) {
		free( pila->datos );
		free( pila );
	}
}

bool pila_apilar( pila_t* pila , void* valor )
{
    if( pila->cantidad > pila->capacidad )
    {
		if(! pila_redimensionar( pila, ( pila->capacidad) * 2 ) )
			return false;
	}
    pila->datos[pila->cantidad++] = valor;
    return true;
}

bool pila_esta_vacia( const pila_t* pila ){
	return pila->cantidad==0;
}

void* pila_ver_tope( const pila_t* pila ){
   return( pila->datos[ pila->cantidad -1 ] );
}

void* pila_desapilar( pila_t* pila )
{
	if( pila->cantidad < ( pila->capacidad/4 ) )
		pila_redimensionar( pila, pila->capacidad / 2 );
	pila->cantidad--;
	return( pila->datos[ pila->cantidad ] );
}

