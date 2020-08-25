;ORGANIZACION DEL COMPUTADOR  2016
; CONVERSOR DE FECHAS(I)
;AQUINO MARIA 99871
segment pila stack
resb 64
segment datos data
fileName	db	"fechas.dat",0	;
fHandle 	resw	1
salto		db 13,10,'$'
aniogp		times 2 resb 1  ;registro de archivo = aniogp+mesgp+diagp
		times 2 resb 1
mesgp		times 2 resb 1
diagp		times 2 resb 1
anioga		times 8 resb 1  ;fecha gregoriana en ascii
diaga		times 4 resb 1
mesga		times 4 resb 1
aniob		dw	     0 ;fecha greg en binario y little endian	
		dw	     0
mesb		db	     0
diab		db	     0
temp2		db	     0
esbis		db	     0 ;flag que indica si el anio es bisiesto
moi400		db	     0 ;flags para poder dividir      
moi100		db	     0
moi4		db	     0
digitos     	times 7 resb 1 ;vector de digitos
len         	db           0 
multi10		dw	     10
div400		dw	     400 ;divisor
dos		db           2  ;multiplicador
temp		dw	     0 ;variable auxiliar
procanio	db	     0 ;flag para procesar anio
procmes		db	     0  ;flag para procesar mes
procdia		db	     0 ; flag para procesar dia
tabdiasxmes	db	     31,28,31,30,31,30,31,31,30,31,30,31 
tabsumdias	dw	     31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 
diaju		dw	     0 ; suma de dias hasta la fecha leida
diajuascii	times 5 resb 1 ; suma de dias hata la fecha leida en ascii
fechjuascii	times 7 resb 1 ; fecha en formato juliano a imprimir por pantalla
noregis		db	"No hay mas fechas en el archivo$"
adios		db	"Adios!$"
msjres1		db	"Se leyo la siguiente fecha en formato gregoriano:$"
msjres2		db	"Resultado de conversion a formato juliano es:$"
continuar	db	"Seguir convirtiendo fechas? S/N$" 
msjErrOpen	db	"Error en apertura$"
msjErrRead	db	"Error en lectura$"
msjErrClose	db	"Error en cierre$"
msjErrdat	db	"Se leyo un dato invalido$"
segment codigo code
..start:
	mov  ax,datos  ;ds <-- dir del segmento de datos
	mov  ds,ax
	mov	ax,pila				;ss <- dir del segmento de pila
	mov	ss,ax
abrir:
	;ABRE EL ARCHIVO PARA LECTURA
	mov	al,0		         ;al = tipo de acceso (0=lectura; 1=escritura; 2=lectura y escritura)
	mov	dx,fileName	         ;dx = dir del nombre del archivo
	mov	ah,3dh		         ;ah = servicio para abrir archivo 3dh
	int	21h
	jc	errOpen
	mov	[fHandle],ax	         ; en ax queda el handle del archivo
lectura:
	;LEE EL ARCHIVO (registro 1)
	mov	bx,[fHandle]	;bx = handle del archivo
	mov	cx,8		;cx = cantidad de bytes a escribir
	mov	dx,aniogp	;dx = dir del area de memoria q contiene los bytes leidos del archivo
	mov	ah,3fh		;ah = servicio para escribir un archivo: 40h
	int	21h
	jc	errRead
	cmp ax,0
	je  noreg
procesaranio:
	mov byte[procanio],1 ;seteo flag para indicar que proceso el anio 
	mov bx,aniogp ;apunto al empaquetado del anio
	mov byte[len],7
	call separardigitos
	call abin
procesarmes:
	mov byte[procanio],0
	mov byte[procmes],1 ; seteo flag para indicar que proceso el mes
	mov bx,mesgp ; apunto al empaquetado del mes
	mov byte[len],3
	call separardigitos
	mov bx,mesga ; apunto al empaquetado del mes
	mov si,0
	call aascii
	call abin
	cmp byte[mesb],12
	jg datInval ; el mes no puede ser mayor a 12
	cmp byte [mesb],1
	jl datInval ;el mes no puede ser menor a 1
	mov byte[procmes],0 
