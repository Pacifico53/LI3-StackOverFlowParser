#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "mesesArray.h"
#include "common.h"

/* mesesArray
 *  Array dos meses, que esta dentro de cada ano, e dentro deste vao estar dias
 */
struct mesesArray {
    int id_mes;
    GArray* dias;
};

MESES create_array_meses (GArray* dias_a){
    MESES a = malloc(sizeof(struct mesesArray));
    a->dias = dias_a;
    return a;
}

int get_id_mes(MESES a){
    return a->id_mes;
}

GArray* get_dias(MESES a){
    return a->dias;
}

void free_mesesArray (MESES a){
    free(a);
}
