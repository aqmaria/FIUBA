///Algoritmos II Wachenchauzer
///Aquino Maria 99871

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

///funcion auxiliar para crear nombre de los archivos
int cuantos_digitos( size_t numero ){
	int digitos=1;
	while( numero>=10 ){
		numero = numero%10;
		digitos++;
	}
	return digitos;
}

void filesplit( char *nombre_archivo, size_t buffer_tam ){
	FILE* arch = fopen( nombre_archivo, "r" );
	if( !arch ){
		printf( "No se puede abrir archivo\n" );
		fclose( arch );
		return;
	}
	///Calculo longitud del archivo.
	fseek( arch, 0 , SEEK_END );
	if( !buffer_tam ){
		fclose( arch );
		return;
	}
	///Calculo cantidad de bloques a leer
	size_t bloques = ftell( arch )/buffer_tam;
	size_t ultimo_bloque_tam = 0;
	///Chequeo si el ultimo bloque a leer tiene tamaño < bytes pedidos
	if( ftell( arch )%buffer_tam !=0 ){
		ultimo_bloque_tam = ftell( arch )%buffer_tam;
		bloques++;
	}
	///Inicializo contador de archivos
	size_t num_archivo = 1;
	fseek( arch, 0 , SEEK_SET );
	size_t nomarch_tam = strlen( nombre_archivo );
	///Copio nombre del archivo para crear los archivos
	char* nombre_archivos = ( char* )malloc( nomarch_tam+5 );
	if( !nombre_archivos ){
		printf( "No hay memoria suficiente..\n" );
		fclose( arch );
		return;
	}
	strcpy( nombre_archivos, nombre_archivo );
	while( num_archivo<= bloques ){
		if( bloques == num_archivo && ultimo_bloque_tam )
			buffer_tam = ultimo_bloque_tam;
		char *buffer = ( char* )malloc( buffer_tam );
		if( !buffer ){
			printf( "No hay memoria suficiente...\n" );
			free( nombre_archivos );
			return;
		}
		fread( buffer ,sizeof( char ) ,buffer_tam ,arch );
		int i = cuantos_digitos( num_archivo );
		char numero[5] = "0000";
		sprintf( numero + ( 4 - i ), "%d" ,( int )num_archivo  );
		strcat( nombre_archivos, numero );
		FILE* arch_aux = fopen( nombre_archivos, "w" );
		fwrite( buffer, sizeof( char ), buffer_tam, arch_aux );
		fclose( arch_aux );
		free( buffer );
		nombre_archivos[ nomarch_tam ] ='\0'; 
		num_archivo++;
	}
	free( nombre_archivos );
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
	filesplit( argv[1], tam );
}
