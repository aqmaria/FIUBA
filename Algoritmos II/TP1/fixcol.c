///Algoritmos II Wachenchauzer
///Aquino Maria 99871
#define _POSIX_C_SOURCE 200809L
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*Funcion auxiliar*/

/* "Trunca" una linea por pantalla de acuerdo al tamanio especificado por parametro ( n ).
 * Cuando se corta la cadena se le agrega el salto de linea antes de imprimirla por pantalla,
 * si los bytes restantes son iguales al tamanio recibido se intuye que ya tiene el salto de linea
 * ,por lo tanto no se agrega */
void truncar_linea_en_pantalla( char* linea, size_t tam_linea, size_t n ){
	char* ptr_linea = linea;
	while( tam_linea > n ){
		if( tam_linea == n ){
			printf( "%s", ptr_linea );
			tam_linea-=n;
			continue;
		}
		char c = '\0';
		strncpy( &c, ptr_linea + n, sizeof( char ) );
		ptr_linea[n] = '\0';
		printf( "%s\n", ptr_linea );
		ptr_linea[n] = c;
		ptr_linea = ptr_linea + n;
		tam_linea-=n;
	}
	if( tam_linea > 0 )
		printf( "%s", ptr_linea );
		
}

void fixcol( char *nombre_archivo, size_t buffer_tam ){
	FILE* arch = fopen( nombre_archivo, "r" );
	if( !arch ){
		printf( "No se puede abrir archivo\n" );
		return;
	}
	///Calculo longitud del archivo.
	fseek( arch, 0 , SEEK_END );
	size_t arch_tam = ftell( arch );
	if( !buffer_tam || !arch_tam ){
		fclose( arch );
		return;
	}
	fseek( arch, 0 , SEEK_SET );
	///el ciclo termina cuando no quedan mas bytes para leer
	while( arch_tam ){
		char* buffer = NULL;
		size_t tam = buffer_tam;
		size_t bytes_leidos = getline( &buffer, &tam , arch );
		if( !buffer ){
			printf( "No hay mas memoria..\n" );
			break;
		}
		if( !bytes_leidos ){
			free( buffer );
			break;
		}
		///si la cantidad de bytes leidos supera al tamanio inicial del buffer,
		/// "trunco" la linea por pantalla
		if( bytes_leidos <= buffer_tam )
			printf( "%s" , buffer );
		else
			truncar_linea_en_pantalla( buffer, bytes_leidos, buffer_tam );
		free( buffer );
		arch_tam -=bytes_leidos;
	}
	fclose( arch );
}

int main( int argc, char * argv[] )
{ 
	if( argc < 3 || argc >3 ){
		printf( "No se reconoce comando\n");
		return 0;
	}
	char* ptr_end;
	size_t tam = strtol( argv[2], &ptr_end, 10 ); 
	if( *ptr_end ){
		printf( "No se reconoce comando");
		return 0;
	}
	fixcol( argv[1], tam );
}
