#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "diaNodo.h"
#include "common.h"

/* diaNodo
 *  Isto é o que esta dentro de cada dia
 */
struct diaNodo{
    GHashTable* questions;
    GHashTable* answers;
};

DIA create_nodo_dia (GHashTable* questions_a, GHashTable* answers_a){
	DIA a = malloc(sizeof(struct diaNodo));
	a->questions = questions_a;
	a->answers = answers_a;
    return a;
}

GHashTable* get_questions(DIA a){
	if(a)
		return(a->questions);
	return NULL;
}

GHashTable* get_answers(DIA a){
	if(a)
		return(a->answers);
	return NULL;
}


void free_diaNodo (DIA a){
	if(a){
		free(a);
	}
}
