///Evaluacion integradora Algoritmos I Servetto-Calvo
/// Aquino Maria Laura Legajo 99871
#include<stdio.h>
#include<string.h>
#include<ctype.h>
#include<unistd.h>
#include<stdlib.h>
#define CANTI 15 //Cantidad de registros que se leen con un solo fread del archivo
#define DNIINV1 0 // Serie de datos invalidos
#define FNACINV1 6
#define APELLINV1 "  "
#define NOMINV1 "  "
#define DNIINV2 1
#define FNACINV2 5
#define APELLINV2 " "
#define NOMINV2 " "

typedef struct persona{
	unsigned long int dni;
	unsigned long int fnac;
	char apell[64];
	char nom[64];
}persona;

FILE* arch1;
FILE* arch2;
FILE* arch3;
char nomarch1[20];
char nomarch2[20];
char nomarch3[20];
char* datousuario[10];
int opcion;
int i;
unsigned long int dni;
unsigned long int fnac;
char apell[64];
char nom[64];
persona p[CANTI];


void menuprin();
/*Menú principal del programa, se conecta con las funciones "creararchivoregistro","importar" y "menusec".*/

int estatusarchbin( char nomarch1[20] );
/* Recibe como parámetro el nombre de un archivo binario, devuelve 0 si el archivo no se encuentra en la misma carpeta del programa,
  y en el caso contrario 1 si el archivo está escrito y -1 si está en blanco. */

int creararchivoregistro();
/* Le pide al usuario un nombre para el archivo de registros.Verifica que no tenga espacios en blanco ni puntos y que no
 haya un archivo con el mismo nombre en la carpeta del programa.Crea automáticamente el archivo de control( con la extension ".ctrl")
 y un archivo para escribir los registros borrados ( con extensión ".old") junto al de registros ( de extension ".dat" ),
 luego los cierra.La función retorna un valor( 1 ó 0) que le permite volver al inicio de la funcion "menuprin" */

int importar();
/* Le pide al usuario el nombre de un archivo de texto para exportar datos y el nombre de un archivo binario para importarlos.
Calcula la cantidad de registros importados.Retorna un valor entero( 1 ó 0) que le permite volver al inicio o final de "menuprin".
Invoca a la funcion "actualizarcantregistros".*/

void actualizarcantregistros( FILE* , long int, int, int, long int );
/*Recibe como parámetros un archivo binario abierto,( preferentemente el de control con su existencia comprobada anteriormente), la cantidad de registros a agregar,el estatus del archivo, 
 un valor entero que indica si la funcion "eliminar" la está invocando( 1 si es así, 0 de lo contrario) y el número de registro a eliminar(este dato que puede ser ignorado en caso
de que el valor de "eliminar" sea 0).*/

int menusec();
/*Le solicita al usuario el nombre del archivo de registros y, en caso de que se localice el archivo, verifica la existencia del archivo de control en la carpeta del programa.
Si ambos archivos se encuentran, se abren en modo r+b. Si el archivo de control no
se encuentra, se interrumpe "menusec".Las opciones que despliega son de recuperación(lectura de registros actuales o
eliminados) ,actualización(modificar datos,eliminar o agregar registros) y regreso al menú anterior(seleccionando esta opción se
cierran todos los archivos ).*/

int leer( FILE*, FILE* );
/*Recibe como parametros el archivo de registros y el archivo de control abiertos en "menusec".Lee el archivo de registros
 y carga en una sola leída CANTI registros en un array variable struct,también lee el número total de registros
 ubicado al principio del archivo de control.Invoca a las funciones "leermenu" e "imprpantalla".Retorna un valor entero que
 le permite volver a "menuprin"*/

void imprpantalla( persona p[CANTI], long int* );
/*Lee los registros guardados en el array variable p struct tipo "persona" y los imprime en pantalla.
A medida que los lee calcula la cantidad restante de registros disponibles para su lectura
modificando el segundo parámetro pasado por referencia que es la cantidad de registros.*/