procesardia:
	mov byte[procdia],1 ;seteo flag para procesar el dia
	mov bx,diagp ;apunto al empaquetado del dia
	mov byte[len],3
	call separardigitos
	mov si,0
	mov bx,diaga ; apunto a la cadena del dia
	call aascii
	call abin
	cmp byte[diab],0 ; dia no debe ser igual a 0
	je datInval
	mov bx,tabdiasxmes ;apunto a tabla
	mov si,0
	mov al,[mesb] ; cargo en al el mes
	sub al,1 ; = mesb-1
	mov byte[temp],0
	mov byte[procdia],0
cmpmes:
	cmp al,byte[temp]
	je  valdia
	inc si ; incremento contador para desplazarme en la tabla
	add byte[temp],1
	jmp cmpmes 
valdia:
	;valido el dia leido del archivo
	mov al,[diab]
	cmp byte[mesb],2
	je valfeb
	cmp al,[bx+si]
	jg  datInval
	jmp calcfecjul
valfeb:
	cmp byte[esbis],1
	jne cmpdfeb
	add al,1
	jmp calcfecjul
cmpdfeb:
	cmp al,[bx+si]
	jg  datInval 
	jmp calcfecjul
separardigitos:
;En esta rutina se validan los empaquetados recibidos como dato
;con el siguiente criterio:
;-Hasta no llegar al digito que representa el signo del empaquetado,
;c/u debe ser menor a 9
;-El ultimo digito debe ser mayor a 9(de lo contrario no seria empaquetado)
;-El signo del empaquetado debe ser positivo (C,A,F,E)
;Tambien se validan datos que cumplan con el siguiente formato:
;Mes debe ser de la forma 0MM (con M>=0) 
;(cualquier otra caracteristica se valida en "procesarmes")
;Dia debe ser de la forma 0DD (con D>=0)
;(cualquier otra caracteristica se valida en "procesardia")
;Anio debe ser de la forma 000AAAA (con A>=0)
;Antes de la rutina se debe apuntar con bx el empaquetado del cual
;se quieren obtener los digitos, y cargar la cantidad de digitos en len
;(sin contar el del signo )
	xor ax,ax
	xor dx,dx
	mov dl,[len] 
	mov byte[temp],0
	mov si,0
	mov di,0
separarbyte: ;ciclo para separar bytes
	mov al,[bx + si]
	shr al,4
	cmp al,9
	jg datInval
	mov [digitos+di],al
	inc di
	add byte[temp],1
	cmp dl,byte[temp]
	je  analizardigitos
	mov al,[bx + si]
	shl al,4
	xor ah,ah
	shr al,4
	cmp al,9
	jg datInval
	mov [digitos+di],al
	add byte[temp],1
	inc si
	inc di
	jmp separarbyte
analizardigitos:
	mov al,[bx + si]
	shl al,4
	xor ah,ah
	shr al,4
	cmp al,10
	jl datInval ; el dato no es empaquetado no tiene A,B,C,D,E,F
	cmp al,11
	je datInval ; el empaquetado es negativo tiene B
	cmp al,13
	je datInval ; el empaquetado es negativo tiene D
	cmp byte[digitos],0
	jg  datInval ; el primer digito debe ser 0 en los tres casos
	cmp byte[procanio],1
	je valanio
	ret
valanio:
;verifico que sea un anio de la forma aaaa
	cmp byte[digitos+1],0
	jne datInval
	cmp byte[digitos+2],0
	jne datInval
	mov bx,anioga
	mov si,0
aascii:
;Convierto a cada digito en un caracter ascii sumando 48 
;a cada uno y los voy copiando en la cadena apuntada por bx+si.
;antes se debe cargar la cantidad de digitos en len.
	xor ax,ax
	mov di,0
	mov byte[temp],0
	mov dl,[len] 
empaacar: ;ciclo para convertir digito a caracter y copiarlo a la cadena
	xor ax,ax
	mov ax,[digitos+di]
	add ax,48
	mov [bx+si],al
	inc si
	xor ax,ax
	inc di
	add byte[temp],1
	cmp byte[temp],dl
	jne empaacar
finascii:
    ;pongo fin de linea
	mov byte[bx+si],'$'
	ret
