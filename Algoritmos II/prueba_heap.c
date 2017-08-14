///Algoritmos II Wachenchauzer Aquino Maria 99871
#include "heap.h"
#include "testing.h"
#include <time.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>


///funcion auxiliar de comparacion
int cmp_ints(const void* a, const void* b) {
	if ( *(int* )a > *( int* )b ) return 1;
	if( *( int* )a == *( int* )b ) return 0;
	return -1;
}


void pruebas_heap_vacio(){
	heap_t* heap = heap_crear( cmp_ints );
	print_test("El heap fue creado, y no es NULL", (heap ));
	print_test("Desencolar es NULL",  ! heap_desencolar(heap) );
	print_test("El heap esta vacio", heap_esta_vacio(heap));
	print_test("El maximo del heap es NULL", !heap_ver_max(heap) );
	heap_destruir(heap, NULL);	
}
void pruebas_heap_pocos_elem(){
	int arreglo[5] ={ 5,8,2,3,10};
	heap_t* heap = heap_crear(cmp_ints);
	print_test("Encolar 5 es true", heap_encolar( heap, &arreglo[0] ) );
	print_test("El maximo del heap es 5", heap_ver_max( heap ) == &arreglo[0] );
	print_test("Encolar 8 es true", heap_encolar( heap, &arreglo[1]));
	print_test("El maximo del heap es 8", heap_ver_max( heap) == &arreglo[1] );
	print_test("Encolar 2 es true", heap_encolar( heap, &arreglo[2] ) );
	print_test("El maximo del heap es 8", heap_ver_max( heap ) == &arreglo[1] );
	print_test("Encolar 3 es true", heap_encolar( heap, &arreglo[3] ) );
	print_test("El maximo del heap es 8", heap_ver_max( heap) == &arreglo[1] );
	print_test("Encolar 10 es true", heap_encolar( heap, &arreglo[4] ) );
	print_test("El maximo del heap es 10", heap_ver_max( heap) == &arreglo[4] );
	heap_destruir(heap, NULL);

}

///funcion auxiliar para mezclar un arreglo
void randomize ( int arr[], int n )
{
    srand ( (int)time(NULL) );
    for (int i = n-1; i > 0; i--){
        int j = rand() % (i+1);
        int aux = arr[i];
        arr[i] = arr[j];
        arr[j]=aux;
    }
}

void pruebas_heap_volumen( size_t largo ){
	bool ok= true;
	heap_t* heap = heap_crear( cmp_ints );
	int arreglo[largo];
	int arreglo_shuffle[largo];
	///los dos arreglos van a tener valores de menor a mayor en un principio
	for ( int i = 0; i < (int)largo ; i++){
		arreglo[i]= i;
		arreglo_shuffle[i] = i;
	}
	///mezclo los elementos de un solo arreglo 
	randomize( arreglo_shuffle, (int)largo );
	//los encolo..
	for( int i=0; i< largo ; i++ )
		heap_encolar( heap, &arreglo_shuffle[i] );
	/// cuando los desencolo verifico que los valores coincidan con los del arreglo sin mezclar
	/// en orden de mayor a menor
	for( int j=( int )largo-1; j>0  && ok ; j-- ){
		int* elemento = heap_desencolar( heap ); 
		if(  *elemento != arreglo[j] ) ok=false;
	}
	print_test("Encolar y desencolar muchos elementos es true", ok );
	heap_destruir( heap , NULL);
}

void pruebas_heap_sort( size_t largo_arr ){
	int arreglo[largo_arr];
	int arreglo_shuffle[largo_arr];
	for ( int i = 0; i < (int)largo_arr ; i++){
		arreglo[i]= i;
		arreglo_shuffle[i] = i;
	}
	///mezclo un arreglo
	randomize( arreglo_shuffle, (int)largo_arr );
	///creo un arreglo de punteros para poner los punteros de cada posicion del array mezclado
	void** arreglo_punteros_shuffle = malloc( sizeof( void* ) * largo_arr );
	for( int i=0 ; i<(int )largo_arr; i++){
		arreglo_punteros_shuffle[i]=&arreglo_shuffle[i];
	}
	heap_sort( arreglo_punteros_shuffle, largo_arr, cmp_ints );
	bool ok=true;
	///verifico que se haya ordenado de menor a mayor 
	for( int j=0; j<largo_arr  && ok ; j++ ){ 
		if(  *(int*)arreglo_punteros_shuffle[j]!=arreglo[j] ) ok=false;
	}
	free( arreglo_punteros_shuffle );
	print_test("Se ordeno arreglo de menor a mayor", ok );
}


void pruebas_heap_alumno(){
	pruebas_heap_vacio();
	pruebas_heap_pocos_elem();
	pruebas_heap_volumen(10000);
	pruebas_heap_sort(10000);
}


