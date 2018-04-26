#include "parser.h"

Date xmlToDate(char* val){
	Date d;
	int ano1 = 0;
	int mes1 = 0;
	int dia1 = 0;
	int i;
	char ano[5];
    strcpy(ano, "1234");
	char mes[3];
    strcpy(mes, "12");
	char dia[3];
    strcpy(dia, "12");
	for(i=0;i<4;i++)
		ano[i]=val[i];
	val = val+5;
	for(i=0;i<2;i++)
		mes[i]=val[i];
	val = val+3;
	for(i=0;i<2;i++)
		dia[i]=val[i];
	ano1 = atoi(ano);
	mes1 = atoi(mes);
	dia1 = atoi(dia);
	d = createDate(dia1, mes1, ano1);
	printf("Ano = %d, Mes =  %d, Dia = %d\n", get_year(d),get_month(d),get_day(d));
	return d;	
}

GArray * init_calendario(){
    int i = 0;
    int j = 0;
    int d = 0;
   
    GArray *calendario = g_array_new(FALSE, FALSE, sizeof(GArray*));

    for (i = 0; i < 10; i++) {
        GArray* um_ano = g_array_new(FALSE, FALSE, sizeof(GArray *));
        for (j = 0; j < 12; j++) {
            GArray* um_mes = g_array_new(FALSE, FALSE, sizeof(GArray *));
            for (d = 0; d < 31; d++) {
                GHashTable *htquestions = g_hash_table_new_full(g_direct_hash, g_direct_equal, NULL, NULL);
                GHashTable *htanswers = g_hash_table_new_full(g_direct_hash, g_direct_equal, NULL, NULL);
                DIA um_dia = create_nodo_dia(htquestions, htanswers);
                g_array_prepend_val(um_mes, um_dia);
            }
            g_array_prepend_val(um_ano, um_mes);
        }
        g_array_prepend_val(calendario, um_ano);
    }

    return calendario;
}

GArray * insert_hastable_answers_calendario(GArray * calendario, ANSWERS ans, int ano, int mes, int dia){
    int id = get_id_a(ans);
    printf("id = %d\n", id);
    printf("Ano - 2009 = %d\n",ano);
    //GHashTable* answers;
    GArray* ano_post;
    ano_post = g_array_index(calendario, GArray *, ano);
    printf("Mes = %d\n", mes);
    
    GArray* mes_post;
    mes_post = g_array_index(ano_post, GArray *, mes-1);
    printf("Dia = %d\n", dia);
    DIA dia_post = g_array_index(mes_post, DIA, dia-1);
    GHashTable* answers = get_answers(dia_post);
    
    if (answers) {
        printf("Hash nao nula!\n");
    }else printf("HASH NULA!!!!!!!\n"); 

    gboolean teste = g_hash_table_insert(answers, GINT_TO_POINTER(id), ans);
    
    if (teste) {
        printf("Resultou!\n-----------------------------\n");
    }else printf("Nao deu!\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"); 

    return calendario;
}

GArray * insert_hastable_questions_calendario(GArray * calendario, POSTS qq, int ano, int mes, int dia){
    int id = get_id_p(qq);
    printf("id = %d\n", id);
    printf("Ano - 2009 = %d\n",ano);
    GArray* ano_post;
    ano_post = g_array_index(calendario, GArray *, ano);
    printf("Mes = %d\n", mes);
    
    GArray* mes_post;
    mes_post = g_array_index(ano_post, GArray *, mes-1);
    printf("Dia = %d\n", dia);
    DIA dia_post = g_array_index(mes_post, DIA, dia-1);
    GHashTable* questions = get_questions(dia_post);
    
    if (questions) {
        printf("Hash nao nula!\n");
    }else printf("HASH NULA!!!!!!!\n"); 

    gboolean teste = g_hash_table_insert(questions, GINT_TO_POINTER(id), qq);
    
    if (teste) {
        printf("Resultou!\n-----------------------------\n");
    }else printf("Nao deu!\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"); 

    return calendario;
}

