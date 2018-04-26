#ifndef __PARSER_H__ 
#define __PARSER_H__

#include "date.h"
#include <stdio.h>
#include <libxml/parser.h>
#include <libxml/tree.h>
#include <string.h>
#include <glib.h>
#include "usersHashTable.h"
#include "postHashTable.h"
#include "answersHashTable.h"
#include "anosArray.h"
#include "mesesArray.h"
#include "diaNodo.h"
#include "tad_community.h"
#include "interface.h"

Date xmlToDate(char* val);

GArray * init_calendario();

GArray * insert_hastable_answers_calendario(GArray * calendario, ANSWERS ans, int ano, int mes, int dia);

GArray * insert_hastable_questions_calendario(GArray * calendario, POSTS qq, int ano, int mes, int dia);

void print_element_namesu(GHashTable *usersht, xmlNode * a_node);

void print_element_namesq(GArray *calendario, xmlNode * a_node);

void print_element_namesa(GArray * calendario, xmlNode * a_node);

void parse_answers(GArray * calendario, char* path);

void parse_questions(GArray * calendario, char* path);

void parse_users(GHashTable * usersht, char* path);

void parse(TAD_community com, char* path);

#endif
