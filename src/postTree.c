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
    long id;
    Date data;
    int score;           //Upvotes - Downvotes
    long user_id;
    char* titulo;
    int comment_count;
    char* tags;
    int numeroRespostas; //Calculado por nos
    GTree* post_answers;
};

POSTS create_tree_posts(long id, Date data, int score, long user_id, char* titulo, int comment_count, char* tags, int numeroRespostas, GTree* post_answers){
	POSTS p = malloc(sizeof(struct TREE_posts));
	p->id = id;
	p->data = data;
	p->score = score;
	p->user_id = user_id;
	p->titulo = mystrdump(titulo);
	p->comment_count = comment_count;
	p->tags = mystrdump(tags);
	p->numeroRespostas = numeroRespostas;
	p->post_answers = NULL; 
	return p;
}
	
long get_id(POSTS p){
	if(p)
			return(p->id);
	return NULL;
}

Date get_data(POSTS p){
	if(p)
			return(p->data);
	return NULL;
}

int get_score(POSTS p){
	if(p)
			return(p->score);
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

GTree* get_post_answers(POSTS p){
	if(p)
			return(p->post_answers);
	return NULL;
}


void free_posttree(POSTS p){
	if(p){
		free(p->get_titulo);
		free(p->get_tags);
		free(p);
	}
}