long int menubusq( FILE*,FILE*,int ); //
/*Es el menu de la busqueda que se realiza por campos. Recibe como parámetros un archivo de registros y el de control, abiertos en menusec() y un entero("busquedapart").
 Utiliza la función "crearper" para crear una variable "persona" con el dato ingresado por el usuario y datos inválidos (predeterminados)
 de acuerdo al campo que se elija para realizar la búsqueda.
 "busquedapart" puede valer 1 ó 0 y determina si se realiza la búsqueda en un solo campo o en todos.
 La función retorna -1 si el archivo de registros está en blanco ó un valor especifico que se relaciona con el valor de
 "busquedapart" ó 0 por defecto, para retornar al menú anterior.*/

persona crearper( unsigned long int, unsigned long int , char apell[64], char nom[64] );
/*Recibe como parametros dni,fnac,apellido y nombres para retornar una variable tipo "persona".*/

long int busqueda( FILE* ,FILE*, persona, int );
/*Recibe como parámetros dos archivos abiertos(de registros y de control) ,una variable tipo "persona" para buscar en el archivo de registros,
 y un entero ("busquedapart" que puede valer 1 ó 0 ). Deben imprimirse los valores de los argumentos de los registros del archivo que tengan
 un valor en comun con el parámetro de tipo "persona" que se le pasó a la función.Para la búsqueda de apellidos y nombres invoca a las
 funciones "amin" y a "divnom" (sólo en el caso de los nombres). Cuando la función recibe 1 como parámetro , se retornará el número de
 registro en el que se halla el dato buscado sólo en el caso de búsqueda por DNI, en caso de no encontrarse el registro, se retorna el
 dni ingresado por el usuario en negativo.Cuando la función recibe 0 como parámetro se imprimen los resultados encontrados
 en la búsqueda por campos. */

void amin(char nom[64]);
/*Convierte todos los caracteres de una cadena char a minusculas*/

void divnom(char*, char*, char* );//no lo uso
/*Recibe como parámetros tres cadenas de caracteres.Las ultimas dos cadenas deben estar vacías.Si la primera cadena tiene " " como
 caracter,los caracteres que anteceden a este caracter se copiarán la segunda cadena, mientras que los precedentes se copiaran a la segunda.
 En caso de que la primera cadena no tenga " " como caracter, se copiaráran todos sus caracteres sólo a la segunda cadena ,
 y la tercera quedará vacía.*/

int validarfecha( unsigned long int fecha );
/*Recibe como parámetro una fecha ingresada por el usuario en formato aaaammdd, corrobora que la fecha no tenga más de 8 digitos,
 que el año no empiece con 0, que los meses no sean mayores que 12 y que los días no sean mayores a 30.
 Exceptúa el caso en donde el año consta de 1 solo dígito de valor 0. Cuando encuentra algun error, retorna un valor mayor a 0.*/

int pedirdatos( FILE* , FILE* ,int , int );
/* Recibe como parámetros dos archivos abiertos (el de registros y el de control), y dos enteros("agreg" y "modif") que pueden valer 1 ó 0.
Si agreg vale 1 ("modif" tiene que valer 0), se le solicitará al usuario el dni de la persona que quiere agregar a través de la función
"menubusq" para comprobar su existencia en el archivo.Si el dni no se encuentra, se piden los datos restantes para grabar el registro
(luego se invoca a la función "agregar").Si "modif" vale 1 ("agreg" tiene que valer 0) se comprueba la existencia del registro que se
quiere modificar a través de "menubusq" utilizando la búsqueda por dni y se le consulta al usuario el dato que quiere modificar,
(luego se invoca a la funcion "modificar").Por defecto la función retorna 1 para interrumpirse y volver al menú anterior.*/

