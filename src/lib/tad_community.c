#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "tad_community.h"

struct TCD_community{
    GArray* anos;
   	GHashTable* userss;
    GHashTable* tagsht;
};

TAD_community init_TCD_community(){
	TAD_community s = malloc(sizeof(struct TCD_community));
	s->userss = g_hash_table_new_full(g_direct_hash, g_direct_equal, NULL, NULL);
	s->anos = g_array_new(FALSE, FALSE, sizeof(GArray *));
	s->tagsht = g_hash_table_new_full(g_direct_hash, g_direct_equal, NULL, NULL);
    return s;
}

GHashTable* get_hash_userss(TAD_community s){
	return s->userss;
}

GArray* get_array_anos(TAD_community s){
	return s->anos;
}

GHashTable* get_hash_tags(TAD_community s){
	return s->tagsht;
}

void set_ht_users(TAD_community s, GHashTable *usersht){
	s->userss = usersht;
}

void set_ht_tags(TAD_community s, GHashTable *tagssht){
	s->tagsht = tagssht;
}

void set_array_anos(TAD_community s, GArray *anos){
	s->anos = anos;
}

void free_TCD_community(TAD_community s){
	free(s);
}
