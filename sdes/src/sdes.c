/*
 ============================================================================
 Name        : main.c
 Author      : rodolfoba
 Version     :
 Copyright   :
 Description : Simplified DES (S-DES) algorithm implementation in C
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "sdes.h"

int criptografar(int mensagem, int chave) {
	return mensagem ^ chave;
}

int descriptografar(int mensagem, int chave) {
	return mensagem ^ chave;
}
