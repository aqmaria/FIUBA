///Algoritmos II Wachenchauzer
///Aquino Maria 99871

#include "prom_movil.h"

double* obtener_promedio_movil(int* arreglo, size_t n, size_t k){
	if( !n )
		return NULL;
	double* vector_promedio = malloc( n* sizeof( double ));
    if ( !vector_promedio )
        return NULL;
	for( int i=0 ; i<n ; i++ ){
		///Sumo el elemento en el que me posiciono
		int sumatoria = arreglo[i];
		int cantidad = 1;
		int contador = (int)k;
		///Si no estoy en la primera posicion, sumo los k-elementos anteriores
		if( i )
			for( int j=i-1 ; contador>0 && j>= 0 ; j--,contador--,cantidad++ )
				sumatoria+=arreglo[j];
		contador = (int)k;
		///Si no estoy en la ultima posicion, sumo los k-elementos siguientes
		if( i != n-1 )
			for( int j=i+1; contador>0 && j<n ; j++, contador--,cantidad++ )
				sumatoria+=arreglo[j];
		double promedio = (double)sumatoria/cantidad;
		vector_promedio[i] = promedio;
	}
	return vector_promedio;
}

double* obtener_promedio_movil2(int* arreglo,size_t n , size_t k ){
	if( !n )
		return NULL;
    double* vector_promedio = malloc( n* sizeof( double ) );
    if( !vector_promedio )
		return NULL;
	/*El vector_sumas[n] va a contener en cada posicion la suma del elemento actual con 
	 * los de las posiciones anteriores (si existen)*/
	int vector_sumas[n];
	int sumatoria=0;
	///Inicializo vector_sumas
	for( int i=0; i<n ; i++ ){
		sumatoria+= arreglo[i];
		vector_sumas[i]=sumatoria;
	}
	for(int i=0; i<n ; i++ ){
		///Si la cantidad del promedio no incluye al ultimo elemento
		if( (k+i)<n ){
			if( i<= k )
				///Si en el promedio esta incluido el elemento de la posicion 0 
				vector_promedio[i] = vector_sumas[ k+i ]/(double)( k+i+1 );
			///Si en el promedio debo excluir elementos del principio del arreglo 
			else 
				vector_promedio[i] = ( vector_sumas[k+i] - vector_sumas[i-k-1] )/ (double)( 2 * k + 1);
		} ///Si la cantidad del promedio incluye el ultimo elemento
		else 
			vector_promedio[i] = ( vector_sumas[n-1] - vector_sumas[i-k-1] )/(double)( n - i +k );
	}
	return vector_promedio;
}