static void print_element_namesu(xmlNode * a_node){
    xmlNode *cur_node = NULL;
    xmlChar* value = NULL;
    char* nomec;
    char* aboutmec;
    long id;
    int reputation;
    USERS users;
    GHashTable* u = g_hash_table_new(g_direct_hash, g_direct_equal);  
    for (cur_node = a_node; cur_node; cur_node = cur_node->next) {
        if (cur_node->type == XML_ELEMENT_NODE) {
            printf("%s:\n", cur_node->name);
            xmlAttr* attribute = cur_node->properties;
            while (attribute && attribute->name && attribute->children) {
                if(!strcmp((char *)attribute->name,"Reputation")){
             	    value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                    reputation = atoi((char *)value);
                    printf("\t%s: %s -> %d\n", attribute->name, value, reputation);
                    xmlFree(value);
                }
                if(!strcmp((char *)attribute->name,"Id")){
                    value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                    id = atol((char *)value);
                    printf("\t%s: %s -> %lu\n", attribute->name, value, id);
                    xmlFree(value);
                }
                if(!strcmp((char *)attribute->name,"AboutMe")){
                    value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                    aboutmec = (char *)value;
                    printf("\t%s: %s -> %s\n", attribute->name, value, aboutmec);
                    xmlFree(value);
                }
                if(!strcmp((char *)attribute->name,"DisplayName")){
                    value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                    nomec = (char *)value;
                    printf("\t%s: %s -> %s\n", attribute->name, value, nomec);
                    xmlFree(value);
                }
                attribute = attribute->next;
         	}
            users= create_hashtable_users(id,nomec,aboutmec,reputation);
            g_hash_table_insert(u, GSIZE_TO_POINTER(id), users);
        }
    print_element_namesu(cur_node->children);
    }
}

static void print_element_namesq(GArray *calendario, xmlNode * a_node){
    xmlNode *cur_node = NULL;
    xmlChar* tipo = NULL;
    int para = 1;
    int parar = 1;
    long id_p;
    int score_p;
    long user_id;
    char* titulo;
    int comment_count;
    char* tags;
    int numeroRespostas;
    char* data;
    Date d;
    POSTS questions;
    for (cur_node = a_node; cur_node; cur_node = cur_node->next) {
        para = 1;
        parar = 1;
        if (cur_node->type == XML_ELEMENT_NODE) {
            xmlAttr* attribute = cur_node->properties;
            xmlAttr* aux = cur_node->properties;
            while (aux && aux->name && aux->children && para){
                if(!strcmp((char *)aux->name, "PostTypeId")){
                    para = 0;
                    tipo = xmlNodeListGetString(cur_node->doc, aux->children, 1);
                }
                aux = aux->next;
            }
            if(tipo && !strcmp((char *)tipo, "1")){
                while (aux && aux->name && aux->children && parar){
                	if(!strcmp((char *)aux->name, "CreationDate")){
                    	parar = 0;
                    	xmlChar* value = xmlNodeListGetString(cur_node->doc, aux->children, 1);
                    	data = (char *)value;
                    	d = xmlToDate(data);
                    	printf("\t%s: %s\n", aux->name, value);
                	}
                	aux = aux->next;
            	}
                while (attribute && attribute->name && attribute->children) {
                    if(!strcmp((char *)attribute->name,"Id")){ 
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        id_p = atol((char *)value);
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"Score")){ 
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        score_p = atoi((char *)value);
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"OwnerUserId")) { 
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        user_id =atol((char * )value);
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"Title")){ 
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        titulo = (char *) value;
                        xmlFree(value);
                    } 
                    if(!strcmp((char *)attribute->name,"Tags")){ 
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        tags = (char *) value;
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"CommentCount")){ 
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        comment_count = atoi((char *)value);
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"AnswerCount")){ 
                       xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                       numeroRespostas = atoi((char *)value);
                       xmlFree(value);
                    }
                    attribute = attribute->next;
            }
            questions = create_hashtable_posts(id_p, score_p, user_id, titulo, comment_count, tags, numeroRespostas); 
            insert_hastable_questions_calendario(calendario, questions,get_year(d)-2009, get_month(d),get_day(d));
            }
        }
        print_element_namesq(calendario, cur_node->children);
    }
}

