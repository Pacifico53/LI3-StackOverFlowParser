#include "date.h"
#include <glib.h>
#include "common.h"
#include <string.h>
#include <stdlib.h>
#ifndef __MESES__
#define __MESES__


typedef struct mesesArray* MESES;


MESES create_array_meses (GArray* dias_a);

GArray* get_dias(MESES a);

void free_mesesArray (MESES a);

#endif
