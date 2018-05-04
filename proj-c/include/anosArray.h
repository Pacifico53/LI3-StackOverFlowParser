#include "date.h"
#include <glib.h>
#include "common.h"
#include <string.h>
#include <stdlib.h>
#ifndef __ANOS__
#define __ANOS__


typedef struct anosArray* ANOS;


ANOS create_array_anos (GArray* meses_a);

GArray* get_meses(ANOS a);

void free_anosArray (ANOS a);

#endif
