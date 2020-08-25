///Algoritmos II Wachenchauzer
///Aquino Maria 99871
#include <stdlib.h>
#include <string.h>

char** split( const char* str, char sep ){
	if( sep== '\0')
		return NULL;
	size_t tam_str = !str?0:strlen( str );
	size_t cant_subcadenas =0;
	for( size_t i=0; i<=tam_str-1 && tam_str!=0 ; i++ ){
		if( str[i]==sep )
			cant_subcadenas++;
	}
	/* La memoria allocada para el arreglo dinámico de "strings" fue calculada de la siguiente manera :  
	 * cantidad de separadores  + 1( la ultima subcadena de la cadena pasada por parametro si esta ultima no es NULL )+ 1( para finalizar el arreglo con NULL )*/
	cant_subcadenas+=2;
	char **strv = malloc( ( sizeof( char* ) *  cant_subcadenas )  );
	if( !strv ) return NULL;
	size_t tam_subcadena = 0;
	cant_subcadenas = 0;
	/// ptr_aux se utiliza para apuntar al inicio de cada subcadena para poder copiarla 
	const char* ptr_aux = str;
	for( int i=0 ; i<=tam_str ; i++ ){
		if( i!= tam_str && str[i]!= sep ){
			++tam_subcadena;
			continue;
		}
		++cant_subcadenas;
		strv[ cant_subcadenas - 1 ] = ( char* )malloc( sizeof( char ) * tam_subcadena + 1 );
		if( !strv[ cant_subcadenas - 1 ] ) return strv;
		strncpy( strv[ cant_subcadenas - 1 ], ptr_aux, tam_subcadena );
		strv[ cant_subcadenas - 1 ][ tam_subcadena  ] = '\0';
		ptr_aux = ptr_aux + ( tam_subcadena + 1 );
		tam_subcadena =0;
	}
	strv[ cant_subcadenas  ] = NULL;
	return strv; 
}

char* join(char** strv, char sep){
	if( sep == '\0' ); 
	char** ptr_aux = strv;
	size_t tam_strv = 0;
	///calculo tamanio del arreglo dinamico
	while ( *ptr_aux != NULL ){
		ptr_aux+=1;
		tam_strv++;
	}
	///calculo tamanio del arreglo original
	size_t tam_str = 0;
	size_t tam_subcadena = 0;
	for( size_t i=0 ; i<tam_strv ; i++ ){
		size_t k = 0;
		/*se suma uno ( para copiar el separador )
		/ya que si el tamanio de la subcadena es 0, hay un separador.
		/Tambien antes de la subcadena hay un separador.*/
		if( i< tam_strv - 1  )
			tam_str++;
		while( strv[i][k++]!='\0')tam_subcadena++;
		tam_str+= tam_subcadena;
		tam_subcadena = 0;
	}
	char* str = malloc( sizeof( char* ) * ( tam_str + 1 ) );
	ptr_aux = strv; 
	size_t j = 0;
	for( size_t i=0; i<tam_strv ; i++ ){
		size_t k = 0;
		if( ptr_aux[i][0]== '\0' && i!=tam_strv-1 ){
			str[j++]=sep;
			continue;
		}
		while( ptr_aux[i][k]!='\0' ) str[j++] = ptr_aux[i][k++];
		if( i!=tam_strv-1 )str[j++] = sep;
	}
	str[j]='\0';
	return str;
}


void free_strv( char *strv[] ){
	char** ptr_aux = strv;
	while ( *ptr_aux != NULL ){
		free( *ptr_aux );
		ptr_aux+=1;
	}
	free( strv );
}

