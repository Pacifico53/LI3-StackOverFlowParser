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
    long id;
    Date data;
    int score;
    long user_id;
    int comment_count;
};

ANSWERS create_tree_answers (long id, Date data, int score, long user_id, int comment_count){
	ANSWERS a = malloc(sizeof(struct TREE_answers));
	a->id = id;
	a->data = data;
	a->score = score;
	a->user_id = user_id;
	a->comment_count = comment_count;
	return a;
}

long get_id(ANSWERS a){
	if(a)
		return(a->id);
	return NULL;
}

Date get_data(ANSWERS a){
	if(a)
		return(a->data);
	return NULL;
}

int get_score(ANSWERS a){
	if(a)
		return(a->score);
	return NULL;
}

long get_user_id(ANSWERS a){
	if(a)
		return(a->user_id);
	return NULL;
}

int get_comment_count(ANSWERS a){
	if(a)
		return(a->comment_count);
	return NULL;
}

void free_answerstree (ANSWERS a){
	if(a){
		free(a);
	}
}
