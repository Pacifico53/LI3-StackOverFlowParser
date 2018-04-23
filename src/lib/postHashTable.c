#include <glib.h>
#include "date.h"
#include <string.h>
#include <stdlib.h>
#include "postTree.h"
#include "common.h"

/*struct TREE_posts
 * Estrutura de cada nodo da tree de posts.
 *
 */
struct TREE_posts {
    long id_p;
    Date data_p;
    int score_p;           //Upvotes - Downvotes
    long user_id;
    char* titulo;
    int comment_count;
    char* tags;
    int numeroRespostas; //Calculado por nos
};

POSTS create_tree_posts(long id_p, Date data_p, int score_p, long user_id, char* titulo, int comment_count, char* tags, int numeroRespostas){
	POSTS p = malloc(sizeof(struct TREE_posts));
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
	if(p)
			return(p->id_p);
	return NULL;
}

Date get_data_p(POSTS p){
	if(p)
			return(p->data_p);
	return NULL;
}

int get_score_p(POSTS p){
	if(p)
			return(p->score_p);
	return NULL;
}

long get_user_id(POSTS p){
	if(p)
			return(p->user_id);
	return NULL;
}

char* get_titulo(POSTS p){
	if(p)
		return(p->titulo);
	return NULL;
}

int get_comment_count(POSTS p){
	if(p)
			return(p->comment_count);
	return NULL;
}

char* get_tags(POSTS p){
	if(p)
			return(p->tags);
	return NULL;
}		

int get_numeroRespostas(POSTS p){
	if(p)
			return(p->numeroRespostas);
	return NULL;
}

void free_posttree(POSTS p){
	if(p){
		free(p->titulo);
		free(p->tags);
		free(p);
	}
}
