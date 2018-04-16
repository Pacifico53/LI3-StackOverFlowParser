#include "common.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// definido par nao dar warnings ao usar a funcao da GNU
char * mystrdup (const char *s) {
    if(s == NULL) return NULL;          
    char *d = malloc (strlen (s) + 1); 
    if (d == NULL) return NULL;       
    strcpy (d,s);                    
    return d;                       
}
