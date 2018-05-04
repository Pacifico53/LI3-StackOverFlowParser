#include "parser.h"
#include "tad_community.h"
#include "interface.h"


// Função auxiliar que transforma uma string, inicialmente retirada do dump, numa data
Date xmlToDate(char* val){
    Date d = NULL;
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
    return d;   
}


//Função que inicia um calendário com 10 anos, e que em cada dia tenha duas hashtable, uma corresponde ás perguntas e a outra corresponde ás respostas 
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
                GHashTable *htquestions = g_hash_table_new_full(g_direct_hash, g_direct_equal, NULL, NULL); //hashtable das perguntas
                GHashTable *htanswers = g_hash_table_new_full(g_direct_hash, g_direct_equal, NULL, NULL); //hashtable das respostas
                DIA um_dia = create_nodo_dia(htquestions, htanswers);
                g_array_prepend_val(um_mes, um_dia);
            }
            g_array_prepend_val(um_ano, um_mes);
        }
        g_array_prepend_val(calendario, um_ano);
    }
    return calendario;
}

//Função que insere uma resposta na hashtable que está no calendário
GArray * insert_hastable_answers_calendario(GArray * calendario, ANSWERS ans, int ano, int mes, int dia){
    int id = get_id_a(ans);
    GArray* ano_post = NULL;
    ano_post = g_array_index(calendario, GArray *, ano);
    
    GArray* mes_post = NULL;
    mes_post = g_array_index(ano_post, GArray *, mes-1);
    DIA dia_post = g_array_index(mes_post, DIA, dia-1);
    GHashTable* answers = get_answers(dia_post);
    
    g_hash_table_insert(answers, GINT_TO_POINTER(id), ans);
    
    return calendario;
}

//Função que insere uma pergunta na hashtable que está no calendário
GArray * insert_hastable_questions_calendario(GArray * calendario, POSTS qq, int ano, int mes, int dia){
    int id = get_id_p(qq);
    GArray* ano_post = NULL;
    ano_post = g_array_index(calendario, GArray *, ano);
    
    GArray* mes_post = NULL;
    mes_post = g_array_index(ano_post, GArray *, mes-1);
    DIA dia_post = g_array_index(mes_post, DIA, dia-1);
    GHashTable* questions = get_questions(dia_post);
    
    g_hash_table_insert(questions, GINT_TO_POINTER(id), qq);
    
    return calendario;
}


//Função que corre o ficheiro em xml e insere na estrutura os utilizadores com os dados que nós pretendemos guardar
void print_element_namesu(GHashTable * usersht, xmlNode * a_node){
    xmlNode *cur_node = NULL;
    xmlChar* value = NULL;
    long id = 0;
    int reputation = 0;
    USERS users;
    for (cur_node = a_node; cur_node; cur_node = cur_node->next) {
        if (cur_node->type == XML_ELEMENT_NODE) {
            xmlAttr* attribute = cur_node->properties;
            GString *nomec = g_string_new(NULL);
            GString *aboutmec = g_string_new(NULL);
            while (attribute && attribute->name && attribute->children) {
                if(!strcmp((char *)attribute->name,"Reputation")){                          //retirar a informção da "Reputation"
                    value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                    reputation = atoi((char *)value);
                    xmlFree(value);
                }
                if(!strcmp((char *)attribute->name,"Id")){                                  //retirar a informação do "Id"
                    value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                    id = atol((char *)value);
                    xmlFree(value);
                }
                if(!strcmp((char *)attribute->name,"AboutMe")){                             //retirar a informação do "AboutMe"
                    value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                    aboutmec = g_string_assign(aboutmec, (char *)value);
                    xmlFree(value);
                }
                if(!strcmp((char *)attribute->name,"DisplayName")){                         //retirar a informação do "DisplayName"
                    value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                    nomec = g_string_assign(nomec, (char *)value);
                    xmlFree(value);
                }
                attribute = attribute->next;
            }
            users = create_hashtable_users(id,nomec,aboutmec,reputation, 0); // cria o utilizador com as informações retiradas
            g_hash_table_insert(usersht, GSIZE_TO_POINTER(id), users);       // insere o utilizador na hashtable
        }
    print_element_namesu(usersht, cur_node->children);
    }
}

//Função que corre o ficheiro em xml e insere na estrutura as tags com os dados que nós pretendemos guardar
void print_element_tags(GHashTable * tagsht, xmlNode * a_node){
    xmlNode *cur_node = NULL;
    xmlChar* value = NULL;
    long id = 0;
    TAG uma_tag = NULL;
    for (cur_node = a_node; cur_node; cur_node = cur_node->next) {
        if (cur_node->type == XML_ELEMENT_NODE) {
            xmlAttr* attribute = cur_node->properties;
            GString *name = g_string_new(NULL);
            while (attribute && attribute->name && attribute->children) {
                if(!strcmp((char *)attribute->name,"Id")){                                  //retirar a informação do "Id"
                    value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                    id = atol((char *)value);
                    xmlFree(value);
                }
                if(!strcmp((char *)attribute->name,"TagName")){                             //retirar a informação do "TagName"
                    value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                    name = g_string_assign(name, (char *)value);
                    xmlFree(value);
                }
                attribute = attribute->next;
            }
            uma_tag = create_hashtable_tag(id, name, 0);                            //criar a tag com as informações retiradas
            g_hash_table_insert(tagsht, GSIZE_TO_POINTER(id), uma_tag);             // insere a tag na hashtable
        }
    print_element_tags(tagsht, cur_node->children);
    }
}

