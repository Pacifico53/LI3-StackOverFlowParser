#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "anosArray.h"
#include "common.h"

/* anosArray
 *  Array dos anos, dentro deste vai estar array de meses
 */
struct anosArray {
    int id_ano;
    GArray* meses;
};

ANOS create_array_anos (GArray* meses_a){
	ANOS a = malloc(sizeof(struct anosArray));
	a->meses = meses_a;
    return a;
}

GArray* get_meses(ANOS a){
	return (a->meses);
}

int get_id_ano(ANOS a){
    return a->id_ano;
}

void free_anosArray (ANOS a){
	free(a);
}