void modificar( FILE* arch1, long int numreg, persona q);
/*Recibe como parámetro un archivo abierto (el de registros) , el número de registro que se va a modificar  y una variable «persona»
que se va a escribir en el archivo (tiene los datos del registro al que se le cambió el valor de un campo).*/

int eliminar( FILE* arch1, FILE* arch2 ,char nomold[20]);
/*Recibe como parámetros dos archivos abiertos (el de registros y el de control )y el nombre ambos sin la extensión para abrir el archivo
 de extensión ".old" para guardar datos borrados ( se verificará si existe en la carpeta del programa y se le notificará al usuario).
 Se utilizará la funcion "menubusq" para encontrar el registro a borrar.Para borrar el registro del archivo, se le insertan datos inválidos
 predeterminados , se agrega el número del registro borrado  al archivo de control, y se graban los datos borrados en el archivo
 de extension ".old".Por último se modifica la cantidad de registros fijado en el archivo de control invocando a la función
 “actualizarcantreg”.Se retorna 1 por defecto para interrumpir a la función "eliminar".*/

void agregarreg( FILE* arch1, FILE* arch2, persona p );
/*Recibe como parámetros dos archivos abiertos (el de registros y el de control) y una variable persona con datos ingresados por el usuario,
para agregar en el archivo de registros.Se chequea  si el archivo de registros está en blanco (por no haber ningun byte en el archivo de
control) o si en un lugar se «borró» un registro para poder escribir otro ( ya que puede haber otro valor además del de la cantidad total
de registros en el archivo de control).La posición “libre” que se va a utilizar primero va a ser la última (si existe) escrita en el archivo
de control.Si se agrega un registro en esta posición en el archivo de registros, se trunca el archivo de control eliminando el numero de la
última "posición libre".*/

int main(){
menuprin();
return 0;
}

void menuprin(){
	printf( "Bienvenido elija una opcion presionando el numero que corresponda\n" );
	printf( "1.Crear nuevo archivo de registros\n2.Importar registros de un archivo de texto\n3.Consultar, modificar y agregar registros\nPresione 0 para salir\n" );
    scanf( "%d",&opcion );
	while( opcion!=0)
	{
		switch( opcion ){
			case 1: creararchivoregistro();
					break;
			case 2: importar();
					break;
			case 3: menusec();
					break;
			default: printf("Opcion no valida\n");
					break;
		}
		printf( "1.Crear nuevo archivo de registros\n2.Importar registros de un archivo de texto\n3.Consultar, modificar y agregar registros\nPresione 0 para salir\n" );
		scanf( "%d",&opcion );
	}
	printf("Fin del programa\n");
}

int creararchivoregistro(){
	getchar();
	printf( "Ingrese nombre del archivo (maximo de 15 caracteres) a crear,\n sin extension y sin espacios,ejemplo: empleados\n" );
	char name[16];
	fgets(name,16,stdin);
	int i=0;
	while( name[i++]!='.' && name[i]!=' ' && i<16 );
	if( name[i-1] == '.' || name[i]==' ' || name[0]==' '){ // Me aseguro de que el usuario no haya puesto una extensión o espacio
		printf("ERROR , retorno al menu principal \n");
		return 1;
	}
	else{
		i=0;
		while( name[i++]!='\n' && i<16 ); // fgets capta el salto de linea, en esta parte lo quito suplantandolo por el final de una cadena char '\0'
		name[i-1]='\0';
		strcpy(nomarch1,name);
		strcpy(nomarch2,nomarch1);
		strcpy(nomarch3,nomarch1);
		strcat(nomarch1,".dat"); //Agrego la extension al nombre del archivo de registro
		strcat(nomarch2,".ctrl"); //Agrego la extension al nombre del archivo de control
		strcat(nomarch3,".old"); //Agrego la extension al nombre del archivo de registros eliminados
		if( estatusarchbin( nomarch1 ) ==1 ){
			printf( "ERROR el archivo ya existe.\nRetorno al menu principal\n" );
			return 1;
			}
		else{
			arch1 =fopen ( nomarch1, "wb" );//Creo el archivo de registro
			fclose( arch1 );
			arch1 = fopen ( nomarch2, "wb" );//Creo el archivo de control
			fclose( arch1 ) ;
			arch1 = fopen( nomarch3, "wb");//Creo archivo para grabar registros eliminados;
			fclose( arch1);
			printf("Se han creado  %s %s %s exitosamente.\nRetorno al menu principal\n",nomarch1,nomarch2,nomarch3);
			return 1;
		}
	}
	return 0;
}

