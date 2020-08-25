///ALGORITMOS II WACHENCHAUZER
///AQUINO MARIA 99871

#include "heap.h"
#include <stdlib.h>

#define LARGO_INICIAL 20
#define FACTOR_DE_OCUPACION 80
#define FACTOR_DE_DESOCUPACION 25

/* DEFINICION DE LA ESTRUCTURA */
struct heap {
	void** arreglo;
	size_t tam;
	size_t cant;
	cmp_func_t cmp;
};

/******* FUNCIONES AUXILIARES*******/
void swap( void* vector[], size_t pos_uno, size_t pos_dos ) {
	void* dato_aux = NULL;
	dato_aux = vector[pos_uno];
	vector[pos_uno] = vector[pos_dos];
	vector[pos_dos] = dato_aux;
}

void heap_upheap( void* vector[], size_t n, size_t pos, cmp_func_t cmp){
	if( pos == 0 ) return;
	size_t padre = ( pos -1 )/2;
	if ( cmp( vector[pos], vector [padre] ) <= 0)return; 
	swap( vector, pos , padre ); 
	heap_upheap( vector, n, padre, cmp );
}

void heap_downheap( void* vector[], size_t n, size_t pos, cmp_func_t cmp ) {
	if ( pos >= n ) return;
	size_t mayor = pos;
	size_t hijo_izq = pos*2 + 1;
	size_t hijo_der = hijo_izq + 1;
	if ( ( hijo_izq < n ) && ( cmp( vector[mayor],vector[hijo_izq] ) < 0 ) ) mayor = hijo_izq;
	if ( ( hijo_der < n ) && ( cmp( vector[mayor],vector[hijo_der] ) < 0 ) ) mayor = hijo_der;
	if ( mayor != pos ) {
		swap( vector, pos, mayor );
		heap_downheap( vector, n, mayor, cmp );
	}
}

void heap_heapify( void* vector[], size_t n, cmp_func_t cmp ) {
	for (size_t i = n/2  ; i > 0; i-- )
		heap_downheap( vector , n , i-1, cmp );
}

bool heap_redimensionar( heap_t* heap, size_t tam_nuevo ) {
    void** datos_nuevo = realloc( heap->arreglo , tam_nuevo * sizeof( void* ) );
    if ( !datos_nuevo ) return false;
    heap->arreglo = datos_nuevo;
    heap->tam = tam_nuevo;
    return true;
}


/* DEFINICION DE PRIMITIVAS */

heap_t *heap_crear(cmp_func_t cmp) {
	if ( !cmp ) return NULL;
	heap_t* heap = malloc( sizeof( heap_t ) );
	if ( !heap ) return NULL;
	void* arreglo = malloc( sizeof(  void* ) * LARGO_INICIAL );
	if( !arreglo ) {
		free( heap );
		return NULL;
	}
	heap->arreglo = arreglo;
	heap->tam = LARGO_INICIAL;
	heap->cant = 0;
	heap->cmp = cmp;
	return heap;
}

void heap_destruir(heap_t *heap, void destruir_elemento( void *e ) ) {
	for( size_t i = 0 ; i<heap->cant && destruir_elemento ;i++ )
		destruir_elemento( heap->arreglo[i] );
	free( heap->arreglo );
	free( heap );
}

size_t heap_cantidad(const heap_t *heap) {
	return heap->cant;
}

bool heap_esta_vacio(const heap_t *heap) {
	return !heap->cant;
}

heap_t *heap_crear_arr( void *arreglo[], size_t n, cmp_func_t cmp ){
	heap_t* heap = heap_crear( cmp );
	if( !heap ) return NULL;
	void** arr = malloc( sizeof( void*)* n);
	if(! arr ){
		free(heap);
		return NULL;
	}
	heap->arreglo = arr;
	heap->cant = n;
	heap->cmp = cmp;
	for( size_t i=0; i< n ; i++ ){
		heap->arreglo[i] = arreglo[i];
	}
	heap_heapify( heap->arreglo,n, cmp);
	return heap;
}

bool heap_encolar( heap_t *heap, void *elem ) {
	if ( heap->cant >=  ( ( heap->tam )*FACTOR_DE_OCUPACION/100 ) ){ 
		if ( !heap_redimensionar( heap, heap->tam * 2 ) )
				return false;
	}
	heap->cant++;
	heap->arreglo[ heap->cant - 1 ] = elem ;
	heap_upheap( heap->arreglo , heap->cant, heap->cant-1, heap->cmp);
    return true;
}

void* heap_ver_max( const heap_t* heap ) {
	if ( heap_esta_vacio( heap ) ) return NULL;
	return heap->arreglo[0];
}

void *heap_desencolar( heap_t *heap ) {
	if( heap_esta_vacio( heap ) ) return NULL;
	void* max = heap_ver_max( heap );
	if ( ( heap->cant <= heap->tam * FACTOR_DE_DESOCUPACION/100 ) && ( heap->tam > LARGO_INICIAL ) )
		heap_redimensionar( heap, (heap->tam)/2 );
	heap->arreglo[0] = heap->arreglo[heap->cant-1] ;
	heap->cant--;
	heap_downheap( heap->arreglo, heap->cant, 0, heap->cmp );
	return max;
}

void heap_sort(void* elementos[], size_t cant, cmp_func_t cmp ) {
	heap_heapify( elementos, cant, cmp );
	for ( size_t i = cant -1 ; i > 0 ; i -- ) {
		swap( elementos, 0, i );
		heap_downheap( elementos, i, 0, cmp );
	}
}
