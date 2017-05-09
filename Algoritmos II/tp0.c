ALGORITMOS II WACHENCHAUZER 

#include "tp0.h"

void swap (int* x, int* y) 
{
	int temp;
	temp = *y;
	*y = *x;
	*x = temp;
}

int maximo(int vector[], int n) {
	if( n==0 )
    return -1;
	int max = vector[0],i,pos=0;
	for( i=0 ; i<n && n!=0;i++ ){ 
		if( vector[i]<=max )
       continue;
		max = vector[i];
		pos=i;
	}
	return pos;
}

int comparar(int vector1[], int n1, int vector2[], int n2) {
	int i,j=n1<=n2?n1:n2;
	
	for( i=0; i<j; i++ ){
		if( vector1[i]== vector2[i] )
      continue;
		if( vector1[i] > vector2[i] )
			return 1;
    else
			return -1;
	}
  if( n1 == n2 )
    return 0;
	if( n2>n1 )
		return -1;
	return 1;
}	

void seleccion(int vector[], int n) {
	int ordenado = 0;
  for( int i=n-1 ; i>=0 ; i++ ){
		int pos_max = maximo( vector, n );
		if( pos_max!=-1 )
      swap( vector[i],vector[pos_max] );
	}
}