int estatusarchbin( char* nomarch ){
	FILE* arch;
	arch=fopen(nomarch,"rb");
	if( arch==NULL)
		return 0;
	else{
		long int pos;
		fseek( arch, 0, SEEK_END);
		pos = ftell(arch);
		if( pos == 0 ){ // me dice que la cantidad de bytes es 0, el archivo está en blanco
			fclose(arch);
			return -1;
		}
		else{
		fclose(arch);
		return 1;
		}
	}
}

int importar(){
	persona r;
	printf(" Ingrese archivo de texto que desea utilizar,para exportar datos,\n con su extension Ej:export.txt\n");
	scanf("%s",nomarch1);
	arch1 = fopen( nomarch1 , "rt" );
	fseek(arch1,0,SEEK_END);
	long int estatustxt = ftell(arch1);
	fseek(arch1,0,SEEK_SET);
	if( arch1 == NULL || estatustxt==0 ){
		printf( "Error archivo no localizado o archivo en blanco.\nRetorno al menu principal\n" );
		return 1;
	}
	else{
		printf( "Ingrese nombre del archivo ,para importar datos,\n con su extension Ej: personas.dat\n" );
		scanf("%s",nomarch1);
		if( estatusarchbin(nomarch1)==0 ){
			printf("Error, no se puede abrir el archivo.\nRetorno al menu principal\n" );
			return 1;
		}
		else{
			strcpy( nomarch2,nomarch1 );
			i=0;
			while( nomarch2[i++]!='.');
			nomarch2[i-1]='\0';
			strcat( nomarch2,".ctrl" );
			int estatusctrl = estatusarchbin( nomarch2 );
			if( estatusctrl==0 ){
				printf("No se pudo abrir el archivo de control\nRetorno al menu principal\n");
				fclose( arch1 );
				return 1;
			}
			else{
				arch2 = fopen( nomarch1 , "ab" );
				arch3 = fopen( nomarch2, "r+b" );
				long int cant=0;
				while( !feof( arch1 )  ){
					if( fscanf( arch1, " %lu %lu %s %[^(1-9)]", &( r.dni ),&( r.fnac ),r.apell,r.nom ) ==0 )
						fscanf( arch1, " %lu %lu %s %[^\n]", &( r.dni ),&( r.fnac ),r.apell,r.nom ) ;
					fwrite( &r, sizeof(persona), 1, arch2 );
					++cant;
				}
				actualizarcantregistros( arch3, cant , estatusctrl, 0 ,0) ;
				fclose( arch3 );
				fclose( arch2 );
				fclose( arch1 );
				printf( "Transferencia exitosa\nRetorno al menu\n" );
				return 1;
			}
		}
	}
}

void actualizarcantregistros( FILE* arch, long int cantiag, int estatusarch, int eliminar,long int regelim ){
	fseek(arch, 0,SEEK_SET);
	if( estatusarch<0 )
		fwrite( &cantiag, sizeof(long int),1, arch );
	else{
		long int canti;
		fread( &canti,sizeof(long int),1,arch );
		if( eliminar ==0 )
			canti = canti + cantiag;
		else{
			fseek(arch, 0,SEEK_END );
			fwrite( &regelim, sizeof(long int),1, arch );
			canti = canti - 1 ;//Porque se borra de a un solo registro
		}
		fseek(arch, 0,SEEK_SET);
		fwrite( &canti, sizeof(long int),1, arch );
	}
}

