#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "tags.h"
#include "common.h"

/* Estrutura do que esta em cada nodo da hastable das tags
 *  
 */
struct HASHTABLE_tags {
    GString * tagname;
    int count;
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

void increment_tagCount(TAG t){
    t->count++;
}

void free_tagsHashTable(TAG t) {
    free(t->tagname);
    free(t);
}
