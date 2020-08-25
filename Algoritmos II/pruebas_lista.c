
#include "testing.h"
#include "lista.h"

#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>

/* Funciones auxiliares */

/* Suma dos numeros pasados por parametro ,devuelve resultado en extra
 Post: Resultado fue devuelto en extra
 */

bool sumar_items(void* elemento , void* extra){
	*(int*)extra += *(int*)elemento;
	return true;
}

/* Imprime por pantalla un entero ,devuelve false si se alcanzó
 * la cantidad de impresiones especificadas en extra.*/
 
bool imprimir_items( void* elemento,void* extra){
	if( *(int*)extra <= 0 )
		return false;
	( *(int*)extra ) --;
	printf( "%d ",*(int*)elemento );
	return true;
}
/* Suma numeros de una lista que contiene enteros
 Pre : lista fue creada y no es vacia, iterador fue creado, 
 * funcion auxiliar de sumar_items fue creada.
 */

void sumar_iter_interno( lista_t* lista ){
	int suma = 0;
	lista_iterar( lista, sumar_items,&suma);
	printf("Total suma = %d\n",suma );
}

/* Imprime por pantalla una cantidad determinada de enteros de una lista. 
 Pre : lista fue creada y no es vacia, iterador fue creado, 
 * funcion auxiliar imprimir_items fue creada.
 */
void imp_iter_interno( lista_t* lista ){
	int contador = 3;
	lista_iterar( lista ,imprimir_items,&contador );
}

/* ******************************************************************
 *                   PRUEBAS UNITARIAS ALUMNO
 * *****************************************************************/

void pruebas_lista_vacia( void ){
	lista_t* lista = lista_crear();
	printf("\nINICIO DE PRUEBAS CON LISTA VACIA \n");
    print_test( "lista_ver_primero es NULL" , !lista_ver_primero( lista ) );
    print_test( "lista_ver_ultimo es NULL" , !lista_ver_ultimo( lista )  );
    print_test( "lista_borrar_priemro es NULL" ,!lista_borrar_primero( lista ));
    print_test( "La lista esta vacia ", lista_esta_vacia( lista )  );
    lista_destruir( lista,NULL );
}
  
void pruebas_lista_volumen( void ){
	printf( "\nINICIO DE PRUEBAS DE VOLUMEN" );
	lista_t* lista = lista_crear();
	/*declaracion de variable utilizada */
	char char_1[] = "a";
	for( size_t i=0 ; i< 1000 ; i++ )
		lista_insertar_primero( lista, &char_1 );
	print_test( "\nInserto elementos\n",true );
	for( size_t i=1000 ; i> 0 ; i-- )
		lista_borrar_primero( lista );
	print_test( "\nBorro todos los elementos",true );
    print_test( "lista_ver_primero es NULL" , !lista_ver_primero( lista )  );
    print_test( "lista_ver_ultimo es NULL" , !lista_ver_ultimo( lista ) );
    print_test( "lista_borrar_priemro es NULL" ,!lista_borrar_primero( lista ) );
    print_test( "La lista esta vacia ", lista_esta_vacia( lista )  );
    lista_destruir( lista,NULL );
}

void pruebas_lista_ver_primero( void ){
	printf( "\nINICIO DE PRUEBAS VER PRIMER ELEMENTO" );
	lista_t* lista = lista_crear();
	printf( "\nAgrego un elemento en la primera posicion" );
	char char_1[] = "a";
	printf( "\nAgrego elementos en la ultima posicion" );
	char char_2[] = "b";
	lista_insertar_primero( lista, &char_1  );
	for( size_t i=0 ; i< 100 ; i++ )
		lista_insertar_ultimo( lista, &char_2  );
	void* primero = lista_ver_primero( lista );
	print_test( "El elemento agregado en la primera posicion es el primero de la lista", primero == &char_1 );
    lista_destruir( lista,NULL );
    printf( "\nINICIO DE PRUEBAS VER ULTIMO ELEMENTO \n" );
	lista = lista_crear();
	printf( "\nAgrego un elemento en la ultima posicion\n" );
	printf( "\nAgrego elementos en la primera posicion\n" );
	lista_insertar_ultimo( lista, &char_1  );
	for( size_t i=0 ; i< 100 ; i++ )
		lista_insertar_primero( lista, &char_2  );
	void* ultimo = lista_ver_ultimo( lista );
	print_test( "El elemento agregado en la ultima posicion es el ultima de la lista", ultimo == &char_1 );
    lista_destruir( lista,NULL );
}

