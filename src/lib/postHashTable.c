#include <glib.h>
#include "date.h"
#include <string.h>
#include <stdlib.h>
#include "postHashTable.h"
#include "common.h"
#include <stdio.h>
/*struct HASTABLE_posts
 * Estrutura da hastable de posts PERGUNTAS.
 *
 */
struct HASHTABLE_posts {
    long id_p;
    int score_p;           //Upvotes - Downvotes
    long user_id;
    GString* titulo;
    int comment_count;
    GString* tags;
    int numeroRespostas; //Calculado por nos
};

POSTS create_hashtable_posts(long id_p, int score_p, long user_id, GString* titulo, int comment_count, GString* tags, int numeroRespostas){
	POSTS p = malloc(sizeof(struct HASHTABLE_posts));
	p->id_p = id_p;
	p->score_p = score_p;
	p->user_id = user_id;
	p->titulo = titulo;
	p->comment_count = comment_count;
	p->tags = tags;
	p->numeroRespostas = numeroRespostas;
	return p;
}
	
long get_id_p(POSTS p){
	return p->id_p;
}

int get_score_p(POSTS p){
	return p->score_p;
}

long get_user_id(POSTS p){
	return p->user_id;
}

GString* get_titulo(POSTS p){
	return p->titulo;
}

int get_comment_count(POSTS p){
	return p->comment_count;
}

GString* get_tags(POSTS p){
	return p->tags;
}		

int get_numeroRespostas(POSTS p){
	return p->numeroRespostas;
}

GList *get_listaTags(POSTS p){
    GList *result = NULL;
    char *tags = get_tags(p)->str;
    char *token = strtok(tags, ">");
    while(token) {
        result = g_list_prepend(result, token);
        token = strtok(NULL, ">");
    }
    return result;
}

void free_postHashTable(POSTS p){
	free(p->titulo);
    free(p->tags);
	free(p);
}
