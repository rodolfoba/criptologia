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

int main(void) {
	puts("Simplified DES (S-DES)");
	//puts("Você deseja Criptografar(c) ou Decriptografar(d)?: ");
	//puts("Digite a mensagem de entrada (bloco binário de 8 bits): ");
	//puts("Digite a chave (binário de 10 bits): ");

	int chave = 0b1001;
	int mensagemPura = 0b1010;

	printf("Chave: %d\n", chave);
	printf("Mensagem pura: %d\n", mensagemPura);

	int mensagemCriptografada = criptografar(mensagemPura, chave);
	printf("Mensagem criptografada: %d\n", mensagemCriptografada);

	int mensagemDescriptografada = descriptografar(mensagemCriptografada, chave);
	printf("Mensagem descriptografada: %d\n", mensagemDescriptografada);

	puts("Fim.");
	return EXIT_SUCCESS;
}