//Função que corre o ficheiro em xml e insere na estrutura as perguntas com os dados que nós pretendemos guardar
void print_element_namesq(GArray *calendario, xmlNode * a_node){
    xmlNode *cur_node = NULL;
    xmlChar* tipo = NULL;
    int para = 1;
    int parar = 1;
    long id_p = 0;
    int score_p = 0;
    long user_id = 0;
    int comment_count = 0;
    int numeroRespostas = 0;
    char* data;
    Date d = NULL;
    POSTS questions = NULL;
    for (cur_node = a_node; cur_node; cur_node = cur_node->next) {
        para = 1;
        parar = 1;
        if (cur_node->type == XML_ELEMENT_NODE) {
            xmlAttr* attribute = cur_node->properties;
            xmlAttr* aux = cur_node->properties;
            while (aux && aux->name && aux->children && para){
                if(!strcmp((char *)aux->name, "PostTypeId")){              //retirar a informação do "PostTypeId"
                    xmlFree(tipo);
                    para = 0;
                    tipo = xmlNodeListGetString(cur_node->doc, aux->children, 1);
                }
                aux = aux->next;
            }
            if(tipo && !strcmp((char *)tipo, "1")){                     //se o "PostTypeId" == 1 então o post é uma pergunta, logo vamos só querer tirar informção destes
                GString* tags = g_string_new(NULL);
                GString* titulo = g_string_new(NULL);
                while (aux && aux->name && aux->children && parar){
                    if(!strcmp((char *)aux->name, "CreationDate")){    //retirar a informação do "CreationDate", para saber em que ano/mes/dia devemos inserir
                        free_date(d);
                        parar = 0;
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, aux->children, 1);
                        data = (char *)value;
                        d = xmlToDate(data);
                        xmlFree(value);
                    }
                    aux = aux->next;
                }
                while (attribute && attribute->name && attribute->children) {
                    if(!strcmp((char *)attribute->name,"Id")){                                          //retirar a informação do "Id"
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        id_p = atol((char *)value);
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"Score")){                                       //retirar a informação do "Score"
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        score_p = atoi((char *)value);
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"OwnerUserId")) {                                //retirar a informação do "OwnerUserId"
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        user_id =atol((char * )value);
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"Title")){                                       //retirar a informação do "Title"
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        titulo = g_string_assign(titulo, (char *)value);
                        xmlFree(value);
                    } 
                    if(!strcmp((char *)attribute->name,"Tags")){                                        //retirar a informação das "Tags"
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        tags = g_string_assign(tags, (char *)value);
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"CommentCount")){                                //retirar a informação do "CommentCount"
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        comment_count = atoi((char *)value);
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"AnswerCount")){                                 //retirar a informação do "AnswerCount"
                       xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                       numeroRespostas = atoi((char *)value);
                       xmlFree(value);
                    }
                    attribute = attribute->next;
            }
            questions = create_hashtable_posts(id_p, score_p, user_id, titulo, comment_count, tags, numeroRespostas); //criar pergunta com as informações retiradas
            insert_hastable_questions_calendario(calendario, questions,get_year(d)-2009, get_month(d),get_day(d));    //inserir a pergunta na hashtable correspondente á data dela
            }
        }
        print_element_namesq(calendario, cur_node->children);
    }
}