abin:
;Convierto el numero almacenado en el empaquetado a convertir
;a binario. Multiplico un digito por 10 y sumo el digito consecutivo
;hasta llegar al ultimo.Se empieza por el digito más significativo.
	xor dx,dx
	xor ax,ax
	mov al,[digitos]
	mov bl,[multi10]	
	imul bx 
	add al,[digitos+1]
	mov [aniob+2],ah
	mov [aniob+3],al
	imul bx
	add al,[digitos+2]
	cmp byte[procmes],1
	jne cont
	mov [mesb],al
	ret
cont:
	cmp byte[procdia],1
	jne cnvbin2w
	mov [diab],al
	ret
cnvbin2w: ; 
	imul bx
	add al,[digitos+3]
	mov [aniob+2],ah
	mov [aniob+3],al
	imul bx
	add al,[digitos+4]
	mov [aniob+2],dx
	mov [aniob],ax 
	mul bx 
	mov [temp],dl
	add al,[digitos+5]
	mov [aniob],ax	
	mov ax,[aniob+2]
	mul bx
	add ax,[temp]
	mov [aniob+2],ax
	mov ax,[aniob]
	mul bx
	mov [temp],dl
	add al,[digitos+6]
	mov [aniob],ax
	mov ax,[aniob+2]
	mul bx
	add ax,[temp]
	mov [aniob+2],ax

esbisiest:
;Para analizar qué anio es bisiesto se tiene la siguiente regla:
;-Si es divisible por 100 y divisible por 400,es bisiesto
;-Si no es divisible por 100 pero si por 4, es bisiesto
	mov ax,[aniob]
	cmp byte[digitos+3],1 ; si el 5to digito=4, se puede dividir entre 400,100 y 4
	jl sidivx400
	mov byte[moi400],1 ; seteo flags de division
	mov byte[moi100],1
	mov byte[moi4],1
	jmp divx100
sidivx400:
	cmp byte[digitos+4],4 ; si el 5to digito=4, se puede dividir entre 400,100 y 4
	jl sidivx100
	mov byte[moi400],1 ; seteo flags de division
	mov byte[moi100],1
	mov byte[moi4],1
	jmp divx100
sidivx100:
	cmp byte[digitos+4],1 ; si el 5to digito>=1 y <4, se puede dividir entre 100 y 4
	jl  sidivx4
	mov byte[moi100],1 ;seteo flags de division
	mov byte[moi4],1	
	jmp divx4
sidivx4:
	cmp byte[digitos+5],0 ; si el 6to digito>0 se puede dividir entre 4
	je  next
	mov byte[moi4],1
	jmp divx4
next:
	cmp byte[digitos+6],4 ; si el 7to digito>=4, se puede dividir entre 4
	jl  procesarmes
	mov byte[moi4],1	
	jmp divx4
divx100:
;El objetivo de la division es para ver si obtengo un resto=0.
;Para la division entre 100 basta que el dividendo sea un 
;multiplo entero de 100, y esto se verifica
;si los 2 ultimos digitos de un numero son iguales a 0
	xor bx,bx 
	mov al,[digitos+5]
	cmp al,0 
	jne divx4 
	mov al,[digitos+6]
	cmp al,0
	jne divx4 ;al no ser multiplo de 100, lo divido por 4
divx400:
	cmp byte[moi400],1 ;verifico que el anio sea divisible por 400
	jne procesarmes
	mov bx,[div400]
	mov ax,[aniob]
	mov dx,[aniob+2]
	div bx
	cmp dx,0
	jne procesarmes
	mov byte[esbis],1 ;indico que el anio es bisiesto
	jmp procesarmes
divx4:
;Una division entre una potencia n de 2 equivale a n corrimientos de bits
;hacia la derecha.La division por 4 equivale a 2 corrimientos.
;Se considera como resto los bits que quedan afuera 
	cmp byte[moi4],1
	jne procesarmes
	mov al,[aniob] ;divido con corrimientos
	shl al,6 ;me quedo con los 2 bits que son eliminados en 2 corrimientos a derecha
	cmp al,0
	jne procesarmes
	mov byte[esbis],1 ;indico que el anio es bisiesto
	jmp procesarmes