int menusec(){
	printf("Ingrese nombre del archivo de registros con su extension \n");
	scanf( "%s",nomarch1 );
	strcpy( nomarch2,nomarch1 );
	if( estatusarchbin(nomarch1)==0){
		printf("No se encontro archivo de registros.\n");
		return 1;
	}
	i=0;
	while( nomarch2[i++]!='.');
	nomarch2[i-1]='\0';
	strcpy( nomarch3,nomarch2);
	strcat( nomarch2,".ctrl" );
	strcat( nomarch3,".old" );
	persona q;
	if( estatusarchbin(nomarch2)==0 ){
		printf("No se encontro archivo de control\n.Regreso al menu principal\n");
		return 1;
	}
	else{
		arch1 = fopen( nomarch1,"r+b");
		arch2= fopen( nomarch2, "r+b");
		printf( "Elija una opcion presionando el numero que corresponda\n" ) ;
		printf( "1.Leer registros actualizados\n2.Leer registros eliminados\n3.Buscar registro por campo\n4.Insertar nuevo registro\n5.Modificar\n6.Eliminar registro\nPresione 0 para volver al menu principal\n");
		scanf( "%d",&opcion );
		getchar();
		while( opcion!=0){
			switch( opcion ){
				case 1:
					leer(arch1,arch2);
					break;
				case 2:
					if(estatusarchbin(nomarch3)==1){
						arch3 = fopen(nomarch3,"rb");
						fread(&q,sizeof(persona),1,arch3);
						while(!feof(arch3)){
							printf("%ld %ld %s %s\n",q.dni,q.fnac,q.apell,q.nom);
							fread(&q,sizeof(persona),1,arch3);
						}
						fclose(arch3);
					}
					else
						printf("No hay registros eliminados\n");
					break;
				case 3: menubusq( arch1,arch2,0 );
					break;
				case 4: pedirdatos(arch1,arch2,1,0);
					fclose(arch2);
					arch2 = fopen(nomarch2,"r+b");
					break;
				case 5: pedirdatos(arch1,arch2,0,1);
					break;
				case 6: eliminar( arch1,arch2,nomarch3);
					break;
				default:
					printf("dato no valido\n");
					break;
			}
		printf( "1.Leer registros actualizados\n2.Leer registros eliminados\n3.Buscar registro por campo\n4.Insertar nuevo registro\n5.Modificar\n6.Eliminar registro\nPresione 0 para volver al menu principal\n");
		scanf( "%d",&opcion );
		getchar();
		}
		fclose(arch1);
		fclose(arch2);
	}
	return 1;
}

int leer( FILE* arch1, FILE* arch2 ){
	fseek( arch2,0,SEEK_END);
	long int estatusctrl = ftell(arch2);
	if( estatusctrl==0 ){
		printf("Archivo en blanco\nRetorno al menu\n");
		return 1;
	}
	fseek( arch1, 0,SEEK_SET);
	long int cantireg;
	fseek( arch2, 0 , SEEK_SET);
	fread( &cantireg, sizeof( long int ) ,1,arch2 );
	printf("Total de registros %ld\n",cantireg);
	while( !feof( arch1 ) ){
		fread( &p,sizeof(persona),CANTI,arch1 );
		imprpantalla( p, &cantireg );
		if( cantireg>CANTI){
			printf("Desea ir a la siguiente pagina?\n1.Si\nPresione cualquier otro numero para salir\n");
			scanf("%u",&opcion );
			if( opcion!= 1)
				return 1;
		}
	}
	printf("Retorno al menu principal\n");
	return 1;
}

