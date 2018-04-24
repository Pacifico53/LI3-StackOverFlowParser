#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "diaNodo.h"
#include "common.h"

/* diaNodo
 *  Isto é o que esta dentro de cada dia
 */
struct diaNodo{
    int id_dia;
    GHashTable* questions;
    GHashTable* answers;
};

DIA create_nodo_dia (GHashTable* questions_a, GHashTable* answers_a){
	DIA a = malloc(sizeof(struct diaNodo));
	a->questions = questions_a;
	a->answers = answers_a;
    return a;
}

int get_id_dia(DIA a){
    return a->id_dia;
}

GHashTable* get_questions(DIA a){
	return (a->questions);
}

GHashTable* get_answers(DIA a){
	return (a->answers);
}


void free_diaNodo (DIA a){
	free(a);
}