//Função que corre o ficheiro em xml e insere na estrutura as respostas com os dados que nós pretendemos guardar
void print_element_namesa(GArray * calendario, xmlNode * a_node){
    xmlNode *cur_node = NULL;
    xmlChar* tipo = NULL;
    int para = 1;
    int parar = 1;
    long id_a = 0;
    int score_a = 0;
    long user_id_a = 0;
    int comment_count_a = 0;
    long parent_id = 0;
    char* data;
    Date d = NULL;
    ANSWERS respostas = NULL;
    for (cur_node = a_node; cur_node; cur_node = cur_node->next) {
        para = 1;
        parar = 1;
        if (cur_node->type == XML_ELEMENT_NODE) {
            xmlAttr* attribute = cur_node->properties;
            xmlAttr* aux = cur_node->properties;
            while (aux && aux->name && aux->children && para){
                if(!strcmp((char *)aux->name, "PostTypeId")){                           //retirar a informação do "PostTypeId"
                    xmlFree(tipo);
                    para = 0;
                    tipo = xmlNodeListGetString(cur_node->doc, aux->children, 1);
                }
                aux = aux->next;
            }
            if(tipo && !strcmp((char *)tipo, "2")){                                 //se o "PostTypeId" == 1 então o post é uma pergunta, logo vamos só querer tirar informção destes
                while (aux && aux->name && aux->children && parar){
                    if(!strcmp((char *)aux->name, "CreationDate")){                 //retirar a informação do "CreationDate", para saber em que ano/mes/dia devemos inserir
                        free_date(d);
                        parar = 0;
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, aux->children, 1);
                        data = (char *)value;
                        d = xmlToDate(data);
                        xmlFree(value);
                    }
                    aux = aux->next;
                }
                while (attribute && attribute->name && attribute->children) {
                    if(!strcmp((char *)attribute->name, "Id")){                                             //retirar a informação do "Id"
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        id_a = atol((char *)value);
                        xmlFree(value);
                    } 
                    if(!strcmp((char *)attribute->name,"Score")){                                           //retirar a informação do "Score"
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        score_a = atoi((char *)value);
                        xmlFree(value);
                    }
                    if(!strcmp((char *)attribute->name,"OwnerUserId")){                                     //retirar a informação do "OwnerUserId"
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        user_id_a = atol((char *)value);
                        xmlFree(value);
                    } 
                    if(!strcmp((char *)attribute->name,"CommentCount")){                                    //retirar a informação do "CommentCount"
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        comment_count_a = atoi((char *)value);
                        xmlFree(value);
                    } 
                    if(!strcmp((char *)attribute->name,"ParentId")) {                                       //retirar a informação do "ParentId"
                        xmlChar* value = xmlNodeListGetString(cur_node->doc, attribute->children, 1);
                        parent_id = atol((char *)value);
                        xmlFree(value);
                    }
                    attribute = attribute->next;                                            
                }
                respostas = create_hashtable_answers (id_a, score_a, user_id_a, comment_count_a, parent_id);            //criar resposta com as informações retiradas
                insert_hastable_answers_calendario(calendario, respostas,get_year(d)-2009, get_month(d),get_day(d));     //inserir a resposta na hashtable correspondente á data dela
            }
        }
        print_element_namesa(calendario, cur_node->children);
    }
}


//Função que corre a função print_element_namesa e a função print_element_namesq orientado a um documento xml, para isso é preciso passar como argumento um path 
void parse_answers(GArray * calendario, char* path){
    char* pathfile = malloc(128 * sizeof(char));
    strcpy(pathfile, path);
    xmlDoc *doc = NULL;
    xmlNode *root_element = NULL;
    strcat(pathfile, "/Posts.xml");                     //ficheiro onde vai ser corrida a função (Posts.xml)
    doc = xmlReadFile(pathfile, NULL, 0);
    //Get the root element node 
    root_element = xmlDocGetRootElement(doc);
    print_element_namesa(calendario, root_element);
    print_element_namesq(calendario, root_element);
    xmlFreeDoc(doc);
    xmlCleanupParser();
}

//Função que corre a função print_element_namesu orientado a um documento xml, para isso é preciso passar como argumento um path 
void parse_users(GHashTable *users, char* path){
    char* pathfile = malloc(128 * sizeof(char));
    strcpy(pathfile, path);
    xmlDoc *doc = NULL;
    xmlNode *root_element = NULL;
    strcat(pathfile, "/Users.xml");                     //ficheiro onde vai ser corrida a função (Users.xml)
    doc = xmlReadFile(pathfile, NULL, 0);
    //Get the root element node 
    root_element = xmlDocGetRootElement(doc);
    print_element_namesu(users, root_element); 
    xmlFreeDoc(doc);
    xmlCleanupParser();
}

//Função que corre a função print_element_tags orientado a um documento xml, para isso é preciso passar como argumento um path 
void parse_tags(GHashTable *tagsht, char* path){
    char* pathfile = malloc(128 * sizeof(char));
    strcpy(pathfile, path);
    xmlDoc *doc = NULL;
    xmlNode *root_element = NULL;
    strcat(pathfile, "/Tags.xml");                      //ficheiro onde vai ser corrida a função (Tags.xml)
    doc = xmlReadFile(pathfile, NULL, 0);
    //Get the root element node 
    root_element = xmlDocGetRootElement(doc);
    print_element_tags(tagsht, root_element); 
    xmlFreeDoc(doc);
    xmlCleanupParser();
}


//Função que dá parse á estrutura toda
void parse(TAD_community com, char* path){
    GArray * calendario = init_calendario();
    GHashTable * usersht = g_hash_table_new_full(g_direct_hash, g_direct_equal, NULL, NULL);
    GHashTable * tagsht = g_hash_table_new_full(g_direct_hash, g_direct_equal, NULL, NULL);
  
    LIBXML_TEST_VERSION 

    printf("A começar o parse...\n");
    parse_answers(calendario, path); //parse das perguntas e respostas
    parse_users(usersht, path);      //parse dos utilizadores
    parse_tags(tagsht, path);        //parse das tags 
    printf("Parse completo.\n");

    set_array_anos(com, calendario);
    set_ht_users(com, usersht);
    set_ht_tags(com, tagsht);
}

