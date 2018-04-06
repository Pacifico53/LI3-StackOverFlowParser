#include <date.h>
#include <stdio.h>
#include <string.h>
#include <common.h>
#include <user.h>
#include <unistd.h>
#include <libxml/parser.h>
#include <libxml/tree.h>


static void print_element_names(xmlNode * a_node){
    xmlNode *cur_node = NULL;
    for (cur_node = a_node; cur_node; cur_node = cur_node->next) {
         if (cur_node->type == XML_ELEMENT_NODE) {
             printf("%s:\n", cur_node->name);
             xmlAttr* attribute = cur_node->properties;
             while (attribute && attribute->name && attribute->children) {
                 xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                 printf("\t%s: %s\n", attribute->name, value);
                 xmlFree(value);
                 attribute = attribute->next;
             }
             printf("----------------------------------------\n");
         }
        print_element_names(cur_node->children);
   }
}

static void store_users(xmlNode * a_node){
    xmlNode *cur_node = NULL;
    USER* users = (USER*) malloc(sizeof(USER) * 1000000);
    int index;
    for (cur_node = a_node, index = 0; cur_node; cur_node = cur_node->next, index++) {
         if (cur_node->type == XML_ELEMENT_NODE) {
          printf("%s:\n", cur_node->name);
             xmlAttr* attribute = cur_node->properties;
             char *id, *displayName, *bio;
             int upVotes = 0, downVotes = 0, nPosts = 0, reputation = 0;
             long *posts;
             while (attribute && attribute->name && attribute->children) {
                xmlChar* xml_value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                char *value = (char*) xml_value;
                printf("\t%s: %s\n", attribute->name, value);
                //sprintf(value,"%s",xml_value);
                if (1 == strcmp(attribute->name,"Id")){
                  id = mystrdup(value);
                }
                else if (1 == strcmp(attribute->name,"UpVotes")){
                  upVotes = atoi(value);      
                }
                else if (1 == strcmp(attribute->name,"DownVotes")){
                  downVotes = atoi(value);
                }
                else if (1 == strcmp(attribute->name,"DisplayName")){
                  displayName = mystrdup(value);
                }
                //else if (!strcmp(attribute->name,"nPosts")){
                //  nPosts = atoi(value);
                //}
                //else if (1 == strcmp(attribute->name,"AboutMe")){
                //  bio = mystrdup(value);
                //}
                else if (1 == strcmp(attribute->name, "Reputation")){
                  reputation = atoi(value);
                }
                //else if (!strcmp(attribute->name, "posts")){
                //  memcpy(posts, value, sizeof(long))
                //}

                xmlFree(value);
                attribute = attribute->next;
             }
             printf("----------------------------------------1\n");
             USER user = create_user(id, upVotes, downVotes, displayName, nPosts, bio, reputation, posts);
             print_user(user);
             users[index] = user;
             printf("----------------------------------------2\n");
        }
        printf("Index: %d\n",index);
        store_users(cur_node->children);    
    }
}

int main(int argc, char **argv){
   xmlDoc *doc = NULL;
   xmlNode *root_element = NULL;
  
   if (argc != 2)  return(1);
   LIBXML_TEST_VERSION    // Macro to check API for match with
                          // the DLL we are using
    //parse the file and get the DOM
   if ((doc = xmlReadFile(argv[1], NULL, 0)) == NULL){
       printf("error: could not parse file %s\n", argv[1]);
       exit(-1);
   }
    //Get the root element node 
    root_element = xmlDocGetRootElement(doc);
    //print_element_names(root_element);
    store_users(root_element);
    xmlFreeDoc(doc);       // free document
    xmlCleanupParser();    // Free globals
    return 0;
}