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


Date xmlToDate(char* val);

GArray * init_calendario();

GArray * insert_hastable_answers_calendario(GArray * calendario, ANSWERS ans, int ano, int mes, int dia);

GArray * insert_hastable_questions_calendario(GArray * calendario, POSTS qq, int ano, int mes, int dia);

static void print_element_namesu(xmlNode * a_node);

static void print_element_namesq(GArray *calendario, xmlNode * a_node);

static void print_element_namesa(GArray * calendario, xmlNode * a_node);

void parse_answers(GArray * calendario, char* path);

void parse_questions(GArray * calendario, char* path);

void parse_users(GArray * calendario, char* path);

void parse(char* path);

#endif
