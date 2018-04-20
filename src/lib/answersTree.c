#include <glib.h>
#include "date.h"
#include <string.h>
#include <stdlib.h>
#include "answersTree.h"
#include "common.h"

/* struct TREE_answers
 * Estrutura de cada nodo da arvore de posts de resposta.
 *
 */
struct TREE_answers {
    long id_a;
    Date data_a;
    int score_a;
    long user_id_a;
    int comment_count_a;
};

ANSWERS create_tree_answers (long id_a, Date data_a, int score_a, long user_id_a, int comment_count_a){
	ANSWERS a = malloc(sizeof(struct TREE_answers));
	a->id_a = id_a;
	a->data_a = data_a;
	a->score_a = score_a;
	a->user_id_a = user_id_a;
	a->comment_count_a = comment_count_a;
	return a;
}

long get_id_a(ANSWERS a){
	if(a)
		return(a->id_a);
	return NULL;
}

Date get_data_a(ANSWERS a){
	if(a)
		return(a->data_a);
	return NULL;
}

int get_score_a(ANSWERS a){
	if(a)
		return(a->score_a);
	return NULL;
}

long get_user_id_a(ANSWERS a){
	if(a)
		return(a->user_id_a);
	return NULL;
}

int get_comment_count_a(ANSWERS a){
	if(a)
		return(a->comment_count_a);
	return NULL;
}

void free_answerstree (ANSWERS a){
	if(a){
		free(a);
	}
}
