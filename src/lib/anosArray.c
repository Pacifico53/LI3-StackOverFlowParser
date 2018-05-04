#include "anosArray.h"

/* anosArray
 *  Array dos anos, dentro deste vai estar array de meses
 */
struct anosArray {
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

void free_anosArray (ANOS a){
    free(a);
}