void imprpantalla( persona p[CANTI], long int* cantireg ){
	long int cont=1;
	for ( i=0 ; i<CANTI && cont<=(*cantireg) && (*cantireg)>0 ; i++ ){
		if( p[i].dni!=DNIINV2 ){
			printf( "%lu %lu %s %s\n",p[i].dni,p[i].fnac,p[i].apell,p[i].nom );
            ++cont;
		}
	}
	*cantireg= *cantireg- cont ;
}

persona crearper( unsigned long int dni, unsigned long int fnac, char apell[64], char nom[64] ){
	persona p;
	p.dni = dni;
	p.fnac = fnac;
	strcpy( p.apell,apell);
	strcpy( p.nom, nom);
	return p;
}

long int menubusq( FILE* arch1 ,FILE* arch2, int busquedapart){
	fseek( arch2,0,SEEK_END);
	long int estatusctrl = ftell(arch2);
	if( estatusctrl==0 && busquedapart==0 ){
		printf("Archivo en blanco\n");
		return -1;
	}
	long int aux;
	if( busquedapart==1)
		opcion=1;
	else{
		printf( "Presione la tecla correspondiente\n1.Buscar por DNI\n2.Buscar por Fecha de nac.\n3.Buscar por Apellido\n4.Buscar por nombre/s\nPresione otro numero para salir\n");
		scanf("%d",&opcion);
		getchar();
	}
	switch( opcion ){
		case 1:
			printf("Ingrese DNI\n");
			scanf("%lu",&dni);
			getchar();
			if( dni==DNIINV1 || dni==DNIINV2 ){
				printf("Dato no valido\nRetorno al menu anterior\n");
				return 0;
			}
			else{
				if(busquedapart==0 ){
					busqueda(arch1,arch2,crearper( dni,FNACINV1,APELLINV1,NOMINV1),0);
					break;
				}
				else{
					aux=busqueda(arch1,arch2,crearper( dni,FNACINV1,APELLINV1,NOMINV1),1);
					return aux;
				}
			}
		case 2:
			printf("Ingrese fecha de nacimiento formato aaaammdd\n");
			scanf("%lu",&fnac);
			if(validarfecha(fnac)!=0 ){
				printf("Dato no valido\nRetorno al menu anterior\n");
					return 0;
			}
			else
				busqueda( arch1 ,arch2,crearper( DNIINV1,fnac,APELLINV1,NOMINV1),0 );
			break;
		case 3:
			printf("Ingrese apellido\n");
			scanf("%s",apell);
			busqueda( arch1,arch2,crearper(DNIINV1,FNACINV1,apell,NOMINV1),0 ) ;
			break;
		case 4:
			printf("Ingrese nombre\n");
			fgets( nom, 64, stdin);
			nom[strlen(nom)-1]='\0';
			if( nom[0]==' ' ){
			printf("Nombre invalido\nRetorno al menu anterior\n");
			return 0;
			}
			busqueda( arch1,arch2,crearper(DNIINV1,FNACINV1,APELLINV1,nom),0 );
			break;
		default:
			break;
	}
	return 0;
}

void amin(char nom[64]){
	int i=0;
	while(nom[i]!='\0'){
		nom[i]=tolower(nom[i]);
		i++;
	}
}

void divnom(char* datousuario, char* a, char* b){
	int i=0; //
	while(datousuario[i++]){
		if(datousuario[i]==' '){
			char* toka;
			char* tokb;
			toka=strtok(datousuario," ");
			tokb=strtok(NULL," ");
			strcpy( a,toka);
			strcpy( b,tokb);
		}
		else{
			strcpy(a,datousuario);//
			strcpy(b,datousuario);
		}
	}
}

