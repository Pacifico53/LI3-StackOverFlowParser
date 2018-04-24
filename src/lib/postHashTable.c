#include <glib.h>
#include "date.h"
#include <string.h>
#include <stdlib.h>
#include "postHashTable.h"
#include "common.h"

/*struct HASTABLE_posts
 * Estrutura da hastable de posts PERGUNTAS.
 *
 */
struct HASHTABLE_posts {
    long id_p;
    Date data_p;
    int score_p;           //Upvotes - Downvotes
    long user_id;
    char* titulo;
    int comment_count;
    char* tags;
    int numeroRespostas; //Calculado por nos
};

POSTS create_hashtable_posts(long id_p, Date data_p, int score_p, long user_id, char* titulo, int comment_count, char* tags, int numeroRespostas){
	POSTS p = malloc(sizeof(struct HASHTABLE_posts));
	p->id_p = id_p;
	p->data_p = data_p;
	p->score_p = score_p;
	p->user_id = user_id;
	p->titulo = mystrdup(titulo);
	p->comment_count = comment_count;
	p->tags = mystrdup(tags);
	p->numeroRespostas = numeroRespostas;
	return p;
}
	
long get_id_p(POSTS p){
	return p->id_p;
}

Date get_data_p(POSTS p){
	return p->data_p;
}

int get_score_p(POSTS p){
	return p->score_p;
}

long get_user_id(POSTS p){
	return p->user_id;
}

char* get_titulo(POSTS p){
	return p->titulo;
}

int get_comment_count(POSTS p){
	return p->comment_count;
}

char* get_tags(POSTS p){
	return p->tags;
}		

int get_numeroRespostas(POSTS p){
	return p->numeroRespostas;
}

void free_postHashTable(POSTS p){
	free(p->titulo);
    free(p->tags);
	free(p);
}
