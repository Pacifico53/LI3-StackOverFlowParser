#include <glib.h>
#ifndef __TAD_community__
#define __TAD_comnunity__

typedef struct TCD_community * TAD_community;

TAD_community init_TCD_community();

GArray* get_array_anos(TAD_community s);

void set_array_anos(TAD_community s, GArray *anos);

GHashTable* get_hash_userss(TAD_community s);

void set_ht_users(TAD_community s, GHashTable *usersht);

void free_TCD_community(TAD_community s);

#endif
