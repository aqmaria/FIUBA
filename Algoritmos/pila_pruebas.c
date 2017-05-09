#include "testing.h"
#include "pila.h"
#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>

/* Algoritmos 2 Wachenchauzer, Maria Aquino 99871 */

/* ******************************************************************
 *                   PRUEBAS UNITARIAS ALUMNO
 * *****************************************************************/
void pruebas_pila_alumno(void){
/*Pruebas para un puntero a pila_t inicializado en NULL*/

	printf("\nINICIO DE PRUEBAS CON PUNTERO A PILA NULL \n");
    pila_t* pila = NULL;    
    print_test("Puntero inicializado a NULL",  pila == NULL);
    /* pila_apilar,pila_ver_tope,pila_desapilar deben devolver false */
    int int_1 = 1;
    print_test( "pila_apilar es false" , !pila_apilar( pila, &int_1 ) );
    print_test( "pila_ver_tope es NULL" , pila_ver_tope( pila ) == NULL );
    print_test( "pila_desapilar es NULL" ,pila_desapilar( pila ) == NULL );
        
/*Pruebas para una instancia de pila_t con direccion asignada por memoria dinámica,
sin apilar ningun elemento */

	printf("\nINICIO DE PRUEBAS CON PILA VACIA \n");
	pila = pila_crear();
	print_test( "La pila fue creada" , pila!=NULL );
    print_test( "pila_ver_tope es NULL" , pila_ver_tope( pila ) == NULL );
    print_test( "pila_desapilar es NULL" ,pila_desapilar( pila ) == NULL );
    print_test( "La pila esta vacia ", ( pila_esta_vacia(pila) ) );
  
/* Pruebas para comprobar la devolucion del ultimo puntero apilado */

	printf("\nINICIO DE PRUEBAS VER TOPE \n");
	/*declaracion de variables utilizadas*/
	float float_1 = 3;
	/*Inicio de pruebas*/
	print_test( "Apilo un puntero a int  " ,pila_apilar( pila, &int_1) );
	print_test( "Apilo un puntero a float " ,pila_apilar( pila, &float_1) );
	void* tope = pila_ver_tope( pila );
	print_test( "El ultimo elemento es el tope", tope == &float_1 );
	
/*Pruebas de volumen */
    printf("\nINICIO DE PRUEBAS DE VOLUMEN \n");
	/*declaracion de variable utilizada */
	char char_1[] = "007";
	for( size_t i=0 ; i< 4 ; i++ )
		print_test( "Apilo un puntero " ,pila_apilar( pila, &char_1) );
	for( size_t i=6 ; i> 0 ; i-- )
		print_test( "Desapilo un puntero  " ,pila_desapilar( pila )!=NULL );
	print_test( "La pila esta vacia", pila_esta_vacia( pila ) );
	print_test( "pila_desapilar es NULL", pila_desapilar( pila )==NULL );
	print_test( "pila_ver_tope es NULL" ,pila_ver_tope( pila )==NULL );
	pila_destruir( pila );
    print_test( "La pila fue destruida",true );

}

