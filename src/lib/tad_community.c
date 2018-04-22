#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "tad_community.h"

struct TCD_community{
    GArray* anos;
    //GArray* meses;
    //GArray* dias;
	//GHashTable* questions;
    //GHashTable* answers;
	GHashTable* userss;
};

TAD_community init_TCD_community(){
	TAD_community s = malloc(sizeof(struct TCD_community));
	s->userss = g_hash_table_new(g_direct_hash, g_direct_equal);
	return s;
}


GHashTable* get_hash_userss(TAD_community s){
	return s->userss;
}

void free_TCD_community(TAD_community s){
	if(s)
		free(s);
}
