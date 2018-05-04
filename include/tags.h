#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "common.h"
#ifndef __TAG__
#define __TAG__
typedef struct HASHTABLE_tags* TAG;

TAG create_hashtable_tag(long id, GString* name, int tagcount);

long get_id_tag(TAG t);

GString* get_tagName(TAG t);

int get_tagCount(TAG t);

void increment_tagCount(TAG t);

void free_tagsHashTable(TAG t);

#endif
