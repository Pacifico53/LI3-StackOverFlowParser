#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "mesesArray.h"
#include "common.h"

/* mesesArray
 *  Array dos meses, que esta dentro de cada ano, e dentro deste vao estar dias
 */
struct mesesArray {
    GArray* dias;
};

MESES create_array_meses (GArray* dias_a){
    MESES a = malloc(sizeof(struct mesesArray));
    a->dias = dias_a;
    return a;
}

GArray* get_dias(MESES a){
    return a->dias;
}

void free_mesesArray (MESES a){
    free(a);
}