long int busqueda( FILE* arch1, FILE* arch2,persona q, int busquedapart ){
	fseek(arch2,0,SEEK_SET);
	long int totalreg;
	long int cantival=0; //cantidad de registros validos
	fread(&totalreg,sizeof(long int),1,arch2);
	fseek(arch1,0,SEEK_SET);
	long int numreg=0,resultados=0;;
	amin(q.apell);
	amin(q.nom);
	char a[64];
	char b[64];
	if( q.nom[0]!=' ')
		divnom(q.nom,a,b);
	while( !feof( arch1 ) ){
		fread( &p,sizeof(persona),CANTI,arch1 );
		for( i=0; i<CANTI && cantival<totalreg; i++ ){ //
			numreg++;
			if( p[i].dni!=DNIINV2 )
                cantival++;
			amin(p[i].nom);
			amin(p[i].apell);
			if( p[i].dni==q.dni && q.dni!=DNIINV1 ){
				++resultados;
				printf("Se ha encontrado %ld %ld %s %s en la base de datos\n",p[i].dni,p[i].fnac,p[i].apell,p[i].nom);
				if( busquedapart==1)
					return numreg; //notifica que todos los datos de una persona ya existen en la base de datos
			}
			else if( p[i].fnac==q.fnac && busquedapart==0){
				printf("%ld %ld %s %s\n",p[i].dni,p[i].fnac,p[i].apell,p[i].nom);
				++resultados;
			}
			else if( strcmp(p[i].apell,q.apell )==0 &&  busquedapart==0 ){
				printf("%ld %ld %s %s\n",p[i].dni,p[i].fnac,p[i].apell,p[i].nom);
				++resultados;
			}
			else if( strstr(p[i].nom,a) && strstr(p[i].nom,b) && strcmp(q.nom,NOMINV1)!=0 && busquedapart==0 ){
				printf("%ld %ld %s %s\n",p[i].dni,p[i].fnac,p[i].apell,p[i].nom);
				++resultados;
			}
		}
	}
	if( busquedapart==0)
		printf("Se encontraron %ld resultados\n",resultados);
	else
		return -q.dni;
	return 0;
}

int validarfecha( unsigned long int fecha ){
	int cont=0;
	if( fecha ==0 )
        return cont;
	char numtext[30];
	sprintf(numtext, "%lu", fecha);
	char a[3];
	a[0]=numtext[4];
	a[1]=numtext[5];
	a[2]='\0';
	char b[3];
	b[0]=numtext[6];
	b[1]=numtext[7];
	b[2]='\0';
	int tam = strlen(numtext);
	if( tam>8 || ( tam<8 && tam!=1 && numtext[0]!='0' ) )
		++cont;
	if( numtext[0]=='0' && tam!=1 )
		++cont;
	if(	(atoi(a)>12 || atoi(a)==0 ) || (atoi(b)==0 || atoi(b)>30 ) )
		++cont;
	return cont;
}

int pedirdatos( FILE* arch1, FILE* arch2,int agreg, int modif ){
	long int aux = menubusq( arch1,arch2,1 );
	persona q;
	if(agreg== 1){
		if(aux>0 ){
			printf("Registro existente\n");
			return 1;
		}
		else{
			if( aux==0 )
				return 1 ;
			else{
				if(aux!=-1){
					q.dni = -aux;
					opcion=1;
				}
			}
		}
	}
	if(modif==1 && aux<0 ){
		printf("Registro inexistente\n");
		return 1;
	}
	else{
		fseek( arch1,(aux-1)*sizeof(persona),SEEK_SET);
		fread( &q, sizeof(persona), 1, arch1 );
	}
	if(modif==1){
		printf("Presione el numero correspondiente para modificar datos\n1.Fecha de nac\n2.Apellido\n3.Nombre/s\nPresione otro numero para salir\n");
		scanf("%d",&opcion);
		getchar();
	}
	switch(opcion){
		case 1:
			printf("Ingrese fecha de nac formato aaaammdd o 0\n");
			scanf("%ld",&q.fnac);
			if( validarfecha(q.fnac)!=0 ){
				printf("Dato no valido\nRetorno al menu anterior\n");
				return 1;
			}
			if( agreg == 0)
				break;
		case 2:
			printf("Ingrese apellido\n");
			scanf( "%s", q.apell);
			getchar();
			if(agreg==0)
				break;
		case 3:
			printf("Ingrese nombre/s\n");
			fgets( q.nom,64,stdin);
				if( q.nom[0]==' ' ){
				printf("Nombre invalido\nRetorno al menu anterior\n");
				return 1;
				}
			break;
		default:
			return 1;

	}
	if(agreg==1)
		agregarreg(arch1,arch2,q);
	if(modif==1)
		modificar( arch1,aux,q);
	printf("Operacion exitosa\n");
	return 1;
}

