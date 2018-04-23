#include "date.h"
#ifndef __DIA__
#define __DIA__


typedef struct diaNodo* DIA;


DIA create_nodo_dia (GHashTable* questions_a, GHashTable* answers_a);

GHashTable* get_questions(DIA a);

GHashTable* get_answers(DIA a);

void free_diaNodo (DIA a);

#endif
