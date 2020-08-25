///Algoritmos II Wachenchauzer 
#include "hash.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define TAM_TABLA_INICIAL 13
#define FACT_REDIMENSION 2
#define FACTOR_OCUPACION 0.75
#define FACTOR_DESOCUPACION 0.25

///funcion de potencia para funcion de hash
long int pot( size_t num, long int exp ){
	if( exp<=1 )
		return 1;
	return num*pot( num,exp-1 );
}

enum estado{ OCUPADO, VACIO, BORRADO } typedef estado_t;

struct tabla_hash{
	char *clave;
	void *dato;
	estado_t estado;
}typedef tabla_hash_t; 

struct hash{
	size_t tam;
	size_t cant;
	hash_destruir_dato_t funcion_destruir;
	tabla_hash_t* tabla;
};

struct hash_iter{
	const hash_t* hash;
	size_t indice; 
	size_t elem_iterados; 
};

/*Crea un arreglo de structs tabla_hash_t.
 * Post: devuelve un puntero al arreglo si pudo reservar memoria,
 * devuelve NULL en caso contrario.
 */
 
tabla_hash_t* crear_tabla( size_t tam ){
	tabla_hash_t* tabla = malloc (tam * sizeof ( tabla_hash_t ) );
	if (! tabla) return NULL;
	for (int i = 0; i < tam; i++)
		tabla[i].estado = VACIO;
	return tabla;
}

/* Devuelve una posicion dentro un rango de acuerdo a la clave pasada por parametro
 * Post: devuelve una posicion
 */
size_t hash_funcion( size_t tamanio,const char* clave ){
	size_t len = strlen( clave );
	size_t hash = 5831;
	size_t M = 33;
	for ( size_t i = 0; i < len; i++ )
		hash =  M* ((size_t)pot( M, (long int)(len - i)) ) * hash + clave[i];  
	return hash % tamanio;
}

/* Busca clave pasada por parametro en la tabla , devuelve puntero a la posicion dentro de la tabla
 * Pre: hash fue creado. 
 * Post: devuelve un puntero si encontró la clave, NULL en caso contrario.
 */
tabla_hash_t* buscar( const hash_t* hash , const char* clave ){
	if( !hash->cant )
		return NULL;
	size_t i = hash_funcion( hash->tam, clave );
	///En esta implementacion se propuso reutilizar los espacios BORRADOS sólo en el caso de que la función de  hashing dé
	///una posición con ese estado
	if( hash->tabla[i].estado == VACIO )
		return NULL;
	if( hash->tabla[i].estado == OCUPADO ){
		if( !strcmp( hash->tabla[i].clave, clave ) )
			return hash->tabla + i;
	}
	size_t j = i;
	while( hash->tabla[j].estado!=VACIO ){
		if( hash->tabla[j].estado==OCUPADO && !strcmp( hash->tabla[j].clave, clave ) ) 
			return hash->tabla + j;
		j++;
	///Cuando termino de analizar el rango [i,hash->tam) en el arreglo, analizo [0,i)  
		if( j == hash->tam )
			j=0;
		if( j == i )
			break;
	}
	return NULL;
}

/* Devuelve la siguiente posicion vacia en la tabla hash de acuerdo al indice pasado por parametro
 * Pre: hash fue creado
 * Post: Devuelve una posicion vacia. ( El hash nunca va a estar lleno 100% por la redimension )
 */
size_t sig_posicion_vacia( hash_t* hash, size_t indice ){
	while ( indice < hash->tam && hash->tabla[indice].estado != VACIO ) indice++; 
	if( indice < hash->tam ) return indice;
	size_t j = 0;
	while ( j < indice && hash->tabla[j].estado != VACIO ) j++;
	return j; 
}

/* Devuelve la siguiente posicion ocupada en la tabla hash de acuerdo al indice pasado por parametro
 * Pre: hash fue creado
 * Post: si se pudo encontrar una posicion ocupada la devuelve, en caso contrario devuelve -1
 */
long int sig_posicion_ocupada( const hash_t* hash , size_t indice ){
	if( indice +1 == hash->tam )
		return -1;
	for( indice = indice +1; indice<hash->tam-1 && hash->tabla[indice].estado!=OCUPADO ; indice++ );
	if( hash->tabla[indice].estado == OCUPADO )
		return ( long int )indice;
	return -1;

}

/* Redimensiona tabla hash al tamanio pasado por parametro.
 * Pre: hash fue creado
 * Post: devuelve true si se pudo efectuar la redimension, false en caso contrario
 */
