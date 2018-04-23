#include <glib.h>
#include "date.h"
#include <string.h>
#include <stdlib.h>
#include "answersHashTable.h"
#include "common.h"

/* struct HASHTABLE_answers
 * Estrutura da hashtable de posts de resposta.
 *
 */
struct HASHTABLE_answers {
    long id_a;
    Date data_a;
    int score_a;
    long user_id_a;
    int comment_count_a;
    long parent_id;
};

ANSWERS create_hashtable_answers (long id_a, Date data_a, int score_a, long user_id_a, int comment_count_a, long parent_id){
	ANSWERS a = malloc(sizeof(struct HASHTABLE_answers));
	a->id_a = id_a;
	a->data_a = data_a;
	a->score_a = score_a;
	a->user_id_a = user_id_a;
	a->comment_count_a = comment_count_a;
    a->parent_id = parent_id;
	return a;
}

long get_id_a(ANSWERS a){
	if(a)
		return a->id_a;
	return -1;
}

Date get_data_a(ANSWERS a){
	if(a)
		return a->data_a;
	return NULL;
}

int get_score_a(ANSWERS a){
	if(a)
		return a->score_a;
	return -11111;
}

long get_user_id_a(ANSWERS a){
	if(a)
		return a->user_id_a;
	return -1;
}

int get_comment_count_a(ANSWERS a){
	if(a)
		return a->comment_count_a;
	return -1;
}

long get_parent_id_a(ANSWERS a){
	if(a)
		return a->parent_id;
	return -1;
}

void free_answersHashTable (ANSWERS a){
	if(a){
		free(a);
	}
}