void modificar( FILE* arch1, long int numreg, persona q){
	fseek( arch1,(numreg-1)*sizeof(persona),SEEK_SET);
	fwrite( &q, sizeof(persona), 1, arch1 );
	printf("Modificacion exitosa\n");
}

int eliminar( FILE* arch1, FILE* arch2, char nomold[20] ){
	fseek( arch2,0,SEEK_END);
	long int estatusctrl = ftell(arch2);
	if( estatusctrl==0 ){
		printf("Archivo en blanco\nRetorno al menu\n");
		return 1;
	}
	fseek( arch2, 0, SEEK_SET);
	long int numreg;
	fread( &numreg,sizeof(long int),1,arch2);
	if( numreg==0 ){
        printf("Archivo en blanco\nRetorno al menu\n" );
        return 1;
	}
	persona p;
	numreg = menubusq( arch1 ,arch2,1 );
	if( numreg<0){
		printf("Registro no encontrado\n");
		return 1;
	}
	else{
		if( estatusarchbin(nomold) == 0 )
			printf("No se encontro %s, no se guardaran registros borrados\n",nomold );
		printf("Desea eliminar registro?\n1.Si\t2.No\n");
		scanf("%d",&opcion);
		switch(opcion){
			case 1:
				arch3=fopen( nomold,"ab");
				fseek(arch1,(numreg-1)*sizeof(persona),SEEK_SET);
				fread( &p,sizeof(persona),1,arch1);
				persona q =crearper( DNIINV2,FNACINV2,APELLINV2,NOMINV2);
				fseek(arch1,(numreg-1)*sizeof(persona),SEEK_SET);
				fwrite( &q,sizeof(persona),1,arch1);
				break;
			case 2:
				printf("Retorno al menu\n");
				return 1;
		}
	}
	actualizarcantregistros( arch2,0, 1, 1 ,numreg);
	if( arch3!=NULL ){
		fwrite( &p, sizeof(persona),1,arch3);
		fclose(arch3);
	}
	printf("Eliminación exitosa regreso al menu\n");
	return 1;
}

void agregarreg( FILE* arch1, FILE* arch2, persona q ){
	fseek( arch2,0,SEEK_END);
	long int pos = ftell( arch2);
	long int ultreg = pos/sizeof(long int);
	if( pos==0 ){
		fseek(arch1,0,SEEK_END);
		fwrite( &q, sizeof(persona),1,arch1 );
		actualizarcantregistros(arch2,1,-1,0,0);
	}
	if( ultreg == 1 ){
		fseek( arch1,0, SEEK_END);
		fwrite( &q, sizeof(persona),1,arch1 );
		actualizarcantregistros(arch2,1,1,0,0);
	}

	if( ultreg >1 ) {
		long int numreg;
		fseek(arch2,(ultreg-1)*sizeof(long int),SEEK_SET);
		fread( &numreg,sizeof(long int),1,arch2);
		fseek(arch1,sizeof(persona)*(numreg-1),SEEK_SET);
		fwrite(&q,sizeof( persona ),1,arch1);
		ftruncate(fileno(arch2),(sizeof( long int)*(ultreg-1) ) );
		actualizarcantregistros(arch2,1,1,0,0);
	}
}