calcfecjul:
;En esta rutina sumo los dias
	mov bx,tabsumdias ;apunto a la tabla con bx
	mov al,[temp] ; temp=mes-1(resultado de rutina anterior)
	sub al,1 
	imul byte[dos] ; multiplico por long de elementos de la tabla
	mov si,0
	mov [temp],al
	xor ax,ax
desptabla:
	cmp al,byte[temp]
	je  sumdias
	inc si
	add al,1
	jmp desptabla 
sumdias:
	xor ax,ax
	cmp byte[mesb],1
	je sumardiasdelmes ;no sumo dias anteriores al mes de enero
sumdiasantesdelmes:
	mov ax,[bx+si] ;copio los dias transcurridos hasta el inicio del mes de la fecha
sumardiasdelmes:
	add al,[diab] ; sumo los dias transcurridos desde el inicio del mes de la fecha 
	mov [diaju],ax
	cmp byte[esbis],1 ; si es bisiesto sumo un dia mas
	je  sum1
	jmp diasjuascii
sum1:
	cmp byte[mesb],3 ;verifico que el mes sea mayor o igual a 3
	jl diasjuascii
	add ax,1
	mov [diaju],ax ;copio la cantidad de dias a variable diaju
diasjuascii:
;Copio a una cadena los digitos de los dias en formato juliano 
;convertidos a caracteres.Para esto divido por 10 el numero de dias en binario,
;y convierto el resto a ascii.Se divide hasta que el dividendo sea < divisor,
;llegado a este punto, se convierte también el cociente a ascii. 
	mov byte[diajuascii+4],'$'
	mov bx,[multi10]
	mov ax,[diaju]
	div bx
	add dx,48
	mov [diajuascii+3],dl
	xor dx,dx ; sino hace overflow
	div bx
	add dx,48
	mov [diajuascii+2],dl
	xor dx,dx ;sino hace overflow
	add ax,48
	mov [diajuascii+1],al
	mov byte[diajuascii],'0'
armarfechajul:
;copio a una cadena la cantidad de dias y 
;los ultimos dos digitos del anio de la fecha leida
	mov ax,[anioga+5]
	mov [fechjuascii],ax
	mov ax,[diajuascii]
	mov [fechjuascii+2],ax
	mov ax,[diajuascii+2]
	mov [fechjuascii+4],ax
	mov byte[fechjuascii+6],'$'
impresultado:
;imprimo resultado
	mov dx,msjres1
	mov ah,9
	int 21h
	mov dx,anioga+3
	mov ah,9
	int 21h
	mov dx,mesga+1
	mov ah,9
	int 21h	
	mov dx,diaga+1
	mov ah,9
	int 21h
	mov dx,salto
	mov ah,9
	int 21h
	mov dx,msjres2
	mov ah,9
	int 21h
	mov dx,fechjuascii	
	mov ah,9
	int 21h  
sisigo:
	mov byte[moi100],0
	mov byte[moi4],0
	mov byte[moi400],0
	mov byte[esbis],0
	mov dx,salto
	mov ah,9
	int 21h
	mov dx,continuar
	mov ah,9
	int 21h	
	mov dx,salto
	mov ah,9
	int 21h
	mov ah,8 ; recibo  opcion del usuario
	int 21h
	cmp al,83 
	je lectura
	cmp al,78
	je fin
	jmp sisigo	
datInval:
	mov dx,salto
	mov ah,9
	int 21h
	mov dx,msjErrdat
	mov ah,9
	int 21h
	jmp sisigo
errOpen:
	mov dx,msjErrOpen
	mov ah,9
	int 21h
	mov dx,salto
	mov ah,9
	int 21h
	jmp fin
errRead:
	mov dx,msjErrRead
	mov ah,9
	int 21h
	mov dx,salto
	mov ah,9
	int 21h
errClose:
	mov dx,salto
	mov ah,9
	int 21h
	mov dx,msjErrClose
	mov ah,9
	int 21h
	jmp fin 
noreg:
	mov dx,noregis
	mov ah,9
	int 21h
closeFil:
	mov bx,[fHandle]
	mov ah,3eh		
	int 21h
	jc errClose
fin:
	mov dx,salto
	mov ah,9
	int 21h
	mov dx,adios
	mov ah,9
	int 21h
	mov ax,4c00h  ; retornar al DOS
	int 21h