bool hash_redimensionar( hash_t* hash, size_t nuevo_tam ){
	tabla_hash_t* nueva_tabla = crear_tabla( nuevo_tam );
	if( !nueva_tabla ){
		printf( "No se pudo redimensionar\n" );
		return false;
	}
	tabla_hash_t* vieja_tabla = hash->tabla;
	size_t viejo_tam = hash->tam;
	hash->tabla = nueva_tabla;
	hash->tam = nuevo_tam;
	for( int i=0 ; i<viejo_tam ; i++ ){
		if( vieja_tabla[i].estado!= OCUPADO )
			continue;
		size_t nueva_pos = hash_funcion( nuevo_tam, vieja_tabla[i].clave );
		///Copio a la nueva tabla los punteros de las posiciones ocupadas en la tabla vieja
		if( hash->tabla[nueva_pos ].estado == OCUPADO )
			nueva_pos = (size_t ) sig_posicion_vacia( hash, nueva_pos ) ;
		hash->tabla[ nueva_pos ].estado = vieja_tabla[i].estado;
		hash->tabla[ nueva_pos ].dato = vieja_tabla[i].dato;
		hash->tabla[ nueva_pos ].clave = vieja_tabla[i].clave;
	}
	free( vieja_tabla );
	return true;
}


//******************PRIMITIVAS DEL HASH CERRADO**************


hash_t *hash_crear( hash_destruir_dato_t destruir_dato ){
	hash_t* hash = malloc ( sizeof ( hash_t ) );
	if (! hash) return NULL;
	hash->tabla = crear_tabla( TAM_TABLA_INICIAL );
	if (! hash->tabla){ 
		free( hash );
		return NULL;
	}
	hash->tam = TAM_TABLA_INICIAL;
	hash->cant = 0;
	hash->funcion_destruir = destruir_dato;
	return hash;
}

bool hash_guardar(hash_t *hash, const char *clave, void *dato){
	tabla_hash_t* tabla = buscar( hash, clave );
	///si se encuentra clave, se reemplaza dato
	if( tabla ){
		if( hash->funcion_destruir )
			hash->funcion_destruir( tabla->dato );
		tabla->dato = dato;
		return true;
	}
	double factor_carga = (double) hash->cant / (double )hash->tam ;
	if ( factor_carga > FACTOR_OCUPACION  ){ 
		if( !hash_redimensionar( hash, hash->tam * FACT_REDIMENSION ) )
			return false;
	}
	char* str_nuevo = malloc ( sizeof ( char ) * ( strlen( clave ) + 1 ) );
	if( !str_nuevo ) return false;
	size_t posicion = hash_funcion( hash->tam, clave );
	if( hash->tabla[posicion].estado == OCUPADO )
		posicion = sig_posicion_vacia( hash, posicion );
	strcpy( str_nuevo, clave );
	hash->tabla[posicion].clave = str_nuevo;
	hash->tabla[posicion].dato = dato;
	hash->tabla[posicion].estado = OCUPADO;
	hash->cant++;
	return true;
}

void* hash_borrar(hash_t *hash, const char *clave){
	double factor_carga = (double) hash->cant / (double )hash->tam ;
	if ( factor_carga < FACTOR_DESOCUPACION  )
		hash_redimensionar( hash, hash->tam/FACT_REDIMENSION );
	tabla_hash_t* tabla = buscar( hash, clave );
	if( !tabla ) return NULL;
	tabla->estado = BORRADO;
	hash->cant--;
	free( tabla->clave );
	return tabla->dato;
}

void* hash_obtener( const hash_t *hash, const char *clave ){
	 tabla_hash_t* tabla = buscar( hash, clave );
	 if( !tabla ) return NULL; 
	 	return tabla->dato;
}

size_t hash_cantidad( const hash_t* hash ){
	return hash->cant; 
}

bool hash_pertenece(const hash_t *hash, const char *clave){
	return buscar( hash,clave )!=NULL;
}


void hash_destruir( hash_t *hash ){
	for (int i = 0; i < hash->tam && hash->cant ; i++){
		if( hash->tabla[i].estado!= OCUPADO )
			continue;
		free( hash->tabla[i].clave );
		if( hash->funcion_destruir )
			hash->funcion_destruir( hash->tabla[i].dato );
	}
	free( hash->tabla );
	free ( hash );
}

//***************PRIMITIVAS DEL ITERADOR***************

hash_iter_t *hash_iter_crear( const hash_t *hash ){
	hash_iter_t* iter = malloc (sizeof ( hash_iter_t ) );
	if (! iter) return NULL;
	iter->hash = hash;
	if( iter->hash->tabla[0].estado!= OCUPADO )
		iter->indice = ( size_t ) sig_posicion_ocupada( iter->hash, 0 );
	else iter->indice = 0;
	iter->elem_iterados = 0;
	return iter;
}
///el iterador avanza sobre posiciones ocupadas 
bool hash_iter_avanzar(hash_iter_t *iter){
	if( hash_iter_al_final( iter ) )
		return false;
	iter->indice = sig_posicion_ocupada( iter->hash, iter->indice );
	iter->elem_iterados++;
	return true;
}

const char *hash_iter_ver_actual(const hash_iter_t *iter){
	if( hash_iter_al_final( iter ) )
		return NULL;
	return iter->hash->tabla[ iter->indice ].clave;
}

bool hash_iter_al_final(const hash_iter_t *iter){
	return iter->elem_iterados == hash_cantidad( iter->hash ) ;
}

void hash_iter_destruir(hash_iter_t* iter){
	free(iter);
}