/* PRUEBAS ITERADOR */
void pruebas_lista_iterador_lista_vacia(){
	printf("\nINICIO DE PRUEBAS ITERADOR CON LISTA VACIA\n");
	lista_t* lista = lista_crear();
	lista_iter_t* iter = lista_iter_crear(lista);
	print_test( "lista_iter_avanzar es false ", !lista_iter_avanzar( iter ) );
	print_test( "lista_iter_al_final es true ", lista_iter_al_final( iter ) );
	print_test( "lista_iter_ver_actual es NULL ", !lista_iter_ver_actual( iter ) );
	char char_1[] = "abcd";
	print_test( "Inserto un elemento con el iterador", lista_iter_insertar( iter, &char_1 ) );
	print_test( "lista_iter_ver_actual es distinto NULL ", lista_iter_ver_actual( iter ) );
	lista_destruir( lista, NULL );
	lista_iter_destruir( iter );
}

void pruebas_lista_iterador_volumen(){
	printf( "\nINICIO DE PRUEBAS DE VOLUMEN PARA ITERADOR\n" );
	lista_t* lista = lista_crear();
	lista_iter_t* iter = lista_iter_crear(lista);
	float float_1 = 3;
	printf("\nAgrego elementos a la lista con el iterador\n");
	for( size_t i=0 ; i< 1000 ; i++ )
		lista_iter_insertar( iter, &float_1  );
	lista_iter_destruir( iter );
	iter = lista_iter_crear(lista);
	print_test( "lista_iter_al_final es false ", !lista_iter_al_final( iter ) );;
	printf("\nBorro elementos de la lista con el iterador\n");
	for( size_t i=0 ; i<1000 ; i++ )
		lista_iter_borrar( iter );
	lista_iter_destruir( iter );
	iter = lista_iter_crear(lista);
	print_test( "lista_iter_avanzar es false ", !lista_iter_avanzar( iter ) );
	print_test( "lista_iter_al_final es true ", lista_iter_al_final( iter ) );
	print_test( "lista_iter_ver_actual es NULL ", !lista_iter_ver_actual( iter ) );
	lista_iter_destruir( iter );
	lista_destruir( lista ,NULL);
}

void pruebas_lista_iterador_iterar(){
	printf( "\nINICIO DE PRUEBAS ITTERADOR\n" );
	lista_t* lista = lista_crear();
	lista_iter_t* iter = lista_iter_crear(lista);
	int int_1 = 3, int_2 = 12, int_3 = 99;
	print_test( "Inserto primer elemento" ,lista_iter_insertar( iter , &int_2) );
	print_test( "Voy al final",lista_iter_avanzar( iter ) );
	print_test( "Inserto al final" ,lista_iter_insertar( iter , &int_3) );
	print_test( "Inserto en el medio",lista_iter_insertar( iter,&int_1) );
	lista_iter_destruir( iter );
	iter = lista_iter_crear(lista);
	print_test( "El primer elemento insertado esta primero",lista_iter_ver_actual(iter)== &int_2 );
	lista_iter_avanzar( iter );
	print_test( "El ultimo elemento insertado esta en el medio",lista_iter_ver_actual(iter)== &int_1 );
	lista_iter_avanzar( iter );
	print_test( "El elemento insertado al final de la lista esta ultimo",lista_iter_ver_actual(iter)== &int_3 );
	lista_iter_destruir( iter );
	iter = lista_iter_crear(lista);
	lista_iter_avanzar( iter );
	print_test( "Borro el elemento del medio ",lista_iter_borrar( iter )== &int_1 );
	lista_iter_destruir( iter );
	iter = lista_iter_crear(lista);
	lista_iter_avanzar( iter );
	print_test( "Borro ultimo elemento",lista_iter_borrar( iter )== &int_3 );
	lista_iter_destruir( iter );
	iter = lista_iter_crear(lista);
	print_test( "Borro primer elemento",lista_iter_borrar( iter )==&int_2 );
	lista_iter_destruir( iter );
	lista_destruir( lista,NULL );
	lista = lista_crear();
	iter = lista_iter_crear( lista );
	printf("\nAgrego elementos a la lista con el iterador\n");
	for( size_t i=0 ; i<12 ; i++ )
		lista_iter_insertar( iter, &int_1  );
	lista_iter_destruir( iter );
	sumar_iter_interno( lista );
	print_test("Prueba iterador interno sin corte",true );
	imp_iter_interno( lista );
	print_test("Prueba iterador interno con corte",true );
	lista_destruir( lista ,NULL);
	

}

void pruebas_lista_alumno( ){
	pruebas_lista_vacia( ) ;
	pruebas_lista_volumen( );
	pruebas_lista_ver_primero( );
	pruebas_lista_iterador_lista_vacia();
	pruebas_lista_iterador_volumen();
	pruebas_lista_iterador_iterar();
}
