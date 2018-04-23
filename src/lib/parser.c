#include "interface.h"
#include <stdio.h>
#include <stdlib.h>
#include <libxml/parser.h>
#include <libxml/tree.h>
#include <string.h>

insereUsers(GHashTable* u){
	char* idc;
	char* nomec;
	char* aboutmec;
	char* reputationc;
	long id;
	int reputation;
	USERS users;


xmlDocPtr doc = xmlParseFile("Users.xml"); 
if (doc == NULL) { 
	printf("Document not parsed. \n"); 
	return -1; 
	} 
	xmlNodePtr cur = xmlDocGetRootElement(doc); 
	if(!cur){ 
		printf("Empty document\n"); xmlFreeDoc(doc); 
		} 
	else { 
	for(cur = cur->xmlChildrenNode; cur; cur = cur->next) {
		idc = xmlGetProp(cur, "Id");
		reputationc = xmlGetProp(cur, "Reputation");
		aboutmec = xmlGetProp(cur, "AboutMe");
		nomec = xmlGetProp(cur, "DisplayName");
		id = atol(idc);
		reputation = atoi(reputationc);
		users = create_hashtable_users(id, nomec, aboutmec, reputationc);
		g_hash_table_insert(u, GSIZE_TO_POINTER(id), users);
		xmlFree(idc);
		xmlFree(reputationc);
		xmlFree(aboutmec);
		xmlFree(nomec);
		}
	}
}
