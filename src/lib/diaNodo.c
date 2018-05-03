#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "diaNodo.h"
#include "common.h"

/* diaNodo
 *  Estrutura de cada nodo dia, com hashtable de posts questions e hashtable posts answers
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
    return (a->questions);
}

GHashTable* get_answers(DIA a){
    return (a->answers);
}


void free_diaNodo (DIA a){
    g_hash_table_destroy(a->questions);
    g_hash_table_destroy(a->answers);
    free(a);
}
