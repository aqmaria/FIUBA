
#include "testing.h"
#include "cola.h"
#include "pila.h"

#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>

/****************************************************************
*				FUNCIONES AUXILIARES
***************************************************************/
/*Funcion para pasar como parametro a cola_destruir
 * Pre = pila fue creada
 * Post = pila destruida
 */
void pila_destruir_wrapper( void* pila ){
	pila_destruir( (pila_t*)pila );
}


/* ******************************************************************
 *                   PRUEBAS UNITARIAS ALUMNO
 * *****************************************************************/

/*Pruebas para una instancia de cola_t con direccion asignada por memoria dinámica,
sin encolar ningun elemento */
void pruebas_cola_vacia( void ){
	cola_t* cola = cola_crear();
	printf("\nINICIO DE PRUEBAS CON COLA VACIA \n");
	print_test( "cola fue creada" , cola!=NULL );
    print_test( "cola_ver_primero es NULL" , cola_ver_primero( cola ) == NULL );
    print_test( "cola_desencolar es NULL" ,cola_desencolar( cola ) == NULL );
    print_test( "La cola esta vacia ", cola_esta_vacia( cola )  );
    print_test( "Cola destruida ", true  );
    cola_destruir( cola,NULL );
}
  
/*Pruebas de volumen */
void pruebas_cola_volumen( void ){
	printf( "\nINICIO DE PRUEBAS DE VOLUMEN \n" );
	cola_t* cola = cola_crear();
	print_test( "cola fue creada" , cola!=NULL );
	/*declaracion de variable utilizada */
	char char_1[] = "a";
	for( size_t i=0 ; i< 1000 ; i++ )
		cola_encolar( cola, &char_1 );
	print_test( "\nEncolo elementos de memoria estatica\n",true );
	for( size_t i=1000 ; i> 0 ; i-- )
		cola_desencolar( cola );
	print_test( "Desencolo los elementos",true );
	print_test( "La cola esta vacia", cola_esta_vacia( cola ) );
	print_test( "cola_desencolar es NULL", cola_desencolar( cola )==NULL );
	print_test( "cola_ver_primero es NULL" ,cola_ver_primero( cola )==NULL );
	print_test( "Cola destruida ", true  );
    cola_destruir( cola,NULL );
}
/* Pruebas para comprobar la devolucion del primer elemento encolado */
void pruebas_cola_ver_primero( void ){
	printf( "\nINICIO DE PRUEBAS VER PRIMER ELEMENTO \n" );
	cola_t* cola = cola_crear();
	print_test( "cola fue creada" , cola!=NULL );
	printf( "\nEncolo elementos de memoria estatica\n" );
	char char_1[] = "a";
	cola_encolar( cola, &char_1  );
	float float_1 = 3;
	for( size_t i=0 ; i< 100 ; i++ )
		cola_encolar( cola, &float_1 );
	void* primero = cola_ver_primero( cola );
	print_test( "El primer elemento encolado es el primero de la cola", primero == &char_1 );
	print_test( "Cola destruida ", true  );
    cola_destruir( cola,NULL );
}
/*Pruebas para funcion pasada por parametro a cola_destruir para eliminar datos*/
void pruebas_cola_destruir( void ){
	printf( "\nINICIO DE PRUEBAS FUNCION AUXILIAR PARA DESTRUIR DATOS\n" );
    printf( "Creo una nueva cola\n" );
    cola_t* cola = cola_crear();
    pila_t* pila_1 = pila_crear();
    pila_t* pila_2 = pila_crear();
    float float_1 = 3;
    pila_apilar( pila_1, &float_1 );
    pila_apilar( pila_1, &float_1 );
    pila_apilar( pila_2, &float_1 );
    pila_apilar( pila_2, &float_1 );
    print_test( "Creo varias pilas con elementos ",true );
    cola_encolar( cola , pila_1 );
    cola_encolar( cola, pila_2 );
    print_test( "Encolo pilas",true );
    cola_destruir( cola , pila_destruir_wrapper );
    print_test( "La cola fue destruida utilizando una funcion auxiliar para destruir los datos",true);
}

void pruebas_cola_alumno( void ){
	pruebas_cola_vacia();
    pruebas_cola_volumen();
    pruebas_cola_ver_primero();
    pruebas_cola_destruir();
}