static void print_element_namesa(GArray * calendario, xmlNode * a_node){
    xmlNode *cur_node = NULL;
    xmlChar* tipo = NULL;
    int para = 1;
    int parar = 1;
    long id_a;
    int score_a;
    long user_id_a;
    int comment_count_a;
    long parent_id;
    char* data;
    Date d;
    ANSWERS respostas;
    for (cur_node = a_node; cur_node; cur_node = cur_node->next) {
        para = 1;
        parar = 1;
        if (cur_node->type == XML_ELEMENT_NODE) {
            xmlAttr* attribute = cur_node->properties;
            xmlAttr* aux = cur_node->properties;
            while (aux && aux->name && aux->children && para){
                if(!strcmp((char *)aux->name, "PostTypeId")){
                    para = 0;
                    tipo = xmlNodeListGetString(cur_node->doc, aux->children, 1);
                }
                aux = aux->next;
            }
            if(tipo && !strcmp((char *)tipo, "2")){
            	while (aux && aux->name && aux->children && parar){
                	if(!strcmp((char *)aux->name, "CreationDate")){
                    	parar = 0;
                    	xmlChar* value = xmlNodeListGetString(cur_node->doc, aux->children, 1);
                    	data = (char *)value;
                    	d = xmlToDate(data);
                    	printf("\t%s: %s\n", aux->name, value);
                	}
                	aux = aux->next;
            	}
                while (attribute && attribute->name && attribute->children) {
                    if(!strcmp((char *)attribute->name, "Id")){
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        id_a = atol((char *)value);
                        xmlFree(value);
                    } 
                    if(!strcmp((char *)attribute->name,"Score")){
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        score_a = atoi((char *)value);
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"OwnerUserId")){
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        user_id_a = atol((char *)value);
                        xmlFree(value);
                    } 
                    if(!strcmp((char *)attribute->name,"CommentCount")){
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        comment_count_a = atoi((char *)value);
                        xmlFree(value);
                    } 
                    if(!strcmp((char *)attribute->name,"ParentId")) {
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        parent_id = atol((char *)value);
                        xmlFree(value);
                    }
                    attribute = attribute->next;                                            
                }
                respostas = create_hashtable_answers (id_a, score_a, user_id_a, comment_count_a, parent_id);
                insert_hastable_answers_calendario(calendario, respostas,get_year(d)-2009, get_month(d),get_day(d)); 
            }
        }
        print_element_namesa(calendario, cur_node->children);
    }
}

void parse_answers(GArray * calendario, char* path){
    printf("--------------------------\nA iniciar parse answers...\n--------------------------\n");
    char* pathfile = malloc(128 * sizeof(char));
    strcpy(pathfile, path);
    xmlDoc *doc = NULL;
    xmlNode *root_element = NULL;
    strcat(pathfile, "/Posts.xml");
    doc = xmlReadFile(pathfile, NULL, 0);
    //Get the root element node 
    root_element = xmlDocGetRootElement(doc);
    print_element_namesa(calendario, root_element);
    xmlFreeDoc(doc);
    xmlCleanupParser();
    printf("DONE!!!!!!\n");
}

void parse_questions(GArray * calendario, char* path){
    printf("--------------------------\nA iniciar parse questions...\n--------------------------\n");
    char* pathfile = malloc(128 * sizeof(char));
    strcpy(pathfile, path);
    xmlDoc *doc = NULL;
    xmlNode *root_element = NULL;
    strcat(pathfile, "/Posts.xml");
    doc = xmlReadFile(pathfile, NULL, 0);
    //Get the root element node 
    root_element = xmlDocGetRootElement(doc);
    print_element_namesq(calendario, root_element);
    xmlFreeDoc(doc);
    xmlCleanupParser();
    printf("DONE!!!\n");
}

void parse_users(GArray * calendario, char* path){
    printf("--------------------------\nA iniciar parse users...\n--------------------------\n");
    char* pathfile = malloc(128 * sizeof(char));
    strcpy(pathfile, path);
    xmlDoc *doc = NULL;
    xmlNode *root_element = NULL;
    strcat(pathfile, "/Users.xml");
    doc = xmlReadFile(pathfile, NULL, 0);
    //Get the root element node 
    root_element = xmlDocGetRootElement(doc);
    print_element_namesu(root_element);
    xmlFreeDoc(doc);
    xmlCleanupParser();
    printf("DONE!!!\n");
}

void parse(TCD_community com, char* path){
    printf("!!!O DUMP TEM DE ESTAR NO MESMO SITIO QUE A PASTA GRUPO2 !!!\n");
    GArray * calendario = init_calendario();
    //xmlDoc *doc = NULL;
    //xmlNode *root_element = NULL;
  
    LIBXML_TEST_VERSION    // Macro to check API for match with
                          // the DLL we are using
    //parse the file and get the DOM
    /*
    doc = xmlReadFile("Posts.xml", NULL, 0);
    
    //Get the root element node 
    root_element = xmlDocGetRootElement(doc);
    print_element_namesq(calendario, root_element);
    */
    parse_answers(calendario, path);
    parse_questions(calendario, path);
    parse_users(calendario, path);
    //xmlFreeDoc(doc);       // free document
    //xmlCleanupParser();    // Free globals
}

