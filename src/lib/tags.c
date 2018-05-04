#include "tags.h"

/* Estrutura do que esta em cada nodo da hastable das tags
 *  
 */
struct HASHTABLE_tags {
    GString * tagname;
    int count;      //inicializado sempre a 0, e so alterado pela funçao increment_tagCount
    long tag_id;
};

TAG create_hashtable_tag(long id, GString* name, int tagcount) {
  TAG t = malloc(sizeof(struct HASHTABLE_tags));
  t->tag_id = id;
  t->tagname = name;
  t->count = tagcount;  
  return t;
}

long get_id_tag(TAG t) {
    return t->tag_id;
}

GString* get_tagName(TAG t) {
    return t->tagname;
}

int get_tagCount(TAG t) {
    return t->count;
}

//Esta funçao é necessaria na query 11, para incrementar o uso de uma tag, e depois poder se ordenar as tags por esse valor
void increment_tagCount(TAG t){
    t->count++;
}

void free_tagsHashTable(TAG t) {
    g_string_free(t->tagname, TRUE);
    free(t);
}
